package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delUser(Long id) {
        entityManager.createQuery("delete from User where id = :id_param")
                .setParameter("id_param", id)
                .executeUpdate();
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getById(Long id) {
        User user = entityManager.createQuery("from User where id = :id_param", User.class)
                .setParameter("id_param", id)
                .getSingleResult();
        return user;
    }
}
