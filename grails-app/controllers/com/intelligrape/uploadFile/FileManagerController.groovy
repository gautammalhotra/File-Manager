package com.intelligrape.uploadFile

import org.springframework.web.multipart.commons.CommonsMultipartFile

class FileManagerController {
    def fileManagerService

    static scaffold = true

    def uploadFile() {
        CommonsMultipartFile commonsMultipartFile = params.upload
        Boolean success = false
        if (commonsMultipartFile) {
            success = fileManagerService.processFile(commonsMultipartFile)
        }
        render "${success}"
    }

    def renderFile() {
        FileManager fileManager = (params.id && params.id.toString().isLong()) ? FileManager.findById(params.long('id')) : null
        if (fileManager) {
            try {
                response.setHeader("Content-disposition", "attachment; filename=${fileManager.fileName}")
                response.setContentType(fileManager.contentType)
                byte[] data = fileManager.content
                response.setContentLength(data.size().toInteger());
                OutputStream out = response.getOutputStream();
                out.write(data);
                out.flush();
                out.close();
            } catch (Throwable throwable) {
                log.error throwable.message
                throwable.printStackTrace()
            }
        } else {
            render ''
        }
    }
}
