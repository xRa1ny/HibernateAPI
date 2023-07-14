package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.dao.RDao;
import me.xra1ny.hibernateapi.models.exception.RHibernateException;
import org.jetbrains.annotations.NotNull;

public class DaoNotRegisteredException extends RHibernateException {
    public DaoNotRegisteredException(@NotNull RDao dao) {
        super("dao " + dao + " is not yet registered");
    }
}
