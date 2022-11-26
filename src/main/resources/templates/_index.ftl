<#macro body>
<aside>
<nav>
    <h3><svg viewBox="3 3 21 21"><path d="M3,6H21V8H3V6M3,11H21V13H3V11M3,16H21V18H3V16Z" /></svg>
        Manual Levels
    </h3>
    <ul>
        <li><a href="/1">Level 1</a>
        <li><a href="/2">Level 2</a>
        <li><a href="/3">Level 3</a>
        <li><a href="/4">Level 4</a>
        <li><a href="/5">Level 5</a>
        <li><a href="/6">Level 6</a>
        <li><a href="/7">Level 7</a>
    </ul>
</nav>
</aside>
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
</#macro>