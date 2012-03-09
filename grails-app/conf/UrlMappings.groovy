class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "fileManager", action: "list")
        "/uploadFile"(controller: "fileManager", action: "uploadFile")
        "/fileManager/create"(controller: "fileManager", action: "list")
        "/fileManager/edit/$id"(controller: "fileManager", action: "list")
        "500"(view: '/error')
        "404"(view: '/error')
        "/openCon"(controller: 'console', action: 'index')
    }
}
