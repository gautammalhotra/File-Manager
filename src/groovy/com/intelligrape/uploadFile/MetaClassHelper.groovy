package com.intelligrape.uploadFile


class MetaClassHelper {
     static void enrichClasses() {
        injectSaveMethod()
    }

    static void injectSaveMethod() {

        Object.metaClass.s = {
            def o = delegate.save(flush: true, failOnError: true)
            if (!o) {
                delegate.errors.allErrors.each {
                    log.error it
                }
            }
            return o
        }
    }
}
