package com.intelligrape.uploadFile

import java.security.MessageDigest

class FileManager {
    String name
    String contentType
    String md5Sum
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name blank: false
        md5Sum blank: false, nullable: true
        contentType blank: false
    }

    FileManager(String fileName, String contentType) {
        this.name = fileName
        this.contentType = contentType
    }

    static FileManager createFile(String fileName, String contentType, byte[] fileByte) {
        FileManager fileManager = null
        try {
            fileManager = new FileManager(fileName, contentType)
            saveFileToFileSystem(fileManager, fileByte)
            fileManager = fileManager.s()
        } catch (Throwable throwable) {
            fileManager = null
            println throwable.message
            throwable.printStackTrace()
        }
        return fileManager
    }

    static void saveFileToFileSystem(FileManager fileManager, byte[] fileByte) {
        File uploadedFile = new File(fileManager.completeFilePath)
        uploadedFile.bytes = fileByte
        fileManager.insertMd5SumAndSaveFileManager(generateMD5(uploadedFile))
    }

    static String generateMD5(File file) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        file.withInputStream() {is ->
            byte[] buffer = new byte[8192]
            int read = 0
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
        }
        byte[] md5sum = digest.digest()
        BigInteger bigInt = new BigInteger(1, md5sum)
        return bigInt.toString(16)
    }


    String getCompleteFilePath() {
        return "${completeFolderPath}/${this.name}"
    }

    String getCompleteFolderPath() {
        return Util.rootFilePath
    }

    public FileManager insertMd5SumAndSaveFileManager(String md5Sum) {
        this.md5Sum = md5Sum
        return this.s()
    }

    String toString() {
        return "File name: ${name}, File content type: ${contentType}, File created on: ${dateCreated}"
    }
}
