package pl.edu.pjwstk.jaz.auction.parameter;


import pl.edu.pjwstk.jaz.auction.AuctionEntity;
import pl.edu.pjwstk.jaz.auction.image.ImageEntity;
import pl.edu.pjwstk.jaz.auction.image.ImageRepository;
import pl.edu.pjwstk.jaz.auth.ProfileEntity;
import pl.edu.pjwstk.jaz.utils.MyUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@ApplicationScoped
public class ParameterRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<ParameterEntity> getAllParameters() {
        return em.createQuery(
                "from ParameterEntity",
                ParameterEntity.class
        ).getResultList();
    }

    @Transactional
    public ParameterEntity getParameterByName(String name) {
        List<ParameterEntity> tmp = em.createQuery(
                "from ParameterEntity where name = :_name",
                ParameterEntity.class
        ).setParameter("_name", name).getResultList();

        return tmp.isEmpty() ? null : tmp.get(0);
    }

    @Transactional
    public void createParameter(String name) {
        em.persist(new ParameterEntity(name));
    }

    @Transactional
    public void removeParameter(String name) {
        ParameterEntity entity = getParameterByName(name);
        if (entity != null)
            em.remove(entity);
        else
            System.out.println("Parameter " + name + " not found");
    }
}
