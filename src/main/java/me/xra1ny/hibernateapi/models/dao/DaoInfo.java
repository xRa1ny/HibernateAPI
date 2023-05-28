package me.xra1ny.hibernateapi.models.dao;

import me.xra1ny.hibernateapi.models.entity.REntity;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoInfo {
    @NotNull
    Class<? extends REntity>[] entities();
}
