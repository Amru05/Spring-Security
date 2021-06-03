package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {
    Role getById(Long id);

    Role getByName(String name);

    List<Role> getAllRoles();

    void createRole(String role);
}
