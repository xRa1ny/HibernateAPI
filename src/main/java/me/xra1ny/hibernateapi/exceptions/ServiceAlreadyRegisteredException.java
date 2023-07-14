package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.exception.RHibernateException;
import me.xra1ny.hibernateapi.models.service.RService;
import org.jetbrains.annotations.NotNull;

public class ServiceAlreadyRegisteredException extends RHibernateException {
    public ServiceAlreadyRegisteredException(@NotNull RService service) {
        super("service " + service + " is already registered!");
    }
}
