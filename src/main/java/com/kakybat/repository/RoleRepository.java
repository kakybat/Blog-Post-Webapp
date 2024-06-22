package com.kakybat.repository;

import com.kakybat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    Role findByRoleName(String roleName);
    Role getByRoleName(String roleName);
}
