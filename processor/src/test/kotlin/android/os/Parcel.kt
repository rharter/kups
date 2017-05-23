package android.os

interface Parcel {
  fun readByte(): Byte
  fun readInt(): Int
  fun readLong(): Long
  fun readFloat(): Float
  fun readDouble(): Double

  fun writeInt(`in`: Int)

}