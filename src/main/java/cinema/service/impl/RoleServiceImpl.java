package cinema.service.impl;

import cinema.dao.RoleDao;
import cinema.model.Role;
import cinema.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao dao;

    public RoleServiceImpl(RoleDao dao) {
        this.dao = dao;
    }

    @Override
    public Role add(Role role) {
        return dao.add(role);
    }

    @Override
    public Role getByName(String roleName) {
        return dao.getByName(roleName).orElseThrow(
                () -> new RuntimeException("Can't found role by name: " + roleName));
    }
}
