import java.io.File
import java.util.regex.Pattern

private const val VARIABLE_SCHEME_REGEX = "\\b(?:var|val|private|public)\\s+(\\w+)"
fun main() {
    println("This application helps you write SerializeName annotation for your variables easily...")
    println("------------Supported-Languages: Kotlin")
    readFileAddress()
}

private fun readFileAddress() {
    print("> Please enter file address(.kt files): ")
    val address = readlnOrNull()
    if (address == null || address.trim().isEmpty()) {
        println("Please enter valid file address!!!")
        readFileAddress()
    } else {
        val file = File(address)
        if (file.extension != "kt") {
            println("Please enter just kotlin files!!!")
        } else {
            doOperation(file)
        }
    }
}

private fun doOperation(givenFile: File) {
    if (givenFile.readText().contains("SerializedName")) {
        println("Please remove all SerializedName annotations and then try again!!!")
        readFileAddress()
    } else {
        val pattern = Pattern.compile(VARIABLE_SCHEME_REGEX)
        val tempFile = File(givenFile.parentFile.path + File.separator + "temp")
        tempFile.createNewFile()
        givenFile.forEachLine { line ->
            val matcher = pattern.matcher(line)
            if (line.startsWith("package")) {
                tempFile.appendText(text = "$line\n\n")
                tempFile.appendText(text = "import com.google.gson.annotations.SerializedName\n")
            } else if (matcher.find()) {
                val variableName = matcher.group(1)
                tempFile.appendText(text = "    @SerializedName(\"$variableName\")\n")
                tempFile.appendText(text = "$line\n")
            } else {
                tempFile.appendText(text = "$line\n")
            }
        }
        givenFile.delete()
        tempFile.renameTo(givenFile)
    }
}