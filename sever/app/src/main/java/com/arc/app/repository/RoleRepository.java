package com.arc.app.repository;

import com.arc.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select e from Role e")
    List<Role> findList();

    @Query(value = "select count(e) from Role e where e.name like :name")
    Long countDuplicate(String name);
}
