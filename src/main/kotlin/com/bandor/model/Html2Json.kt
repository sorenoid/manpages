import com.bandor.model.Manual

fun String.guts(startTag: String, endTag: String): String =
    replaceAfterLast(endTag, "")
        .replaceBefore(startTag, "")
        .replace(endTag, "")
        .replace(startTag, "")

object Html2Json {
    val manuals = mutableListOf<Manual>()

    init {
        val apropos = this::class.java.classLoader.getResource("manual-html/1/apropos.html")!!.readText()
        val head = apropos.guts("<head>", "</head>")
        val body = apropos.guts("<body>", "</body>")

        val title = head.guts("<title>", "</title>")
        // println("body: $body")
        manuals.add(Manual(title, body))
        val yes = this::class.java.classLoader.getResource("manual-html/1/yes.html")!!.readText()
        val head1 = yes.guts("<head>", "</head>")
        val body1 = yes.guts("<body>", "</body>")

        val title1 = head1.guts("<title>", "</title>")
        // println("body: $body")
        manuals.add(Manual(title1, body1))

    }
}