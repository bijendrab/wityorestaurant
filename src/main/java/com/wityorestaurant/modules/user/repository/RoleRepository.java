package com.wityorestaurant.modules.user.repository;

import com.wityorestaurant.modules.user.model.Role;
import com.wityorestaurant.modules.user.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
