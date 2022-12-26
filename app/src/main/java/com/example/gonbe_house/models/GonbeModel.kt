package com.example.gonbe_house.models

import android.os.Parcel
import android.os.Parcelable

data class GonbeModel(val name: String?, val image: String?,
                      var menus: List<Menus?>?) :

    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Menus)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeTypedList(menus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GonbeModel> {
        override fun createFromParcel(parcel: Parcel): GonbeModel {
            return GonbeModel(parcel)
        }

        override fun newArray(size: Int): Array<GonbeModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Menus(val name: String?, val price: Int,  val url: String?, var totalInCart: Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeString(url)
        parcel.writeInt(totalInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menus> {
        override fun createFromParcel(parcel: Parcel): Menus {
            return Menus(parcel)
        }

        override fun newArray(size: Int): Array<Menus?> {
            return arrayOfNulls(size)
        }
    }
}