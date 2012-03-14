package com.intelligrape.uploadFile

class FileManager {
    String name
    String contentType
    String path
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name blank: false
        path blank: false
        contentType blank: false
    }

    FileManager(String fileName, String contentType) {
        this.name = fileName
        this.contentType = contentType
        this.path = "${FileManagerConstant.FILE_MANAGER_FOLDER_NAME}/${UUID.randomUUID().toString()}"
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
    }

    String getCompleteFilePath() {
        return "${completeFolderPath}/${this.name}"
    }

    String getCompleteFolderPath() {
        return createDirectoryIfNotExist("${Util.rootFilePath}/${this.path}")
    }

    public String createDirectoryIfNotExist(String directoryPath) {
        File directory = new File(directoryPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directoryPath
    }

    String toString() {
        return "File name: ${name}, File content type: ${contentType}, File path: ${path}, File created on: ${dateCreated}"
    }
}
