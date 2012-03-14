package com.intelligrape.uploadFile

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Util {

    static String getRootFilePath() {
        String path = ConfigurationHolder.config.fileManagerRootFilePath
        if (path.isEmpty()) {
            path = tempDir
        }
        return path
    }

    static String getTempDir() {
        return System.getProperty('java.io.tmpdir')
    }
}
