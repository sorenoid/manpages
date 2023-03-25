import com.bandor.model.Manual
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.DirectoryStream
import java.util.jar.JarFile
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.system.exitProcess

fun String.guts(startTag: String, endTag: String): String =
    replaceAfterLast(endTag, "").replaceBefore(startTag, "").replace(endTag, "").replace(startTag, "")

object FileSlurper {
    val manuals = mutableListOf<Manual>()

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

//        slurpLocalTwo()

        manuals.sortByDescending { it.name }
        addIntroTocs()
        serialize()
    }

    private val json = Json {
        prettyPrint = true
    }

    private fun serialize() {
        val outDir = File("/home/sorenoid/bandor/outputJson")
        outDir.mkdirs()
        outDir?.let {
            val newDir = File("${it.path}/json")
            newDir.mkdirs()
            manuals
                .filter { manny ->
                    if (manny.name.contains("/")) {
                        println("skipping ${manny.level} ${manny.name}")
                        return@filter false
                    } else {
                        true
                    }
                }
                .forEach { manual ->
                    val levelDir = File(newDir, "${manual.level}")
                    if (!levelDir.exists()) {
                        levelDir.mkdirs()
                    }
                    val manJson = Json.encodeToString (manual)
                    val jsonFile = File(levelDir, "${manual.name}.json")
                    println("creating ${jsonFile.canonicalPath}")
                    jsonFile.createNewFile()
                    jsonFile.writeText(manJson)
                }
        }
    }

    fun slurpLocalTwo() {
        val manOneDir = File(this::class.java.classLoader.getResource("manual-html/1").toExternalForm())

        //val manOne = this::class.java.classLoader.getResource("manual-html/1")
        //val manOneDir = File(manOne.toURI())
        manOneDir.listFiles()?.forEach {
            println("found file ${it}")
        }
        //manOneDir.canonicalPath
        //}
    }

    fun slurpLocal(): Unit {
        // for running from the command line (without jar...)
//        repeat(7) {
//            val level = it + 1
//            val man = this::class.java.classLoader.getResource("manual-html")
//            if (man.)
//            val manDir = File(man, "$level")
//            manDir.listFiles()?.forEach { levelDir ->
//                slurpLocal(levelDir, level)
//            } ?: throw IllegalAccessException("cant find $man level $level")
//        }
    }

    private fun addIntroTocs() {
        manuals.filter { manual ->
            manual.name == "intro"
        }.forEach { intro ->
            val levelManuals = mutableListOf<String>()
            intro.headings.add("<p>level ${intro.level} manuals</p><hr>")
            manuals.filter { manual ->
                manual.level == intro.level && manual.name != "intro"
            }.forEach { manual ->
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
            val contents = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"))
            val head = contents.guts("<head>", "</head>").lowercase()
            var body = contents.guts("<body>", "</body>")
            var description = body
                .guts("<a name=\"NAME\"></a>", "<h2>SYNOPSIS")
                .guts("<p style=\"margin-left:11%; margin-top: 1em\">","</p>")
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
                manuals.add(Manual(title, description, body, headings, level))
            }
        }
    }

//    private fun slurpLocal(file: File, level: Int) {
//        val manOne = this::class.java.classLoader.getResource("manual-html/1")
////        val manOneDir = File(manOne.path)
////        manOneDir.listFiles().forEach {
////            val contents = this::class.java.classLoader.getResource("manual-html/1/${it.name}")!!.readText()
////            val head = contents.guts("<head>", "</head>").lowercase()
////            val body = contents.guts("<body>", "</body>")
////
////            val title = head.guts("<title>", "</title>")
////            // println("body: $body")
////            manuals.add(Manual(title, body))
////        }
//        val headings = mutableListOf<String>()
//        val contents = this::class.java.classLoader.getResource("manual-html/${level}/${file.name}")!!.readText()
//        val head = contents.guts("<head>", "</head>").lowercase()
//        var body = contents.guts("<body>", "</body>")
//
//        val title = head.guts("<title>", "</title>")
//        if (title.isNotEmpty()) {
//            val matcher = headingPattern.matcher(body)
//            while (matcher.find()) {
//                val str = matcher.group().removeSuffix("<br>")
//
//                headings.add(str)
//            }
//            headings.forEach {
//                body = body.replaceFirst("$it<br>", "")
//            }
//            // println("body: $body")
//            manuals.add(Manual(title, body, headings, level))
//        }
//    }
}