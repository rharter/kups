package com.ryanharter.parcelize.processor

import java.util.Locale

import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

class TestProcessingEnvironment(private val messager: Messager, private val elements: Elements, private val types: Types) : ProcessingEnvironment {

  override fun getOptions(): Map<String, String>? {
    return null
  }

  override fun getMessager(): Messager {
    return messager
  }

  override fun getFiler(): Filer? {
    return null
  }

  override fun getElementUtils(): Elements {
    return elements
  }

  override fun getTypeUtils(): Types {
    return types
  }

  override fun getSourceVersion(): SourceVersion? {
    return null
  }

  override fun getLocale(): Locale? {
    return null
  }
}
