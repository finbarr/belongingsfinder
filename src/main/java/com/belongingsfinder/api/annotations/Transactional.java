package com.belongingsfinder.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * @author finbarr
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface Transactional {
}
