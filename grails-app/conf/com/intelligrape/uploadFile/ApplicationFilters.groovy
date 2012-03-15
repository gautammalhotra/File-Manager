package com.intelligrape.uploadFile

class ApplicationFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                log.info("GrailsAccessLog:${params}");
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }

        paramsTrim(controller: '*', action: '*') {
            before = {
                params?.each {String key, value ->
                    try {
                        if (value?.getClass() == String) {
                            params[key] = value?.trim()
                        }
                    } catch (Throwable t) {
                        t.printStackTrace()
                    }
                }
            }
        }

    }
}
