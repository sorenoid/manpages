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
<nav class="footer"><i>Powered by <a href="https://ktor.io">Ktor</a></i> | <i><a href="https://github.com/sorenoid/manpages/tree/main">Source for this site</a></i> | <i><a href="#top">Back to top</a></i></nav>
<script>
    // open/close toc content on header click
    var asideheaders = document.querySelectorAll('aside nav > h3');
    Array.prototype.forEach.call(asideheaders, function(el, index, array){
        el.addEventListener('click', function (e) {
            // avoid having to use computed style. http://stackoverflow.com/a/21696585/172068
            if(this.nextElementSibling.offsetParent === null){
                this.nextElementSibling.style.display = 'block';
            } else {
                this.nextElementSibling.style.display = 'none';
            }
        });
    });
</script>
</body>
</html>
