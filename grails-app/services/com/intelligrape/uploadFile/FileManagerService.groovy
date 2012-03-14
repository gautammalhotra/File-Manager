package com.intelligrape.uploadFile

import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.security.MessageDigest

class FileManagerService {

    public FileManager processFile(CommonsMultipartFile commonsMultipartFile) {
        FileManager fileManager = null
        try {
            String fileName = commonsMultipartFile.getOriginalFilename()
            String contentType = commonsMultipartFile.getContentType()
            byte[] fileBytes = commonsMultipartFile.getBytes()
            fileManager = FileManager.createFile(fileName, contentType, fileBytes)
        } catch (Throwable throwable) {
            log.error throwable.message
            throwable.printStackTrace()
        }
        return fileManager
    }

    public byte[] getFileBytes(FileManager fileManager) {
        byte[] fileBytes = null
        try {
            File storedFile = new File(fileManager.completeFilePath)
            if (storedFile.exists()) {
                fileBytes = storedFile.bytes
            } else {
                log.error "File not found for file manager id : ${fileManager}"
            }
        } catch (Throwable throwable) {
            println throwable.message
            throwable.printStackTrace()
        }
        return fileBytes
    }

    public Boolean deleteFile(FileManager fileManager) {
        Boolean success = false
        try {
            File uploadedFile = new File(fileManager.completeFolderPath)
            if (uploadedFile.isDirectory()) {
                uploadedFile.deleteDir()
            } else {
                log.error "No file exists during delete operation for file manager: ${fileManager}"
            }
            fileManager.delete(flush: true)
            success = true
        } catch (Throwable throwable) {
            log.error "Exception occurred while deleting file manager:${fileManager}"
            println throwable.message
            throwable.printStackTrace()
        }
        return success
    }

    def generateMD5(File file) {
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
}