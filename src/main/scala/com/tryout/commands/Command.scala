package com.tryout.commands

import com.tryout.filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {
  val MKDIR = "mkdir"
  val LS = "ls"

  def emptyCommand: Command = (state: State) => state.setMessage("")
  def incompleteCommand(name: String): Command = (state: State) => state.setMessage(s"$name is incomplete. Please give all arguments")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    if (input.trim.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) {
        if (tokens.length < 2) incompleteCommand(MKDIR)
        else new Mkdir(tokens(1))
      }
    else if (LS.equals(tokens(0)))  {
      new Ls
    }
    else new UnknownCommand
  }
}