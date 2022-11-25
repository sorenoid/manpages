<#-- @ftlvariable name="manual" type="com.bandor.models.Manual" -->
<#import "_layout.ftl" as layout />
<@layout.header>
   <a href="/">Linux Manual Pages</> | <a href="/${manual.level}">${manual.level}</a> | <a href="/${manual.level}/${manual.name}">${manual.name}</a>
    <hr>
    <script async src="https://cse.google.com/cse.js?cx=83a0261b6bc34463a">
    </script>
    <div class="gcse-search"></div>
    <div>
        ${manual.body}
        <hr>
    </div>
</@layout.header>