package com.intelligrape.uploadFile

import org.springframework.web.multipart.commons.CommonsMultipartFile

class FileManagerController {
    def fileManagerService

    static scaffold = true

    def uploadFile() {
        CommonsMultipartFile commonsMultipartFile = params.upload
        String message = commonsMultipartFile ? fileManagerService.isFileAlreadyExists(commonsMultipartFile) : "No file has been uploaded"
        if (!message) {
            FileManager fileManager = fileManagerService.processFile(commonsMultipartFile)
            message = FileManager.generateMD5(new File(fileManager.completeFilePath))
        }
        render message
        render "\n"
    }

    def renderFile() {
        FileManager fileManager = (params.id && params.id.toString().isLong()) ? FileManager.findById(params.long('id')) : null
        if (fileManager) {
            try {
                response.setHeader("Content-disposition", "attachment; filename=${fileManager.name}")
                response.setContentType(fileManager.contentType)
                byte[] data = fileManagerService.getFileBytes(fileManager)
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

    def delete() {
        FileManager fileManager = (params.id && params.id.toString().isLong()) ? FileManager.findById(params.long('id')) : null
        if (fileManager) {
            if (fileManagerService.deleteFile(fileManager)) {
                flash.message = "File manager with id: ${fileManager.id} has been deleted successfully"
            } else {
                flash.message = "File manager cannot be deleted successfully"
            }
        } else {
            flash.message = "No file manager found with id: ${params.id}"
        }
        redirect(controller: 'fileManager', action: 'list')
    }
}
