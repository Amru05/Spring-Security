package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("FROM User").getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delUser(Long id) {
        entityManager.createQuery("DELETE FROM User WHERE id = :id_param")
                .setParameter("id_param", id)
                .executeUpdate();
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getById(Long id) {
        try {
            return entityManager.createQuery("FROM User WHERE id = :id_param", User.class)
                    .setParameter("id_param", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getByUsername(String username) {
        try {
            return entityManager.createQuery("FROM User WHERE username = :username_param", User.class)
                    .setParameter("username_param", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
