import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import org.apache.commons.codec.binary.Hex
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

fun decodePrintable(printable: String): ByteArray {
    val checksum = printable.substring(0, 16)
    val rawish = checksum.replace(Regex("""\\x"""), "")
    val decoded = Hex.decodeHex(rawish)
    return decoded + printable.substring(16).toByteArray()
}

fun main() {
//    BufferedWriter(FileWriter(File("get.txt"))).use { writer ->
//        File("input.txt").forEachLine { id ->
//            val escaped = id.replace("\"\"", "\\\"")
//            val json = """{"message": { "_id":$escaped}}"""
//            val parser: Parser = Parser.default()
//            val stringBuilder: StringBuilder = StringBuilder(String(json.toByteArray()))
//            val wtf = parser.parse(stringBuilder) as JsonObject
//            val hbaseId = MessageParser().generateKeyFromRecordBody(wtf)
//            val out = printableKey(hbaseId) //.replace("\"", "\\\"")
//            val reverted = decodePrintable(out)
//            println(reverted.contentEquals(hbaseId))
//            println(String(reverted))
//            println(String(hbaseId))
//            println("====")
//        }
//    }
//    BufferedWriter(FileWriter(File("get.txt"))).use { writer ->
//        File("input.txt").forEachLine {
//            val (id, table) = it.split(" ")
//            val escaped = id.replace("\"\"", "\\\"")
//            println(escaped)
////        println(id)
////        println(escaped)
//            //val json = """{"message": { "_id": "$escaped" }}"""
//            //println(json)
//            //val parser: Parser = Parser.default()
//            //val stringBuilder: StringBuilder = StringBuilder(String(json.toByteArray()))
//            //val wtf =  parser.parse(stringBuilder) as JsonObject
//            //val hbaseId = MessageParser().generateKeyFromRecordBody(wtf)
//            val str = """get '${table}', $escaped, {CONSISTENCY => 'STRONG'}""".trimMargin()
//            println(str)
//            writer.write("$str\n")
//        }
//
//    }


    val entries = listOf(
        listOf ("matchingService:localUserMatchingData", """{"personId":"e705c196-0416-458f-9d36-21c22fd348b3"}""", 1570125817378),
        listOf ("quartz:claimantEvent", """{"claimantEventId":"662247cd-87d9-45e8-be2c-246b532f3cca"}""", 1570125817061)
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d9638065e0d8f169fa8a076"}""", 1570125830778),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d96384222a5255b22ccd98e"}""", 1570125890409),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d96384f22a5255b22ccd99d"}""", 1570125903291),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d96388122a5255b22ccd9c4"}""", 1570125953617),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d9638d4001ecb2124bcb302"}""", 1570126036964),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d9638ec3b9d71282a65a1a3"}""", 1570126060539),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d96390eef3bad01a2340a7e"}""", 1570126094505),
//        listOf ("accepted_data:educationCircumstances", """{"id":"5d9639343b9d71282a65a1fc"}""", 1570126132309)
    )


    entries.forEach {
        val json = """{"message": { "_id": ${it[1]}}}"""
        val parser: Parser = Parser.default()
        val stringBuilder: StringBuilder = StringBuilder(String(json.toByteArray()))
        val wtf =  parser.parse(stringBuilder) as JsonObject
        val hbaseId = MessageParser().generateKeyFromRecordBody(wtf)
        println("""get '${it[0]}', "${printableKey(hbaseId).replace("\"", "\\\"")}", {TIMESTAMP => ${it[2]}, CONSISTENCY => 'STRONG'}""")
    }


//    val jsons = listOf(
//        """{"id":"5d9637f9001ecb2124bcb248"}""" to "database:collection"
//    )
//
//    jsons.forEach {
//        val json = """{"message": { "_id": ${it.first}}}"""
//        println(json)
//        val parser: Parser = Parser.default()
//        val stringBuilder: StringBuilder = StringBuilder(String(json.toByteArray()))
//        val wtf =  parser.parse(stringBuilder) as JsonObject
//        val hbaseId = MessageParser().generateKeyFromRecordBody(wtf)
//        println("""get '${it.second}', "${printableKey(hbaseId).replace("\"", "\\\"")}", {CONSISTENCY => 'STRONG'}""")
//    }
}

fun printableKey(key: ByteArray): String {
    val hash = key.slice(IntRange(0, 3))
    val hex = hash.map { String.format("\\x%02X", it) }.joinToString("")
    val renderable = key.slice(IntRange(4, key.size - 1)).map{ it.toChar() }.joinToString("")
    return "${hex}${renderable}"
}
