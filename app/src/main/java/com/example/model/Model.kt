package com.example.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by aarslan on 16/02/2022.
 */

@Entity
data class Country(
    @ColumnInfo(name="name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name="capital")
    @SerializedName("capital")
    val capital: String?,
    @ColumnInfo(name="region")
    @SerializedName("region")
    val region: String?,
    @ColumnInfo(name="currency")
    @SerializedName("currency")
    val currency: String?,
    @ColumnInfo(name="language")
    @SerializedName("language")
    val language: String?,
    @ColumnInfo(name="flag")
    @SerializedName("flag")
    val flagUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}

