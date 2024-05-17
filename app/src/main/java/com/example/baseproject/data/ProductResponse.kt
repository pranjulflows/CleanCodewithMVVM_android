package com.example.baseproject.data


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class ProductResponse : ArrayList<ProductResponse.ProductResponseItem>(){
    @Parcelize
    data class ProductResponseItem(
        @SerializedName("category")
        var category: String? = null,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("image")
        var image: String? = null,
        @SerializedName("price")
        var price: Double? = null,
        @SerializedName("rating")
        var rating: Rating? = null,
        @SerializedName("title")
        var title: String? = null
    ) : Parcelable {
        @Parcelize
        data class Rating(
            @SerializedName("count")
            var count: Int? = null,
            @SerializedName("rate")
            var rate: Double? = null
        ) : Parcelable
    }
}