package me.xra1ny.hibernateapi.models.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import me.xra1ny.hibernateapi.exceptions.ClassNotAnnotatedException;
import me.xra1ny.hibernateapi.models.HibernateConfiguration;
import me.xra1ny.hibernateapi.models.entity.REntity;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class RDao {
    @Getter(onMethod = @__(@NotNull))
    private final HibernateConfiguration hibernateConfiguration;

    @Getter(onMethod = @__(@NotNull))
    private final List<Class<? extends REntity>> entities = new ArrayList<>();

    public RDao(@NotNull HibernateConfiguration hibernateConfiguration) throws ClassNotAnnotatedException {
        final DaoInfo info = getClass().getDeclaredAnnotation(DaoInfo.class);

        if(info == null) {
            throw new ClassNotAnnotatedException(getClass(), DaoInfo.class);
        }

        this.hibernateConfiguration = hibernateConfiguration;
        this.entities.addAll(Arrays.asList(info.entities()));
    }

    public abstract void onEnable();

    public final void persist(@NotNull Object object) {
        try(Session session = getHibernateConfiguration().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    public final void remove(@NotNull Object object) {
        try(Session session = getHibernateConfiguration().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(object);
            session.getTransaction().commit();
        }
    }

    public final <T extends REntity> List<T> getAll(@NotNull Class<T> type) {
        try(Session session = getHibernateConfiguration().getSessionFactory().openSession()) {
            final CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = builder.createQuery(type);
            final Root<T> from = criteria.from(type);

            criteria.select(from);

            final TypedQuery<T> typed = session.createQuery(criteria);

            try {
                return typed.getResultList();
            }catch(NoResultException ex) {
                return Collections.emptyList();
            }
        }
    }

    @SafeVarargs
    @NotNull
    public final <T extends REntity> List<T> getAllByColumnValues(@NotNull Class<T> type, @NotNull Map.Entry<String, Object> @NotNull ... columnValues) {
        try(Session session = getHibernateConfiguration().getSessionFactory().openSession()) {
            final CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = builder.createQuery(type);
            final Root<T> from = criteria.from(type);

            criteria.select(from);

            final List<Predicate> predicates = new ArrayList<>();

            for(Map.Entry<String, Object> columns : columnValues) {
                predicates.add(builder.equal(from.get(columns.getKey()), columns.getValue()));
            }

            criteria.where(predicates.toArray(new Predicate[0]));

            final TypedQuery<T> typed = session.createQuery(criteria);

            try {
                return typed.getResultList();
            }catch (NoResultException ex) {
                return Collections.emptyList();
            }
        }

    }
}
