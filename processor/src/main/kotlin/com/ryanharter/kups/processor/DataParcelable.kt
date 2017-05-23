package com.ryanharter.kups.processor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KotlinFile
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements

class DataParcelable(val elements: Elements) {
  /**
   * Creates the Parcelable wrapper source for the current TypeElement.
   */
  fun createParcelableWrapper(typeElement: TypeElement): KotlinFile {
    val typeName = ClassName.get(typeElement)
    return KotlinFile.builder(typeName.packageName(), "Parcelable${typeName.simpleName()}")
        .apply {
          val properties = typeElement.getFields(elements).map {
            val name = it.simpleName.toString()
            PropertySpec.builder(name, TypeName.get(it.asType())).initializer(name).build()
          }

          val wrapperClassName = ClassName.get(typeName.packageName(), "Parcelable${typeName.simpleName()}")
          val wrapper = TypeSpec.classBuilder(wrapperClassName).apply {
            addModifiers(KModifier.SEALED, KModifier.DATA)
            addSuperinterface(ClassName.get("android.os", "Parcelable"))

            addProperties(properties)

            primaryConstructor(FunSpec.constructorBuilder().addParameters(properties.map{
              ParameterSpec.builder(it.name, it.type).build()
            }).build())

            addFun(FunSpec.builder("extract").apply {
              returns(typeName)

              addCode("return %T(", typeName)
              var isFirst = true
              properties.forEach {
                if (isFirst) {
                  isFirst = false
                } else {
                  addCode(", ")
                }
                addCode("%N", it)
              }
              addCode(")\n")
            }.build())

            addFun(FunSpec.builder("describeContents")
                .returns(Int::class)
                .addStatement("return 0")
                .build())
            addFun(FunSpec.builder("writeToParcel").apply {
              addModifiers(KModifier.OVERRIDE)
              addParameter("dest", ClassName.get("android.os", "Parcel"))
              addParameter("flags", Int::class)
              properties.forEach {
                when (it.type) {
                  TypeName.get(Int::class) -> addStatement("dest.writeInt(%N)", it)
                  TypeName.get(String::class) -> addStatement("dest.writeString(%N)", it)
                  TypeName.get(Double::class) -> addStatement("dest.writeDouble(%N)", it)
                }
              }
            }.build())

          }.build()
          addType(wrapper)

          addFun(FunSpec.builder("write${typeName.simpleName()}").apply {
            receiver(ClassName.get("android.os", "Parcel"))

            val p = ParameterSpec.builder("p", typeName).build()
            val flags = ParameterSpec.builder("parcelableFlags", Int::class).defaultValue("0").build()
            addParameters(listOf(p, flags))

            addCode("writeParcelable(%T(", wrapperClassName)
            var first = true
            properties.forEach {
              if (!first) addCode(", ")
              addCode("%N.%N", p, it)
              first = false
            }
            addStatement("), %N)", flags)
          }.build())

          addFun(FunSpec.builder("read${typeName.simpleName()}").apply {
            receiver(ClassName.get("android.os", "Parcel"))
            returns(typeName)
            addStatement("val p: %T = readParcelable()", wrapperClassName)
            addStatement("return p.extract()")
          }.build())

          addFun(FunSpec.builder("asParcelable").apply {
            receiver(typeName)
            returns(wrapperClassName)

            addCode("return %T(", wrapperClassName)
            var isFirst = true
            properties.forEach {
              if (isFirst) {
                isFirst = false
              } else {
                addCode(", ")
              }
              addCode("%N", it)
            }
            addCode(")\n")
          }.build())
        }.build()
  }
}

fun TypeElement.getConstructor(elements: Elements) = elements.getAllMembers(this).first { it.kind == ElementKind.CONSTRUCTOR } as ExecutableElement
fun TypeElement.getFields(elements: Elements) = elements.getAllMembers(this).filter { it.kind == ElementKind.FIELD }.map { it as VariableElement }