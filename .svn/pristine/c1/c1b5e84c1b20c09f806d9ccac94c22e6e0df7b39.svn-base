package com.wanmi.ares.marketing.coupon.service;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.coupon.CouponActivityEffectRequest;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.coupon.CouponActivityEffectView;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CouponScheduled
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/1/11 9:36
 * @Version 1.0
 **/
@Service
public class CouponActivityEffectExportService implements ExportBaseService {


    @Autowired
    private CouponEffectService couponEffectService;
    @Autowired
    private OsdService osdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CouponEffectExportService couponEffectExportService;

    /**
     * 活动分析导出列
     * @return
     */
    public Column[] getEffectUploadColumn(){
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("优惠券活动名称", new SpelColumnRender<CouponActivityEffectView>("activityName")));
        columns.add(new Column("活动类型", (cell, object) -> {
            CouponActivityEffectView couponActivityEffectView = (CouponActivityEffectView) object;
            cell.setCellValue(couponActivityEffectView.getActivityType().toString());
        }));
        columns.addAll(couponEffectExportService.commonSheet());
        return columns.toArray(new Column[columns.size()]);
    }


    private String couponActivityEffectUpload(List<CouponActivityEffectView> list, String commonFileName,
                                              int pageNum) throws Exception {
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.addSheet("活动分析-优惠券活动效果", this.getEffectUploadColumn(), list);

        return couponEffectExportService.uploadFile(excelHelper, commonFileName, pageNum);
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws Exception {
        List<String> retList = new ArrayList<>();
        if (StringUtils.isEmpty(query.getCompanyId())) {
            query.setCompanyId("0");
        }
        if (query.getStoreId() == null) {
            query.setStoreId(-1L);
        }

        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("marketing/coupon/%s/%s/%s优惠券活动报表_%s_%s-%s",
                query.getStoreId(),
                query.getStatType().name(),
                storeService.getStoreName(query),
                query.getDateFrom(),
                query.getDateTo(),
                randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        CouponActivityEffectRequest request = new CouponActivityEffectRequest();
        request.setStatType(query.getStatType().getValue());
        request.setStoreId(query.getStoreId());
        request.setPageSize(query.getSize());
        int pageNum = 0;
        boolean flag = true;
        while (flag) {
            request.setPageNum(pageNum);
            PageInfo<CouponActivityEffectView> pageInfo;

            pageInfo = couponEffectService.pageCouponActivityEffect(request);

            if (pageInfo.getTotal() <= 0) {
                retList.add(couponActivityEffectUpload(pageInfo.getList(), commonFileName, pageNum));
                flag = false;
                break;
            } else {
                if (pageInfo.getSize() > 0) {
                    retList.add(couponActivityEffectUpload(pageInfo.getList(), commonFileName, pageNum));
                    if (pageInfo.isHasNextPage()) {
                        pageNum++;
                        continue;
                    } else {
                        flag = false;
                        break;
                    }
                } else {
                    flag = false;
                    break;
                }
            }
        }
        return BaseResponse.success(retList);
    }
}
