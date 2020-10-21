package com.tryout.files


class File(override val parentPath: String,
          override val name: String,
          contents: String) extends DirEntry(parentPath, name) {
  override def asDirectory: Directory =
    throw new FileSystemException("File cannot be converted to a directory")

  override def getType: String = "File"

  override def asFile: File = this

  override def isDirectory: Boolean = true
  override def isFile: Boolean = false
}

object File {
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}
