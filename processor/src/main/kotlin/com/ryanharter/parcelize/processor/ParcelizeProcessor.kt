package com.ryanharter.parcelize.processor

import com.ryanharter.parcelize.Parcelize
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

class ParcelizeProcessor : AbstractProcessor() {

  override fun getSupportedAnnotationTypes() = mutableSetOf(Parcelize::class.qualifiedName!!)

  override fun getSupportedSourceVersion() = SourceVersion.latestSupported()!!

  override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
    return false
  }
}