package com.example.androidbasics

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImageViewModel : ViewModel() {

    var uri: Uri? by mutableStateOf(null)
    private set

    fun updateImage(uri:Uri?){
        this.uri = uri
    }

}