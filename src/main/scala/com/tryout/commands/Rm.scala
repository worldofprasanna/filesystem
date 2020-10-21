package com.tryout.commands
import com.tryout.files.Directory
import com.tryout.filesystem.State

class Rm(name: String) extends Command {

  def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
    else {
      val nextDirectory = currentDirectory.findEntry(path.head)
      if (!nextDirectory.isDirectory) currentDirectory
      else {
        val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
        if (newNextDirectory == nextDirectory) currentDirectory
        else currentDirectory.replaceEntry(path.head, newNextDirectory)
      }
    }
  }

  def doRm(state: State, path: String): State = {
    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)

    if (newRoot == state.root)
      state.setMessage(path + ": no such directory")
    else
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
 }

  override def apply(state: State): State = {
    val wd = state.wd
    val absolutePath = {
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name
    }

    if (Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("Not Supported to delete root")
    else
      doRm(state, absolutePath)
  }
}