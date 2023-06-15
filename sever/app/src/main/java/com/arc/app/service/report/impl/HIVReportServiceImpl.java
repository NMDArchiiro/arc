package com.arc.app.service.report.impl;

import com.arc.app.entity.report.HIVReport;
import com.arc.app.repository.base.AdminUnitRepository;
import com.arc.app.repository.base.HealthOrgRepository;
import com.arc.app.repository.report.HIVReportRepository;
import com.arc.app.request.base.UserRequest;
import com.arc.app.request.report.HIVReportRequest;
import com.arc.app.request.search.HIVReportSearchRequest;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.service.report.*;
import com.arc.app.utils.constants.HIVConstants;
import com.arc.app.utils.enums.AccountTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    @Resource
    private ATHReportService athReportService;
    @Resource
    private POCReportService pocReportService;
    @Resource
    private MDReportService mdReportService;
    @Resource
    private ARVReportService arvReportService;
    @Resource
    private CIReportService ciReportService;
    @Resource
    private MTCReportService mtcReportService;
    @Resource
    private PREPReportService prepReportService;
    @Resource
    private CReportService cReportService;
    @Resource
    private RPREPReportService rprepReportService;
    @Resource
    private CIHCReportService cihcReportService;
    @Resource
    private HGReportService hgReportService;
    @Resource
    private SPReportService spReportService;
    @Resource
    private ICReportService icReportService;
    @Resource
    private HIReportService hiReportService;
    @Resource
    private HealthOrgRepository healthOrgRepository;
    @Resource
    private AdminUnitRepository adminUnitRepository;
    private static Logger LOG = LoggerFactory.getLogger(HIVReportServiceImpl.class);

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
    public HIVReportRequest exitsQuarter(HIVReportRequest request) {
        if (request != null) {
            Long adminUnitId = null;
            Long orgId = null;
            if (request.getProvince() != null && request.getProvince().getId() != null) {
                adminUnitId = request.getProvince().getId();
            }
            if (request.getDistrict() != null && request.getDistrict().getId() != null) {
                adminUnitId = request.getDistrict().getId();
            }
            if (request.getCommune() != null && request.getCommune().getId() != null) {
                adminUnitId = request.getCommune().getId();
            }
            if (request.getHealthOrg() != null && request.getHealthOrg().getId() != null) {
                orgId = request.getHealthOrg().getId();
            }
            if (request.getQuarter() == null || request.getYear() == null || adminUnitId == null) {
                return null;
            }
            List<HIVReport> hivReports = hivReportRepository.hivReportExistQuarter(request.getQuarter(), request.getYear(), adminUnitId, orgId);
            if (!CollectionUtils.isEmpty(hivReports)) {
                return setRole(new HIVReportRequest(hivReports.get(0)));
            }
        }
        return null;
    }

    @Override
    public HIVReportRequest exitsYear(HIVReportRequest request) {
        if (request != null) {
            Long adminUnitId = null;
            Long orgId = null;
            if (request.getProvince() != null && request.getProvince().getId() != null) {
                adminUnitId = request.getProvince().getId();
            }
            if (request.getDistrict() != null && request.getDistrict().getId() != null) {
                adminUnitId = request.getDistrict().getId();
            }
            if (request.getHealthOrg() != null && request.getHealthOrg().getId() != null) {
                orgId = request.getHealthOrg().getId();
            }
            if (request.getYear() == null || adminUnitId == null) {
                return null;
            }
            List<HIVReport> hivReports = hivReportRepository.hivReportExistYear(request.getYear(), adminUnitId, orgId);
            if (!CollectionUtils.isEmpty(hivReports)) {
                return setRole(new HIVReportRequest(hivReports.get(0)));
            }
        }
        return null;
    }

    @Override
    public HIVReportRequest createQuarterCommune() {
        if (userSecurityService.getAccountType() != null) {
            // Bao cao quy xa
            HIVReportRequest result = new HIVReportRequest();
            result.setHriReport(hriReportService.getHRIReport(null));
            result.setAthReport(athReportService.getATHReport(null));
            result.setPocReport(pocReportService.getPOCReport(null));
            return setRole(result, HIVConstants.VIEW_QUARTER_WARD);
        }
        return null;
    }

    @Override
    public HIVReportRequest createQuarterDistrict() {
        if (userSecurityService.getAccountType() != null) {
            // Bao cao quy huyen
            // BC1, BC2, BC3, BC4, BC5, BC6, BC7, BC8
            HIVReportRequest result = new HIVReportRequest();
            if (!userSecurityService.getAccountType().equals(AccountTypeEnum.COMMUNE.getKey())
                    && !userSecurityService.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                result.setHriReport(hriReportService.getHRIReport(null));
                result.setAthReport(athReportService.getATHReport(null));
                result.setPocReport(pocReportService.getPOCReport(null));
                result.setMdReport(mdReportService.getMDReport(null));
                result.setArvReport(arvReportService.getARVReport(null));
                result.setCiReport(ciReportService.getCIReport(null));
                result.setMtcReport(mtcReportService.getMTCReport(null));
                result.setPrepReport(prepReportService.getPREPReport(null));
                return setRole(result, HIVConstants.VIEW_QUARTER_DISTRICT);
            } else if (userSecurityService.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                if (userSecurityService.hasHRIReport()) {
                    result.setHriReport(hriReportService.getHRIReport(null));
                }
                if (userSecurityService.hasATHReport()) {
                    result.setAthReport(athReportService.getATHReport(null));
                }
                if (userSecurityService.hasPOCReport()) {
                    result.setPocReport(pocReportService.getPOCReport(null));
                }
                if (userSecurityService.hasMDReport()) {
                    result.setMdReport(mdReportService.getMDReport(null));
                }
                if (userSecurityService.hasARVReport()) {
                    result.setArvReport(arvReportService.getARVReport(null));
                }
                if (userSecurityService.hasCIReport()) {
                    result.setCiReport(ciReportService.getCIReport(null));
                }
                if (userSecurityService.hasMTCReport()) {
                    result.setMtcReport(mtcReportService.getMTCReport(null));
                }
                if (userSecurityService.hasPREPReport()) {
                    result.setPrepReport(prepReportService.getPREPReport(null));
                }
                return setRole(result, HIVConstants.VIEW_QUARTER_AFFILIATE_DISTRICT);
            }
        }
        return null;
    }

    @Override
    public HIVReportRequest createYearDistrict() {
        if (userSecurityService.getAccountType() != null) {
            // Bao cao nam huyen
            // BC10, BC11, BC12
            HIVReportRequest result = new HIVReportRequest();
            if (!userSecurityService.getAccountType().equals(AccountTypeEnum.COMMUNE.getKey())
                    && !userSecurityService.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                result.setRprep(rprepReportService.getRPREPReport(null));
                result.setCihcReport(cihcReportService.getCIHCReport(null));
                result.setHgReport(hgReportService.getHGReport(null));
                return setRole(result, HIVConstants.VIEW_QUARTER_DISTRICT);
            } else if (userSecurityService.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                if (userSecurityService.hasRPREPReport()) {
                    result.setRprep(rprepReportService.getRPREPReport(null));
                }
                if (userSecurityService.hasCIHCReport()) {
                    result.setCihcReport(cihcReportService.getCIHCReport(null));
                }
                if (userSecurityService.hasHGReport()) {
                    result.setHgReport(hgReportService.getHGReport(null));
                }
                return setRole(result, HIVConstants.VIEW_QUARTER_AFFILIATE_DISTRICT);
            }
        }
        return null;
    }

    @Override
    public HIVReportRequest createQuarterProvince() {
        if (userSecurityService.getAccountType() != null) {
            // Bao cao quy tinh
            // BC1, BC2, BC3, BC4, BC5, BC6, BC7, BC8, BC9
            HIVReportRequest result = new HIVReportRequest();
            if (userSecurityService.getAccountType().equals(AccountTypeEnum.PROVINCE.getKey())) {
                result.setHriReport(hriReportService.getHRIReport(null));
                result.setAthReport(athReportService.getATHReport(null));
                result.setPocReport(pocReportService.getPOCReport(null));
                result.setMdReport(mdReportService.getMDReport(null));
                result.setArvReport(arvReportService.getARVReport(null));
                result.setCiReport(ciReportService.getCIReport(null));
                result.setMtcReport(mtcReportService.getMTCReport(null));
                result.setPrepReport(prepReportService.getPREPReport(null));
                result.setCReport(cReportService.getCReport(null));
                return setRole(result, HIVConstants.VIEW_QUARTER_PROVINCE);
            } else if (userSecurityService.getAccountType().equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                if (userSecurityService.hasHRIReport()) {
                    result.setHriReport(hriReportService.getHRIReport(null));
                }
                if (userSecurityService.hasATHReport()) {
                    result.setAthReport(athReportService.getATHReport(null));
                }
                if (userSecurityService.hasPOCReport()) {
                    result.setPocReport(pocReportService.getPOCReport(null));
                }
                if (userSecurityService.hasMDReport()) {
                    result.setMdReport(mdReportService.getMDReport(null));
                }
                if (userSecurityService.hasARVReport()) {
                    result.setArvReport(arvReportService.getARVReport(null));
                }
                if (userSecurityService.hasCIReport()) {
                    result.setCiReport(ciReportService.getCIReport(null));
                }
                if (userSecurityService.hasMTCReport()) {
                    result.setMtcReport(mtcReportService.getMTCReport(null));
                }
                if (userSecurityService.hasPREPReport()) {
                    result.setPrepReport(prepReportService.getPREPReport(null));
                }
                if (userSecurityService.hasPREPReport()) {
                    result.setPrepReport(prepReportService.getPREPReport(null));
                }
                return setRole(result, HIVConstants.VIEW_QUARTER_AFFILIATE_PROVINCE);
            }
        }
        return null;
    }

    @Override
    public HIVReportRequest createYearProvince() {
        if (userSecurityService.getAccountType() != null) {
            // Bao cao nam tinh
            // BC10, BC11, BC12, BC13, BC14, BC15
            HIVReportRequest result = new HIVReportRequest();
            if (userSecurityService.getAccountType().equals(AccountTypeEnum.PROVINCE.getKey())) {
                result.setRprep(rprepReportService.getRPREPReport(null));
                result.setCihcReport(cihcReportService.getCIHCReport(null));
                result.setHgReport(hgReportService.getHGReport(null));
                result.setSpReport(spReportService.getSPReport(null));
                result.setIcReport(icReportService.getICReport(null));
                result.setHiReport(hiReportService.getHIReport(null));
                return setRole(result, HIVConstants.VIEW_QUARTER_PROVINCE);
            } else if (userSecurityService.getAccountType().equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                if (userSecurityService.hasRPREPReport()) {
                    result.setRprep(rprepReportService.getRPREPReport(null));
                }
                if (userSecurityService.hasCIHCReport()) {
                    result.setCihcReport(cihcReportService.getCIHCReport(null));
                }
                if (userSecurityService.hasHGReport()) {
                    result.setHgReport(hgReportService.getHGReport(null));
                }
                if (userSecurityService.hasSPReport()) {
                    result.setSpReport(spReportService.getSPReport(null));
                }
                if (userSecurityService.hasICReport()) {
                    result.setIcReport(icReportService.getICReport(null));
                }
                if (userSecurityService.hasHIReport()) {
                    result.setHiReport(hiReportService.getHIReport(null));
                }
                return setRole(result, HIVConstants.VIEW_QUARTER_AFFILIATE_PROVINCE);
            }
            return setRole(result);
        }
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
                // DVTT tai khoan huyen/tinh
                if (request.getHriReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setHriReport(hriReportService.getHRIReport(null));
                }
                if (request.getAthReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setAthReport(athReportService.getATHReport(null));
                }
                if (request.getCiReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setCiReport(ciReportService.getCIReport(null));
                }
                if (request.getCReport() == null && request.getId() != null && request.getQuarter() != null && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())) {
                    request.setCReport(cReportService.getCReport(null));
                }
                if (request.getArvReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setArvReport(arvReportService.getARVReport(null));
                }
                if (request.getCihcReport() == null && request.getId() != null && request.getQuarter() == null) {
                    request.setCihcReport(cihcReportService.getCIHCReport(null));
                }
                if (request.getSpReport() == null && request.getId() != null && request.getQuarter() == null && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())) {
                    request.setSpReport(spReportService.getSPReport(null));
                }
                if (request.getHiReport() == null && request.getId() != null && request.getQuarter() == null && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())) {
                    request.setHiReport(hiReportService.getHIReport(null));
                }
                if (request.getHgReport() == null && request.getId() != null && request.getQuarter() == null) {
                    request.setHgReport(hgReportService.getHGReport(null));
                }
                if (request.getIcReport() == null && request.getId() != null && request.getQuarter() == null && typeAccount.equals(AccountTypeEnum.PROVINCE.getKey())) {
                    request.setIcReport(icReportService.getICReport(null));
                }
                if (request.getMdReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setMdReport(mdReportService.getMDReport(null));
                }
                if (request.getMtcReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setMtcReport(mtcReportService.getMTCReport(null));
                }
                if (request.getPrepReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setPrepReport(prepReportService.getPREPReport(null));
                }
                if (request.getPocReport() == null && request.getId() != null && request.getQuarter() != null) {
                    request.setPocReport(pocReportService.getPOCReport(null));
                }
                if (request.getRprep() == null && request.getId() != null && request.getQuarter() == null) {
                    request.setRprep(rprepReportService.getRPREPReport(null));
                }
            }
            if (typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || typeAccount.equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                // Neu la tai khoan DVTT Tinh/Huyen
                if (currentAccount.getHasHIReport() == null || !currentAccount.getHasHIReport()) {
                    request.setHriReport(null);
                } else {
                    if (request.getHriReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setHriReport(hriReportService.getHRIReport(null));
                    }
                }
                if (currentAccount.getHasATHReport() == null || !currentAccount.getHasATHReport()) {
                    request.setAthReport(null);
                } else {
                    if (request.getAthReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setAthReport(athReportService.getATHReport(null));
                    }
                }
                if (currentAccount.getHasCIReport() == null || !currentAccount.getHasCIReport()) {
                    request.setCiReport(null);
                } else {
                    if (request.getCiReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setCiReport(ciReportService.getCIReport(null));
                    }
                }
                if (currentAccount.getHasCReport() == null || !currentAccount.getHasCReport()) {
                    request.setCReport(null);
                } else {
                    if (request.getCReport() == null && request.getId() != null && request.getQuarter() != null && typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                        request.setCReport(cReportService.getCReport(null));
                    }
                }
                if (currentAccount.getHasARVReport() == null || !currentAccount.getHasARVReport()) {
                    request.setArvReport(null);
                } else {
                    if (request.getArvReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setArvReport(arvReportService.getARVReport(null));
                    }
                }
                if (currentAccount.getHasCIHCReport() == null || !currentAccount.getHasCIHCReport()) {
                    request.setCihcReport(null);
                } else {
                    if (request.getCihcReport() == null && request.getId() != null && request.getQuarter() == null) {
                        request.setCihcReport(cihcReportService.getCIHCReport(null));
                    }
                }
                if (currentAccount.getHasSPReport() == null || !currentAccount.getHasSPReport()) {
                    request.setSpReport(null);
                } else {
                    if (request.getSpReport() == null && request.getId() != null && request.getQuarter() == null && typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                        request.setSpReport(spReportService.getSPReport(null));
                    }
                }
                if (currentAccount.getHasHIReport() == null || !currentAccount.getHasHIReport()) {
                    request.setHiReport(null);
                } else {
                    if (request.getHiReport() == null && request.getId() != null && request.getQuarter() == null && typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                        request.setHiReport(hiReportService.getHIReport(null));
                    }
                }
                if (currentAccount.getHasHGReport() == null || !currentAccount.getHasHGReport()) {
                    request.setHgReport(null);
                } else {
                    if (request.getHgReport() == null && request.getId() != null && request.getQuarter() == null) {
                        request.setHgReport(hgReportService.getHGReport(null));
                    }
                }
                if (currentAccount.getHasICReport() == null || !currentAccount.getHasICReport()) {
                    request.setIcReport(null);
                } else {
                    if (request.getIcReport() == null && request.getId() != null && request.getQuarter() == null && typeAccount.equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                        request.setIcReport(icReportService.getICReport(null));
                    }
                }
                if (currentAccount.getHasMDReport() == null || !currentAccount.getHasMDReport()) {
                    request.setMdReport(null);
                } else {
                    if (request.getMdReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setMdReport(mdReportService.getMDReport(null));
                    }
                }
                if (currentAccount.getHasMTCReport() == null || !currentAccount.getHasMTCReport()) {
                    request.setMtcReport(null);
                } else {
                    if (request.getMtcReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setMtcReport(mtcReportService.getMTCReport(null));
                    }
                }
                if (currentAccount.getHasPREPReport() == null || !currentAccount.getHasPREPReport()) {
                    request.setPrepReport(null);
                } else {
                    if (request.getPrepReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setPrepReport(prepReportService.getPREPReport(null));
                    }
                }
                if (currentAccount.getHasPOCReport() == null || !currentAccount.getHasPOCReport()) {
                    request.setPocReport(null);
                } else {
                    if (request.getPocReport() == null && request.getId() != null && request.getQuarter() != null) {
                        request.setPocReport(pocReportService.getPOCReport(null));
                    }
                }
                if (currentAccount.getHasRPREPReport() == null || !currentAccount.getHasRPREPReport()) {
                    request.setRprep(null);
                } else {
                    if (request.getRprep() == null && request.getId() != null && request.getQuarter() == null) {
                        request.setRprep(rprepReportService.getRPREPReport(null));
                    }
                }
            }
        }
        return request;
    }

    @Override
    public HIVReportRequest saveHIVReport(HIVReportRequest request) {
        if (request != null) {
            UserRequest currentUser = userSecurityService.getCurrentUser();
            HIVReport hivReport = null;
            if (request.getId() != null) {
                hivReport = hivReportRepository.findById(request.getId()).orElse(null);
            }
            if (hivReport == null) {
                // Truong hop tim khong theo id
                Long adminUnitId = null;
                Long healthOrgId = null;
                if (request.getHealthOrg() != null && request.getHealthOrg().getId() != null) {
                    healthOrgId = request.getHealthOrg().getId(); // Co the null
                }
                if (request.getCommune() != null && request.getCommune().getId() != null) {
                    adminUnitId = request.getCommune().getId();
                } else if (request.getDistrict() != null && request.getDistrict().getId() != null) {
                    adminUnitId = request.getDistrict().getId();
                } else if (request.getProvince() != null && request.getProvince().getId() != null) {
                    adminUnitId = request.getProvince().getId();
                }
                List<HIVReport> listData = hivReportRepository.findByYearQuarterAdminUnitHealthOrg(request.getYear(), request.getQuarter(), adminUnitId, healthOrgId);
                if (!CollectionUtils.isEmpty(listData)) {
                    hivReport = listData.get(0);
                }
            }
            if (hivReport == null) {
                hivReport = new HIVReport();
            }
            hivReport.setReporter(currentUser.getDisplayName());
            hivReport.setStatus(request.getStatus());
            hivReport.setQuarter(request.getQuarter());
            hivReport.setYear(request.getYear());
            hivReport.setNote(request.getNote());
            if (request.getHealthOrg() != null && request.getHealthOrg().getId() != null) {
                hivReport.setHealthOrg(healthOrgRepository.findById(request.getHealthOrg().getId()).orElse(null));
            }
            if (request.getCommune() != null && request.getCommune().getId() != null) {
                hivReport.setAdminUnit(adminUnitRepository.findById(request.getCommune().getId()).orElse(null));
            } else if (request.getDistrict() != null && request.getDistrict().getId() != null) {
                hivReport.setAdminUnit(adminUnitRepository.findById(request.getDistrict().getId()).orElse(null));
            } else if (request.getDistrict() != null && request.getProvince().getId() != null) {
                hivReport.setAdminUnit(adminUnitRepository.findById(request.getProvince().getId()).orElse(null));
            } else {
                hivReport.setAdminUnit(null);
            }
            // HRI Report
            if (request.getHriReport() != null) {
                hivReport.setHriReport(hriReportService.setData(request.getHriReport()));
            }
            // ATH Report
            if (request.getAthReport() != null) {
                hivReport.setAthReport(athReportService.setData(request.getAthReport()));
            }
            // POC Report
            if (request.getPocReport() != null) {
                hivReport.setPocReport(pocReportService.setData(request.getPocReport()));
            }
            // MD Report
            if (request.getMdReport() != null) {
                hivReport.setMdReport(mdReportService.setData(request.getMdReport()));
            }
            // ARV Report
            if (request.getArvReport() != null) {
                hivReport.setArvReport(arvReportService.setData(request.getArvReport()));
            }
            // CI Report
            if (request.getCiReport() != null) {
                hivReport.setCiReport(ciReportService.setData(request.getCiReport()));
            }
            // MTC Report
            if (request.getMtcReport() != null) {
                hivReport.setMtcReport(mtcReportService.setData(request.getMtcReport()));
            }
            // PREP Report
            if (request.getPrepReport() != null) {
                hivReport.setPrepReport(prepReportService.setData(request.getPrepReport()));
            }
            // C Report
            if (request.getCReport() != null) {
                hivReport.setCReport(cReportService.setData(request.getCReport()));
            }
            // RPREP Report
            if (request.getRprep() != null) {
                hivReport.setRprepReport(rprepReportService.setData(request.getRprep()));
            }
            // CIHC Report
            if (request.getCihcReport() != null) {
                hivReport.setCihcReport(cihcReportService.setData(request.getCihcReport()));
            }
            // HG Report
            if (request.getHgReport() != null) {
                hivReport.setHgReport(hgReportService.setData(request.getHgReport()));
            }
            // SP Report
            if (request.getSpReport() != null) {
                hivReport.setSpReport(spReportService.setData(request.getSpReport()));
            }
            // IC Report
            if (request.getIcReport() != null) {
                hivReport.setIcReport(icReportService.setData(request.getIcReport()));
            }
            // HI Report
            if (request.getHiReport() != null) {
                hivReport.setHiReport(hiReportService.setData(request.getHiReport()));
            }
            hivReport = hivReportRepository.saveAndFlush(hivReport);
            return setRole(new HIVReportRequest(hivReport));
        }
        return null;
    }

    @Override
    public void confirmHIVReport(Long idReport, Integer status) {
        
    }

    @Override
    public HIVReportRequest changeStatus(Long idReport, Integer status, HIVReportRequest request) {
        return null;
    }

    @Override
    public Page<HIVReportRequest> paging(HIVReportSearchRequest request) {
        return null;
    }

    @Override
    public Page<HIVReportRequest> pagingQuarter(HIVReportSearchRequest dto) {
        return null;
    }

    @Override
    public Page<HIVReportRequest> pagingYear(HIVReportSearchRequest dto) {
        return null;
    }
}
