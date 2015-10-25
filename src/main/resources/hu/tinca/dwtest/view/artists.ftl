<#-- @ftlvariable artists="" type="hu.tinca.dwtest.view.ArtistsView" -->
<html>
    <body>
        <h1>Similar artists</h1>
        <#list artists as name>
            ${name?html} <BR>
        </#list>
    </body>
</html>