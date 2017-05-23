package com.ryanharter.kups.processor

import java.util.Locale

import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

class TestProcessingEnvironment(private val messager: javax.annotation.processing.Messager, private val elements: javax.lang.model.util.Elements, private val types: javax.lang.model.util.Types) : javax.annotation.processing.ProcessingEnvironment {

  override fun getOptions(): Map<String, String>? {
    return null
  }

  override fun getMessager(): javax.annotation.processing.Messager {
    return messager
  }

  override fun getFiler(): javax.annotation.processing.Filer? {
    return null
  }

  override fun getElementUtils(): javax.lang.model.util.Elements {
    return elements
  }

  override fun getTypeUtils(): javax.lang.model.util.Types {
    return types
  }

  override fun getSourceVersion(): javax.lang.model.SourceVersion? {
    return null
  }

  override fun getLocale(): java.util.Locale? {
    return null
  }
}
