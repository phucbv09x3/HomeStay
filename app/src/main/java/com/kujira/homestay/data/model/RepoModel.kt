package com.kujira.homestay.data.model

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

/**
 * Created by OpenYourEyes on 11/2/20
 */
@Parcelize
data class RepoModel(
    val id: Int,
    val node_id: String,
    val name: String,
    val full_name: String,
    val owner: Owner,
    var position: Int
) : Parcelable


class ProvinceModel {
    @PropertyName("imageUrl")
    var imageUrl: String? = ""

    @PropertyName("name")
    var name: String? = ""

    constructor()
    constructor(imageUrl: String, name: String) {
        this.imageUrl = imageUrl
        this.name = name
    }
}

data class Pro(
    var imageUrl: String,
    var name : String
)