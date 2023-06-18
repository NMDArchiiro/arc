package com.arc.app.service.report.impl;

import com.arc.app.entity.report.*;
import com.arc.app.repository.base.AdminUnitRepository;
import com.arc.app.repository.base.FileDescriptionRepository;
import com.arc.app.repository.base.HealthOrgRepository;
import com.arc.app.repository.report.*;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.base.UserRequest;
import com.arc.app.request.report.*;
import com.arc.app.request.search.HIVReportSearchRequest;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.service.report.*;
import com.arc.app.utils.constants.HIVConstants;
import com.arc.app.utils.enums.AccountTypeEnum;
import com.arc.app.utils.enums.InputStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
    @Resource
    private FileDescriptionRepository fileDescriptionRepository;
    @Resource
    private HRIReportContentRepository hriReportContentRepository;
    @Resource
    private ATHReportContentRepository athReportContentRepository;
    @Resource
    private POCReportContentRepository pocReportContentRepository;
    @Resource
    private MDReportContentRepository mdReportContentRepository;
    @Resource
    private ARVReportContentRepository arvReportContentRepository;
    @Resource
    private CIReportContentRepository ciReportContentRepository;
    @Resource
    private MTCReportContentRepository mtcReportContentRepository;
    @Resource
    private PREPReportContentRepository prepReportContentRepository;
    @Resource
    private CReportContentRepository cReportContentRepository;
    @Resource
    private RPREPReportContentRepository rprepReportContentRepository;
    @Resource
    private CIHCReportContentRepository cihcReportContentRepository;
    @Resource
    private HGReportContentRepository hgReportContentRepository;
    @Resource
    private SPReportContentRepository spReportContentRepository;
    @Resource
    private ICReportContentRepository icReportContentRepository;
    @Resource
    private HIReportContentRepository hiReportContentRepository;
    @Resource
    private DashboardNotificationService dashboardNotificationService;
    private static Logger LOG = LoggerFactory.getLogger(HIVReportServiceImpl.class);

    @Override
    public HIVReportRequest getHIVReport(Long id) {
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
    public Boolean makeDelete(Long id) {
        try {
            HIVReport hivReport = hivReportRepository.findById(id).orElse(null);
            if (hivReport != null) {
                hivReport.setVoided(true);
                hivReportRepository.save(hivReport);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
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
                    healthOrgId = request.getHealthOrg().getId();
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
            } else if (request.getProvince() != null && request.getProvince().getId() != null) {
                hivReport.setAdminUnit(adminUnitRepository.findById(request.getProvince().getId()).orElse(null));
            } else {
                hivReport.setAdminUnit(null);
            }
            // Luu thua du lieu de thuan tien cho viec query
            if (request.getCommune() != null && request.getCommune().getId() != null) {
                hivReport.setCommune(adminUnitRepository.findById(request.getCommune().getId()).orElse(null));
            } else {
                hivReport.setCommune(null);
            }
            if (request.getDistrict() != null && request.getDistrict().getId() != null) {
                hivReport.setDistrict(adminUnitRepository.findById(request.getDistrict().getId()).orElse(null));
            } else {
                hivReport.setDistrict(null);
            }
            if (request.getProvince() != null && request.getProvince().getId() != null) {
                hivReport.setProvince(adminUnitRepository.findById(request.getProvince().getId()).orElse(null));
            } else {
                hivReport.setCommune(null);
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
    public HIVReportRequest changeStatus(Long idReport, Integer status, HIVReportRequest request) {
        HIVReport hivReport = hivReportRepository.findById(idReport).orElse(null);
        if (hivReport != null && status != null) {
            if (status.equals(InputStatusEnum.LOCK.getKey())) {
                hivReport.setIsLock(true);
            } else if (status.equals(InputStatusEnum.UN_LOCK.getKey())) {
                hivReport.setIsLock(false);
            } else {
                hivReport.setStatus(status);
            }
            hivReport.setNote(request.getNote());
            if (request.getFile() != null && request.getFile().getId() != null) {
                hivReport.setFile(fileDescriptionRepository.findById(request.getFile().getId()).orElse(null));
            } else {
                hivReport.setFile(null);
            }
            if (status.equals(InputStatusEnum.COMPLETE.getKey())) {
                hivReport = updateValueToWhenComplete(hivReport);
            }
            UserRequest currentUser = userSecurityService.getCurrentUser();
            if (currentUser != null) {
                hivReport.setReporter(currentUser.getDisplayName());
            }
            hivReport = hivReportRepository.save(hivReport);
            return setRole(new HIVReportRequest(hivReport));
        }
        return null;
    }

    @Override
    public Page<HIVReportRequest> pagingQuarter(HIVReportSearchRequest request) {
        if (request != null && request.getPageIndex() != null && request.getPageSize() != null) {
            int pageIndex = request.getPageIndex();
            int pageSize = request.getPageSize();
            if (pageIndex > 0) {
                pageIndex--;
            } else {
                pageIndex = 0;
            }
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            UserRequest currentUser = userSecurityService.getCurrentUser();
            if (currentUser.getProvince() != null && currentUser.getProvince().getId() != null) {
                request.setProvinceId(currentUser.getProvince().getId());
            }
            if (currentUser.getDistrict() != null && currentUser.getDistrict().getId() != null) {
                request.setDistrictId(currentUser.getDistrict().getId());
            }
            if (currentUser.getCommune() != null && currentUser.getCommune().getId() != null) {
                request.setCommuneId(currentUser.getCommune().getId());
            }
            if (currentUser.getHealthOrganization() != null && currentUser.getHealthOrganization().getId() != null
                    && currentUser.getAccountType() != null &&
                    (currentUser.getAccountType().equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || currentUser.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey()))) {
                request.setOrgId(currentUser.getHealthOrganization().getId());
            }
            if (currentUser.getHealthOrganization() != null && currentUser.getAccountType() != null &&
                    currentUser.getAccountType().equals(AccountTypeEnum.AREA.getKey()) && !CollectionUtils.isEmpty(currentUser.getHealthOrganization().getListAdminUnit())) {
                for (AdminUnitRequest adminUnitRequest : currentUser.getHealthOrganization().getListAdminUnit()) {
                    request.getProvinceIds().add(adminUnitRequest.getId());
                }
            }
            Page<HIVReportRequest> hivReportFormrequestPage = null;
            if (currentUser.getAccountType() != null) {
                if (currentUser.getAccountType() < 3) {
                    hivReportFormrequestPage = hivReportRepository.getPageQuarterProvince(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getProvinceIds(), pageable);
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.PROVINCE.getKey())) {
                    if (request.getType() != null && request.getType().equals(1)) {
                        hivReportFormrequestPage = hivReportRepository.getPageQuarterProvince(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getProvinceIds(), pageable);
                    }
                    if (request.getType() != null && request.getType().equals(2)) {
                        hivReportFormrequestPage = hivReportRepository.getPageQuarterUnderProvince(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getOrgId(), pageable);
                    }
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.DISTRICT.getKey())) {
                    if (request.getType() != null && request.getType().equals(1)) {
                        hivReportFormrequestPage = hivReportRepository.getPageQuarterDistrict(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getDistrictId(), pageable);
                    }
                    if (request.getType() != null && request.getType().equals(2)) {
                        hivReportFormrequestPage = hivReportRepository.getPageQuarterUnderDistrict(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getCommuneId(), request.getOrgId(), pageable);
                    }
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.COMMUNE.getKey())) {
                    hivReportFormrequestPage = hivReportRepository.getPageQuarterCommune(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getCommuneId(), pageable);
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                    hivReportFormrequestPage = hivReportRepository.getPageQuarterAffiliateProvince(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getOrgId(), pageable);
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                    hivReportFormrequestPage = hivReportRepository.getPageQuarterAffiliateDistrict(request.getQuarter(), request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getOrgId(), pageable);
                }
            }
            if (!ObjectUtils.isEmpty(hivReportFormrequestPage) && !CollectionUtils.isEmpty(hivReportFormrequestPage.getContent())) {
                for (HIVReportRequest hivReportRequest : hivReportFormrequestPage.getContent()) {
                    setRole(hivReportRequest);
                }
            }
            return hivReportFormrequestPage;
        }
        return null;
    }

    @Override
    public Page<HIVReportRequest> pagingYear(HIVReportSearchRequest request) {
        if (request != null && request.getPageIndex() != null && request.getPageSize() != null) {
            int pageIndex = request.getPageIndex();
            int pageSize = request.getPageSize();
            if (pageIndex > 0) {
                pageIndex--;
            } else {
                pageIndex = 0;
            }
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            UserRequest currentUser = userSecurityService.getCurrentUser();
            if (currentUser.getProvince() != null && currentUser.getProvince().getId() != null) {
                request.setProvinceId(currentUser.getProvince().getId());
            }
            if (currentUser.getDistrict() != null && currentUser.getDistrict().getId() != null) {
                request.setDistrictId(currentUser.getDistrict().getId());
            }
            if (currentUser.getCommune() != null && currentUser.getCommune().getId() != null) {
                request.setCommuneId(currentUser.getCommune().getId());
            }
            if (currentUser.getHealthOrganization() != null && currentUser.getHealthOrganization().getId() != null
                    && currentUser.getAccountType() != null &&
                    (currentUser.getAccountType().equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) || currentUser.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey()))) {
                request.setOrgId(currentUser.getHealthOrganization().getId());
            }
            if (currentUser.getHealthOrganization() != null && currentUser.getAccountType() != null &&
                    currentUser.getAccountType().equals(AccountTypeEnum.AREA.getKey()) && !CollectionUtils.isEmpty(currentUser.getHealthOrganization().getListAdminUnit())) {
                for (AdminUnitRequest adminUnitRequest : currentUser.getHealthOrganization().getListAdminUnit()) {
                    request.getProvinceIds().add(adminUnitRequest.getId());
                }
            }
            Page<HIVReportRequest> hivReportFormrequestPage = null;
            if (currentUser.getAccountType() != null) {
                if (currentUser.getAccountType() < 3) {
                    hivReportFormrequestPage = hivReportRepository.getPageYearProvince(request.getYear(), request.getProvinceId(), request.getProvinceIds(), pageable);
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.PROVINCE.getKey())) {
                    if (request.getType() != null && request.getType().equals(1)) {
                        hivReportFormrequestPage = hivReportRepository.getPageYearProvince(request.getYear(), request.getProvinceId(), request.getProvinceIds(), pageable);
                    }
                    if (request.getType() != null && request.getType().equals(2)) {
                        hivReportFormrequestPage = hivReportRepository.getPageYearUnderProvince(request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getOrgId(), pageable);
                    }
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.DISTRICT.getKey())) {
                    if (request.getType() != null && request.getType().equals(1)) {
                        hivReportFormrequestPage = hivReportRepository.getPageYearDistrict(request.getYear(), request.getProvinceId(), request.getDistrictId(), pageable);
                    }
                    if (request.getType() != null && request.getType().equals(2)) {
                        hivReportFormrequestPage = hivReportRepository.getPageYearUnderDistrict(request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getCommuneId(), request.getOrgId(), pageable);
                    }
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.UNDER_PROVINCE.getKey())) {
                    hivReportFormrequestPage = hivReportRepository.getPageYearAffiliateProvince(request.getYear(), request.getProvinceId(), request.getOrgId(), pageable);
                } else if (currentUser.getAccountType().equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
                    hivReportFormrequestPage = hivReportRepository.getPageYearAffiliateDistrict(request.getYear(), request.getProvinceId(), request.getDistrictId(), request.getOrgId(), pageable);
                }
            }

            if (!ObjectUtils.isEmpty(hivReportFormrequestPage) && !CollectionUtils.isEmpty(hivReportFormrequestPage.getContent())) {
                for (HIVReportRequest hivReportRequest : hivReportFormrequestPage.getContent()) {
                    setRole(hivReportRequest);
                }
            }
            return hivReportFormrequestPage;
        }
        return null;
    }

    @Override
    public HIVReportRequest totalDistrictQuarter(HIVReportRequest request) {
        if (request != null && request.getQuarter() != null && request.getYear() != null && request.getDistrict() != null && request.getDistrict().getId() != null) {
            HIVReportRequest result = new HIVReportRequest();
            result.setProvince(request.getProvince());
            result.setDistrict(request.getDistrict());
            result.setYear(request.getYear());
            result.setQuarter(request.getQuarter());
            // BC1
            List<HRIReportContentRequest> hri = hriReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            HRIReportRequest report1 = new HRIReportRequest();
            if (CollectionUtils.isEmpty(hri)) {
                report1.setContents(hriReportService.getHRIReport(null).getContents());
            } else {
                report1.setContents(hri);
            }
            result.setHriReport(report1);
            // BC2
            List<ATHReportContentRequest> ath = athReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            ATHReportRequest report2 = new ATHReportRequest();
            if (CollectionUtils.isEmpty(ath)) {
                report2.setContents(athReportService.getATHReport(null).getContents());
            } else {
                report2.setContents(ath);
            }
            result.setAthReport(report2);
            // BC3
            List<POCReportContentRequest> poc = pocReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            POCReportRequest report3 = new POCReportRequest();
            if (CollectionUtils.isEmpty(poc)) {
                report3.setContents(pocReportService.getPOCReport(null).getContents());
            } else {
                report3.setContents(poc);
            }
            result.setPocReport(report3);
            // BC4
            List<MDReportContentRequest> md = mdReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            MDReportRequest report4 = new MDReportRequest();
            if (CollectionUtils.isEmpty(md)) {
                report4.setContents(mdReportService.getMDReport(null).getContents());
            } else {
                report4.setContents(md);
            }
            result.setMdReport(report4);
            // BC5
            List<ARVReportContentRequest> arv = arvReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            ARVReportRequest report5 = new ARVReportRequest();
            if (CollectionUtils.isEmpty(arv)) {
                report5.setContents(arvReportService.getARVReport(null).getContents());
            } else {
                report5.setContents(arv);
            }
            result.setArvReport(report5);
            // BC6
            List<CIReportContentRequest> ci = ciReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            CIReportRequest report6 = new CIReportRequest();
            if (CollectionUtils.isEmpty(ci)) {
                report6.setContents(ciReportService.getCIReport(null).getContents());
            } else {
                report6.setContents(ci);
            }
            result.setCiReport(report6);
            // BC7
            List<MTCReportContentRequest> mtc = mtcReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            MTCReportRequest report7 = new MTCReportRequest();
            if (CollectionUtils.isEmpty(mtc)) {
                report7.setContents(mtcReportService.getMTCReport(null).getContents());
            } else {
                report7.setContents(mtc);
            }
            result.setMtcReport(report7);
            // BC8
            List<PREPReportContentRequest> prep = prepReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            PREPReportRequest report8 = new PREPReportRequest();
            if (CollectionUtils.isEmpty(prep)) {
                report8.setContents(prepReportService.getPREPReport(null).getContents());
            } else {
                report8.setContents(prep);
            }
            result.setPrepReport(report8);
            result.setNotifications(dashboardNotificationService.getNotificationReportDistrict(request.getQuarter(), request.getYear(), request.getDistrict().getId()));
            return updateValueToWhenTotal(setRole(result));
        }
        return null;
    }

    @Override
    public HIVReportRequest totalDistrictYear(HIVReportRequest request) {
        if (request != null && request.getYear() != null && request.getDistrict() != null && request.getDistrict().getId() != null) {
            HIVReportRequest result = new HIVReportRequest();
            result.setProvince(request.getProvince());
            result.setDistrict(request.getDistrict());
            result.setYear(request.getYear());
            result.setQuarter(request.getQuarter());
            // Bao cao 10
            List<RPREPReportContentRequest> rprep = rprepReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            RPREPReportRequest report10 = new RPREPReportRequest();
            if (CollectionUtils.isEmpty(rprep)) {
                report10.setContents(rprepReportService.getRPREPReport(null).getContents());
            } else {
                report10.setContents(rprep);
            }
            result.setRprep(report10);
            // Bao cao 11
            List<CIHCReportContentRequest> cihc = cihcReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            CIHCReportRequest report11 = new CIHCReportRequest();
            if (CollectionUtils.isEmpty(cihc)) {
                report11.setContents(cihcReportService.getCIHCReport(null).getContents());
            } else {
                report11.setContents(cihc);
            }
            result.setCihcReport(report11);
            // Bao cao 12
            List<HGReportContentRequest> hg = hgReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            HGReportRequest report12 = new HGReportRequest();
            if (CollectionUtils.isEmpty(hg)) {
                report12.setContents(hgReportService.getHGReport(null).getContents());
            } else {
                report12.setContents(hg);
            }
            result.setHgReport(report12);
            result.setNotifications(dashboardNotificationService.getNotificationReportDistrict(request.getQuarter(), request.getYear(), request.getDistrict().getId()));
            return updateValueToWhenTotal(setRole(result));
        }
        return null;
    }

    @Override
    public HIVReportRequest totalProvinceQuarter(HIVReportRequest request) {
        if (request != null && request.getQuarter() != null && request.getYear() != null && request.getDistrict() != null && request.getDistrict().getId() != null) {
            HIVReportRequest result = new HIVReportRequest();
            result.setProvince(request.getProvince());
            result.setDistrict(request.getDistrict());
            result.setYear(request.getYear());
            result.setQuarter(request.getQuarter());
            // BC1
            List<HRIReportContentRequest> hri = hriReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            HRIReportRequest report1 = new HRIReportRequest();
            if (CollectionUtils.isEmpty(hri)) {
                report1.setContents(hriReportService.getHRIReport(null).getContents());
            } else {
                report1.setContents(hri);
            }
            result.setHriReport(report1);
            // BC2
            List<ATHReportContentRequest> ath = athReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            ATHReportRequest report2 = new ATHReportRequest();
            if (CollectionUtils.isEmpty(ath)) {
                report2.setContents(athReportService.getATHReport(null).getContents());
            } else {
                report2.setContents(ath);
            }
            result.setAthReport(report2);
            // BC3
            List<POCReportContentRequest> poc = pocReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            POCReportRequest report3 = new POCReportRequest();
            if (CollectionUtils.isEmpty(poc)) {
                report3.setContents(pocReportService.getPOCReport(null).getContents());
            } else {
                report3.setContents(poc);
            }
            result.setPocReport(report3);
            // BC4
            List<MDReportContentRequest> md = mdReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            MDReportRequest report4 = new MDReportRequest();
            if (CollectionUtils.isEmpty(md)) {
                report4.setContents(mdReportService.getMDReport(null).getContents());
            } else {
                report4.setContents(md);
            }
            result.setMdReport(report4);
            // BC5
            List<ARVReportContentRequest> arv = arvReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            ARVReportRequest report5 = new ARVReportRequest();
            if (CollectionUtils.isEmpty(arv)) {
                report5.setContents(arvReportService.getARVReport(null).getContents());
            } else {
                report5.setContents(arv);
            }
            result.setArvReport(report5);
            // BC6
            List<CIReportContentRequest> ci = ciReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            CIReportRequest report6 = new CIReportRequest();
            if (CollectionUtils.isEmpty(ci)) {
                report6.setContents(ciReportService.getCIReport(null).getContents());
            } else {
                report6.setContents(ci);
            }
            result.setCiReport(report6);
            // BC7
            List<MTCReportContentRequest> mtc = mtcReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            MTCReportRequest report7 = new MTCReportRequest();
            if (CollectionUtils.isEmpty(mtc)) {
                report7.setContents(mtcReportService.getMTCReport(null).getContents());
            } else {
                report7.setContents(mtc);
            }
            result.setMtcReport(report7);
            // BC8
            List<PREPReportContentRequest> prep = prepReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            PREPReportRequest report8 = new PREPReportRequest();
            if (CollectionUtils.isEmpty(prep)) {
                report8.setContents(prepReportService.getPREPReport(null).getContents());
            } else {
                report8.setContents(prep);
            }
            // BC9
            List<CReportContentRequest> c = cReportContentRepository.findTotalQuarterFor(request.getYear(), request.getQuarter(), request.getDistrict().getId());
            CReportRequest report9 = new CReportRequest();
            if (CollectionUtils.isEmpty(c)) {
                report9.setContents(cReportService.getCReport(null).getContents());
            } else {
                report9.setContents(c);
            }
            result.setCReport(report9);
            result.setNotifications(dashboardNotificationService.getNotificationReportDistrict(request.getQuarter(), request.getYear(), request.getDistrict().getId()));
            return updateValueToWhenTotal(setRole(result));
        }
        return null;
    }

    @Override
    public HIVReportRequest totalProvinceYear(HIVReportRequest request) {
        if (request != null && request.getYear() != null && request.getDistrict() != null && request.getDistrict().getId() != null) {
            HIVReportRequest result = new HIVReportRequest();
            result.setProvince(request.getProvince());
            result.setDistrict(request.getDistrict());
            result.setYear(request.getYear());
            result.setQuarter(request.getQuarter());
            // Bao cao 10
            List<RPREPReportContentRequest> rprep = rprepReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            RPREPReportRequest report10 = new RPREPReportRequest();
            if (CollectionUtils.isEmpty(rprep)) {
                report10.setContents(rprepReportService.getRPREPReport(null).getContents());
            } else {
                report10.setContents(rprep);
            }
            result.setRprep(report10);
            // Bao cao 11
            List<CIHCReportContentRequest> cihc = cihcReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            CIHCReportRequest report11 = new CIHCReportRequest();
            if (CollectionUtils.isEmpty(cihc)) {
                report11.setContents(cihcReportService.getCIHCReport(null).getContents());
            } else {
                report11.setContents(cihc);
            }
            result.setCihcReport(report11);
            // Bao cao 12
            List<HGReportContentRequest> hg = hgReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            HGReportRequest report12 = new HGReportRequest();
            if (CollectionUtils.isEmpty(hg)) {
                report12.setContents(hgReportService.getHGReport(null).getContents());
            } else {
                report12.setContents(hg);
            }
            result.setHgReport(report12);
            // Bao cao 13
            List<SPReportContentRequest> sp = spReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            SPReportRequest report13 = new SPReportRequest();
            if (CollectionUtils.isEmpty(sp)) {
                report13.setContents(spReportService.getSPReport(null).getContents());
            } else {
                report13.setContents(sp);
            }
            result.setSpReport(report13);
            // Bao cao 14
            List<ICReportContentRequest> ic = icReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            ICReportRequest report14 = new ICReportRequest();
            if (CollectionUtils.isEmpty(ic)) {
                report14.setContents(icReportService.getICReport(null).getContents());
            } else {
                report14.setContents(ic);
            }
            result.setIcReport(report14);
            // Bao cao 15
            List<HIReportContentRequest> hi = hiReportContentRepository.findTotalYear(request.getYear(), request.getDistrict().getId());
            HIReportRequest report15 = new HIReportRequest();
            if (CollectionUtils.isEmpty(hi)) {
                report15.setContents(hiReportService.getHIReport(null).getContents());
            } else {
                report15.setContents(hi);
            }
            result.setHiReport(report15);
            result.setNotifications(dashboardNotificationService.getNotificationReportDistrict(request.getQuarter(), request.getYear(), request.getDistrict().getId()));
            return updateValueToWhenTotal(setRole(result));
        }
        return null;
    }

    private HIVReport updateValueToWhenComplete(HIVReport entity) {
        // Sau khi complete thi update value ve 0
        if (entity.getHriReport() != null && !CollectionUtils.isEmpty(entity.getHriReport().getContents())) {
            for (HRIReportContent content : entity.getHriReport().getContents()) {
                if (content.getNumberCondom() == null) {
                    content.setNumberCondom(0);
                }
                if (content.getNumberLubricant() == null) {
                    content.setNumberLubricant(0);
                }
                if (content.getNumberSyringeNeedle() == null) {
                    content.setNumberSyringeNeedle(0);
                }
            }
        }
        if (entity.getAthReport() != null && !CollectionUtils.isEmpty(entity.getAthReport().getContents())) {
            for (ATHReportContent content : entity.getAthReport().getContents()) {
                if (content.getNumberAdviseFemale() == null) {
                    content.setNumberAdviseFemale(0);
                }
                if (content.getNumberAdviseMale() == null && content.getContent() != null && content.getContent().getIndexNumber() != 9
                        && content.getContent().getIndexNumber() != 7 && content.getContent().getIndexNumber() != 8) {
                    content.setNumberAdviseMale(0);
                }
                if (content.getNumberAdviseTotal() == null) {
                    content.setNumberAdviseTotal(0);
                }
                if (content.getNumberTestingFemale() == null) {
                    content.setNumberTestingFemale(0);
                }
                if (content.getNumberTestingMale() == null && content.getContent() != null && content.getContent().getIndexNumber() != 9
                        && content.getContent().getIndexNumber() != 7 && content.getContent().getIndexNumber() != 8) {
                    content.setNumberTestingMale(0);
                }
                if (content.getNumberTestingTotal() == null) {
                    content.setNumberTestingTotal(0);
                }

                if (content.getNumberPersonAdviseFemale() == null) {
                    content.setNumberPersonAdviseFemale(0);
                }
                if (content.getNumberPersonAdviseMale() == null && content.getContent() != null && content.getContent().getIndexNumber() != 9
                        && content.getContent().getIndexNumber() != 7 && content.getContent().getIndexNumber() != 8) {
                    content.setNumberPersonAdviseMale(0);
                }
                if (content.getNumberPersonAdviseTotal() == null) {
                    content.setNumberPersonAdviseTotal(0);
                }
                if (content.getNumberPersonTestingFemale() == null) {
                    content.setNumberPersonTestingFemale(0);
                }
                if (content.getNumberPersonTestingMale() == null && content.getContent() != null && content.getContent().getIndexNumber() != 9
                        && content.getContent().getIndexNumber() != 7 && content.getContent().getIndexNumber() != 8) {
                    content.setNumberPersonTestingMale(0);
                }
                if (content.getNumberPersonTestingTotal() == null) {
                    content.setNumberPersonTestingTotal(0);
                }
            }
        }
        if (entity.getPocReport() != null && !CollectionUtils.isEmpty(entity.getPocReport().getContents())) {
            for (POCReportContent content : entity.getPocReport().getContents()) {
                if (content.getAmount() == null) {
                    content.setAmount(0);
                }
            }
        }
        if (entity.getMdReport() != null && !CollectionUtils.isEmpty(entity.getMdReport().getContents())) {
            for (MDReportContent c : entity.getMdReport().getContents()) {
                if (c.getNumberFemalePatient() == null) {
                    c.setNumberFemalePatient(0);
                }
                if (c.getNumberMalePatient() == null) {
                    c.setNumberMalePatient(0);
                }
                if (c.getNumberTotalPatient() == null) {
                    c.setNumberTotalPatient(0);
                }
            }
        }
        if (entity.getArvReport() != null && !CollectionUtils.isEmpty(entity.getArvReport().getContents())) {
            for (ARVReportContent c : entity.getArvReport().getContents()) {
                // Bo nhung dong khong duoc ghi
                if (c.getNumberFemaleOver15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberFemaleOver15(0);
                }
                if (c.getNumberFemaleUnder15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberFemaleUnder15(0);
                }
                if (c.getNumberMaleOver15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberMaleOver15(0);
                }
                if (c.getNumberMaleUnder15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberMaleUnder15(0);
                }
                if (c.getNumberTotal() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberTotal(0);
                }
                if (c.getNumberTotalOver15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberTotalOver15(0);
                }
                if (c.getNumberTotalUnder15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 11) {
                    c.setNumberTotalUnder15(0);
                }
            }
        }
        if (entity.getCiReport() != null && !CollectionUtils.isEmpty(entity.getCiReport().getContents())) {
            for (CIReportContent c : entity.getCiReport().getContents()) {
                // Bo nhung dong khong duoc ghi
                if (c.getNumberFemaleOver15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberFemaleOver15(0);
                }
                if (c.getNumberFemaleUnder15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberFemaleUnder15(0);
                }
                if (c.getNumberMaleOver15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberMaleOver15(0);
                }
                if (c.getNumberMaleUnder15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberMaleUnder15(0);
                }
                if (c.getNumberTotal() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberTotal(0);
                }
                if (c.getNumberTotalOver15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberTotalOver15(0);
                }
                if (c.getNumberTotalUnder15() == null && c.getContent() != null && c.getContent().getIndexNumber() != 1
                        && c.getContent().getIndexNumber() != 6) {
                    c.setNumberTotalUnder15(0);
                }
            }
        }
        if (entity.getMtcReport() != null && !CollectionUtils.isEmpty(entity.getMtcReport().getContents())) {
            for (MTCReportContent c : entity.getMtcReport().getContents()) {
                if (c.getAmount() == null) {
                    c.setAmount(0);
                }
            }
        }
        if (entity.getPrepReport() != null && !CollectionUtils.isEmpty(entity.getPrepReport().getContents())) {
            for (PREPReportContent c : entity.getPrepReport().getContents()) {
                if (c.getNumberTotal() == null) {
                    c.setNumberTotal(0);
                }
                if (c.getNumberMSM() == null) {
                    c.setNumberMSM(0);
                }
                if (c.getNumberNCMT() == null) {
                    c.setNumberNCMT(0);
                }
                if (c.getNumberOther() == null) {
                    c.setNumberOther(0);
                }
                if (c.getNumberTG() == null) {
                    c.setNumberTG(0);
                }
                if (c.getNumberPNBD() == null) {
                    c.setNumberPNBD(0);
                }
            }
        }
        if (entity.getCReport() != null && !CollectionUtils.isEmpty(entity.getCReport().getContents())) {
            for (CReportContent c : entity.getCReport().getContents()) {
                if (c.getNumberNegative() == null) {
                    c.setNumberNegative(0);
                }
                if (c.getNumberPositive() == null) {
                    c.setNumberPositive(0);
                }
                if (c.getNumberUnknown() == null) {
                    c.setNumberUnknown(0);
                }
                if (c.getTotal() == null) {
                    c.setTotal(0);
                }
            }
        }
        if (entity.getRprepReport() != null && !CollectionUtils.isEmpty(entity.getRprepReport().getContents())) {
            for (RPREPReportContent c : entity.getRprepReport().getContents()) {
                if (c.getNumberTotal() == null) {
                    c.setNumberTotal(0);
                }
                if (c.getNumberMSM() == null) {
                    c.setNumberMSM(0);
                }
                if (c.getNumberNCMT() == null) {
                    c.setNumberNCMT(0);
                }
                if (c.getNumberOther() == null) {
                    c.setNumberOther(0);
                }
                if (c.getNumberTG() == null) {
                    c.setNumberTG(0);
                }
                if (c.getNumberPNBD() == null) {
                    c.setNumberPNBD(0);
                }
            }
        }
        if (entity.getCihcReport() != null && !CollectionUtils.isEmpty(entity.getCihcReport().getContents())) {
            for (CIHCReportContent c : entity.getCihcReport().getContents()) {
                if (c.getNumberFemaleOver15() == null) {
                    c.setNumberFemaleOver15(0);
                }
                if (c.getNumberFemaleUnder15() == null) {
                    c.setNumberFemaleUnder15(0);
                }
                if (c.getNumberMaleOver15() == null) {
                    c.setNumberMaleOver15(0);
                }
                if (c.getNumberMaleUnder15() == null) {
                    c.setNumberMaleUnder15(0);
                }
                if (c.getNumberTotal() == null) {
                    c.setNumberTotal(0);
                }
                if (c.getNumberTotalOver15() == null) {
                    c.setNumberTotalOver15(0);
                }
                if (c.getNumberTotalUnder15() == null) {
                    c.setNumberTotalUnder15(0);
                }
            }
        }
        if (entity.getHgReport() != null && !CollectionUtils.isEmpty(entity.getHgReport().getContents())) {
            for (HGReportContent c : entity.getHgReport().getContents()) {
                if (c.getNumberManage() == null && c.getContent() != null && c.getContent().getIndexNumber() != 2
                        && c.getContent().getIndexNumber() != 3 && c.getContent().getIndexNumber() != 4) {
                    c.setNumberManage(0);
                }
                if (c.getNumberEstimate() == null) {
                    c.setNumberEstimate(0);
                }
            }
        }
        if (entity.getSpReport() != null && !CollectionUtils.isEmpty(entity.getSpReport().getContents())) {
            for (SPReportContent c : entity.getSpReport().getContents()) {
                if (c.getAmount() == null) {
                    c.setAmount(0);
                }
            }
        }
        if (entity.getIcReport() != null && !CollectionUtils.isEmpty(entity.getIcReport().getContents())) {
            for (ICReportContent c : entity.getIcReport().getContents()) {
                if (c.getLatest() == null && c.getContent() != null && c.getContent().getIndexNumber() != 5) {
                    c.setLatest(0);
                }
                if (c.getCapacityBuilding() == null && c.getContent() != null && c.getContent().getIndexNumber() != 5) {
                    c.setCapacityBuilding(0);
                }
                if (c.getProvision() == null && c.getContent() != null && c.getContent().getIndexNumber() != 5) {
                    c.setProvision(0);
                }
                if (c.getReviews() == null && c.getContent() != null && c.getContent().getIndexNumber() != 5) {
                    c.setReviews(0);
                }
                if (c.getTreatment() == null) {
                    c.setTreatment(0);
                }
            }
        }
        if (entity.getHiReport() != null && !CollectionUtils.isEmpty(entity.getHiReport().getContents())) {
            for (HIReportContent c : entity.getHiReport().getContents()) {
                if (c.getNumberFemaleOver15() == null) {
                    c.setNumberFemaleOver15(0);
                }
                if (c.getNumberFemaleUnder15() == null) {
                    c.setNumberFemaleUnder15(0);
                }
                if (c.getNumberMaleOver15() == null) {
                    c.setNumberMaleOver15(0);
                }
                if (c.getNumberMaleUnder15() == null) {
                    c.setNumberMaleUnder15(0);
                }
                if (c.getNumberTotal() == null) {
                    c.setNumberTotal(0);
                }
                if (c.getNumberTotalOver15() == null) {
                    c.setNumberTotalOver15(0);
                }
                if (c.getNumberTotalUnder15() == null) {
                    c.setNumberTotalUnder15(0);
                }
            }
        }
        return entity;
    }

    private HIVReportRequest updateValueToWhenTotal(HIVReportRequest request) {
        // Sau khi tinh tong thi can set null cho nhung dong khong duoc ghi (Khong thi nhung dong khong duoc ghi hien thi la 0)
        if (request.getAthReport() != null && !CollectionUtils.isEmpty(request.getAthReport().getContents())) {
            for (ATHReportContentRequest content : request.getAthReport().getContents()) {
                if ((content.getIndexNumber() == 9 || content.getIndexNumber() == 7 || content.getIndexNumber() == 8)) {
                    content.setNumberAdviseMale(null);
                    content.setNumberTestingMale(null);
                    content.setNumberPersonAdviseMale(null);
                    content.setNumberPersonTestingMale(null);
                }
            }
        }
        if (request.getArvReport() != null && !CollectionUtils.isEmpty(request.getArvReport().getContents())) {
            for (ARVReportContentRequest c : request.getArvReport().getContents()) {
                if (c.getIndexNumber() == 1 || c.getIndexNumber() == 3 || c.getIndexNumber() == 11) {
                    c.setNumberFemaleOver15(null);
                    c.setNumberFemaleUnder15(null);
                    c.setNumberMaleOver15(null);
                    c.setNumberMaleUnder15(null);
                    c.setNumberTotal(null);
                    c.setNumberTotalOver15(null);
                    c.setNumberTotalUnder15(null);
                }
            }
        }
        if (request.getCiReport() != null && !CollectionUtils.isEmpty(request.getCiReport().getContents())) {
            for (CIReportContentRequest c : request.getCiReport().getContents()) {
                if (c.getIndexNumber() == 1 || c.getIndexNumber() == 6) {
                    c.setNumberFemaleOver15(null);
                    c.setNumberFemaleUnder15(null);
                    c.setNumberMaleOver15(null);
                    c.setNumberMaleUnder15(null);
                    c.setNumberTotal(null);
                    c.setNumberTotalOver15(null);
                    c.setNumberTotalUnder15(null);
                }
            }
        }
        if (request.getHgReport() != null && !CollectionUtils.isEmpty(request.getHgReport().getContents())) {
            for (HGReportContentRequest c : request.getHgReport().getContents()) {
                if (c.getIndexNumber() == 2 || c.getIndexNumber() == 3 || c.getIndexNumber() == 4) {
                    c.setNumberManage(null);
                }
            }
        }
        if (request.getIcReport() != null && !CollectionUtils.isEmpty(request.getIcReport().getContents())) {
            for (ICReportContentRequest c : request.getIcReport().getContents()) {
                if (c.getIndexNumber() == 5) {
                    c.setLatest(null);
                    c.setCapacityBuilding(null);
                    c.setProvision(null);
                    c.setReviews(null);
                }
            }
        }
        return request;
    }


}
