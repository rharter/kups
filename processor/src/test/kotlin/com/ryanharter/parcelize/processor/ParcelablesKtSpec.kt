package com.ryanharter.parcelize.processor

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class ParcelablesKtSpec: Spek({

  val destParcel by memoized { ParameterSpec.builder("dest", ClassName.get("android.os", "Parcel")).build() }

  describe("String") {
    val property = PropertySpec.builder("foo", String::class).build()

    it("creates read from parcel method") {
      val actual = property.readFromParcelStatement(destParcel)
      assertThat(actual.toString()).isEqualTo("dest.readString()")
    }
  }
})