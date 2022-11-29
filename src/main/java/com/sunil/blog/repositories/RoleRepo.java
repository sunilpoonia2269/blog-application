package com.sunil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunil.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
