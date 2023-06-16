package com.arc.app.service.base.impl;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.entity.base.HealthOrg;
import com.arc.app.repository.base.AdminUnitRepository;
import com.arc.app.repository.base.HealthOrgRepository;
import com.arc.app.request.base.HealthOrgRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.base.HealthOrganizationService;
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
    private HealthOrgRepository healthOrgRepository;

    @Resource
    private AdminUnitRepository adminUnitRepository;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public ResponseObject find(Long id) {
        try {
            HealthOrg healthOrg = healthOrgRepository.findById(id).orElse(null);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new HealthOrgRequest(healthOrg, true));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean duplicate(String code) {
        try {
            Long countDuplicate = healthOrgRepository.countDuplicate(code);
            return countDuplicate > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ResponseObject save(HealthOrgRequest request) {
        try {
            HealthOrg healthOrg = null;
            if(request.getId()!= null) {
                healthOrg = healthOrgRepository.findById(request.getId()).orElse(null);
            }
            if(healthOrg == null) {
                healthOrg = new HealthOrg();
            }
            healthOrg.setCode(request.getCode());
            healthOrg.setName(request.getName());
            if(request.getParent() != null && request.getParent().getId() != null) {
                HealthOrg parent = healthOrgRepository.findById(request.getParent().getId()).orElse(null);
                healthOrg.setParent(parent);
            }
            if(request.getProvince() != null && request.getProvince().getId()!= null) {
                AdminUnit province = adminUnitRepository.findById(request.getProvince().getId()).orElse(null);
                healthOrg.setProvince(province);
            }
            if(request.getDistrict() != null && request.getDistrict().getId()!= null) {
                AdminUnit district = adminUnitRepository.findById(request.getDistrict().getId()).orElse(null);
                healthOrg.setDistrict(district);
            }
            if(request.getCommune() != null && request.getCommune().getId()!= null) {
                AdminUnit commune = adminUnitRepository.findById(request.getCommune().getId()).orElse(null);
                healthOrg.setCommune(commune);
            }
            healthOrgRepository.save(healthOrg);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new HealthOrgRequest(healthOrg, false));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean delete(Long id) {
        if(id != null) {
            HealthOrg healthOrg = healthOrgRepository.findById(id).orElse(null);
            if(healthOrg != null) {
                healthOrgRepository.delete(healthOrg);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseObject setLocked(Long id) {
        if(id != null) {
            HealthOrg healthOrg = healthOrgRepository.findById(id).orElse(null);
            if(healthOrg != null) {
                healthOrg.setLocked(true);
                healthOrgRepository.save(healthOrg);
                return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new HealthOrgRequest(healthOrg, false));
            }
        }
        return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
    }

    @Override
    public Page<HealthOrgRequest> getPage(SearchRequest request) {
        if(request != null) {
            int pageIndex = request.getPageIndex();
            int pageSize = request.getPageSize();
            if (pageIndex > 0) {
                pageIndex--;
            } else {
                pageIndex = 0;
            }
            String sqlSelect = "select new com.arc.app.request.base.HealthOrgRequest(e, true) from HealthOrg e ";
            String sqlCount = "select count(e) from HealthOrg e ";
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
            Query query = manager.createQuery(sqlSelect, HealthOrgRequest.class);
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
            List<HealthOrgRequest> listData = query.getResultList();
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
