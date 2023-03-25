import com.bandor.model.Manual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.jar.JarFile
import java.util.regex.Pattern
import java.util.stream.Collectors
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
                if (name.startsWith("json/")) {
                    slurpJson(name)
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

    private fun slurpJson(name: String) {
        val inputStream = javaClass.classLoader.getResourceAsStream(name)
        if (inputStream != null) {
            val contents = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"))
            if (contents.isNotEmpty()) {
                val manual = Json.decodeFromString<Manual>(Manual.serializer(), contents)
                manuals.add(manual)
            }
        }
    }

    val sentinel: MutableStateFlow<ServerStatus> = MutableStateFlow(ServerStatus.NotReady)
}
sealed class ServerStatus {
    object NotReady: ServerStatus()
    object Ready: ServerStatus()
}