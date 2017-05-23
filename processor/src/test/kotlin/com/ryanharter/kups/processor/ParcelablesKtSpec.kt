package com.ryanharter.kups.processor

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class ParcelablesKtSpec: Spek({

  val parcel by memoized { ParameterSpec.builder("dest", ClassName.get("android.os", "Parcel")).build() }

  describe("String") {
    val property = PropertySpec.builder("foo", String::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readString()")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeString(foo)")
    }
  }

  describe("Int") {
    val property = PropertySpec.builder("foo", Int::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readInt()")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeInt(foo)")
    }
  }

  describe("Byte") {
    val property = PropertySpec.builder("foo", Byte::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readByte()")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeByte(foo)")
    }
  }

  describe("Short") {
    val property = PropertySpec.builder("foo", Short::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readInt() as Short")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeInt(foo)")
    }
  }

  describe("Char") {
    val property = PropertySpec.builder("foo", Char::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readInt() as Char")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeInt(foo)")
    }
  }

  describe("Long") {
    val property = PropertySpec.builder("foo", Long::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readLong()")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeLong(foo)")
    }
  }

  describe("Float") {
    val property = PropertySpec.builder("foo", Float::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readFloat()")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeFloat(foo)")
    }
  }

  describe("Double") {
    val property = PropertySpec.builder("foo", Double::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readDouble()")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeDouble(foo)")
    }
  }

  describe("Boolean") {
    val property = PropertySpec.builder("foo", Boolean::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.readInt() == 1")
    }

    it("creates write to parcel method") {
      val actual = property.writeToParcelStatement(parcel)
      assertThat(actual.toString()).isEqualTo("dest.writeInt(if (foo) 1 else 0)")
    }
  }
})