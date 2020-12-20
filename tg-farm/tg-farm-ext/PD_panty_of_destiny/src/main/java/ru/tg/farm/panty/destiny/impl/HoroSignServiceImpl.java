package ru.tg.farm.panty.destiny.impl;

import ru.tg.farm.panty.destiny.service.HoroSignService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class HoroSignServiceImpl implements HoroSignService {

    @PersistenceContext
    public EntityManager entityManager;

    public void truncate(){
        entityManager.createNativeQuery("truncate table public.horo_sign")
                .executeUpdate();
    }

    public void add(String code){
        entityManager.createNativeQuery("insert into public.horo_sign (code, dt) values (:code, now())")
                .setParameter("code", code)
                .executeUpdate();
    }

    public Integer getFill(){
        return Integer.parseInt(String.valueOf(entityManager.createNativeQuery("select count(*) from public.horo_sign")
                .getSingleResult()
        ));
    }

    public Boolean contains(String code) {
        return Boolean.valueOf(String.valueOf(entityManager.createNativeQuery("select exists(select * from public.horo_sign " +
                "where code = :code)")
                .setParameter("code", code)
                .getSingleResult()
        ));
    }
}
