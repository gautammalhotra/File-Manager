import com.intelligrape.uploadFile.MetaClassHelper

class BootStrap {

    def init = { servletContext ->
        MetaClassHelper.enrichClasses()
    }
    def destroy = {
    }
}
