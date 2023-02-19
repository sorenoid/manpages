<#macro head level = -1 name = "bar">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#if level!=-1 && name !="bar">
    <title>${name}(${level}) man page</title>
    <#else>
    <title>Linux Manpages Online - man.page</title>
    </#if>
    <meta name="robots" content="index,follow"/>
    <link rel="stylesheet" type="text/css" href="/___/css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="/___/css/style.css"/>

    <script language="JavaScript" type="text/javascript">
        function wysiwygUrl(base, tform) {
            window.location.href = base + tform.elements.page.value;
            return false;
        }
    </script>
</head>
</#macro>
<#macro header level=-1 name="bar">
<header>
    <div class="ad_header">
        <!-- adsense -->
        <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-1076242142955733"
             crossorigin="anonymous"></script>
    </div>
    <div class="header">
    <#if level!=-1 && name !="bar">
    <h1><a name="top" href="/">Linux Manual Pages</a></h1><nav> | <a href="/${level}">${level}</a> | <a href="/${level}/${name}">${name}</a> </nav>
    <#else>
    <h1><a name="top" href="/">Linux Manual Pages</a></h1>
    </#if>
    <form method="get" accept-charset="utf-8" action="/"
          onsubmit="return wysiwygUrl('/',this)" autofocus>
        <label for="lookup">Search Manpages:</label>
        <input name="page" id="lookup" type="text"/>
        <input type="submit" id="lookup_button" value="go" name="do[go]"/>
        <!-- input type="submit" value="search" name="do[search]" /-->
    </form>
    </div>

</header>
</#macro>
<#macro body>
    <#nested>
</#macro>
<#macro aside headings>
<aside>
<nav>
    <h3><svg viewBox="3 3 21 21"><path d="M3,6H21V8H3V6M3,11H21V13H3V11M3,16H21V18H3V16Z" /></svg>
        Contents
    </h3>
    <ul>
    <#list headings as heading>
        <li>${heading}</li>
    </#list>
    </ul>
</nav>
</aside>

</#macro>
