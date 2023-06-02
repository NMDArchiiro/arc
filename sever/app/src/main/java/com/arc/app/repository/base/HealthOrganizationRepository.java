package com.arc.app.repository.base;

import com.arc.app.entity.base.HealthOrganization;
import com.arc.app.request.base.HealthOrganizationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthOrganizationRepository extends JpaRepository<HealthOrganization, Long> {
    @Query(value = "select count(e) from HealthOrganization e where (e.locked is null  or e.locked is false) and e.code like :code")
    Long countDuplicate(String code);

    @Query(value = "select new com.arc.app.request.base.HealthOrganizationRequest(e, false) from HealthOrganization e where (e.locked is null  or e.locked is false) and e.code like :code")
    List<HealthOrganizationRequest> findDuplicateCode(String code);
}
