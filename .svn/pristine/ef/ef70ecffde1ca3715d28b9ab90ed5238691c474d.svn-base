package com.wanmi.sbc.mq.report.entity;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 查询接口
 * Created by jinwei on 6/5/2017.
 */
@Schema
@Data
public class ReturnQueryRequest extends BaseQueryRequest {

    @Schema(description = "退单Id")
    private String rid;

    @Schema(description = "订单Id")
    private String tid;

    @Schema(description = "买家名称")
    private String buyerName;

    @Schema(description = "买家账号")
    private String buyerAccount;

    @Schema(description = "子订单ID")
    private String ptid;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String consigneeName;

    @Schema(description = "收货人电话")
    private String consigneePhone;

    /**
     * 退单流程状态
     */
    @Schema(description = "退单流程状态")
    private ReturnFlowState returnFlowState;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    @Schema(description = "商品编号")
    private String skuNo;

    /**
     * 退单编号列表
     */
    @Schema(description = "退单编号列表")
    private String[] rids;

    /**
     * 退单创建开始时间，精确到天
     */
    @Schema(description = "退单创建开始时间，精确到天")
    private String beginTime;

    /**
     * 退单创建结束时间，精确到天
     */
    @Schema(description = "退单创建结束时间，精确到天")
    private String endTime;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String supplierCode;

    /**
     * 业务员id
     */
    @Schema(description = "业务员id")
    private String employeeId;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Object[] customerIds;

    /**
     * 供应商公司id
     */
    @Schema(description = "供应商公司id")
    private Long providerCompanyInfoId;

    /**
     * 业务员id集合
     */
    @Schema(description = "业务员id集合")
    private List<String> employeeIds;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private StoreType storeType;

    /**
     * 带货视频号
     */
    @Schema(description = "带货视频号")
    private String videoName;

    /**
     * 场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）
     */
    @Schema(description = "场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    private Integer sceneGroup;

    /**
     * 代销平台标识
     */
    @Schema(description = "代销平台标识")
    private SellPlatformType sellPlatformType;

    /**
     * 退单完成开始时间
     */
    @Schema(description = "退单完成开始时间，精确到天")
    private String completionBeginTime;

    /**
     * 退单完成结束时间
     */
    @Schema(description = "退单完成结束时间，精确到天")
    private String completionEndTime;

    @Override
    public Integer getPageNum() {
        return super.getPageNum();
    }

    @Override
    public String getSortColumn() {
        return "createTime";
    }

    @Override
    public String getSortRole() {
        return "desc";
    }

    @Override
    public String getSortType() {
        return "Date";
    }

    private String buildEsFuzzyStr(String str) {
        return String.format("\"*%s*\" OR *%s*", str, str);
    }
}
