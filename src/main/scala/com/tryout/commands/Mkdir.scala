package com.tryout.commands

import com.tryout.files.{DirEntry, Directory}
import com.tryout.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path, name)
}
