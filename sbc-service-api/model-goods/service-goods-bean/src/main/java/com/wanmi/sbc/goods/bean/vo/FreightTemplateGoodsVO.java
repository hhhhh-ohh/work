package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.ValuationType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 单品运费模板
 * Created by sunkun on 2018/5/2.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateGoodsVO extends FreightTemplateVO {

    private static final long serialVersionUID = 5570730069474743627L;

    /**
     * 发货地-省份
     */
    @Schema(description = "发货地-省份")
    private Long provinceId;

    /**
     * 发货地-地市
     */
    @Schema(description = "发货地-地市")
    private Long cityId;

    /**
     * 发货地-区镇
     */
    @Schema(description = "发货地-区镇")
    private Long areaId;

    /**
     * 发货地-街道
     */
    @Schema(description = "发货地-街道")
    private Long streetId;

    /**
     * 是否包邮(0:不包邮,1:包邮)
     */
    @Schema(description = "是否包邮，0:不包邮,1:包邮")
    private DefaultFlag freightFreeFlag;

    /**
     * 计价方式(0:按件数,1:按重量,2:按体积) {@link ValuationType}
     */
    @Schema(description = "计价方式，0:按件数,1:按重量,2:按体积")
    private ValuationType valuationType;

    /**
     * 是否指定条件包邮(0:不指定,1:指定)
     */
    @Schema(description = "是否指定条件包邮，0:不指定,1:指定")
    private DefaultFlag specifyTermFlag;

    /**
     * 单品运费模板快递运送
     */
    @Schema(description = "单品运费模板快递运送")
    private List<FreightTemplateGoodsExpressVO> freightTemplateGoodsExpresses;

    /**
     * 单品运费模板指定包邮条件
     */
    @Schema(description = "单品运费模板指定包邮条件")
    private List<FreightTemplateGoodsFreeVO> freightTemplateGoodsFrees;

    /**
     * 与收货地匹配的单品运费模板
     * 用于运费计算,无需重复匹配收货地
     */
    @Schema(description = "单品运费模板指定包邮条件，用于运费计算,无需重复匹配收货地")
    private FreightTemplateGoodsExpressVO expTemplate;
}
