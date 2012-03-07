package com.intelligrape.uploadFile

import javax.servlet.http.HttpServletRequest

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
                            params[key] = value.trim()
                        }
                    } catch (Throwable t) {
                        t.printStackTrace()
                    }
                }
            }
        }

        consoleImports(controller: 'console', action: '*') {
            before = {
                if (isValidUser(request)) {
                    String importStatements = """import com.intelligrape.uploadFile.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder   """
                    session['_grails_console_last_code_'] = session['_grails_console_last_code_'] ?: importStatements
                } else {
                    flash.errorMessage = "Sorry, you're not authorized to view this page."
                    redirect(controller: 'register', action: 'index')
                }
            }
        }
    }

    private Boolean isValidUser(HttpServletRequest request) {
        Boolean isValid = false
        String requesterIpAddress = getRequesterIP(request)
        if (User.isSiteAdminLoggedIn() || requesterIpAddress in ConfigurationHolder.config.fyt.console.ips || GrailsUtil.environment == "development") {
            isValid = true
        }
        return isValid
    }
}
