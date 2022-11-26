<#macro head>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Linux Manpages Online - man.page</title>
    <meta name="robots" content="index,follow"/>
    <link rel="stylesheet" type="text/css" href="___/css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="___/css/style.css"/>

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
        <ins class="adsbygoogle"
             data-ad-client="ca-pub-1076242142955733"
             crossorigin="anonymous"
             data-ad-format="auto"></ins>
    </div>
    <#if level!=-1 && name !="bar">
    <h1><a name="top" href="/">Linux Manual Pages</a></h1><nav> | <a href="/${level}">${level}</a> | <a href="/${name}">${name}</a> </nav>
    <#else>
    <h1><a name="top" href="/">Linux Manual Pages</a></h1>
    </#if>
    <form method="get" accept-charset="utf-8" action="/"
          onsubmit="return wysiwygUrl('/',this)">
        <label for="lookup">Search Manpages:</label>
        <input name="page" id="lookup" type="text"/>
        <input type="submit" value="go" name="do[go]"/>
        <!-- input type="submit" value="search" name="do[search]" /-->
    </form>

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
