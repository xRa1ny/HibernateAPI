package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.exception.HibernateException;
import me.xra1ny.hibernateapi.models.service.RService;
import org.jetbrains.annotations.NotNull;

public class ServiceAlreadyRegisteredException extends HibernateException {
    public ServiceAlreadyRegisteredException(@NotNull RService service) {
        super("service " + service + " is already registered!");
    }
}
