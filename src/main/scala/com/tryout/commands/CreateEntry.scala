package com.tryout.commands

import com.tryout.files.{DirEntry, Directory}
import com.tryout.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  def checkValidName(name: String): Boolean = name.contains(".")

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    if (path.isEmpty) currentDirectory.addEntry(newEntry)
    else {
      val oldEntry = currentDirectory.findEntry(path.head)
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail, newEntry))
    }
  }

  def doCreateEntry(state: State, name: String): State = {
    val wd = state.wd

    val allDirsInPath = wd.getAllFoldersInPath
//    val newDir = Directory.empty(wd.path, name)
    val newEntry: DirEntry = createSpecificEntry(state)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage(s"Entry ${name} already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"${name} must not contain separator /")
    } else if (checkValidName(name)) {
      state.setMessage(s"${name} is not valid one")
    } else {
      doCreateEntry(state, name)
    }
  }

  def createSpecificEntry(state: State): DirEntry
}
