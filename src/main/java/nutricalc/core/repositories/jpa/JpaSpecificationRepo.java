package nutricalc.core.repositories.jpa;

import nutricalc.core.models.entities.Specification;
import nutricalc.core.repositories.SpecificationRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaSpecificationRepo implements SpecificationRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Specification addSpecification(Specification data) {
        em.persist(data);
        return data;
    }

    @Override
    public List searchSpecificationsByTitle(String specificationTitle) {
        Query query = em.createQuery("SELECT s from Specification s where s.title=?1");
        query.setParameter(1, specificationTitle);
        List specifications = query.getResultList();
        if(specifications.isEmpty()){
            return null;
        }else {
            return specifications;
        }
    }

    @Override
    public Specification findSpecification(Long id) {
        return em.find(Specification.class, id);
    }

    @Override
    public Specification updateSpecification(Long id, Specification data) {
        Specification specification = em.find(Specification.class, id);
        specification.setTitle(data.getName(), data.getProducer());
        specification.setEnergyKj(data.getEnergyKj());
        specification.setEnergyKCal(data.getEnergyKCal());
        specification.setCarbohydrates(data.getCarbohydrates(), data.getSugars());
        specification.setFats(data.getFats(), data.getSaturatedFats());
        specification.setProtein(data.getProtein());
        specification.setSalt(data.getSalt());
        specification.setComment(data.getComment());
        return specification;
    }

    @Override
    public List<Specification> findAllSpecifications() {
        Query query = em.createQuery("SELECT s from Specification s");
        return query.getResultList();
    }
}
