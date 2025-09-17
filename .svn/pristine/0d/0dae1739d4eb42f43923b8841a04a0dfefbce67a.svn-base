package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardContactType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wur
 * @className GiftCardVO
 * @description 礼品卡实体类
 * @date 2022/12/8 18:38
 **/
@Schema
@Data
public class GiftCardVO implements Serializable {

    private static final long serialVersionUID = 4043264917929737209L;

    /**
     * 礼品卡Id
     */
    @Schema(description = "礼品卡Id")
    private Long giftCardId;

    /**
     *  礼品卡名称
     */
    @Schema(description = "礼品卡名称")
    private String name;

    /**
     *  封面类型 0：指定颜色 1:指定图片
     */
    @Schema(description = "封面类型 0：指定颜色 1:指定图片")
    private DefaultFlag backgroundType;

    /**
     *  封面数值
     */
    @Schema(description = "封面数值")
    private String backgroundDetail;

    /**
     *  面值
     */
    @Schema(description = "面值")
    private Long parValue;

    /**
     * 库存类型
     */
    @Schema(description = "库存类型, 0:有限制 1：无限制")
    private DefaultFlag stockType;

    /**
     * 剩余库存
     */
    @Schema(description = "剩余库存")
    private Long stock;

    /**
     * 最近编辑库存
     */
    @Schema(description = "最近编辑库存")
    private Long originStock;

    /**
     * 已生成卡总数
     */
    @Schema(description = "已生成卡总数")
    private Long makeNum;

    /**
     * 已发卡总数
     */
    @Schema(description = "已发卡总数")
    private Long sendNum;

    /**
     * 过期类型
     */
    @Schema(description = "过期类型")
    private ExpirationType expirationType;

    /**
     * 指定月数
     */
    @Schema(description = "指定月数")
    private Long rangeMonth;

    /**
     * 指定失效时间
     */
    @Schema(description = "指定失效时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expirationTime;

    /**
     * 关联商品 0：全部 1:按品牌 2：按分类 3：按店铺 4：自定义商品
     */
    @Schema(description = "关联商品 0：全部 1:按品牌 2：按分类 3：按店铺 4：自定义商品")
    private GiftCardScopeType scopeType;

    /**
     * 使用说明
     */
    @Schema(description = "使用说明")
    private String useDesc;

    /**
     * 客服类型
     */
    @Schema(description = "客服类型")
    private GiftCardContactType contactType;

    /**
     * 客服联系方式
     */
    @Schema(description = "客服联系方式")
    private String contactPhone;

    /**
     * 目标资源Id
     */
    @Schema(description = "目标资源Id")
    private List<String> scopeIdList;

    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    @Schema(description = "适用商品数量 -1可选一种 -99可全选 其他代表N种")
    private Integer scopeGoodsNum;

    @Schema(description = "适用商品数量总数")
    private Integer totalGoodsNum;

}