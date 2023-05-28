package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.dao.RDao;
import me.xra1ny.hibernateapi.models.exception.HibernateException;
import org.jetbrains.annotations.NotNull;

public class DaoNotRegisteredException extends HibernateException {
    public DaoNotRegisteredException(@NotNull RDao dao) {
        super("dao " + dao + " is not yet registered");
    }
}
