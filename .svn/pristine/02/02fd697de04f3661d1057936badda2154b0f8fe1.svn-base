package com.wanmi.sbc.goods.bean.vo;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>抢购商品表VO</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashSaleGoodsSimpleVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 活动日期：2019-06-11
     */
    @Schema(description = "活动日期：2019-06-11")
    private String activityDate;

    /**
     * 活动时间：13:00
     */
    @Schema(description = "活动时间：13:00")
    private String activityTime;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * spuID
     */
    @Schema(description = "spuID")
    private String goodsId;

    /**
     * 抢购价
     */
    @Schema(description = "抢购价")
    private BigDecimal price;

    /**
     * 上限数量
     */
    @Schema(description = "上限数量")
    private Integer stock;

    /**
     * 抢购销量
     */
    @Schema(description = "抢购销量")
    private Long salesVolume = 0L;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long cateId;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Integer maxNum = 0;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    private Integer minNum;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;
    /**
     * 包邮标志，0：不包邮 1:包邮
     */
    @Schema(description = "包邮标志，0：不包邮 1:包邮")
    private Integer postage;

    /**
     * 删除标志，0:未删除 1:已删除
     */
    @Schema(description = "删除标志，0:未删除 1:已删除")
    private DeleteFlag delFlag;


    /**
     * 活动日期+时间
     */
    @Schema(description = "活动日期+时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityFullTime;

    /**
     * 活动开始时间
     */
    @Schema(description = "startTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "endTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;


    public static List<FlashSaleGoodsSimpleVO> toBean(List<Map<String,Object>> list){
        List<FlashSaleGoodsSimpleVO> retList = new ArrayList<>();
        String str = JSONObject.toJSONString(list);
        retList = JSONArray.parseArray(str,FlashSaleGoodsSimpleVO.class);

        return retList;
    }

}
