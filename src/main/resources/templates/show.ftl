<#-- @ftlvariable name="manual" type="com.bandor.models.Manual" -->
<#import "_layout.ftl" as layout />

<@layout.head level=manual.level name=manual.name />
<@layout.body>
    <div>
        ${manual.body}
    </div>
</@layout.body>