package com.arc.app.service.report.impl;

import com.arc.app.entity.report.HIVReport;
import com.arc.app.repository.report.HIVReportRepository;
import com.arc.app.request.base.UserRequest;
import com.arc.app.request.report.HIVReportRequest;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.service.report.HIVReportService;
import com.arc.app.service.report.HRIReportService;
import com.arc.app.utils.constants.HIVConstants;
import com.arc.app.utils.enums.AccountTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class HIVReportServiceImpl implements HIVReportService {
    @Resource
    private UserSecurityService userSecurityService;
    @Resource
    private HIVReportRepository hivReportRepository;
    @Resource
    private HRIReportService hriReportService;

    @Override
    public HIVReportRequest getHIVReportForm(Long id) {
        try {
            HIVReport hivReport = hivReportRepository.findById(id).orElse(null);
            if (hivReport != null) {
                return new HIVReportRequest(hivReport);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HIVReportRequest saveHIVReport(Long id) {
        return null;
    }

    @Override
    public HIVReportRequest setRole(HIVReportRequest request, Integer... type) {
        UserRequest currentAccount = userSecurityService.getCurrentUser();
        Integer typeAccount = userSecurityService.getAccountType();
        boolean isAdmin = userSecurityService.isAdmin();
        boolean isRoleView = userSecurityService.isRoleView();
        boolean isRoleEdit = userSecurityService.isRoleEdit();
        boolean isRoleConfirm = userSecurityService.isRoleConfirm();
        if (type != null && type.length > 0 && typeAccount != null) {
            if (type[0].equals(HIVConstants.VIEW_QUARTER_WARD)) {
                // Quy xa
                request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()));
                request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.COMMUNE.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
                request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                request.setCanConfirmSame((isAdmin || isRoleConfirm) && (typeAccount.equals(AccountTypeEnum.COMMUNE.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
            }
            if (type[0].equals(HIVConstants.VIEW_QUARTER_AFFILIATE_DISTRICT)) {
                // Quy don vi truc thuoc huyen
                request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()));
                request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.UNDER_DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
                request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                request.setCanConfirmSame((isAdmin || isRoleConfirm) && (typeAccount.equals(AccountTypeEnum.UNDER_DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
            }
            if (type[0].equals(HIVConstants.VIEW_QUARTER_DISTRICT)) {
                // Quy huyen
                request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()));
                request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                request.setCanConfirmSame((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
            }
            if (type[0].equals(HIVConstants.VIEW_QUARTER_AFFILIATE_PROVINCE)) {
                // Quy don vi truc thuoc tinh
                request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()));
                request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                request.setCanConfirmSame((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
            }
            if (type[0].equals(HIVConstants.VIEW_QUARTER_PROVINCE)) {
                // Quy tinh
                request.setCanConfirm((isAdmin || isRoleConfirm) && (typeAccount.equals(AccountTypeEnum.VAAC.getKey()) || typeAccount.equals(AccountTypeEnum.AREA.getKey())));
                request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                request.setCanConfirmSame((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()));
            }
        }
        if (request != null && typeAccount != null) {
            if (request.getCommune() != null) {
                // Xa
                request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()));
                request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.COMMUNE.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
                request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                request.setCanConfirmSame((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.COMMUNE.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
            } else {
                if (request.getDistrict() != null) {
                    if (request.getHealthOrg() != null) {
                        // Don vi truc thuoc huyen
                        request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()));
                        request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.UNDER_DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
                        request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                        request.setCanConfirmSame((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.UNDER_DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey())));
                    } else {
                        // Huyen
                        request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()));
                        request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                        request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                        request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                    }
                } else {
                    if (request.getProvince() != null) {
                        if (request.getHealthOrg() != null) {
                            // Don vi truc thuoc tinh
                            request.setCanConfirm((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()));
                            request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                            request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                            request.setCanConfirmSame((isAdmin || isRoleConfirm) && (typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                        } else {
                            // Tinh
                            request.setCanConfirm((isAdmin || isRoleConfirm) && (typeAccount.equals(AccountTypeEnum.VAAC.getKey()) || typeAccount.equals(AccountTypeEnum.AREA.getKey())));
                            request.setCanEdit((isAdmin || isRoleEdit) && (typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())));
                            request.setCanView(isAdmin || isRoleView || isRoleEdit || isRoleConfirm);
                            request.setCanConfirmSame((isAdmin || isRoleConfirm) && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()));
                        }
                    }
                }
            }
            if (request.getHealthOrg() != null && (typeAccount.equals(AccountTypeEnum.PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.DISTRICT.getKey()))) {
                if (request.getHriReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setHriReport(hriReportService.getHRIReport(null));
                }
            }
            if (typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                if (currentAccount.getHasHIReport() == null || !currentAccount.getHasHIReport()) {
                    request.setHriReport(null);
                } else {
                    if (request.getHriReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setHriReport(hriReportService.getHRIReport(null));
                    }
                }
            }
        }
        return request;
    }

    @Override
    public HIVReportRequest createQuarterCommune(Long id) {
        if (userSecurityService.getAccountType() != null) {
            // Bao cao quy xa
            HIVReportRequest result = new HIVReportRequest();
            result.setHriReport(hriReportService.getHRIReport(null));
            return setRole(result, HIVConstants.VIEW_QUARTER_WARD);
        }
        return null;
    }
}
