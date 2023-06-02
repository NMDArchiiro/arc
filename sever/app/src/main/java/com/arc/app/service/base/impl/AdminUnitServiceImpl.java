package com.arc.app.service.base.impl;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.repository.base.AdminUnitRepository;
import com.arc.app.request.base.AdminUnitImportRequest;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.base.AdminUnitService;
import com.arc.app.utils.constants.ARCConstants;
import com.arc.app.utils.enums.ResponseEnum;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AdminUnitServiceImpl implements AdminUnitService {
    @Resource
    private AdminUnitRepository adminUnitRepository;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public ResponseObject find(Long id) {
        try {
            AdminUnit adminUnit = adminUnitRepository.findById(id).orElse(null);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new AdminUnitRequest(adminUnit, true));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean duplicate(String code) {
        try {
            Long countDuplicate = adminUnitRepository.countDuplicate(code);
            return countDuplicate > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ResponseObject save(AdminUnitRequest request) {
        try {
            AdminUnit adminUnit = null;
            if(request.getId()!= null) {
                adminUnit = adminUnitRepository.findById(request.getId()).orElse(null);
            }
            if(adminUnit == null) {
                adminUnit = new AdminUnit();
            }
            adminUnit.setCode(request.getCode());
            adminUnit.setName(request.getName());
            adminUnit.setLevel(request.getLevel());
            if(request.getParent() != null && request.getParent().getId() != null) {
                AdminUnit parent = adminUnitRepository.findById(request.getParent().getId()).orElse(null);
                adminUnit.setParent(parent);
            }
            adminUnitRepository.save(adminUnit);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), adminUnit);
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean delete(Long id) {
        if(id != null) {
            AdminUnit adminUnit = adminUnitRepository.findById(id).orElse(null);
            if(adminUnit != null) {
                adminUnitRepository.delete(adminUnit);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseObject setLocked(Long id) {
        if(id != null) {
            AdminUnit adminUnit = adminUnitRepository.findById(id).orElse(null);
            if(adminUnit != null) {
                adminUnit.setLocked(true);
                adminUnitRepository.save(adminUnit);
                return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), adminUnit);
            }
        }
        return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
    }

    @Override
    public Page<AdminUnitRequest> getPage(SearchRequest request) {
        if(request != null) {
            int pageIndex = request.getPageIndex();
            int pageSize = request.getPageSize();
            if (pageIndex > 0) {
                pageIndex--;
            } else {
                pageIndex = 0;
            }
            String sqlSelect = "select new com.arc.app.request.AdminUnitRequest(e, true) from AdminUnit e ";
            String sqlCount = "select count(e) from AdminUnit e ";
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
            Query query = manager.createQuery(sqlSelect, AdminUnitRequest.class);
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
            List<AdminUnitRequest> listData = query.getResultList();
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Long numberResult = (Long) queryCount.getSingleResult();
            return new PageImpl<>(listData, pageable, numberResult);
        }
        return null;
    }

    @Override
    public ResponseObject saveListData(InputStream inputStream) {
        try {
            List<AdminUnitImportRequest> listData = this.readDataFromExcel(inputStream);
            if(!CollectionUtils.isEmpty(listData)) {
                for(AdminUnitImportRequest request : listData) {
                    // Tinh
                    AdminUnitRequest province = null;
                    if(request.getCodeProvince() != null) {
                        List<AdminUnitRequest> listUnit = adminUnitRepository.findDuplicateCode(request.getCodeProvince());
                        if(!CollectionUtils.isEmpty(listUnit)) {
                            province = listUnit.get(0);
                        }
                        if(province == null) {
                            province = new AdminUnitRequest();
                            province.setCode(request.getCodeProvince());
                            province.setName(request.getNameProvince());
                            province.setLevel(ARCConstants.LEVEL_PROVINCE);
                            this.save(province);
                        }
                    }
                    // Huyen
                    AdminUnitRequest district = null;
                    if(request.getCodeDistrict() != null) {
                        List<AdminUnitRequest> listUnit = adminUnitRepository.findDuplicateCode(request.getCodeDistrict());
                        if(!CollectionUtils.isEmpty(listUnit)) {
                            district = listUnit.get(0);
                        }
                        if(district == null) {
                            district = new AdminUnitRequest();
                            district.setCode(request.getCodeDistrict());
                            district.setName(request.getNameDistrict());
                            district.setLevel(ARCConstants.LEVEL_DISTRICT);
                            district.setParent(province);
                            this.save(district);
                        }
                    }
                    // Huyen
                    AdminUnitRequest commune = null;
                    if(request.getCodeCommune() != null) {
                        List<AdminUnitRequest> listUnit = adminUnitRepository.findDuplicateCode(request.getCodeCommune());
                        if(!CollectionUtils.isEmpty(listUnit)) {
                            commune = listUnit.get(0);
                        }
                        if(commune == null) {
                            commune = new AdminUnitRequest();
                            commune.setCode(request.getCodeCommune());
                            commune.setName(request.getNameCommune());
                            commune.setLevel(ARCConstants.LEVEL_COMMUNE);
                            commune.setParent(district);
                            this.save(commune);
                        }
                    }
                }
            }
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), null);
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    public List<AdminUnitImportRequest> readDataFromExcel(InputStream inputStream) {
        List<AdminUnitImportRequest> result = new ArrayList<>();
        try {
            Workbook wb = new XSSFWorkbook(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            int rowIndex = 1;
            int lastRow = sheet.getLastRowNum();
            Row row = null;
            Cell cell = null;
            while (rowIndex <= lastRow) {
                row = sheet.getRow(rowIndex);
                if(row != null) {
                    AdminUnitImportRequest request = new AdminUnitImportRequest();
                    // Ten tinh
                    cell = row.getCell(0);
                    if(cell!= null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                request.setNameProvince(cell.getStringCellValue().trim());
                                break;
                            case NUMERIC:
                                request.setNameProvince(String.valueOf(cell.getNumericCellValue()));
                                break;
                        }
                    }
                    // Code tinh
                    cell = row.getCell(1);
                    if(cell!= null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                request.setCodeProvince(cell.getStringCellValue().trim());
                                break;
                            case NUMERIC:
                                request.setCodeProvince(String.valueOf(cell.getNumericCellValue()));
                                break;
                        }
                    }
                    // Ten huyen
                    cell = row.getCell(2);
                    if(cell!= null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                request.setNameDistrict(cell.getStringCellValue().trim());
                                break;
                            case NUMERIC:
                                request.setNameDistrict(String.valueOf(cell.getNumericCellValue()));
                                break;
                        }
                    }
                    // Code huyen
                    cell = row.getCell(3);
                    if(cell!= null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                request.setCodeDistrict(cell.getStringCellValue().trim());
                                break;
                            case NUMERIC:
                                request.setCodeDistrict(String.valueOf(cell.getNumericCellValue()));
                                break;
                        }
                    }
                    // Ten xa
                    cell = row.getCell(4);
                    if(cell!= null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                request.setNameCommune(cell.getStringCellValue().trim());
                                break;
                            case NUMERIC:
                                request.setNameCommune(String.valueOf(cell.getNumericCellValue()));
                                break;
                        }
                    }
                    // code xa
                    cell = row.getCell(5);
                    if(cell!= null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                request.setCodeCommune(cell.getStringCellValue().trim());
                                break;
                            case NUMERIC:
                                request.setCodeCommune(String.valueOf(cell.getNumericCellValue()));
                                break;
                        }
                    }
                    if (!request.objectIsNew()) {
                        result.add(request);
                    }
                    rowIndex++;
                }
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}
