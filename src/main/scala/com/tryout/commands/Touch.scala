package com.tryout.commands
import com.tryout.files.{DirEntry, File}
import com.tryout.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)
}
