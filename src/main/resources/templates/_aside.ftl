<#-- @ftlvariable name="headings" type="String">" -->
<aside>
<nav>
    <h3><svg viewBox="3 3 21 21"><path d="M3,6H21V8H3V6M3,11H21V13H3V11M3,16H21V18H3V16Z" /></svg>
        Contents
    </h3>
    <ul>
    <#list headings? as heading>
        <li><a href="#${heading}">${heading}</a></li>
    </#list>
    </ul>
</nav>
</aside>