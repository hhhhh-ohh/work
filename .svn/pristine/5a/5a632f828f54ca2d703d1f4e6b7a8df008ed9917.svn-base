package com.wanmi.ares.report.customer.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.base.service.CustomerAreaTradeBaseService;
import com.wanmi.ares.report.customer.dao.AreaDistributeReportMapper;
import com.wanmi.ares.report.customer.dao.CustomerOrderReportMapper;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.customer.model.request.CustomerOrderDataRequest;
import com.wanmi.ares.report.customer.model.root.CustomerAreaReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.source.model.root.region.City;
import com.wanmi.ares.source.model.root.region.Province;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jodd.util.RandomString;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 会员模块导出报表
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class CustomerAreaTradeExportService implements ExportBaseService {

    @Autowired
    private CustomerOrderReportMapper customerOrderReportMapper;

    @Autowired
    private CustomerTradeExportService customerTradeExportService;

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;


    @Autowired
    private ReplayStoreMapper replayStoreMapper;

    @Autowired
    private AreaDistributeReportMapper areaDistributeReportMapper;

    @Autowired
    private CustomerAreaTradeBaseService customerAreaTradeBaseService;


    private static final String EXCEL_TYPE = "%s.xls";

    /**
     * 导出列字段
     * @return
     */
    public Column[] getAreaReportColumn(){
        return new Column[]{
                new Column("客户地区", (cell, object) -> {
                    CustomerAreaReport areaReport = (CustomerAreaReport) object;
                    cell.setCellValue(parseAreaName(areaReport.getCityId()));
                }),
                new Column("下单笔数", new SpelColumnRender<CustomerAreaReport>("orderCount")),
                new Column("下单件数", new SpelColumnRender<CustomerAreaReport>("skuCount")),
                new Column("下单金额", (cell, object) -> {
                    CustomerAreaReport areaReport = (CustomerAreaReport) object;
                    cell.setCellValue(areaReport.getAmount().toString());
                }),
                new Column("付款订单数", new SpelColumnRender<CustomerAreaReport>("payOrderCount")),
                new Column("付款金额", (cell, object) -> {
                    CustomerAreaReport areaReport = (CustomerAreaReport) object;
                    cell.setCellValue(areaReport.getPayAmount().toString());
                }),
                new Column("客单价", (cell, object) -> {
                    CustomerAreaReport areaReport = (CustomerAreaReport) object;
                    if (Objects.nonNull(areaReport.getUserPerPrice())) {
                        cell.setCellValue(areaReport.getUserPerPrice().setScale(2, RoundingMode.HALF_UP).toString());
                    } else {
                        cell.setCellValue(BigDecimal.ZERO.setScale(2).toString());
                    }
                }),
                new Column("笔单价", (cell, object) -> {
                    CustomerAreaReport areaReport = (CustomerAreaReport) object;
                    if (Objects.nonNull(areaReport.getOrderPerPrice())) {
                        cell.setCellValue(areaReport.getOrderPerPrice().setScale(2, RoundingMode.HALF_UP).toString());
                    } else {
                        cell.setCellValue(BigDecimal.ZERO.setScale(2).toString());
                    }
                }),
                new Column("退单笔数", new SpelColumnRender<CustomerAreaReport>("returnCount")),
                new Column("退货件数", new SpelColumnRender<CustomerAreaReport>("returnSkuCount")),
                new Column("退单金额", (cell, object) -> {
                    CustomerAreaReport areaReport = (CustomerAreaReport) object;
                    cell.setCellValue(areaReport.getReturnAmount().toString());
                })
        };
    }

    /**
     * 会员地区报表构造
     *
     * @param customerReports customerReports
     * @param excelHelper     excelHelper
     */
    private void customerAreaReportSheet(List<CustomerAreaReport> customerReports, ExcelHelper excelHelper) {
        excelHelper.addSheet("客户订货报表-按地区查看", getAreaReportColumn(), customerReports);
    }

    /**
     * 计算页码
     *
     * @param count
     * @param size
     * @return
     */
    private int calPage(int count, int size) {
        int page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }

    private static Set<String> specialRegion = new HashSet(Arrays.asList("810000", "820000", "710000"));


    /**
     * 获取省市区名字
     *
     * @param cityCode cityCode
     * @return name
     */
    private String parseAreaName(String cityCode) {
        String areaName = "";
        if (specialRegion.contains(cityCode)) {
            switch (cityCode) {
                case "810000":
                    areaName = "香港特别行政区";
                    break;
                case "820000":
                    areaName = "澳门特别行政区";
                    break;
                case "710000":
                    areaName = "台湾省";
                    break;
                default:
            }
        } else if (cityCode.equals("-1")) {
            areaName = "其他";
        } else {
            City city = areaDistributeReportMapper.queryCityInfo(cityCode);
            if (Objects.nonNull(city)) {
                Province province = areaDistributeReportMapper.queryProvinceInfo(cityCode);
                if (Objects.nonNull(province)) {
                    areaName =
                            ObjectUtils.toString(province.getName()).concat("/").concat(ObjectUtils.toString(city.getName()));
                }
            }
        }
        if (StringUtils.isBlank(areaName)) {
            areaName = "其他";
        }
        return areaName;
    }

    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws IOException {
        List<String> fileUrl = Lists.newArrayList();
        if (StringUtils.isBlank(exportQuery.getCompanyId())) {
            exportQuery.setCompanyId(Constants.BOSS_ID);
        }
        int storeType = exportQuery.getStoreSelectType() == null ? 0 : exportQuery.getStoreSelectType().toValue();
        LocalDate endDate = DateUtil.parse2Date(exportQuery.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        //客户名称
        String commonFileName = String.format("customer/order/%s/%s客户订货报表_按地区查看_%s_%s-%s", DateUtil.format(endDate,
                DateUtil.FMT_MONTH_2), storeService.getStoreName(exportQuery), exportQuery.getDateFrom(),
                exportQuery.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(DateUtil.parse2Date(exportQuery.getDateFrom(), DateUtil.FMT_DATE_1));
        customerOrderDataRequest.setEndDate(DateUtil.parse2Date(exportQuery.getDateTo(), DateUtil.FMT_DATE_1).plusDays(1));
        customerOrderDataRequest.setShopType(0);
        customerOrderDataRequest.setStoreSelectType(storeType);
        int totalNum = customerOrderReportMapper.exportCustomerAreaOrderTotalForBoss(customerOrderDataRequest);
        if (!exportQuery.getCompanyId().equals(Constants.BOSS_ID)) {
            customerOrderDataRequest.setShopType(1);
            customerOrderDataRequest.setCompanyInfoId(Integer.parseInt(exportQuery.getCompanyId()));
            totalNum = customerOrderReportMapper.exportCustomerAreaOrderTotalForSupplier(customerOrderDataRequest);
        }
        //查询说有门店或者商家
        else if (storeType != 0) {
            totalNum = customerOrderReportMapper.exportCustomerAreaOrderTotalForSupplierByStoreType(customerOrderDataRequest);
        }

        if (totalNum == 0) {
            String fileName = String.format(EXCEL_TYPE, commonFileName);
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper excelHelper = new ExcelHelper();
                customerTradeExportService.customerReportSheet(Lists.newArrayList(), excelHelper);
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, fileName);
            }
            fileUrl.add(fileName);
            return BaseResponse.success(fileUrl);
        }
        int fileSize = calPage(totalNum, exportQuery.getSize());
        for (int i = 0; i < fileSize; i++) {
            ExcelHelper excelHelper = new ExcelHelper();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int num = 0; num < calPage(totalNum - i * exportQuery.getSize(), 500); num++) {
                customerOrderDataRequest.setNumber((long) num + i * 10);
                customerOrderDataRequest.setSize(500L);
                List<CustomerAreaReport> customerReports = customerAreaTradeBaseService.queryExport(exportQuery,customerOrderDataRequest);
                customerAreaReportSheet(customerReports, excelHelper);
                excelHelper.write(byteArrayOutputStream);
                if (num > 9) {
                    break;
                }
            }
            String fileName = i == 0 ? String.format(EXCEL_TYPE, commonFileName) : String.format("%s(%s).xls",
                    commonFileName,
                    String.valueOf(i));
            osdService.uploadExcel(byteArrayOutputStream, fileName);
            fileUrl.add(fileName);
        }
        return BaseResponse.success(fileUrl);
    }
}
