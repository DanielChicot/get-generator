import com.beust.klaxon.JsonObject
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.Parser
import com.beust.klaxon.lookup
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.CRC32

open class Converter {
    fun sortJsonByKey(unsortedJson: JsonObject): String {
        val sortedEntries = unsortedJson.toSortedMap(compareBy { it })
        val json = JsonObject(sortedEntries)
        return json.toJsonString()
    }

    fun generateFourByteChecksum(input: String): ByteArray {
        val bytes = input.toByteArray()
        val checksum = CRC32()
        checksum.update(bytes, 0, bytes.size)
        return ByteBuffer.allocate(4).putInt(checksum.value.toInt()).array()
    }
}
