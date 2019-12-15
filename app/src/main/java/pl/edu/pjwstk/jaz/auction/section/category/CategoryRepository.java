package pl.edu.pjwstk.jaz.auction.section.category;

import pl.edu.pjwstk.jaz.auction.parameter.ParameterEntity;
import pl.edu.pjwstk.jaz.auction.section.SectionEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.TransientReference;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@ApplicationScoped
public class CategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createCategory(String categoryName, Long section_id){
        em.persist(new CategoryEntity(categoryName));
    }

    @Transactional
    public void createCategory(CategoryEntity categoryEntity){
        em.persist(categoryEntity);
    }

    @Transactional
    public void removeCategory(String name){
        CategoryEntity entity = getCategoryByName(name);
        if (entity != null)
            em.remove(entity);
        else
            System.out.println("Category " + name + " not found");
    }

    @Transactional
    public CategoryEntity getCategory(Long id) {
        return em.find(CategoryEntity.class, id);
    }

    @Transactional
    public List<CategoryEntity> getAllCategories() {
        return em.createQuery(
                "from CategoryEntity",
                CategoryEntity.class
        ).getResultList();
    }

    @Transactional
    public Map<String, Object> getCategoriesAsMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        getAllCategories().forEach(x -> result.put(x.getName(), x.getName()));
        return result;
    }

    @Transactional
    public CategoryEntity getCategoryByName(String name) {
        List<CategoryEntity> tmp = em.createQuery(
                "from CategoryEntity where name = :_name",
                CategoryEntity.class
        ).setParameter("_name", name).getResultList();

        return tmp.isEmpty() ? null : tmp.get(0);
    }
}
