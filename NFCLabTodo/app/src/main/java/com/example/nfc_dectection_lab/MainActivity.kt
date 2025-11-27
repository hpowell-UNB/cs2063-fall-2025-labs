package com.example.nfc_dectection_lab

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var tvStatus: TextView
    private lateinit var tvCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        tvCode = findViewById(R.id.tvCode)

        // TODO 1 get the default NFC adapter
        // TODO 2 Update the status message to inform the user if an adapter can't be found
        // TODO See NfcAdapter docs: https://developer.android.com/reference/android/nfc/NfcAdapter

        // In our handle intent function, we will handle what happens when a tag is detected
        handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        enableForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatch()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun enableForegroundDispatch() {
        val adapter = nfcAdapter ?: return
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_MUTABLE else 0
        )
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try { addDataScheme("http"); addDataScheme("https") } catch (_: Exception) {}
        })
        val techList = arrayOf<Array<String>>()
        adapter.enableForegroundDispatch(this, pendingIntent, filters, techList)
    }

    private fun disableForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(this)
    }

    // We need to get the information from the tag here
    private fun handleIntent(intent: Intent) {
        val action = intent.action
        // There are TWO (or more) options here for our action
        // Did anything from your research suggest which action to use
        // NfcAdapter has the list of actions we can detect
        // TODO 3 set the action to check for (DO NOT LEAVE NULL)
        val tagAction = null

        if (action == tagAction) {
            // TODO 4 get the extra from the intent, the extra here will have our info from the tag

            // TODO 5 Process the tag information (your earlier research can help tell you what the message might look like
            // This might be helpful too https://developer.android.com/reference/android/nfc/tech/Ndef

            // TODO 6 call this function with your "msg: NdefMessage" object
            //  processNdefMessage()
        }
    }

    // TODO 7 Submit the lab
    // Create a function to send an NFC message containing the code and your names to an NFC reader at the front of the class
    // This is a bit bigger of a task but you already did the reverse of this!

    // We actually parse the record and handle what we do after here we will include an image - we can do this part to "hide" the treasure
    private fun processNdefMessage(msg: NdefMessage) {
        val records = msg.records
        if (records.isEmpty()) return

        // Try to get a URI record first, then fallback to text
        var urlString: String? = null
        for (r in records) {
            if (isUriRecord(r)) {
                urlString = r.toUriString()
                break
            } else if (isTextRecord(r)) {
                val text = r.toText()
                if (text.startsWith("http://") || text.startsWith("https://")) {
                    urlString = text
                    break
                }
            }
        }

        // here is our info!
        if (urlString != null) {
            tvStatus.text = "Tag URL: $urlString"
            val code = extractFinalPathSegment(urlString)
            tvCode.text = if (code != null) "Code: $code" else "No code found"
        } else {
            tvStatus.text = "No URL record found on tag"
            tvCode.text = ""
        }
    }

    private fun extractFinalPathSegment(url: String): String? {
        return try {
            val uri = Uri.parse(url)
            val last = uri.lastPathSegment
            last
        } catch (e: Exception) {
            null
        }
    }

    private fun isUriRecord(record: NdefRecord): Boolean {
        return record.tnf == NdefRecord.TNF_WELL_KNOWN && record.type.contentEquals(NdefRecord.RTD_URI)
    }

    private fun isTextRecord(record: NdefRecord): Boolean {
        return record.tnf == NdefRecord.TNF_WELL_KNOWN && record.type.contentEquals(NdefRecord.RTD_TEXT)
    }

    // helpers to convert records (simple implementations)
    private fun NdefRecord.toUriString(): String? {
        return try {
            // Android's NdefRecord has a built-in createUri; parsing raw payload is simple enough:
            val payload = this.payload
            val prefix = URI_PREFIX_MAP[payload[0].toInt()] ?: ""
            val uri = prefix + String(payload, 1, payload.size - 1, Charsets.UTF_8)
            uri
        } catch (e: Exception) {
            null
        }
    }

    private fun NdefRecord.toText(): String {
        val payload = this.payload
        val textEncoding = if ((payload[0].toInt() and 0x80) == 0) Charsets.UTF_8 else Charsets.UTF_16
        val languageCodeLength = payload[0].toInt() and 0x3F
        return String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, textEncoding)
    }

    private fun showNfcUnavailable() {
        AlertDialog.Builder(this)
            .setTitle("NFC not available")
            .setMessage("This device does not support NFC. The lab requires an NFC-capable device.")
            .setPositiveButton("OK", null)
            .show()
    }

    // This is a hidden helper function for you if you find it. Reading existing code
    // is always important, especially when provided for you :) -Taylor
    private fun readNdefFromIntent(intent: Intent): NdefMessage? {
        val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (rawMsgs != null && rawMsgs.isNotEmpty()) {
            return rawMsgs[0] as NdefMessage
        }
        return null
    }

    companion object {
        // URI prefix map from NDEF RTD_URI spec
        private val URI_PREFIX_MAP = mapOf(
            0 to "",
            1 to "http://www.",
            2 to "https://www.",
            3 to "http://",
            4 to "https://",
            5 to "tel:",
            6 to "mailto:",
            7 to "ftp://anonymous:anonymous@",
            8 to "ftp://ftp.",
            9 to "ftps://",
            10 to "sftp://",
            11 to "smb://",
            12 to "nfs://",
            13 to "ftp://",
            14 to "dav://",
            15 to "news:",
            16 to "telnet://",
            17 to "imap:",
            18 to "rtsp://",
            19 to "urn:",
            20 to "pop:",
            21 to "sip:",
            22 to "sips:",
            23 to "tftp:",
            24 to "btspp://",
            25 to "btl2cap://",
            26 to "btgoep://",
            27 to "tcpobex://",
            28 to "irdaobex://",
            29 to "file://",
            30 to "urn:epc:id:",
            31 to "urn:epc:tag:",
            32 to "urn:epc:pat:",
            33 to "urn:epc:raw:",
            34 to "urn:epc:",
            35 to "urn:nfc:"
        )
    }
}
