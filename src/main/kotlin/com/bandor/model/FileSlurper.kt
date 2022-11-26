import com.bandor.model.Manual
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.jar.JarFile
import java.util.regex.Pattern
import java.util.stream.Collectors

fun String.guts(startTag: String, endTag: String): String =
    replaceAfterLast(endTag, "")
        .replaceBefore(startTag, "")
        .replace(endTag, "")
        .replace(startTag, "")

object FileSlurper {
    val manuals = mutableListOf<Manual>()

    init {
        // for running in an executable jar
        val jarFile = File(javaClass.protectionDomain.codeSource.location.path)
        if (jarFile.isFile) {
            val jar = JarFile(jarFile)
            val entries = jar.entries()
            val headingPattern = Pattern.compile("(<a href=\"#.+\">.+</a><br>)")
            while (entries.hasMoreElements()) {
                val headings = mutableListOf<String>()
                val name = entries.nextElement().name
                if (name.startsWith("manual-html/1")) {
                    val inputStream = javaClass.classLoader.getResourceAsStream(name)
                    if (inputStream != null) {
                        val contents = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"))
                        val head = contents.guts("<head>", "</head>").lowercase()
                        var body = contents.guts("<body>", "</body>")

                        val title = head.guts("<title>", "</title>")
                        val matcher = headingPattern.matcher(body)
                        while (matcher.find()) {
                            val str = matcher.group().removeSuffix("<br>")

                            headings.add(str)
                        }
                        headings.forEach {
                            body = body.replaceFirst(it + "<br>", "")
                        }
                        // println("body: $body")
                        manuals.add(Manual(title, body, headings))
                    }
                }
            }
        }

        // for running from the command line (without jar...)

//        val manOne = this::class.java.classLoader.getResource("manual-html/1")
//        val manOneDir = File(manOne.path)
//        manOneDir.listFiles().forEach {
//            val contents = this::class.java.classLoader.getResource("manual-html/1/${it.name}")!!.readText()
//            val head = contents.guts("<head>", "</head>").lowercase()
//            val body = contents.guts("<body>", "</body>")
//
//            val title = head.guts("<title>", "</title>")
//            // println("body: $body")
//            manuals.add(Manual(title, body))
//        }

        manuals.sortByDescending { it.name }
    }
}