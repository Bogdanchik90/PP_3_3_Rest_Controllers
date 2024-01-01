package ru.kata.spring.boot_security.demo.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByIdIn(List<Integer> roleIds);

//    Optional<Role> findRoleByRoleName(String name);
    Optional<Role> findRoleByName(String name);
}
