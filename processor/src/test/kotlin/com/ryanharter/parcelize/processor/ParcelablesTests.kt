package com.ryanharter.parcelize.processor

import com.google.common.truth.Truth.assertThat
import com.google.testing.compile.CompilationRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.util.Elements
import kotlin.properties.Delegates

class ParcelablesTests {

  @get:Rule val rule = CompilationRule()

  var elements by Delegates.notNull<Elements>()
  var processingEnvironment by Delegates.notNull<ProcessingEnvironment>()

  @Before fun setup() {
    val messager = TestMessager()
    elements = rule.elements
    processingEnvironment = TestProcessingEnvironment(messager, elements, rule.types)
  }

  data class SimpleParcelable(val a: Int, val b: Double, val c: String)

  @Test fun simple() {
    val out = """
        |package com.ryanharter.parcelize.processor
        |
        |import android.os.Parcel
        |import android.os.Parcelable
        |import java.lang.String
        |import kotlin.Double
        |import kotlin.Int
        |
        |data sealed class ParcelableSimpleParcelable(val a: Int, val b: Double,
        |    val c: String) : Parcelable {
        |  fun unparcel(): ParcelablesTests.SimpleParcelable {
        |    return ParcelablesTests.SimpleParcelable(a, b, c)
        |  }
        |
        |  fun describeContents(): Int {
        |    return 0
        |  }
        |
        |  override fun writeToParcel(dest: Parcel, flags: Int) {
        |    dest.writeInt(a)
        |    dest.writeDouble(b)
        |    dest.writeString(c)
        |  }
        |}
        |
        |fun Parcel.writeSimpleParcelable(p: ParcelablesTests.SimpleParcelable, parcelableFlags: Int = 0) {
        |  writeParcelable(ParcelableSimpleParcelable(p.a, p.b, p.c), parcelableFlags)
        |}
        |
        |fun Parcel.readSimpleParcelable(): ParcelablesTests.SimpleParcelable {
        |  val p: ParcelableSimpleParcelable = readParcelable()
        |  return p.unparcel()
        |}
        |
        |fun ParcelablesTests.SimpleParcelable.asParcelable(): ParcelableSimpleParcelable {
        |  return ParcelableSimpleParcelable(a, b, c)
        |}
        |""".trimMargin()

    val elements = processingEnvironment.elementUtils
    val actual = DataParcelable(elements).createParcelableWrapper(
        elements.getTypeElement(SimpleParcelable::class.qualifiedName))

    assertThat(actual).isEqualTo(out)
  }
}