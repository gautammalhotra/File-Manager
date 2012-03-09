package com.intelligrape.uploadFile

class FileManager {

    String fileName
    String contentType
    byte[] content
    Date dateCreated
    Date lastUpdated

    static mapping = {
        content sqlType: 'longblob'
    }

    static constraints = {
        fileName blank: false
        contentType blank: false
        content blank: false
    }

    FileManager(String fileName, String contentType, byte[] content) {
        this.fileName = fileName
        this.contentType = contentType
        this.content = content
    }

    static FileManager createFile(String fileName, String contentType, byte[] content) {
        FileManager fileManager = null
        try {
            fileManager = new FileManager(fileName, contentType, content).s()
        } catch (Throwable throwable) {
            fileManager = null
            println throwable.message
            throwable.printStackTrace()
        }
        return fileManager
    }
}
