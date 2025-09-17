package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoPageRequest;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponInfoVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodePageRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodePageResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponDiscountMode;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @description 优惠券信息导出
 * @author  lvzhenwei
 * @date 2022/12/20 10:14 上午
 **/
@Service
@Slf4j
public class CouponDetailExportService implements ExportBaseService {
    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private OsdService osdService;

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private EsCouponInfoQueryProvider esCouponInfoQueryProvider;

    public static final int MAX_SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        CouponCodePageRequest couponCodePageRequest = JSON.parseObject(data.getParam(), CouponCodePageRequest.class);
        String exportName = String.format("优惠券详情记录报表");
        String fileName = String.format("优惠券详情记录_%s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + exportUtilService.getRandomNum());

        log.info("{} export begin, param:{}", exportName, couponCodePageRequest);

        List<String> resourceKeyList = new ArrayList<>();
        String resourceKey = String.format("userGiftCardDetail/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);

        // 构造分页请求
        couponCodePageRequest.setPageSize(MAX_SIZE);

        // 获取总数
        CouponCodePageResponse response = couponCodeQueryProvider.page(couponCodePageRequest).getContext();
        List<String> couponIdList = new ArrayList<>();
        response.getCouponCodeVos().getContent().forEach(couponCodeVO -> {
            couponIdList.add(couponCodeVO.getCouponId());
        });
        List<EsCouponInfoVO> esCouponInfoVOList = esCouponInfoQueryProvider.page(EsCouponInfoPageRequest.builder().couponIds(couponIdList).build()).getContext().getCouponInfos().getContent();
        Map<String,EsCouponInfoVO> esCouponInfoVOMap = new HashMap<>();
        esCouponInfoVOList.forEach(esCouponInfoVO -> {
            esCouponInfoVOMap.put(esCouponInfoVO.getCouponId(),esCouponInfoVO);
        });
        response.getCouponCodeVos().getContent().forEach(couponCodeVO -> {
            EsCouponInfoVO esCouponInfoVO = esCouponInfoVOMap.get(couponCodeVO.getCouponId());
            if(Objects.nonNull(esCouponInfoVO)){
                couponCodeVO.setScopeNames(esCouponInfoVO.getScopeNames());
            }
        });
        if (Objects.nonNull(response)) {
            Long totalCount = response.getUnUseCount();
            //总页数
            long fileSize = (long) Math.ceil(1.0 * totalCount / MAX_SIZE);
            // 写入表头
            ExcelHelper<CouponCodeVO> excelHelper = new ExcelHelper<>();
            Column[] columns = this.getColumns();
//         没有数据则生成空表
            if (totalCount == 0) {
                String newFileName = String.format("%s.xls", resourceKey);
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.addSheet(exportName, columns, Collections.emptyList());
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, newFileName);
                resourceKeyList.add(newFileName);
                return BaseResponse.success(String.join(",", resourceKeyList));
            }
            //分页查询、导出
            for (int i = 0; i < fileSize; i++) {
                couponCodePageRequest.setPageNum(0);
                List<CouponCodeVO> dataList = response.getCouponCodeVos().getContent();
                excelHelper.addSheet(exportName, columns, dataList);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                excelHelper.write(byteArrayOutputStream);
                //如果超过一页，文件名后缀增加(索引值)
                String suffix = StringUtils.EMPTY;
                if (fileSize > 1) {
                    suffix = "(".concat(String.valueOf(i + 1)).concat(")");
                }
                String newFileName = String.format("%s%s.xls", resourceKey, suffix);
                // 报表上传
                osdService.uploadExcel(byteArrayOutputStream, newFileName);
                resourceKeyList.add(newFileName);

            }
        }
        return BaseResponse.success(String.join(",", resourceKeyList));
    }

