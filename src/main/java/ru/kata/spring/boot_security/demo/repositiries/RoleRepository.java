package ru.kata.spring.boot_security.demo.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Set<Role> findByIdIn(Set<Integer> roleIds);

}
