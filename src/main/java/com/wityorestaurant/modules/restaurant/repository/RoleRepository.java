package com.wityorestaurant.modules.restaurant.repository;

import com.wityorestaurant.modules.restaurant.model.Role;
import com.wityorestaurant.modules.restaurant.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
