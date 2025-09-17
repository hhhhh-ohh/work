package com.wanmi.sbc.order.trade.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>跨境商品信息VO</p>
 * @author
 * @date
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrossGoodsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键ID")
	private String crossId;

	/**
	 * 货品ID
	 */
	@Schema(description = "货品ID")
	private String goodsInfoId;

	/**
	 * 商品ID
	 */
	@Schema(description = "商品ID")
	private String goodsId;

    /**
     * 商品上架英文名称
     */
    @Schema(description = "商品上架英文名称")
    private String subTitle;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private String storeId;

	/**
	 * 贸易类型
	 */
	@Schema(description = "贸易类型")
	private String tradeType;

	/**
	 * 电子口岸
	 */
	@Schema(description = "电子口岸")
	private String electronPort;

	/**
	 * 计量单位
	 */
	@Schema(description = "计量单位")
	private String unit;

	/**
	 * 原产国
	 */
	@Schema(description = "原产国")
	private String country;

	/**
	 * 包装类型
	 */
	@Schema(description = "包装类型")
	private String packageType;

	/**
	 * 商品备案编号
	 */
	@Schema(description = "商品备案编号")
	private String recordNo;

	/**
	 * 商品备案名称
	 */
	@Schema(description = "商品备案名称")
	private String recordName;

	/**
	 * 商品备案价格
	 */
	@Schema(description = "商品备案价格")
	private BigDecimal recordPrice;

	/**
	 * HS编码
	 */
	@Schema(description = "HS编码")
	private String hsCode;

	/**
	 * 关税税率
	 */
	@Schema(description = "关税税率")
	private BigDecimal tariffRate;

	/**
	 * 消费税率
	 */
	@Schema(description = "消费税率")
	private BigDecimal consumptionTaxRate;

	/**
	 * 增值税率
	 */
	@Schema(description = "增值税率")
	private BigDecimal valueAddedTaxRate;

	/**
	 * 行邮税率
	 */
	@Schema(description = "行邮税率")
	private BigDecimal taxRate;

	/**
	 * 综合税率
	 */
	@Schema(description = "综合税率")
	private BigDecimal complexRate;

	/**
	 * 关联2010项号
	 */
	@Schema(description = "关联2010项号")
	private String itemNo;

	/**
	 * 业务模式
	 */
	@Schema(description = "业务模式")
	private String businessModel;

	/**
	 * 主要成分
	 */
	@Schema(description = "主要成分")
	private String mainIngredients;

    /**
     * 用途、功效
     */
    @Schema(description = "用途、功效")
    private String efficacy;

    /**
     * 商品描述
     */
    @Schema(description = "商品描述")
    private String remark;

	/**
	 * 生产企业注册号
	 */
	@Schema(description = "生产企业注册号")
	private String enterpriseRegisteredNumber;

	/**
	 * 生产企业名称
	 */
	@Schema(description = "生产企业名称")
	private String enterpriseName;

	/**
	 * 生产企业所属国
	 */
	@Schema(description = "生产企业所属国")
	private String enterpriseCountries;

	/**
	 * 体积(cm3)
	 */
	@Schema(description = "体积(cm3)")
	private BigDecimal volume;

	/**
	 * 法定第一单位编码
	 */
	@Schema(description = "法定第一单位编码")
	private String firstUnit;

	/**
	 * 法定第一数量
	 */
	@Schema(description = "法定第一数量")
	private Integer firstNumber;

	/**
	 * 法定第二单位编码
	 */
	@Schema(description = "法定第二单位编码")
	private String secondUnit;

	/**
	 * 法定第二数量
	 */
	@Schema(description = "法定第二数量")
	private Integer secondNumber;

	/**
	 * 重量
	 */
	@Schema(description = "重量")
	private BigDecimal weight;

	/**
	 * 备案状态 0：未备案，1：申请备案,2：备案成功，3：已失败
	 */
	@Schema(description = "备案状态 0：未备案，1：申请备案,2：备案成功，3：已失败")
	private Integer recordStatus;

	/**
	 * 备案失败原因
	 */
	@Schema(description = "备案失败原因")
	private String recordRejectReason;

	/**
	 * 企业商品货号
	 */
	@Schema(description = "企业商品货号")
	private String enterpriseProductId;

	/**
	 * 海关商品备案号
	 */
	@Schema(description = "海关商品备案号")
	private String customsRecordId;

	/**
	 * 检疫商品备案号
	 */
	@Schema(description = "检疫商品备案号")
	private String customsInspectionId;

	/**
	 * 海关商品编码
	 */
	@Schema(description = "海关商品编码")
	private String customsProductCode;

	/**
	 * 海关商品规格型号
	 */
	@Schema(description = "海关商品规格型号")
	private String customsProductSpec;

	/**
	 * 检疫商品规格型号
	 */
	@Schema(description = "检疫商品规格型号")
	private String inspectionProductSpec;

	/**
	 * 商品税号
	 */
	@Schema(description = "商品税号")
	private String productTaxCode;

	/**
	 * 币制
	 */
	@Schema(description = "币制")
	private String currency;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

    /**
     * 图片
     */
    @Schema(description = "图片")
	private String goodsInfoImg;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * SKU编码
     */
    @Schema(description = "SKU编码")
    private String goodsInfoNo;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String storeName;

    /**
     * 品牌ID
     */
    @Schema(description = "品牌ID")
    private Long brandId;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brand;
}