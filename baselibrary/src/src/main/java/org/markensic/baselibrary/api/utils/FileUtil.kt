package org.markensic.baselibrary.api.utils

import java.io.File

object FileUtil {
    fun createFile(path:String): File {
        return File(path).also {
            if (!it.exists()) {
                val dirPath = path.substring(0, path.lastIndexOf("/"))
                createFileDir(dirPath)

                if (path != dirPath) {
                    it.createNewFile()
                }
            }
        }
    }

    fun iterateFileInDir(path: String, handler: (f: File) -> Unit) {
        File(path).also {
            if (it.exists() && it.isDirectory) {
                it.walk().iterator().forEach { file ->
                    handler(file)
                }
            }
        }
    }

    fun createFileDir(path:String): Boolean {
        return File(path).mkdirs()
    }

    fun deleteFile(path:String): Boolean {
        return File(path).let {
            if (it.exists()) {
                it.deleteRecursively()
            } else {
                true
            }
        }
    }

    fun appendToFile(path: String, text: String) {
        createFile(path).also {
            it.appendText(text)
        }

    }

    fun writeToFile(path: String, text: String) {
        createFile(path).also {
            it.writeText(String())
        }
    }
}