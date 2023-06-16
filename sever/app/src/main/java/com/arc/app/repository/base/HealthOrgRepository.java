package com.arc.app.repository.base;

import com.arc.app.entity.base.HealthOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthOrgRepository extends JpaRepository<HealthOrg, Long> {
    @Query(value = "select count(e) from HealthOrg e where (e.locked is null  or e.locked is false) and e.code like :code")
    Long countDuplicate(String code);
}
