package pl.edu.pjwstk.jaz.auction.section;

import pl.edu.pjwstk.jaz.auction.section.category.CategoryEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@ApplicationScoped
public class SectionRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public SectionEntity getSection(Long id) {
        return em.find(SectionEntity.class, id);
    }

    @Transactional
    public List<SectionEntity> getAllSections() {
        return em.createQuery(
                "from SectionEntity",
                SectionEntity.class
        ).getResultList();
    }

    @Transactional
    public Map<String, Object> getSectionsAsMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        getAllSections().forEach(x -> result.put(x.getName(), x.getName()));
        return result;
    }

    @Transactional
    public SectionEntity getSectionByName(String name) {
        List<SectionEntity> tmp = em.createQuery(
                "from SectionEntity where name = :_name",
                SectionEntity.class
        ).setParameter("_name", name).getResultList();

        return tmp.isEmpty() ? null : tmp.get(0);
    }

    @Transactional
    public void createSection(String name) {
        em.persist(new SectionEntity(name));
    }

    @Transactional
    public void removeSection(String name) {
        SectionEntity section = getSectionByName(name);
        if (section != null) {
            for (CategoryEntity c : section.getCategories()) em.remove(c);
            em.remove(section);
        } else
            System.out.println("Section " + name + " not found");
    }
}
