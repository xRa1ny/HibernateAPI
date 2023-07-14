package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.exception.RHibernateException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public class ClassNotAnnotatedException extends RHibernateException {
    public ClassNotAnnotatedException(@NotNull Class<?> clazz, @NotNull Class<? extends Annotation> annotation) {
        super("class " + clazz + " needs to be annotated with " + annotation);
    }
}
