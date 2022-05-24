package jboster.poshe.one.utils

import android.text.TextUtils
import java.io.*

fun readFile(filePath: String, charsetName: String): StringBuilder? {
    val file = File(filePath)
    val fileContent = StringBuilder("")
    if (file == null || !file.isFile) {
        return null
    }
    var reader: BufferedReader? = null
    var inputStreamReader: InputStreamReader? = null
    return try {
        inputStreamReader = InputStreamReader(
            FileInputStream(file), charsetName
        )
        reader = BufferedReader(inputStreamReader)
        var line: String? = null
        while (reader.readLine().also { line = it } != null) {
            if (fileContent.toString() != "") {
                fileContent.append("\r\n")
            }
            fileContent.append(line)
        }
        fileContent
    } catch (e: IOException) {
        throw RuntimeException("IOException occurred. ", e)
    } finally {
        inputStreamReader?.close()
        reader?.close()
    }
}

fun writeFile(filePath: String, content: String?, append: Boolean): Boolean {
    if (TextUtils.isEmpty(content)) {
        return false
    }
    var fileWriter: FileWriter? = null
    return try {
        makeDirs(filePath)
        fileWriter = FileWriter(filePath, append)
        fileWriter.write(content)
        true
    } catch (e: IOException) {
        throw java.lang.RuntimeException("IOException occurred. ", e)
    } finally {
        fileWriter?.close()
    }
}

fun makeDirs(filePath: String): Boolean {
    val folderName: String = getFolderName(filePath)
    if (TextUtils.isEmpty(folderName)) {
        return false
    }
    val folder = File(folderName)
    return if (folder.exists() && folder.isDirectory) true else folder.mkdirs()
}

fun getFolderName(filePath: String): String {
    if (TextUtils.isEmpty(filePath)) {
        return filePath
    }
    val filePosi = filePath.lastIndexOf(File.separator)
    return if (filePosi == -1) "" else filePath.substring(0, filePosi)
}