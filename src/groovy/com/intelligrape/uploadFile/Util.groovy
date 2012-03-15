package com.intelligrape.uploadFile

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Util {

    static String getRootFilePath() {
        String path = ConfigurationHolder.config.fileManagerRootFilePath
        if (path.isEmpty()) {
            path = tempDir
        }
        createDirectoryIfNotExist(path)
        return path
    }

    static String getTempDir() {
        return System.getProperty('java.io.tmpdir')
    }

    static String createDirectoryIfNotExist(String directoryPath) {
        File directory = new File(directoryPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directoryPath
    }

    static String generateRandomFolderInTemp() {
        String path = "${tempDir}/${UUID.randomUUID().toString()}"
        return createDirectoryIfNotExist(path)
    }
}
