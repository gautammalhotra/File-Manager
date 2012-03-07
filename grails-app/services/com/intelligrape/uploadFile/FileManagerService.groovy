package com.intelligrape.uploadFile

import org.springframework.web.multipart.commons.CommonsMultipartFile

class FileManagerService {

    public Boolean processFile(CommonsMultipartFile commonsMultipartFile) {
        Boolean success = false
        try {
            String fileName = commonsMultipartFile.getOriginalFilename()
            String contentType = commonsMultipartFile.getContentType()
            byte[] content = commonsMultipartFile.getBytes()
            FileManager fileManager = FileManager.createFile(fileName, contentType, content)
            success = true
        } catch (Throwable throwable) {
            log.error throwable.message
            throwable.printStackTrace()
        }
        return success
    }
}
