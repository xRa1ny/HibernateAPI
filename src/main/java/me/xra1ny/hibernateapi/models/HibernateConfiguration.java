package me.xra1ny.hibernateapi.models;

import lombok.Getter;
import me.xra1ny.hibernateapi.models.dao.DaoManager;
import me.xra1ny.hibernateapi.models.dao.RDao;
import me.xra1ny.hibernateapi.models.service.RService;
import me.xra1ny.hibernateapi.models.service.ServiceManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jetbrains.annotations.NotNull;

public final class HibernateConfiguration {
    @Getter(onMethod = @__(@NotNull))
    private final Configuration configuration;

    @Getter(onMethod = @__(@NotNull))
    private final ServiceManager serviceManager;

    @Getter(onMethod = @__(@NotNull))
    private final DaoManager daoManager;

    @Getter(onMethod = @__(@NotNull))
    private SessionFactory sessionFactory;

    public HibernateConfiguration(@NotNull String urlToCfgXmlFile) {
        this.configuration = new Configuration().configure(urlToCfgXmlFile);
        this.serviceManager = new ServiceManager(this);
        this.daoManager = new DaoManager(this);
    }

    public void enable() {
        this.sessionFactory = this.configuration.buildSessionFactory();

        for(RService service : this.serviceManager.getServices()) {
            service.onEnable();
        }

        for(RDao dao : this.daoManager.getDaos()) {
            dao.onEnable();
        }
    }
}
