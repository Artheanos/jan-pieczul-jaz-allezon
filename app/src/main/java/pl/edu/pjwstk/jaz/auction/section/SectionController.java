package pl.edu.pjwstk.jaz.auction.section;

import pl.edu.pjwstk.jaz.auction.parameter.ParameterRequest;
import pl.edu.pjwstk.jaz.auction.section.category.CategoryEntity;
import pl.edu.pjwstk.jaz.auction.section.category.CategoryRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;


@Named
@RequestScoped
public class SectionController {

    @Inject
    private CategoryRepository categoryRepository;
    @Inject
    private SectionRepository sectionRepository;

    @Inject
    private ParameterRequest parameterRequest;

    public SectionEntity getSection(long id) {
        return sectionRepository.getSection(id);
    }

    public List<SectionEntity> getAllSections() {
        return sectionRepository.getAllSections();
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public Map<String, Object> getCategoriesAsMap() {
        return categoryRepository.getCategoriesAsMap();
    }

    public Map<String, Object> getSectionsAsMap() {
        return sectionRepository.getSectionsAsMap();
    }

    public String commitSections() {
        for (String s : parameterRequest.getSplitAddedParameters())
            sectionRepository.createSection(s);

        for (String s : parameterRequest.getSplitRemovedParameters())
            sectionRepository.removeSection(s);

        return "editsections.xhtml?faces-redirect=true";
    }

    public String commitCategories(/* Section id is taken from requestParameterMap */) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        long section_id = Long.parseLong(params.get("section_id_parameter"));

        for (String s : parameterRequest.getSplitAddedParameters())
            categoryRepository.createCategory(new CategoryEntity(
                    s,
                    sectionRepository.getSection(section_id)
            ));

        for (String s : parameterRequest.getSplitRemovedParameters())
            categoryRepository.removeCategory(s);

        return "editcategories.xhtml?faces-redirect=true&section_id=" + section_id;
    }
}
