package me.xra1ny.hibernateapi.models.dao;

import lombok.Getter;
import me.xra1ny.hibernateapi.exceptions.DaoAlreadyRegisteredException;
import me.xra1ny.hibernateapi.exceptions.DaoNotRegisteredException;
import me.xra1ny.hibernateapi.models.HibernateConfiguration;
import me.xra1ny.hibernateapi.models.entity.REntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public final class DaoManager {
    @Getter(onMethod = @__(@NotNull))
    private final HibernateConfiguration hibernateConfiguration;

    @Getter(onMethod = @__({@NotNull, @Unmodifiable}))
    private final List<RDao> daos = new ArrayList<>();

    public DaoManager(@NotNull HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    public boolean isRegistered(@NotNull RDao dao) {
        return this.daos.contains(dao);
    }

    public void register(@NotNull RDao dao) throws DaoAlreadyRegisteredException {
        if(isRegistered(dao)) {
            throw new DaoAlreadyRegisteredException(dao);
        }

        for(Class<? extends REntity> entity : dao.getEntities()) {
            System.out.println(entity);
            this.hibernateConfiguration.getConfiguration().addAnnotatedClass(entity);
        }

        this.daos.add(dao);
    }

    public void registerAll(@NotNull String packageName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, DaoAlreadyRegisteredException {
        for(Class<? extends RDao> daoClass : new Reflections(packageName).getSubTypesOf(RDao.class)) {
            register(daoClass.getDeclaredConstructor(HibernateConfiguration.class).newInstance(this.hibernateConfiguration));
        }
    }

    public void unregister(@NotNull RDao dao) throws DaoNotRegisteredException {
        if(!isRegistered(dao)) {
            throw new DaoNotRegisteredException(dao);
        }

        this.daos.remove(dao);
    }

    public void unregisterAll(@NotNull String packageName) throws DaoNotRegisteredException {
        for(RDao dao : this.daos) {
            if(!dao.getClass().getPackage().getName().equals(packageName)) {
                continue;
            }

            unregister(dao);
        }
    }

    public void unregisterAll() throws DaoNotRegisteredException {
        for(RDao dao : this.daos) {
            unregister(dao);
        }
    }

    @Nullable
    public <T extends RDao> T get(@NotNull Class<T> type) {
        return this.daos.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElse(null);
    }
}