    /**
     * 获取表头
     */
    public Column[] getColumns() {
        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column("优惠券名称", new SpelColumnRender<CouponCodeVO>("couponName")));
        columnList.add(new Column("面值", (cell, object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getDenominationValue(couponCodeVO));
        }));
        columnList.add(new Column("有效期", (cell,object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getEffectiveTime(couponCodeVO.getStartTime(),couponCodeVO.getEndTime()));
        }));
        columnList.add(new Column("领取时间", new SpelColumnRender<CouponCodeVO>("acquireTime")));
        columnList.add(new Column("使用范围",(cell,object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getScopeTypeName(couponCodeVO));
        }));

        columnList.add(new Column("优惠券类型", (cell,object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getCouponMarketingTypeName(couponCodeVO.getCouponMarketingType()));
        }));
        columnList.add(new Column("优惠券来源", (cell,object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getPlatformFlagName(couponCodeVO.getPlatformFlag()));
        }));
        columnList.add(new Column("店铺名称", (cell, object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getStoreName(couponCodeVO));
        }));
        columnList.add(new Column("状态", (cell, object) -> {
            CouponCodeVO couponCodeVO = (CouponCodeVO) object;
            cell.setCellValue(this.getCouponStatus(couponCodeVO));
        }));
        return columnList.toArray(new Column[0]);
    }

    public String getStoreName(CouponCodeVO couponCodeVO){
        if (DefaultFlag.NO == couponCodeVO.getPlatformFlag()) {
            return couponCodeVO.getStoreName();
        } else if (DefaultFlag.YES == couponCodeVO.getPlatformFlag()) {
            return "-";
        }
        return "-";
    }

    public String getCouponStatus(CouponCodeVO couponCodeVO) {
        int couponStatus = couponCodeVO.getCouponStatus();
        if(Objects.nonNull(couponStatus)){
            if(couponStatus == 1){
                return "生效中";
            }
            if(couponStatus == 2 ){
                return "未生效";
            }
        }
        return "";
    }

    public String getScopeTypeName (CouponCodeVO couponCodeVO) {
        ScopeType scopeType = couponCodeVO.getScopeType();
        List<String> scopeNames = couponCodeVO.getScopeNames();
        StringBuffer scopeNamesStr = new StringBuffer();
        if(!CollectionUtils.isEmpty(scopeNames)){
            scopeNames.forEach(scopeName->{
                if(StringUtils.isNotBlank(scopeNamesStr.toString())){
                    scopeNamesStr.append(",");
                    scopeNamesStr.append(scopeName);
                } else {
                    scopeNamesStr.append(scopeName);
                }

            });
        }
        if (ScopeType.ALL == scopeType) {
            return "全部商品";
        } else if (ScopeType.BRAND == scopeType) {
            return "限品牌:"+scopeNamesStr.toString();
        } else if (ScopeType.BOSS_CATE == scopeType || ScopeType.STORE_CATE == scopeType) {
            return "限类目:"+scopeNamesStr.toString();
        } else if (ScopeType.SKU == scopeType) {
            return "部分商品";
        } else if (ScopeType.STORE == scopeType) {
            return "限店铺:部分店铺";
        }
        return "";
    }

    public String getCouponMarketingTypeName (CouponMarketingType couponMarketingType) {
        if (CouponMarketingType.REDUCTION_COUPON == couponMarketingType) {
            return "满减券";
        } else if (CouponMarketingType.DISCOUNT_COUPON == couponMarketingType) {
            return "满折券";
        } else if (CouponMarketingType.FREIGHT_COUPON == couponMarketingType) {
            return "运费券";
        }
        return "";
    }

    public String getPlatformFlagName (DefaultFlag defaultFlag) {
        if (DefaultFlag.NO == defaultFlag) {
            return "商家";
        } else if (DefaultFlag.YES == defaultFlag) {
            return "平台";
        }
        return "";
    }

    private String getEffectiveTime(LocalDateTime startTime, LocalDateTime endTime) {
        String a = DateUtil.format(startTime,DateUtil.FMT_TIME_1);
        String b = DateUtil.format(endTime,DateUtil.FMT_TIME_1);
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(" ~ ");
        sb.append(b);
        return sb.toString();
    }

    /**
     * 获取面值
     * @param couponCodeVO
     * @return
     */
    public String getDenominationValue(CouponCodeVO couponCodeVO) {
        StringBuilder denominationValueStr = new StringBuilder();
        denominationValueStr.append("满 ");
        FullBuyType fullBuyType = couponCodeVO.getFullBuyType();
        if(fullBuyType == FullBuyType.NO_THRESHOLD){
            denominationValueStr.append("0");
        } else {
            denominationValueStr.append(couponCodeVO.getFullBuyPrice().setScale(0, RoundingMode.DOWN).toString());
        }
        String denominationStr = couponCodeVO.getDenomination().setScale(0, RoundingMode.DOWN).toString();
        if(couponCodeVO.getCouponMarketingType() == CouponMarketingType.REDUCTION_COUPON){
            denominationValueStr.append(" 减 ");
            denominationValueStr.append(denominationStr);
        } else if (couponCodeVO.getCouponMarketingType() == CouponMarketingType.DISCOUNT_COUPON){
            denominationValueStr.append("打");
            denominationValueStr.append(couponCodeVO.getDenomination().multiply(new BigDecimal(10)).stripTrailingZeros().toString());
            denominationValueStr.append("折");
            if(Objects.nonNull(couponCodeVO.getMaxDiscountLimit())){
                denominationValueStr.append("，最多减 ");
                denominationValueStr.append(couponCodeVO.getMaxDiscountLimit().setScale(0, RoundingMode.DOWN).toString());
                denominationValueStr.append("元");
            }
        } else if (couponCodeVO.getCouponMarketingType() == CouponMarketingType.FREIGHT_COUPON){
            if(couponCodeVO.getCouponDiscountMode() == CouponDiscountMode.FREIGHT_FREE){
                denominationValueStr.append("包邮");
            } else {
                denominationValueStr.append("减");
                denominationValueStr.append(denominationStr);
                denominationValueStr.append("元运费");
            }
        }
        return denominationValueStr.toString();
    }
}
