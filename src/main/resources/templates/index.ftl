<#-- @ftlvariable name="articles" type="kotlin.collections.List<com.bandor.models.Manual>" -->
<#import "_layout.ftl" as layout />
<@layout.head/>
<@layout.body>
<#list manuals?reverse as manual>
<div>
    <h3>
        <a href="/${manual.level}/${manual.name}">${manual.name}</a>
    </h3>
</div>
</#list>
<hr>
</@layout.body>