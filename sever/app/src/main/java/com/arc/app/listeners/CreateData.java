package com.arc.app.listeners;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.request.base.RoleRequest;
import com.arc.app.request.base.UserRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.response.ResponseList;
import com.arc.app.service.base.RoleService;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.service.base.UserService;
import com.arc.app.service.report.ReportContentService;
import com.arc.app.utils.enums.AccountTypeEnum;
import com.arc.app.utils.enums.ReportEnum;
import com.arc.app.utils.enums.ResponseEnum;
import com.arc.app.utils.enums.RoleEnum;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class CreateData {
    @Resource
    private RoleService roleService;

    @Resource
    private UserSecurityService userSecurityService;

    @Resource
    private UserService userService;

    @Resource
    private ReportContentService reportContentService;

    @Resource
    private Environment environment;

    public void createRole(Long id, String roleName) {
        if(!roleService.duplicateRole(roleName)) {
            RoleRequest request = new RoleRequest();
            request.setId(id);
            request.setName(roleName);
            roleService.saveRole(request);
        }
    }

    public void createUser() {
        UserRequest request = new UserRequest();
        request.setUsername("admin");
        request.setPassword("123456");
        List<RoleRequest> roles = new ArrayList<>();
        RoleRequest role = new RoleRequest();
        role.setId(RoleEnum.ROLE_ADMIN.getId());
        role.setName(RoleEnum.ROLE_ADMIN.getName());
        roles.add(role);
        request.setRoles(roles);
        request.setDisplayName("Nguyễn Đức");
        try {
            String mail = environment.getProperty("spring.mail.username");
            request.setEmail(mail);
        } catch (Exception e) {
            System.out.println(ResponseEnum.ERROR.getMessage());
        }
        request.setAccountType(AccountTypeEnum.VAAC.getKey());
        request.setActive(true);
        // Tam
        request.setHasHRIReport(true);
        request.setHasATHReport(true);
        request.setHasPOCReport(true);
        request.setHasMDReport(true);
        request.setHasARVReport(true);
        request.setHasCIReport(true);
        request.setHasMTCReport(true);
        request.setHasPREPReport(true);
        request.setHasCReport(true);
        request.setHasRPREPReport(true);
        request.setHasCIHCReport(true);
        request.setHasHGReport(true);
        request.setHasSPReport(true);
        request.setHasICReport(true);
        request.setHasHIReport(true);
        if(!userSecurityService.duplicateUser(request.getUsername())) {
            userService.saveUser(request);
        }
    }

    public void createReport() {
        this.createContent(ReportEnum.HRI.getNumber());
        this.createContent(ReportEnum.ATH.getNumber());
        this.createContent(ReportEnum.POC.getNumber());
        this.createContent(ReportEnum.MD.getNumber());
        this.createContent(ReportEnum.ARV.getNumber());
        this.createContent(ReportEnum.CI.getNumber());
        this.createContent(ReportEnum.MTC.getNumber());
        this.createContent(ReportEnum.PREP.getNumber());
        this.createContent(ReportEnum.C.getNumber());
        this.createContent(ReportEnum.RPREP.getNumber());
        this.createContent(ReportEnum.CIHC.getNumber());
        this.createContent(ReportEnum.HG.getNumber());
        this.createContent(ReportEnum.SP.getNumber());
        this.createContent(ReportEnum.IM.getNumber());
        this.createContent(ReportEnum.HI.getNumber());
    }

    public void createContent(Integer reportNumber) {
        if (reportNumber.equals(ReportEnum.HRI.getNumber())) {//BẢNG 1: HOẠT ĐỘNG CAN THIỆP GIẢM TÁC HẠI
            this.saveReportContent(1,"1", "Người sử dụng ma túy", reportNumber, true, false, false);
            this.saveReportContent(2, "2","Người bán dâm", reportNumber, true, false, false);
            this.saveReportContent(3, "3","Người có quan hệ tình dục đồng giới", reportNumber, true, false, false);
            this.saveReportContent(4, "4","Người chuyển đổi giới tính (TG)", reportNumber, true, false, false);
            this.saveReportContent(5, "5","Vợ, chồng, bạn tình, bạn chích của người nhiễm HIV", reportNumber, true, false, false);
            this.saveReportContent(6, "6","Đối tượng khác", reportNumber, true, false, false);
        }else if (reportNumber.equals(ReportEnum.ATH.getNumber())) {//BẢNG 2: TƯ VẤN XÉT NGHIỆM HIV
            this.saveReportContent(1,"I", "Người từ 15 tuổi trở lên", reportNumber, false,true, false);
            this.saveReportContent(2, "1","Người sử dụng ma túy", reportNumber,true,false,false);
            this.saveReportContent(3, "2","Người bán dâm", reportNumber,true,false,false);
            this.saveReportContent(4, "3","Người có quan hệ tình dục đồng giới", reportNumber,true,false,false);
            this.saveReportContent(5, "4","Người chuyển đổi giới tính (TG)", reportNumber,true,false,false);
            this.saveReportContent(6, "5","Vợ, chồng, bạn tình, bạn chích của người nhiễm HIV", reportNumber,true,false,false);
            this.saveReportContent(7, "6","Phụ nữ mang thai", reportNumber,true,false,false);
            this.saveReportContent(8, "6.1","Thời kỳ mang thai", reportNumber,true,false,true);
            this.saveReportContent(9, "6.2","Giai đoạn chuyển dạ, đẻ", reportNumber,true,false,true);
            this.saveReportContent(10, "7","Bệnh nhân lao", reportNumber,true,false,false);
            this.saveReportContent(11, "8","Can phạm, phạm nhân", reportNumber,true,false,false);
            this.saveReportContent(12, "9","Bệnh nhân mắc các nhiễm trùng LTQĐTD", reportNumber,true,false,false);
            this.saveReportContent(13, "10","Thanh niên khám tuyển nghĩa vụ quân sự", reportNumber,true,false,false);
            this.saveReportContent(14, "11","Các đối tượng khác", reportNumber,true,false,false);
            this.saveReportContent(15, "II","Trẻ em dưới 15 tuổi", reportNumber,false,true,false);
        }else if (reportNumber.equals(ReportEnum.POC.getNumber())) {//BẢNG 3: Truyền thông phòng, chống HIV/AIDS
            this.saveReportContent(1, "1","Số lượt truyền thông về HIV/AIDS (các hình thức)", reportNumber,true,false,false);
            this.saveReportContent(2, "2","Số lượt người được truyền thông về HIV/AIDS", reportNumber,true, false,false);
        }else if (reportNumber.equals(ReportEnum.MD.getNumber())) {//Điều trị nghiện các chất dạng thuốc phiện bằng thuốc thay thế (Methadon)
            this.saveReportContent(1, "1","Số bệnh nhân hiện đang điều trị Methadone tại thời điểm báo cáo. Trong đó:",reportNumber, true,false,false);
            this.saveReportContent(2, "1.1","Số bệnh nhân điều trị trên 6 tháng", reportNumber, true,false,true);
            this.saveReportContent(3, "1.2","Số bệnh nhân điều trị trên 12 tháng", reportNumber, true,false,true);
            this.saveReportContent(4, "1.3","Số bệnh nhân HIV (+)", reportNumber, true,false,true);
            this.saveReportContent(5, "2","Số bệnh nhân nhận thuốc tại cơ sở điều trị", reportNumber, true,false,false);
            this.saveReportContent(6, "3","Số bệnh nhân nhận thuốc tại cơ sở cấp phát thuốc", reportNumber, true,false,false);
            this.saveReportContent(7, "4","Số bệnh nhân được cấp phát thuốc nhiều ngày", reportNumber, true,false,false);
            this.saveReportContent(8, "5","Số bệnh nhân bỏ điều trị trong kỳ báo cáo", reportNumber, true,false,false);
        }else if (reportNumber.equals(ReportEnum.ARV.getNumber())) {//Điều trị ARV
            this.saveReportContent(1, "I","Điều trị ARV", reportNumber,false,true,false);
            this.saveReportContent(2, "1","Số bệnh nhân điều trị ARV cuối kỳ báo cáo trước", reportNumber,true,false,false);
            this.saveReportContent(3, "2","Số bệnh nhân điều trị ARV trong kỳ báo cáo", reportNumber,true,false,false);
            this.saveReportContent(4, "2.1","Số bệnh nhân bắt đầu điều trị lần đầu", reportNumber,true,false,true);
            this.saveReportContent(5, "2.2","Số bệnh nhân điều trị lại", reportNumber,true,false,true);
            this.saveReportContent(6, "2.3","Số bệnh nhân chuyển đến", reportNumber,true,false,true);
            this.saveReportContent(7, "2.4","Số bệnh nhân chuyển đi", reportNumber,true,false,true);
            this.saveReportContent(8, "2.5","Số bệnh nhân bỏ điều trị", reportNumber,true,false,true);
            this.saveReportContent(9, "2.6","Số bệnh nhân tử vong", reportNumber,true,false,true);
            this.saveReportContent(10, "2.7","Số bệnh nhân hiện đang điều trị ARV tính đến cuối kỳ báo cáo này", reportNumber,true,false,true);
            this.saveReportContent(11, "II","Xét nghiệm Tải lượng vi rút", reportNumber,false,true,false);
            this.saveReportContent(12,"1","Số bệnh nhân điều trị ARV được làm và có kết quả xét nghiệm tải lượng định kỳ tại thời điểm 6 tháng",
                    reportNumber,true,false,false);
            this.saveReportContent(13, "1","Trong đó, số bệnh nhân có kết quả tải lượng HIV dưới 1000 cp/ml", reportNumber,true,false,true);
            this.saveReportContent(14,"2",
                    "Số bệnh nhân điều trị ARV được làm và có kết quả xét nghiệm tải lượng định kỳ hằng năm",
                    reportNumber,true,false,false);
            this.saveReportContent(15, "2","Trong đó, số bệnh nhân có kết quả tải lượng HIV dưới 1000 cp/ml", reportNumber,true,false,true);
        }else if (reportNumber.equals(ReportEnum.CI.getNumber())) {//QUẢN LÝ ĐIỀU TRỊ ĐỒNG NHIỄM HIV VÀ LAO
            this.saveReportContent(1, "I","Điều trị Lao tiềm ẩn", reportNumber, false, true , false);
            this.saveReportContent(2, "1",
                    "Số bệnh nhân bắt đầu điều trị ARV lần đầu đủ điều kiện điều trị lao tiềm ẩn được bắt đầu điều trị lao tiềm ẩn trong kỳ báo cáo",
                    reportNumber, true, false, false);
            this.saveReportContent(3,"2", "Số bệnh nhân bắt đầu điều trị ARV lần đầu hoàn thành điều trị lao tiềm ẩn trong kỳ báo cáo ", reportNumber, true, false, true);
            this.saveReportContent(4, "3",
                    "Số bệnh nhân đang điều trị ARV được điều trị lao tiềm ẩn trong kỳ báo cáo ",
                    reportNumber, true, false, false);
            this.saveReportContent(5, "4","Số bệnh nhân đang điều trị ARV hoàn thành điều trị lao tiềm ẩn trong kỳ báo cáo",
                    reportNumber, true, false, false);
            this.saveReportContent(6, "II","Điều trị đồng nhiễm HIV và Lao", reportNumber, false, true , false);
            this.saveReportContent(7, "1","Số bệnh nhân đang điều trị ARV chẩn đoán mắc lao trong kỳ báo cáo",
                    reportNumber, true, false, false);
            this.saveReportContent(8, "2","Số bệnh nhân đang điều trị ARV bắt đầu được điều trị lao trong kỳ báo cáo", reportNumber, true, false, false);
            this.saveReportContent(9, "3","Số bệnh nhân đang điều trị lao phát hiện nhiễm HIV bắt đầu được điều trị ARV trong kỳ báo cáo", reportNumber, true, false, false);
        }else if (reportNumber.equals(ReportEnum.MTC.getNumber())) {//DỰ PHÒNG LÂY TRUYỀN HIV TỪ MẸ SANG CON
            this.saveReportContent(1, "1","Số phụ nữ mang thai nhiễm HIV được điều trị ARV trong kỳ báo cáo, Trong đó:",
                    reportNumber, true,false, false);
            this.saveReportContent(2, "1.1","Điều trị ARV trước khi có thai", reportNumber, true,false, true);
            this.saveReportContent(3, "1.2"," Bắt đầu điều trị ARV trong thời kỳ mang thai", reportNumber, true,false, true);
            this.saveReportContent(4, "1.3", "Bắt đầu điều trị ARV trong khi chuyển dạ, đẻ", reportNumber, true,false, true);
            this.saveReportContent(5, "2","Số trẻ đẻ sống sinh ra từ mẹ nhiễm HIV, trong đó:", reportNumber, true,false, false);
            this.saveReportContent(6, "2.1"," Được dự phòng ARV", reportNumber, true,false, true);
            this.saveReportContent(7, "2.2"," Được dự phòng bằng thuốc co-trimoxazole (CTX) trong vòng 2 tháng sau sinh",
                    reportNumber, true,false, true);
            this.saveReportContent(8, "2.3"," Được điều trị ARV khi xét nghiệm sinh học phân tử (PCR) lần 1 dương tính", reportNumber, true,false, true);
        } else if (reportNumber.equals(ReportEnum.PREP.getNumber())) {//DỰ PHÒNG TRƯỚC PHƠI NHIỄM HIV (PrEP)
            this.saveReportContent(1,"1","Số khách hàng điều trị PrEP lần đầu trong kỳ báo cáo",reportNumber,true,false,false);
            this.saveReportContent(2, "2","Số khách hàng điều trị PrEP ít nhất 1 lần trong kỳ báo cáo (bao gồm số khách hàng đang điều trị từ kỳ báo cáo trước cộng với số KH mới điều trị cộng với số KH điều trị lại)", reportNumber,true, false, false);
            this.saveReportContent(3, "3","Số khách hàng đang điều trị PrEP tính tại thời điểm cuối kỳ báo cáo (số khách hàng tại mục 2 trừ đi số bỏ trị và số chuyển đi)", reportNumber,true,false,false);
        }else if (reportNumber.equals(ReportEnum.C.getNumber())) {//Chuẩn đoán nhiễm HIV cho trẻ dưới 18 tháng tuổi
            this.saveReportContent(1, "1",
                    "Số trẻ em sinh ra từ mẹ nhiễm HIV được làm xét nghiệm sinh học phân tử (PCR) lần 1, trong đó:",
                    reportNumber, true, false,false);
            this.saveReportContent(2, "2", "Số trẻ em được xét nghiệm trong vòng 2 tháng tuổi:", reportNumber, true, false, false);
            this.saveReportContent(3, "3","Số trẻ em được xét nghiệm từ 2 đến 18 tháng tuổi:", reportNumber, true, false, false);
        }else if (reportNumber.equals(ReportEnum.RPREP.getNumber())) {//Duy trì điều trị dự phòng trước phơi nhiễm HIV (PrEP)
            this.saveReportContent(1, "1", "Số khách hàng bắt đầu điều trị PrEP trong năm " +
                            "(số khách hàng điều trị lần đầu và số khách hàng đã điều trị của các năm trước điều trị lại)",
                    reportNumber, true, false,false);
            this.saveReportContent(2, "2",
                    "Số khách hàng bắt đầu điều trị PrEP trong năm duy trì điều trị trong 3 tháng liên tục",
                    reportNumber, true, false,false);
            this.saveReportContent(3, "3",
                    "Số khách hàng bắt đầu điều trị PrEP trong năm bỏ trị (số khách hàng điều trị lần đầu và số" +
                            " khách hàng đã điều trị của các năm trước điều trị lại bỏ điều trị)",
                    reportNumber, true, false,false);
        }else if (reportNumber.equals(ReportEnum.CIHC.getNumber())) {//Điều trị đồng nhiễm HIV và viêm gan C
            this.saveReportContent(1, "1","Số bệnh nhân nhiễm HIV đồng nhiễm viêm gan C trong kỳ báo cáo", reportNumber, true,false,false);
            this.saveReportContent(2, "2","Số bệnh nhân nhiễm HIV đồng nhiễm viêm gan C được bắt đầu điều trị viêm gan C trong kỳ báo cáo" ,reportNumber,true, false, false);
        }else if (reportNumber.equals(ReportEnum.HG.getNumber())) {//Số lượng tối tượng nguy cơ cao
            this.saveReportContent(1, "1","Nghiện chích ma tuý", reportNumber,true, false, false);
            this.saveReportContent(2, "2","Phụ nữ bán dâm", reportNumber,true, false, false);
            this.saveReportContent(3, "3","Nam có quan hệ tình dục với nam", reportNumber,true, false, false);
            this.saveReportContent(4, "4","Người chuyển đổi giới tính (TG)", reportNumber,true, false, false);
        } else if (reportNumber.equals(ReportEnum.SP.getNumber())) {//Điểm cung cấp dịch vụ
            this.saveReportContent(1, "1","Số phòng khám ngoại trú (OPC)", reportNumber,true, false, false);
            this.saveReportContent(2, "2","Số cơ sở điều trị Methadone", reportNumber,true, false, false);
            this.saveReportContent(3, "3","Số cở sở cấp phát thuốc Methadone", reportNumber,true, false, false);
            this.saveReportContent(4, "4","Số cơ sở tư vấn xét nghiệm tự nguyện", reportNumber,true, false, false);
            this.saveReportContent(5, "5","Số cơ sở điều trị PrEP", reportNumber,true, false, false);
        }else if(reportNumber.equals(ReportEnum.IM.getNumber())){//Kinh phí triển khai các dịch vụ phòng chống HIV / AIDS
            this.saveReportContent(1, "1","Ngân sách địa phương", reportNumber,true, false, false);
            this.saveReportContent(2, "2","Viện trợ", reportNumber,true, false, false);
            this.saveReportContent(3, "3","Khu vực tư nhân", reportNumber,true, false, false);
            this.saveReportContent(4, "4","Xã hội hoá", reportNumber,true, false, true);
            this.saveReportContent(5, "5","Đồng chi trả ARV", reportNumber,true, false, false);
            this.saveReportContent(6, "6","Thu phí dịch vụ", reportNumber,true, false, false);
        }else if(reportNumber.equals(ReportEnum.HI.getNumber())){// Bảo hiểm y tế
            this.saveReportContent(1, "1","Số người đang điều trị ARV đến cuối kỳ báo cáo có thẻ BHYT được chi trả 100% chi phí khám, chữa bệnh", reportNumber,true, false, false);
            this.saveReportContent(2, "2","Số người đang điều trị ARV đến cuối kỳ báo cáo có thẻ BHYT được chi trả 95% chi phí khám, chữa bệnh",reportNumber,true, false, false);
            this.saveReportContent(3,"3","Số người đang điều trị ARV đến cuối kỳ báo cáo có thẻ BHYT được chi trả 80% chi phí khám, chữa bệnh", reportNumber,true, false, false);
        }
    }
    public void saveReportContent(Integer indexNumber, String subContent, String title, Integer reportNumber
            , Boolean canWrite, Boolean bold, Boolean italics) {
        ReportContentRequest request = new ReportContentRequest();
        request.setIndexNumber(indexNumber);
        request.setSubContent(subContent);
        request.setTitle(title);
        request.setReportNumber(reportNumber);
        request.setCanWrite(canWrite);
        request.setBold(bold);
        request.setItalics(italics);
        ResponseList<ReportContent> existData = reportContentService.getByIndexNumber(indexNumber, reportNumber);
        if (CollectionUtils.isEmpty(existData.getContents())) {
            reportContentService.saveReportContent(request);
        }
    }
}
