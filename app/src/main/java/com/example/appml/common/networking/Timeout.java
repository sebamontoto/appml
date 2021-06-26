package com.example.appml.common.networking;

import androidx.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;

/**
 * specifies the possible kinds of elements which can be annotated with the annotation
 */
@Target(ElementType.ANNOTATION_TYPE)
/**
 * specifies whether the annotation is stored in the compiled class files and whether
 * it's visible through reflection at runtime
 */
@Retention(RetentionPolicy.RUNTIME)

//annotation class Timeout(val timeout: Long, val timeUnit: TimeUnit)
public @interface Timeout {
    int timeout();
    TimeUnit timeUnit();
}
