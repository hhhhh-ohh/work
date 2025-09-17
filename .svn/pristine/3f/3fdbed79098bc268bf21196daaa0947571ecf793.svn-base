package com.wanmi.sbc.mq.report.service;/**
 * @author 黄昭
 * @create 2021/9/9 16:32
 */

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingPageRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className PickupSettingExportService
 * @description TODO
 * @author 黄昭
 * @date 2021/9/9 16:32
 **/
@Service
@Slf4j
public class PickupSettingExportService implements ExportBaseService {

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    public static final int size = 5000;


    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        PickupSettingPageRequest req = JSON.parseObject(data.getParam(), PickupSettingPageRequest.class);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出自提点明细_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("pickupSetting/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("自提点明细", columns);

        req.setDelFlag(DeleteFlag.NO);
        req.putSort("id", "desc");

        //总数量
        Long total = pickupSettingQueryProvider.total(req).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, size);
        //分页处理
        int rowIndex = 0;
        req.setPageSize(size);
        for (int i = 0; i < fileSize; i++) {
            req.setPageNum(i);
            List<PickupSettingVO> dataRecords = pickupSettingQueryProvider.page(req).getContext().getPickupSettingVOS().getContent();
            getInfo(dataRecords);
            excelHelper.addSXSSFSheetRow(sheet, columns, dataRecords, rowIndex + 1);
            rowIndex = rowIndex + dataRecords.size();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    /**
     * 导出列表数据具体实现
     */
    public Column[] getColumns() {

        return new Column[]{
                new Column("自提点名称", new SpelColumnRender<PickupSettingVO>("name")),
                new Column("所在地区", new SpelColumnRender<PickupSettingVO>("address")),
                new Column("详细地址",new SpelColumnRender<PickupSettingVO>("pickupAddress")),
                new Column("自提点联系电话", new SpelColumnRender<PickupSettingVO>("phone")),
                new Column("所属商家", new SpelColumnRender<PickupSettingVO>("storeName")),
                new Column("审核状态", new SpelColumnRender<PickupSettingVO>("auditStatusName")),
                new Column("驳回理由", new SpelColumnRender<PickupSettingVO>("auditReason")),
                new Column("启用状态", new SpelColumnRender<PickupSettingVO>("enableStatusName")),
        };
    }

    public void getInfo(List<PickupSettingVO> pickupSettingList) {

        if (CollectionUtils.isNotEmpty(pickupSettingList)) {
            List<String> addrIds = new ArrayList<>();
            pickupSettingList.forEach(detail -> {
                addrIds.add(Objects.toString(detail.getProvinceId()));
                addrIds.add(Objects.toString(detail.getCityId()));
                addrIds.add(Objects.toString(detail.getAreaId()));
                addrIds.add(Objects.toString(detail.getStreetId()));
            });
            //根据Id获取地址信息
            List<PlatformAddressVO> voList = platformAddressQueryProvider.list(PlatformAddressListRequest.builder()
                    .addrIdList(addrIds).build()).getContext().getPlatformAddressVOList();

            Map<String, String> addrMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(voList)) {
                addrMap = voList.stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
            }

            BaseResponse<ListNoDeleteStoreByIdsResponse> response = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest
                    .builder().storeIds(pickupSettingList.stream().map(PickupSettingVO::getStoreId).collect(Collectors.toList())).build());

            for (PickupSettingVO pickupSetting : pickupSettingList) {

                List<StoreVO> storeVOList = response.getContext().getStoreVOList();
                if (CollectionUtils.isNotEmpty(storeVOList)) {
                    Optional<StoreVO> optional = storeVOList.stream().filter(storeVO -> pickupSetting.getStoreId().equals(storeVO.getStoreId())).findFirst();
                    optional.ifPresent(storeVO -> pickupSetting.setStoreName(storeVO.getStoreName()));
                }

                pickupSetting.setAuditStatusName(AuditStatus.WAIT_CHECK.equals(AuditStatus.fromValue(pickupSetting.getAuditStatus()))?"待审核"
                        :AuditStatus.CHECKED.equals(AuditStatus.fromValue(pickupSetting.getAuditStatus()))?"已审核":"审核不通过");
                String provinceName = addrMap.getOrDefault(Objects.toString(pickupSetting.getProvinceId()), StringUtils.EMPTY);
                String cityName = addrMap.getOrDefault(Objects.toString(pickupSetting.getCityId()), StringUtils.EMPTY);
                String areaName = addrMap.getOrDefault(Objects.toString(pickupSetting.getAreaId()),StringUtils.EMPTY);
                String streetName = addrMap.getOrDefault(Objects.toString(pickupSetting.getStreetId()), StringUtils.EMPTY);
                String address = provinceName + (StringUtils.isBlank(provinceName) ? StringUtils.EMPTY : "/") +
                        cityName + (StringUtils.isBlank(cityName) ? StringUtils.EMPTY : "/") +
                        areaName + (StringUtils.isBlank(areaName) ? StringUtils.EMPTY : "/") +
                        streetName;
                pickupSetting.setAddress(address);
                pickupSetting.setName(EnableStatus.ENABLE.toValue()==pickupSetting.getIsDefaultAddress()?pickupSetting.getName()+"(默认自提点)":pickupSetting.getName());
                if (AuditStatus.CHECKED.equals(AuditStatus.fromValue(pickupSetting.getAuditStatus()))) {
                    pickupSetting.setEnableStatusName(EnableStatus.ENABLE.toValue() == pickupSetting.getEnableStatus() ? "启用" : "禁用");
                }
            }
        }
    }
}
