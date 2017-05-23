package android.os

interface Parcelable {
  fun describeContents(): Int
  fun writeToParcel(dest: Parcel, flags: Int)
}