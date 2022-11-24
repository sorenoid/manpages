<#-- @ftlvariable name="articles" type="kotlin.collections.List<com.bandor.models.Manual>" -->
<#import "_layout.ftl" as layout />
<@layout.header>
<h1>Linux Manual Pages </h1>
<hr>
<script async src="https://cse.google.com/cse.js?cx=83a0261b6bc34463a">
</script>
<div class="gcse-search"></div>
<#list manuals?reverse as manual>
<div>
    <h3>
        <a href="/${manual.level}/${manual.name}">${manual.name}</a>
    </h3>
</div>
</#list>
<hr>
</@layout.header>