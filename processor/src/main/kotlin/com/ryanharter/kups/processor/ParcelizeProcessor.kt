package com.ryanharter.kups.processor

import com.google.auto.service.AutoService
import com.ryanharter.kups.Parcelize
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class ParcelizeProcessor : AbstractProcessor() {

  override fun getSupportedAnnotationTypes() = mutableSetOf(Parcelize::class.qualifiedName!!)

  override fun getSupportedSourceVersion() = SourceVersion.latestSupported()!!

  override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment?): Boolean {
    val dataParcelable = DataParcelable(processingEnv.elementUtils)

    val adapters = annotations.map { dataParcelable.createParcelableWrapper(it) }
    adapters.forEach { it.writeTo(processingEnv.filer) }

    return true
  }
}