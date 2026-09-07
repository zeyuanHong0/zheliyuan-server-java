package org.zly.blogbackjava.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SafeSvgValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SafeSvg {

    String message() default "SVG 格式不合法或存在安全隐患";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
