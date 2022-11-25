<#macro head level=-1 name="bar">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Linux Manpages Online - man.page</title>
    <meta name="robots" content="index,follow"/>
    <link rel="stylesheet" type="text/css" href="css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>

    <script language="JavaScript" type="text/javascript">
        function wysiwygUrl(base, tform) {
            window.location.href = base + tform.elements.page.value;
            return false;
        }
    </script>
</head>

<header>
    <div class="ad_header">
        <!-- adsense -->
        <ins class="adsbygoogle"
             style="display:block"
             data-ad-client="ca-pub-1076242142955733"
             crossorigin="anonymous"
             data-ad-format="auto"></ins>
    </div>
    <h1><a name="top" href="/">Manpages</a></h1>
    <form method="get" accept-charset="utf-8" action="/"
          onsubmit="return wysiwygUrl('/',this)">
        <label for="lookup">Manpage:</label>
        <input name="page" id="lookup" type="text"/>
        <input type="submit" value="go" name="do[go]"/>
        <!-- input type="submit" value="search" name="do[search]" /-->
    </form>
    <#if level!=-1 && name!="bar">
        <a href="/">Linux Manual Pages</> | <a href="/${level}">${level}</a> | <a href="/${level}/${name}">${name}</a>
    </#if>
</header>
</#macro>
<#macro body>
<body>
    <#nested>
    <p><i>Powered by Ktor</i></p>
    <a href="/">Back to the main page</a>
</body>
</html>
</#macro>