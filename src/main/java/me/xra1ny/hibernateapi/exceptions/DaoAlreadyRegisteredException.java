package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.dao.RDao;
import me.xra1ny.hibernateapi.models.exception.RHibernateException;
import org.jetbrains.annotations.NotNull;

public class DaoAlreadyRegisteredException extends RHibernateException {
    public DaoAlreadyRegisteredException(@NotNull RDao dao) {
        super("dao " + dao + " is already registered!");
    }
}
