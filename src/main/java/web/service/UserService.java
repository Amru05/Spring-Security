package web.service;

import org.springframework.stereotype.Service;
import web.model.User;

import java.util.List;

@Service
public interface UserService {
    List<User> getUsers();

    void addUser(User user);

    void delUser(Long id);

    void editUser(User user);

    User getById(Long id);
}
