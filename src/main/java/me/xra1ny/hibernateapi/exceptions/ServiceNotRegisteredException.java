package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.exception.HibernateException;
import me.xra1ny.hibernateapi.models.service.RService;
import org.jetbrains.annotations.NotNull;

public class ServiceNotRegisteredException extends HibernateException {
    public ServiceNotRegisteredException(@NotNull RService service) {
        super("service " + service + " is not yet registered!");
    }
}
