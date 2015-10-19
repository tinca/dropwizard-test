<#-- @ftlvariable name="" type="hu.tinca.dwtest.view.ArtistView" -->
<html>
    <body>
        <h1>Artist information</h1>
        <B>Name:</B> ${artist.name?html}
        <BR>
        <B>Biography:</B>
         <BR>
        Published:${artist.bio.published?html}
        <BR>
        <I>${artist.bio.summary?html}</I>
        <P>
        ${artist.bio.content?html}
    </body>
</html>