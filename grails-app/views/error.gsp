<!doctype html>
<html>
<head>
    <title>Error Page</title>
    <meta name="layout" content="main">
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css">--}%
</head>

<body>

<div class="contest">
    <div class="headingContent">
        <div align="center" class="teaser-divider">
            <div class="dialog well">
                <h1 style="color: #0000ff;"><g:message code="sorry.we.could.not.complete.your.request"
                               default="SORRY, WE COULD NOT COMPLETE YOUR REQUEST"/>
                </h1>

                <h2>Looks like there is some problem serving your request. This can be because of various reasons such as:</h2>

                <div style="padding-left:30px">
                    <h4># You are trying to access a page which does not exist.</h4>
                    <h4># You do-not have necessary rights to access the page you are trying to reach.</h4>
                    <h4># We are experiencing some problem at our server side.</h4>
                    <br/>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
div h4, div.dialog h2 {
    text-align: left;
    display: block;
}

.dialog {
    color: #fff;
}

</style>
</body>
</html>