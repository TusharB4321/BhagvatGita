package com.example.bhagvatgita

import android.app.Application
import java.io.File

class BaseClass:Application() {
    override fun onCreate() {
        super.onCreate()
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }

}