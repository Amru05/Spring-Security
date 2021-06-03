package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager = null;

    @Override
    public Role getById(Long id) {
        try {
            return entityManager.createQuery("FROM Role WHERE id = :id_param", Role.class)
                    .setParameter("id_param", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Role getByName(String name) {
        try {
            return entityManager.createQuery("FROM Role WHERE roleName = :roleName_param", Role.class)
                    .setParameter("roleName_param", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role").getResultList();
    }

    @Override
    public void createRole(String role) {
        entityManager.persist(new Role(role));
    }

}
