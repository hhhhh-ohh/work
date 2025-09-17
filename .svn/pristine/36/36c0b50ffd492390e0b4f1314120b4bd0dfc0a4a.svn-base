package com.wanmi.sbc.dw.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.recommend
 * @Description: 推荐效果分析查询结果
 * @Author: 何军红
 * @Time: 2021/4/6
 */
@Data
@Schema
public class RecommendEffectAnalysisResultVO {


    /**
     * 曝光人数：有访问并且浏览过推荐坑位数据的去重人数
     */
    @Schema(description = "曝光人数")
    private Integer exposureNumberOfPeople = 0;

    /**
     * 曝光商品数：被推荐坑位展示出来的去重商品数
     */
    @Schema(description = "曝光商品数")
    private Integer exposureNumberOfGoodsUV = 0;

    /**
     * 曝光量：被推荐坑位展示出来的数据量(不去重)  PV
     */

    @Schema(description = "曝光量")
    private Integer exposureNumberOfGoodsPV = 0;

    /**
     * 点击量：推荐坑位商品数据被点击次数
     */

    @Schema(description = "点击量")
    private Integer clickNumberOfGoodsPV = 0;

    /**
     * 点击人数：点击推荐坑位数据的去重人数
     */
    @Schema(description = "点击人数")
    private Integer clickNumberOfPeopleUV = 0;
    /**
     * 点击率：点击量/曝光量×100%，
     */
    @Schema(description = "点击率")
    private String clickPercent = "0.00%";
    /**
     * 平均点击次数：点击量/曝光人数
     */
    @Schema(description = "平均点击次数")
    private String avgCntOfClick = "0.00";

    /**
     * 加购次数：从推荐坑位点击加购的次数（推荐坑位直接加购以及推荐坑位进入商详页后再加购都包含在内）
     */
    @Schema(description = "加购次数")
    private Integer purchasedNumberOfGoodsPV = 0;


    /**
     * 加购人数：从推荐坑位点击加购的去重人数（推荐坑位直接加购以及推荐坑位进入商详页后再加购都包含在内）
     */
    @Schema(description = "加购人数")
    private Integer purchasedNumberOfPeopleUV = 0;

    /**
     * 下单笔数：从推荐坑位成功提交的订单数（推荐坑位立即购买以及从推荐坑位加购后2天内提交订单都包含在内）
     */
    @Schema(description = "下单笔数")
    private Integer orderNumber = 0;

    @Schema(description = "下单人数")
    private Integer orderNumberOfPeople = 0;

    /**
     * 付款笔数：统计订单中成功付款的订单数
     */
    @Schema(description = "付款笔数")
    private Integer paymentNumber = 0;

    /**
     * 付款人数：统计订单中成功付款的去重人数（线上线下付款都以已付款状态为准）
     */
    @Schema(description = "付款人数")
    private Integer paymentNumberOfPeople = 0;

    /**
     * 付款金额：统计订单中成功付款的订单总金额（线上线下付款都以已付款状态为准）
     */
    @Schema(description = "付款金额")
    private String paymentAmountOfMoney = "0.00";

    /**
     * 下单付款转化率：付款人数/下单人数×100%
     */
    @Schema(description = "下单付款转化率")
    private String percentOfOrderToPay = "0.00%";

    /**
     * 曝光-付款展示率：曝光人数/付款人数×100%，
     */
    @Schema(description = "曝光-付款展示率")
    private String percentOfDisplayToPay = "0.00%";

    /**
     * 时间维度
     */
    @Schema(description = "时间维度")
    private String timeDimension;

    /**
     * 报表展示聚合维度维度key
     */
    @Schema(description = "报表维度,日期或者商品名称或者类目名称或者品牌名称")
    private String itemName;

    @Schema(description = "总条数")
    private Integer totalNum;
}
