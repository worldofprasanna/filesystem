package com.tryout.commands

import com.tryout.filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {
  def from(input: String): Command =
    new UnknownCommand
}
