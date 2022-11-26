<#-- @ftlvariable name="articles" type="kotlin.collections.List<com.bandor.models.Manual>" -->
<#import "_layout.ftl" as layout />
<!DOCTYPE html>
<html lang="en">
<@layout.head/>
<body>
<@layout.header/>
<@layout.body>
<#list manuals?reverse as manual>
<div>
    <h3>
        <a href="/${manual.name}">${manual.name}</a>
    </h3>
</div>
</#list>
<hr>
</@layout.body>
<p><i>Powered by Ktor</i></p>
</body>
</html>
