package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.exception.RHibernateException;
import me.xra1ny.hibernateapi.models.service.RService;
import org.jetbrains.annotations.NotNull;

public class ServiceNotRegisteredException extends RHibernateException {
    public ServiceNotRegisteredException(@NotNull RService service) {
        super("service " + service + " is not yet registered!");
    }
}
