package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.exception.HibernateException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public class ClassNotAnnotatedException extends HibernateException {
    public ClassNotAnnotatedException(@NotNull Class<?> clazz, @NotNull Class<? extends Annotation> annotation) {
        super("class " + clazz + " needs to be annotated with " + annotation);
    }
}
