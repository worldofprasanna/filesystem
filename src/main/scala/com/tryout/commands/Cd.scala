package com.tryout.commands
import java.nio.file.Path

import com.tryout.files.{DirEntry, Directory}
import com.tryout.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    findEntryHelper(root, tokens)
  }

  override def apply(state: State): State = {
    val root = state.root
    val wd = state.wd

    val absolutePath = {
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir
    }

    val destinationDirectory = doFindEntry(root, absolutePath)
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + ": no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }
}
