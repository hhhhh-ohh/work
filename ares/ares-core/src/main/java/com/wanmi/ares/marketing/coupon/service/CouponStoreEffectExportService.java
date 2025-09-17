package com.wanmi.ares.marketing.coupon.service;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.coupon.CouponStoreEffectView;
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
public class CouponStoreEffectExportService implements ExportBaseService {


    @Autowired
    private CouponEffectService couponEffectService;
    @Autowired
    private CouponEffectExportService couponEffectExportService;
    @Autowired
    private OsdService osdService;
    @Autowired
    private StoreService storeService;

    /**
     * 导出列字段
     * @return
     */
    public Column[] getStoreEffectColumn(){
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("店铺名称", new SpelColumnRender<CouponStoreEffectView>("storeName")));
        columns.addAll(couponEffectExportService.commonSheet());
        return columns.toArray(new Column[columns.size()]);
    }

    private String couponStoreEffectUpload(List<CouponStoreEffectView> list, String commonFileName, int pageNum) throws Exception {
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.addSheet("活动分析-优惠券店铺效果", getStoreEffectColumn(), list);

        return couponEffectExportService.uploadFile(excelHelper, commonFileName, pageNum);
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws Exception {
        if (StringUtils.isEmpty(query.getCompanyId())) {
            query.setCompanyId("0");
        }
        if (query.getStoreId() == null || query.getStoreId() == -1) {
            query.setStoreId(null);
        }

        List<String> retList = new ArrayList<>();
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("marketing/coupon/%s/%s/%s店铺优惠券报表_%s_%s-%s",
                query.getStoreId(),
                query.getStatType().name(),
                storeService.getStoreName(query),
                query.getDateFrom(),
                query.getDateTo(),
                randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        CouponEffectRequest request = new CouponEffectRequest();
        request.setStatType(query.getStatType().getValue());
        request.setStoreId(query.getStoreId());
        request.setPageSize(query.getSize());
        /*request.setSortName(query.getSortName());
        request.setSort(query.getSortOrder());*/
        int pageNum = 0;
        boolean flag = true;
        while (flag) {
            request.setPageNum(pageNum);
            PageInfo<CouponStoreEffectView> pageInfo;
            pageInfo = couponEffectService.pageCouponStoreEffect(request);
            if (pageInfo.getTotal() <= 0) {
                retList.add(couponStoreEffectUpload(pageInfo.getList(), commonFileName, pageNum));
                flag = false;
                break;
            } else {
                if (pageInfo.getSize() > 0) {
                    retList.add(couponStoreEffectUpload(pageInfo.getList(), commonFileName, pageNum));
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
