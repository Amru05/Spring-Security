package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    public void delUser(Long id) {
        userDao.delUser(id);
    }

    @Override
    public void editUser(User user) {
        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDao.getById(user.getId()).getPassword());
        }
        userDao.editUser(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByUsername(String name) {
        return userDao.getByUsername(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.getByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException("Unknow user: " + name);
        }
        return user;
    }

    @PostConstruct
    public void initDB() {

        if (roleDao.getByName("ADMIN") == null) {
            System.out.println("Let's create a role 'ADMIN' necessary for work");
            roleDao.createRole("ADMIN");
        }
        if (roleDao.getByName("USER") == null) {
            System.out.println("Let's create a role 'ADMIN' necessary for work");
            roleDao.createRole("USER");
        }
        if (userDao.getByUsername("admin") == null) {
            System.out.println("Let's create a user: 'admin' with password: 'password' necessary for work");
            User user = new User("admin",
                    "password",
                    true,
                    new HashSet<Role>() {{
                        add(roleDao.getByName("ADMIN"));
                        add(roleDao.getByName("USER"));
                    }});
            addUser(user);
        }
    }

}
