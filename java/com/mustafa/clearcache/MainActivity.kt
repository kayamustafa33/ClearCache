package com.mustafa.clearcache

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mustafa.clearcache.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun clearAllCache(context: Context) {
        try {
            val dir = context.externalCacheDir
            if (dir != null && dir.isDirectory) {
                deleteDir(dir)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.listFiles()
            for (child in children!!) {
                val success = deleteDir(child)
                if (!success) {
                    return false
                }
            }
        }
        return dir?.delete() ?: false
    }



    fun getCacheSize(context: Context): Long {
        var cacheSize: Long = 0
        try {
            val cacheDirectory = context.cacheDir
            cacheSize = getDirSize(cacheDirectory)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cacheSize
    }

    fun getDirSize(dir: File): Long {
        var size: Long = 0
        if (dir.exists()) {
            for (file in dir.listFiles()!!) {
                size += if (file.isDirectory) {
                    getDirSize(file)
                } else {
                    file.length()
                }
            }
        }
        return size
    }



    fun clearCacheBtn(view : View){
        clearAllCache(applicationContext)
    }

    fun getCacheSize(view : View){
        val size = getCacheSize(applicationContext)
        binding.sizeTextView.text = "$size"
    }


}