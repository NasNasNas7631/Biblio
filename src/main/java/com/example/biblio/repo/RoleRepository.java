package com.example.biblio.repo;

import com.example.biblio.obj.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}