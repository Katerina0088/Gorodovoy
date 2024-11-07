package com.zags.gorodovoy.Services.RoleService;

import com.zags.gorodovoy.models.Role;
import com.zags.gorodovoy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAllRoles();
    }
}
