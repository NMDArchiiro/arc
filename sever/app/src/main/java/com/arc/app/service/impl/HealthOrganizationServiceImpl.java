package com.arc.app.service.impl;

import com.arc.app.entity.AdminUnit;
import com.arc.app.entity.HealthOrganization;
import com.arc.app.repository.AdminUnitRepository;
import com.arc.app.repository.HealthOrganizationRepository;
import com.arc.app.request.AdminUnitRequest;
import com.arc.app.request.HealthOrganizationRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.HealthOrganizationService;
import com.arc.app.utils.enums.ResponseEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

@Transactional
@Service
public class HealthOrganizationServiceImpl implements HealthOrganizationService {
    @Resource
    private HealthOrganizationRepository healthOrganizationRepository;

    @Resource
    private AdminUnitRepository adminUnitRepository;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public ResponseObject find(Long id) {
        try {
            HealthOrganization healthOrganization = healthOrganizationRepository.findById(id).orElse(null);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new HealthOrganizationRequest(healthOrganization, true));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean duplicate(String code) {
        try {
            Long countDuplicate = healthOrganizationRepository.countDuplicate(code);
            return countDuplicate > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ResponseObject save(HealthOrganizationRequest request) {
        try {
            HealthOrganization healthOrganization = null;
            if(request.getId()!= null) {
                healthOrganization = healthOrganizationRepository.findById(request.getId()).orElse(null);
            }
            if(healthOrganization == null) {
                healthOrganization = new HealthOrganization();
            }
            healthOrganization.setCode(request.getCode());
            healthOrganization.setName(request.getName());
            if(request.getParent() != null && request.getParent().getId() != null) {
                HealthOrganization parent = healthOrganizationRepository.findById(request.getParent().getId()).orElse(null);
                healthOrganization.setParent(parent);
            }
            if(request.getProvince() != null && request.getProvince().getId()!= null) {
                AdminUnit province = adminUnitRepository.findById(request.getProvince().getId()).orElse(null);
                healthOrganization.setProvince(province);
            }
            if(request.getDistrict() != null && request.getDistrict().getId()!= null) {
                AdminUnit district = adminUnitRepository.findById(request.getDistrict().getId()).orElse(null);
                healthOrganization.setDistrict(district);
            }
            if(request.getCommune() != null && request.getCommune().getId()!= null) {
                AdminUnit commune = adminUnitRepository.findById(request.getCommune().getId()).orElse(null);
                healthOrganization.setCommune(commune);
            }
            healthOrganizationRepository.save(healthOrganization);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new HealthOrganizationRequest(healthOrganization, false));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean delete(Long id) {
        if(id != null) {
            HealthOrganization healthOrganization = healthOrganizationRepository.findById(id).orElse(null);
            if(healthOrganization != null) {
                healthOrganizationRepository.delete(healthOrganization);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseObject setLocked(Long id) {
        if(id != null) {
            HealthOrganization healthOrganization = healthOrganizationRepository.findById(id).orElse(null);
            if(healthOrganization != null) {
                healthOrganization.setLocked(true);
                healthOrganizationRepository.save(healthOrganization);
                return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new HealthOrganizationRequest(healthOrganization, false));
            }
        }
        return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
    }

    @Override
    public Page<HealthOrganizationRequest> getPage(SearchRequest request) {
        if(request != null) {
            int pageIndex = request.getPageIndex();
            int pageSize = request.getPageSize();
            if (pageIndex > 0) {
                pageIndex--;
            } else {
                pageIndex = 0;
            }
            String sqlSelect = "select new com.arc.app.request.HealthOrganizationRequest(e, true) from HealthOrganization e ";
            String sqlCount = "select count(e) from HealthOrganization e ";
            String orderBy = " order by e.createDate desc";
            String where = " where (e.locked is null or e.locked is false) ";
            if(request.getParentId() != null) {
                where += " and (e.parent.id = :parentId)";
            } else {
                where += " and (e.parent is null)";
            }
            if(request.getTextSearch() != null) {
                where += " and ( e.code like :textSearch OR e.name like :textSearch) ";
            }
            sqlSelect += where + orderBy;
            sqlCount += where;
            Query query = manager.createQuery(sqlSelect, HealthOrganizationRequest.class);
            Query queryCount = manager.createQuery(sqlCount);
            if(request.getParentId() != null) {
                query.setParameter("parentId", request.getParentId());
                queryCount.setParameter("parentId", request.getParentId());
            }
            if(request.getTextSearch() != null) {
                query.setParameter("textSearch", '%' + request.getTextSearch() + '%');
                queryCount.setParameter("textSearch", '%' + request.getTextSearch() + '%');
            }
            query.setFirstResult(pageIndex * pageSize);
            query.setMaxResults(request.getPageSize());
            List<HealthOrganizationRequest> listData = query.getResultList();
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Long numberResult = (Long) queryCount.getSingleResult();
            return new PageImpl<>(listData, pageable, numberResult);
        }
        return null;
    }

    @Override
    public ResponseObject saveListData(InputStream inputStream) {
        return null;
    }
}
