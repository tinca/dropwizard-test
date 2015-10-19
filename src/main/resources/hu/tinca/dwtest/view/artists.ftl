<#-- @ftlvariable name="" type="hu.tinca.dwtest.view.ArtistsView" -->
<html>
    <body>
        <h1>Similar artists</h1>
        <#list artists as artist>
            ${artist.name?html} <BR>
        </#list>
    </body>
</html>