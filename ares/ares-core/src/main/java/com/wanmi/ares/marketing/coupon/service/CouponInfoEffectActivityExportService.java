package com.wanmi.ares.marketing.coupon.service;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.marketing.coupon.dao.CouponActivityEffectRecentMapper;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.coupon.CouponInfoEffectView;
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
public class CouponInfoEffectActivityExportService implements ExportBaseService {


    @Autowired
    private CouponEffectService couponEffectService;
    @Autowired
    private CouponActivityEffectRecentMapper couponActivityEffectRecentMapper;
    @Autowired
    private OsdService osdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CouponEffectExportService couponEffectExportService;

    @Override
    public BaseResponse exportReport(ExportQuery query) throws Exception {
        List<String> retList = new ArrayList<>();
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
            PageInfo<CouponInfoEffectView> pageInfo = new PageInfo<>();
            String commonFileName = osdService.getFileRootPath();
            if (StringUtils.isNotEmpty(query.getParam())) {
                String randomData = RandomString.get().randomAlpha(4);
                commonFileName = commonFileName.concat(String.format("marketing/coupon/%s/%s/%s%s优惠券活动详情报表_%s_%s-%s",
                        query.getStoreId(),
                        query.getStatType().name(),
                        storeService.getStoreName(query),
                        couponActivityEffectRecentMapper.getActivityNameById(query.getParam()),
                        query.getDateFrom(),
                        query.getDateTo(),
                        randomData));
                request.setId(query.getParam());
                pageInfo = couponEffectService.pageCouponInfoEffectByActivityId(request);
            }
            if (pageInfo.getTotal() <= 0) {
                retList.add(couponEffectExportService.couponInfoEffectUpload(pageInfo.getList(), commonFileName,
                        pageNum));
                flag = false;
                break;
            } else {
                if (pageInfo.getSize() > 0) {
                    retList.add(couponEffectExportService.couponInfoEffectUpload(pageInfo.getList(), commonFileName,
                            pageNum));
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
