package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    void addUser(User user);

    void delUser(Long id);

    void editUser(User user);

    User getById(Long id);

    User getByUsername(String username);

    List<Role> getAllRoles();

    Role getRoleByName(String name);

    void initDB();
}
