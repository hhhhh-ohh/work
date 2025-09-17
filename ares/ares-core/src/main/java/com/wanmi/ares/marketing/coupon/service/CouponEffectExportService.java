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
import com.wanmi.ares.view.coupon.CouponEffectView;
import com.wanmi.ares.view.coupon.CouponInfoEffectView;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName CouponScheduled
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/1/11 9:36
 * @Version 1.0
 **/
@Service
public class CouponEffectExportService implements ExportBaseService {


    @Autowired
    private CouponEffectService couponEffectService;
    @Autowired
    private OsdService osdService;
    @Autowired
    private StoreService storeService;

    /**
     * 导出优惠券效果数据
     * @param query
     * @return
     * @throws Exception
     */
    public List<String> exportCouponInfoEffect(ExportQuery query) throws Exception{

        List<String> retList = new ArrayList<>();
        CouponEffectRequest request = new CouponEffectRequest();
        request.setStatType(query.getStatType().getValue());
        request.setStoreId(query.getStoreId());
        request.setPageSize(query.getSize());
        int pageNum = 0;
        boolean flag = true;
        while (flag){
            request.setPageNum(pageNum);
            PageInfo<CouponInfoEffectView> pageInfo;
            String commonFileName = osdService.getFileRootPath();

            if(StringUtils.isEmpty(query.getCompanyId())){
                query.setCompanyId("0");
            }
            if(query.getStoreId() == null){
                request.setStoreId(-1L);
            }
            String randomData = RandomString.get().randomAlpha(4);
            commonFileName = commonFileName.concat(String.format("marketing/coupon/%s/%s/%s优惠券报表_%s_%s-%s",
                    query.getStoreId(),
                    query.getStatType().name(),
                    storeService.getStoreName(query) ,
                    query.getDateFrom(),
                    query.getDateTo(),
                    randomData));
            pageInfo = couponEffectService.pageCouponInfoEffect(request);

            if(pageInfo.getTotal()<=0){
                retList.add(couponInfoEffectUpload(pageInfo.getList(),commonFileName,pageNum));
                flag = false;
                break;
            }else{
                if(pageInfo.getSize()>0){
                    retList.add(couponInfoEffectUpload(pageInfo.getList(),commonFileName,pageNum));
                    if(pageInfo.isHasNextPage()){
                        pageNum++;
                        continue;
                    }else {
                        flag = false;
                        break;
                    }
                }else{
                    flag = false;
                    break;
                }
            }
        }
        return retList;
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getEffectUploadColumn(){
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("优惠券名称", new SpelColumnRender<CouponInfoEffectView>("couponName")));
        columns.add(new Column("面值", new SpelColumnRender<CouponInfoEffectView>("denominationStr")));
        columns.addAll(commonSheet());
        return columns.toArray(new Column[columns.size()]);
    }


    public String couponInfoEffectUpload(List<CouponInfoEffectView> list, String commonFileName, int pageNum) throws Exception {
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.addSheet("活动分析-优惠券效果", getEffectUploadColumn(), list);

        return uploadFile(excelHelper,commonFileName,pageNum);
    }

