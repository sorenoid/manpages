import com.bandor.model.Manual
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.jar.JarFile
import java.util.stream.Collectors

fun String.guts(startTag: String, endTag: String): String =
    replaceAfterLast(endTag, "")
        .replaceBefore(startTag, "")
        .replace(endTag, "")
        .replace(startTag, "")

object Html2Json {
    val manuals = mutableListOf<Manual>()

    init {
        // for running in an executable jar
        var jarFile = File(javaClass.protectionDomain.codeSource.location.path)
        if (jarFile.isFile) {
            val jar = JarFile(jarFile)
            val entries = jar.entries()
            while (entries.hasMoreElements()) {
                val name = entries.nextElement().name
                if (name.startsWith("manual-html/1")) {
                    val inputStream = javaClass.classLoader.getResourceAsStream(name)
                    val contents = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"))
                    val head = contents.guts("<head>", "</head>").lowercase()
                    val body = contents.guts("<body>", "</body>")

                    val title = head.guts("<title>", "</title>")
                    // println("body: $body")
                    manuals.add(Manual(title, body))

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