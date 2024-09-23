package com.dev.frameflow

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import android.provider.MediaStore
import android.database.Cursor

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List of drawable resource IDs
        val imageList = listOf(
            R.drawable.lion,
            R.drawable.tiger,
            R.drawable.bear
        )

        // Find the ViewPager2 in the layout
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        // Set the adapter
        val adapter = ImagePagerAdapter(imageList)
        viewPager.adapter = adapter
    }

    private fun loadImagesFromStorage() {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val imagePath = it.getString(columnIndex)
                imageList.add(imagePath)
            }
        }
        imageAdapter.notifyDataSetChanged()
    }
}