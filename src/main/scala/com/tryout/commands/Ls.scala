package com.tryout.commands
import com.tryout.files.DirEntry
import com.tryout.filesystem.State

class Ls extends Command {

  def createNiceOutput(contents: List[DirEntry]): String =
    if (contents.isEmpty) ""
    else {
      val head = contents.head
      head.name + "[" + head.getType + "]" + "\n" +createNiceOutput(contents.tail)
    }

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val output = createNiceOutput(contents)
    state.setMessage(output)
  }
}
