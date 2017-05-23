package com.ryanharter.parcelize.processor

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ArrayTypeName
import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.BYTE
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DOUBLE
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.LONG
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.SHORT
import com.squareup.kotlinpoet.TypeName
import com.sun.xml.internal.fastinfoset.util.StringArray
import java.io.Serializable

class Parcelables {

  companion object {

    val STRING = TypeName.get(String::class)
    val MAP = ClassName.get(Map::class)
    val LIST = ClassName.get(List::class)
    val BOOLEANARRAYCLASS = ClassName.get(BooleanArray::class)
    val BOOLEANARRAY = ArrayTypeName.of(Boolean::class)
    val BYTEARRAYCLASS = ClassName.get(ByteArray::class)
    val BYTEARRAY = ArrayTypeName.of(Byte::class)
    val CHARARRAYCLASS = ClassName.get(CharArray::class)
    val CHARARRAY = ArrayTypeName.of(Char::class)
    val INTARRAYCLASS = ClassName.get(IntArray::class)
    val INTARRAY = ArrayTypeName.of(Int::class)
    val LONGARRAYCLASS = ClassName.get(LongArray::class)
    val LONGARRAY = ArrayTypeName.of(Long::class)
    val STRINGARRAYCLASS = ClassName.get(StringArray::class)
    val STRINGARRAY = ArrayTypeName.of(String::class)
    val SPARSEARRAY = ClassName.get("android.util", "SparseArray")
    val SPARSEBOOLEANARRAY = ClassName.get("android.util", "SparseBooleanArray")
    val BUNDLE = ClassName.get("android.os", "Bundle")
    val PARCELABLE = ClassName.get("android.os", "Parcelable")
    val PARCELABLEARRAY = ArrayTypeName.of(PARCELABLE)
    val CHARSEQUENCE = ClassName.get(CharSequence::class)
    val IBINDER = ClassName.get("android.os", "IBinder")
    val OBJECTARRAY = ArrayTypeName.of(Any::class)
    val SERIALIZABLE = ClassName.get(Serializable::class)
    val PERSISTABLEBUNDLE = ClassName.get("android.os", "PersistableBundle")
    val SIZE = ClassName.get("android.util", "Size")
    val SIZEF = ClassName.get("android.util", "SizeF")
    val TEXTUTILS = ClassName.get("android.text", "TextUtils")

    private val VALID_TYPES = setOf(ANY, BOOLEAN, BYTE, SHORT, INT, LONG, CHAR, FLOAT, DOUBLE,
        STRING, MAP, LIST, BOOLEANARRAYCLASS, BOOLEANARRAY, BYTEARRAYCLASS, BYTEARRAY,
        CHARARRAYCLASS, CHARARRAY, INTARRAYCLASS, INTARRAY, LONGARRAYCLASS, LONGARRAY,
        STRINGARRAYCLASS, STRINGARRAY, SPARSEARRAY, SPARSEBOOLEANARRAY, BUNDLE, PARCELABLE,
        PARCELABLEARRAY, CHARSEQUENCE, IBINDER, OBJECTARRAY, SERIALIZABLE, PERSISTABLEBUNDLE, SIZE,
        SIZEF, TEXTUTILS)

    fun isValidType(type: TypeName) = VALID_TYPES.contains(type)

  }
}

fun PropertySpec.readFromParcelStatement(parcel: ParameterSpec): CodeBlock {
  return when (type) {
    TypeName.get(String::class) -> CodeBlock.of("%N.readString()", parcel)
    else -> throw IllegalArgumentException("Invalid type: $this")
  }
}

fun PropertySpec.writeToParcelStatement(parcel: ParameterSpec): CodeBlock {
  return when (type) {
    TypeName.get(Int::class) -> CodeBlock.of("dest.writeInt(%N)", this)
    TypeName.get(String::class) -> CodeBlock.of("dest.writeString(%N)", this)
    TypeName.get(Double::class) -> CodeBlock.of("dest.writeDouble(%N)", this)
    else -> throw IllegalArgumentException("Invalid type: $this")
  }
}