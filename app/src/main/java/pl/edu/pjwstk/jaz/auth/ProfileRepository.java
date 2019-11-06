package pl.edu.pjwstk.jaz.auth;

import pl.edu.pjwstk.jaz.register.RegisterRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.*;

@ApplicationScoped
public class ProfileRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean profileExists(Map<String, String> map) {
        StringBuilder query = new StringBuilder();
        query.append("from ProfileEntity where ");

        Iterator it = map.entrySet().iterator();
        Map.Entry pair = (Map.Entry) it.next();

        query.append(pair.getKey()).append(" = '").append(pair.getValue()).append("'");

        while (it.hasNext()) {
            pair = (Map.Entry) it.next();
            query.append(" and ").append(pair.getKey()).append(" = '").append(pair.getValue()).append("'");
        }

        List list = em.createQuery(query.toString(), ProfileEntity.class).getResultList();

        return !list.isEmpty();
    }

    @Transactional
    public boolean profileExists(String key, String value) {
        List list = em.createQuery(String.format(
                "from ProfileEntity where %s = '%s'", key, value
        ), ProfileEntity.class).getResultList();

        return !list.isEmpty();
    }

    @Transactional
    public ProfileEntity getProfile(String key, String value) {
        List<ProfileEntity> list = em.createQuery(String.format(
                "from ProfileEntity where %s = '%s'", key, value
        ), ProfileEntity.class).getResultList();

        if (!list.isEmpty())
            return list.get(0);
        else
            return null;
    }

    @Transactional
    public boolean profileMatchesPassword(ProfileEntity profile, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, profile.getEncryptedPassword());
    }

    @Transactional
    public void createProfile(RegisterRequest registerRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        ProfileEntity profile = new ProfileEntity(
                registerRequest.getName(),
                registerRequest.getSurname(),
                registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail(),
                registerRequest.getBirthDate()
        );

        em.persist(profile);
    }
}
