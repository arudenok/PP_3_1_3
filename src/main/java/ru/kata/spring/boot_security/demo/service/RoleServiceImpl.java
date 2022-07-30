package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> getAllRoles() {
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        return roles;
    }

    @Override
    public Set<Role> getByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select role from Role role where role.name = :name", Role.class);
        query.setParameter("name", name);
        return query.getResultStream().collect(Collectors.toSet());
    }
}
