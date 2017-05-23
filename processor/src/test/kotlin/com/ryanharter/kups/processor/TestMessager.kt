package com.ryanharter.kups.processor

import java.io.PrintStream
import javax.annotation.processing.Messager
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.tools.Diagnostic


class TestMessager : javax.annotation.processing.Messager {

  override fun printMessage(kind: javax.tools.Diagnostic.Kind, msg: CharSequence) {
    printMessage(kind, msg, null)
  }

  override fun printMessage(kind: javax.tools.Diagnostic.Kind, msg: CharSequence, e: javax.lang.model.element.Element?) {
    printMessage(kind, msg, e, null)
  }

  override fun printMessage(kind: javax.tools.Diagnostic.Kind, msg: CharSequence, e: javax.lang.model.element.Element?, a: javax.lang.model.element.AnnotationMirror?) {
    printMessage(kind, msg, e, a, null)
  }

  override fun printMessage(kind: javax.tools.Diagnostic.Kind, msg: CharSequence, e: javax.lang.model.element.Element?, a: javax.lang.model.element.AnnotationMirror?, v: javax.lang.model.element.AnnotationValue?) {
    val out: java.io.PrintStream
    if (kind == javax.tools.Diagnostic.Kind.ERROR) {
      out = System.err
    } else {
      out = System.out
    }
    out.println(msg)
  }
}
