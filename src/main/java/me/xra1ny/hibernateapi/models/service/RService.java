package me.xra1ny.hibernateapi.models.service;

import lombok.Getter;
import me.xra1ny.hibernateapi.models.HibernateConfiguration;
import org.jetbrains.annotations.NotNull;

public abstract class RService {
    @Getter(onMethod = @__(@NotNull))
    private final HibernateConfiguration hibernateConfiguration;

    public abstract void onEnable();

    public RService(@NotNull HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }
}
