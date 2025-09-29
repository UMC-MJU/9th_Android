package com.example.week2_flo.data

// build.gradle(Module): plugins { id("kotlin-parcelize") } 필요
import android.os.Parcelable


data class Album(
    val title: String = "",
    val singer : String = ""
)

