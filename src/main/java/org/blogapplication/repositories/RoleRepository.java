package org.blogapplication.repositories;

import org.blogapplication.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
}
