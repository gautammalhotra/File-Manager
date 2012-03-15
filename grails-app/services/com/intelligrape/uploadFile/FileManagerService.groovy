package com.intelligrape.uploadFile

import org.springframework.web.multipart.commons.CommonsMultipartFile

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

    public String isFileAlreadyExists(CommonsMultipartFile commonsMultipartFile) {
        String message = ""
        File tempFolderLocation = null
        try {
            String tempFolderStr = Util.generateRandomFolderInTemp()
            tempFolderLocation = new File(tempFolderStr)
            File uploadedFile = new File("${tempFolderStr}/${commonsMultipartFile.getOriginalFilename()}")
            uploadedFile.bytes = commonsMultipartFile.bytes
            FileManager fileManager = FileManager.findByMd5Sum(FileManager.generateMD5(uploadedFile))
            if (fileManager) {
                message = "File already exists with same md5sum: ${fileManager.md5Sum} ${(fileManager.name == commonsMultipartFile.getOriginalFilename()) ? 'and same' : 'but with different'} name: ${fileManager.name}"
            }
        } catch (Throwable throwable) {
            log.error throwable.message
            throwable.printStackTrace()
        }
        finally {
            if (tempFolderLocation.exists()) {
                tempFolderLocation.deleteDir()
            }
        }
        return message
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
            File uploadedFile = new File(fileManager.completeFilePath)
            if (uploadedFile.exists()) {
                uploadedFile.delete()
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
}