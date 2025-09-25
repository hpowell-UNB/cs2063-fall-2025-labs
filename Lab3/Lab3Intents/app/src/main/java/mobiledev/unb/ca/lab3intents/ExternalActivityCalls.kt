package mobiledev.unb.ca.lab3intents

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.OutputStream

class ExternalActivityCalls : AppCompatActivity() {
    // Attributes for storing the file photo path
    private lateinit var currentPhotoPath: String

    // Activity listeners
    private var cameraActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.external_activity_calls)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.external_activity_calls)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Register the activity listener
        setCameraActivityResultLauncher()

        // Register the button listeners
        val cameraButton: Button = findViewById(R.id.btnCamera)
        cameraButton.setOnClickListener {
            dispatchTakePhotoIntent()
        }

        val emailButton: Button = findViewById(R.id.btnEmail)
        emailButton.setOnClickListener {
            dispatchSendEmailIntent()
        }

        val backButton = findViewById<Button>(R.id.btnBack)
        backButton.setOnClickListener {
            // TODO: Add intent to navigate back to MainActivity
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_external_activity_calls, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Email functions
    private fun dispatchSendEmailIntent() {
        // Handle the send email intent
        // TODO: Complete implementation
    }

    // Camera Functions
    private fun dispatchTakePhotoIntent() {
        // Handle the photo intent
        // TODO: Complete implementation
    }

    private fun setCameraActivityResultLauncher() {
        // TODO: Handle the image capture result
    }

    private fun galleryAddPic() {
        Log.d(TAG, "Saving image to the gallery")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 (API 29) and above
            mediaStoreAddPicToGallery()
        } else {
            // Pre Android 10
            mediaScannerAddPicToGallery()
        }
        Log.i(TAG, "Image saved!")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun mediaStoreAddPicToGallery() {
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)

        val contentValues = getContentValues()
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        contentValues.put(MediaStore.Images.Media.IS_PENDING, true)

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                saveImageToStream(bitmap, outputStream)
            }
            contentValues.apply {
                put(MediaStore.Images.Media.IS_PENDING, false)
                resolver.update(uri, this, null, null)
            }
        }
    }

    private fun getContentValues() : ContentValues {
        return ContentValues().apply {
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        // The use function is a Kotlin extension function which automatically closes
        // the OutputStream after the block of code inside it has executed.
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        } ?: Log.e(TAG, "Error saving the file: outputStream is null")
    }

    private fun mediaScannerAddPicToGallery() {
        val file = File(currentPhotoPath)
        MediaScannerConnection.scanFile(this@ExternalActivityCalls,
            arrayOf(file.toString()),
            arrayOf(file.name),
            null)
    }
    companion object {
        // String for LogCat documentation
        private const val TAG = "External Activity Calls"
    }
}