package com.arc.app.repository;

import com.arc.app.entity.AdminUnit;
import com.arc.app.request.AdminUnitRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUnitRepository extends JpaRepository<AdminUnit, Long> {
    @Query(value = "select count(e) from AdminUnit e where (e.locked is null  or e.locked is false) and e.code like :code")
    Long countDuplicate(String code);

    @Query(value = "select new com.arc.app.request.AdminUnitRequest(e, false ) from AdminUnit e where (e.locked is null  or e.locked is false) and e.code like :code")
    List<AdminUnitRequest> findDuplicateCode(String code);
}
