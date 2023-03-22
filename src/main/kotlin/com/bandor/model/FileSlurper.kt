import com.bandor.model.Manual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val manuals = mutableSetOf<Manual>()

    private val headingPattern = Pattern.compile("(<a href=\"#.+\">.+</a><br>)")

    init {
        // for running in an executable jar
        val jarFile = File(javaClass.protectionDomain.codeSource.location.path)
        if (jarFile.isFile) {
            val jar = JarFile(jarFile)
            val entries = jar.entries()
            while (entries.hasMoreElements()) {
                val name = entries.nextElement().name
                if (name.startsWith("manual-html/1")) {
                    slurp(name, 1)
                } else if (name.startsWith("manual-html/2")) {
                    slurp(name, 2)
                } else if (name.startsWith("manual-html/3")) {
                    slurp(name, 3)
                } else if (name.startsWith("manual-html/4")) {
                    slurp(name, 4)
                } else if (name.startsWith("manual-html/5")) {
                    slurp(name, 5)
                } else if (name.startsWith("manual-html/6")) {
                    slurp(name, 6)
                } else if (name.startsWith("manual-html/7")) {
                    slurp(name, 7)
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

        //manuals.sortByDescending { it.name }

        addIntroTocs()
    }

    private fun addIntroTocs() {
        manuals.filter { manual ->
            manual.name == "intro"
        }.forEach { intro ->
            val levelManuals = mutableListOf<String>()
            intro.headings.add("<p>level ${intro.level} manuals</p><hr>")
            manuals
                .filter { manual ->
                    manual.level == intro.level && manual.name != "intro"
                }
                .forEach { manual ->
                    levelManuals.add("<a href=\"/${intro.level}/${manual.name}\">${manual.name.uppercase()}</a><br>")
                }

            levelManuals.sort()
            intro.headings.addAll(levelManuals)
        }
    }

    private fun slurp(name: String, level: Int) {
        val headings = mutableListOf<String>()
        val inputStream = javaClass.classLoader.getResourceAsStream(name)
        if (inputStream != null) {
            val contents = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"))
            val head = contents.guts("<head>", "</head>").lowercase()
            var body = contents.guts("<body>", "</body>")

            val title = head.guts("<title>", "</title>")
            if (title.isNotEmpty()) {
                val matcher = headingPattern.matcher(body)
                while (matcher.find()) {
                    val str = matcher.group().removeSuffix("<br>")

                    headings.add(str)
                }
                headings.forEach {
                    body = body.replaceFirst(it + "<br>", "")
                }
                // println("body: $body")
                manuals.add(Manual(title, body, headings, level))
            }
        }
    }

    val sentinel: MutableStateFlow<ServerStatus> = MutableStateFlow(ServerStatus.NotReady)
}

sealed class ServerStatus {
    object NotReady: ServerStatus()
    object Ready: ServerStatus()
}