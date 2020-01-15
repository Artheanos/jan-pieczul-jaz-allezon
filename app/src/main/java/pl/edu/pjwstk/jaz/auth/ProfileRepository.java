package pl.edu.pjwstk.jaz.auth;

import pl.edu.pjwstk.jaz.register.RegisterRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import org.springframework.security.crypto.bcrypt.*;

@ApplicationScoped
public class ProfileRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ProfileEntity getProfile(String key, String value) {
        String qlString;

        switch (key) {
            case "email":
                qlString = "from ProfileEntity where email = :_value";
                break;
            case "username":
                qlString = "from ProfileEntity where username = :_value";
                break;

            default:
                return null;
        }

        List<ProfileEntity> list = em.createQuery(
                qlString,
                ProfileEntity.class
        )
                .setParameter("_value", value).getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public boolean profileExists(String key, String value) {
        return getProfile(key, value) != null;
    }

    @Transactional
    public boolean profileMatchesPassword(ProfileEntity profile, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, profile.getEncryptedPassword());
    }

    @Transactional
    public ProfileEntity createProfile(RegisterRequest registerRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return new ProfileEntity(
                registerRequest.getName(),
                registerRequest.getSurname(),
                registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail(),
                registerRequest.getBirthDate()
        );
    }

    @Transactional
    public void saveProfile(ProfileEntity profileEntity) {
        em.persist(profileEntity);
    }
}
