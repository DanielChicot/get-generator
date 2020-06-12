import MessageParser
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
fun printableKey(key: ByteArray): String {
    val hash = key.slice(IntRange(0, 3))
    val hex = hash.map { String.format("\\x%02X", it) }.joinToString("")
    val renderable = key.slice(IntRange(4, key.size - 1)).map{ it.toChar() }.joinToString("")
    return "${hex}${renderable}"
}

val jsons = listOf(""
)

fun main() {
    jsons.forEach {
        val json = """{"message": { "_id": $it}}"""
        val parser: Parser = Parser.default()
        val stringBuilder: StringBuilder = StringBuilder(String(json.toByteArray()))
        val wtf =  parser.parse(stringBuilder) as JsonObject
        val hbaseId = MessageParser().generateKeyFromRecordBody(wtf)
        println("""get 'third_party_landlord:landlordToDo', "${printableKey(hbaseId).replace("\"", "\\\"")}"""")
    }
}
