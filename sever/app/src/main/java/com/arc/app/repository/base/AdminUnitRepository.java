package com.arc.app.repository.base;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.dto.AdminUnitDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUnitRepository extends JpaRepository<AdminUnit, Long> {
    @Query(value = "select count(e) from AdminUnit e where (e.locked is null  or e.locked is false) and e.code like :code")
    Long countDuplicate(String code);

    @Query(value = "select new com.arc.app.request.base.AdminUnitRequest(e, false ) from AdminUnit e where (e.locked is null  or e.locked is false) and e.code like :code")
    List<AdminUnitRequest> findDuplicateCode(String code);

    @Query("select new com.arc.app.request.dto.AdminUnitDto(e,true) from AdminUnit e " +
            "where (e.locked is null OR e.locked = false) " +
            "and e.parent is null order by e.code")
    List<AdminUnitDto> findAllParentSimple();

    @Query("Select e From AdminUnit e Where (e.locked is null OR e.locked = false) AND e.parent is not null and e.parent.parent is not null and e.level = :level")
    List<AdminUnit> getAllCommune(Integer level);
}
