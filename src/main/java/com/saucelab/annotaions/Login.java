package com.saucelab.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Login annotation is used to indicate that a test method
 * requires a login process to be executed before the test runs.
 *
 * @Target(ElementType.METHOD) - Indicates that this annotation is applicable to methods.
 * @Retention(RetentionPolicy.RUNTIME) - The annotation will be available at runtime for reflection.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
