<#-- @ftlvariable name="manual" type="com.bandor.models.Manual"-->
<#import "_layout.ftl" as layout />
<!DOCTYPE html>
<html lang="en">
<@layout.head/>
<body>
<@layout.header level=manual.level name=manual.name />
<@layout.body>
    <div class="main">
    <main>
        ${manual.body}
    </main>

</@layout.body>
<@layout.aside headings=manual.headings/>
</div>
<p><i>Powered by <a href="https://ktor.io">Ktor</a></i> | <a href="#top">Back to top</a></p>
</body>
</html>