    public List<Column> commonSheet() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("已领取人/张", new SpelColumnRender<CouponEffectView>("acquireData")));
        columns.add(new Column("已使用人/张", new SpelColumnRender<CouponEffectView>("useData")));
        columns.add(new Column("使用率%", (cell, object) -> {
            CouponEffectView couponEffectView = (CouponEffectView) object;
            if(Objects.nonNull(couponEffectView.getAcquireData())){
                String use = couponEffectView.getAcquireData().split("/")[1];
                if(StringUtils.isNotBlank(use) && Long.parseLong(use) > 0){
                    cell.setCellValue(couponEffectView.getUseRate() + "%");
                }else{
                    cell.setCellValue("-");
                }
            }else{
                cell.setCellValue("-");
            }
        }));
        columns.add(new Column("ROI", (cell, object) -> {
            CouponEffectView couponEffectView = (CouponEffectView) object;
            if (Objects.nonNull(couponEffectView.getRoi())){
                cell.setCellValue(couponEffectView.getRoi().doubleValue());
            }else{
                cell.setCellValue("-");
            }
        }));
        columns.add(new Column("营销支付金额", new SpelColumnRender<CouponEffectView>("payMoney")));
        columns.add(new Column("营销优惠金额", new SpelColumnRender<CouponEffectView>("discountMoney")));
        columns.add(new Column("营销支付人数", new SpelColumnRender<CouponEffectView>("payCustomerCount")));
        columns.add(new Column("营销支付件数", new SpelColumnRender<CouponEffectView>("payGoodsCount")));
        columns.add(new Column("营销支付订单数", new SpelColumnRender<CouponEffectView>("payTradeCount")));
        columns.add(new Column("新成交客户", new SpelColumnRender<CouponEffectView>("newCustomerCount")));
        columns.add(new Column("老成交客户", new SpelColumnRender<CouponEffectView>("oldCustomerCount")));
        columns.add(new Column("连带率", (cell, object) -> {
            CouponEffectView couponEffectView = (CouponEffectView) object;
            if (Objects.nonNull(couponEffectView.getJointRate())){
                cell.setCellValue(couponEffectView.getJointRate().doubleValue());
            }else{
                cell.setCellValue("-");
            }
        }));
        columns.add(new Column("客单价", (cell, object) -> {
            CouponEffectView couponEffectView = (CouponEffectView) object;
            if (Objects.nonNull(couponEffectView.getCustomerPrice())){
                cell.setCellValue(couponEffectView.getCustomerPrice().doubleValue());
            }else{
                cell.setCellValue("-");
            }
        }));
        columns.add(new Column("供货价", (cell, object) -> {
            CouponEffectView couponEffectView = (CouponEffectView) object;
            if (Objects.nonNull(couponEffectView.getSupplyPrice())){
                cell.setCellValue(couponEffectView.getSupplyPrice().doubleValue());
            }else{
                cell.setCellValue("-");
            }
        }));
        return columns;
    }

    String uploadFile(ExcelHelper excelHelper, String commonFileName, int pageNum) throws Exception{
        String fileName = null;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            excelHelper.write(baos);
            //如果超过一页，文件名后缀增加(索引值)
            String suffix = StringUtils.EMPTY;
            if (pageNum > 0) {
                suffix = "(".concat(String.valueOf(pageNum + 1)).concat(")");
            }
            fileName = String.format("%s%s.xls", commonFileName, suffix);
            osdService.uploadExcel(baos, fileName);
        }
        return fileName;
    }

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
        while (flag){
            request.setPageNum(pageNum);
            PageInfo<CouponInfoEffectView> pageInfo;
            String commonFileName = osdService.getFileRootPath();

            if(StringUtils.isEmpty(query.getCompanyId())){
                query.setCompanyId("0");
            }
            if(query.getStoreId() == null){
                request.setStoreId(-1L);
            }

            String randomData = RandomString.get().randomAlpha(4);
            commonFileName = commonFileName.concat(String.format("marketing/coupon/%s/%s/%s优惠券报表_%s_%s-%s",
                    query.getStoreId(),
                    query.getStatType().name(),
                    storeService.getStoreName(query) ,
                    query.getDateFrom(),
                    query.getDateTo(),
                    randomData));
            pageInfo = couponEffectService.pageCouponInfoEffect(request);
            if(pageInfo.getTotal()<=0){
                retList.add(couponInfoEffectUpload(pageInfo.getList(),commonFileName,pageNum));
                flag = false;
                break;
            }else{
                if(pageInfo.getSize()>0){
                    retList.add(couponInfoEffectUpload(pageInfo.getList(),commonFileName,pageNum));
                    if(pageInfo.isHasNextPage()){
                        pageNum++;
                        continue;
                    }else {
                        flag = false;
                        break;
                    }
                }else{
                    flag = false;
                    break;
                }
            }
        }
        return BaseResponse.success(retList);
    }
}
