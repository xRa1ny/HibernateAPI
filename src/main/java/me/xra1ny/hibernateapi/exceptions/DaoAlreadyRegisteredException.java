package me.xra1ny.hibernateapi.exceptions;

import me.xra1ny.hibernateapi.models.dao.RDao;
import me.xra1ny.hibernateapi.models.exception.HibernateException;
import org.jetbrains.annotations.NotNull;

public class DaoAlreadyRegisteredException extends HibernateException {
    public DaoAlreadyRegisteredException(@NotNull RDao dao) {
        super("dao " + dao + " is already registered!");
    }
}
