package me.xra1ny.hibernateapi.models.service;

import lombok.Getter;
import me.xra1ny.hibernateapi.exceptions.ServiceAlreadyRegisteredException;
import me.xra1ny.hibernateapi.exceptions.ServiceNotRegisteredException;
import me.xra1ny.hibernateapi.models.HibernateConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class ServiceManager {
    @Getter(onMethod = @__(@NotNull))
    private final HibernateConfiguration hibernateConfiguration;

    @Getter(onMethod = @__({@NotNull, @Unmodifiable}))
    private final List<RService> services = new ArrayList<>();

    public ServiceManager(@NotNull HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    public boolean isRegistered(@NotNull RService service) {
        return this.services.contains(service);
    }

    public void register(@NotNull RService service) throws ServiceAlreadyRegisteredException {
        if(isRegistered(service)) {
            throw new ServiceAlreadyRegisteredException(service);
        }

        this.services.add(service);
    }

    public void registerAll(@NotNull String packageName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ServiceAlreadyRegisteredException {
        for(Class<? extends RService> serviceClass : new Reflections(packageName).getSubTypesOf(RService.class)) {
            register(serviceClass.getDeclaredConstructor(HibernateConfiguration.class).newInstance(this.hibernateConfiguration));
        }
    }

    public void unregister(@NotNull RService service) throws ServiceNotRegisteredException {
        if(!isRegistered(service)) {
            throw new ServiceNotRegisteredException(service);
        }

        this.services.remove(service);
    }

    public void unregisterAll(@NotNull String packageName) throws ServiceNotRegisteredException {
        for(RService service : this.services) {
            if(!service.getClass().getPackage().getName().equals(packageName)) {
                continue;
            }

            unregister(service);
        }
    }

    public void unregisterAll() throws ServiceNotRegisteredException {
        for(RService service : this.services) {
            unregister(service);
        }
    }

    @Nullable
    public <T extends RService> T get(@NotNull Class<T> type) {
        return this.services.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElse(null);
    }
}
