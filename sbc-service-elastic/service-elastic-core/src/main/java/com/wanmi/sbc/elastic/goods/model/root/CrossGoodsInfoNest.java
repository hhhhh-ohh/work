package com.wanmi.sbc.elastic.goods.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
*
 * 跨境商品信息VO
 * @author  wur
 * @date: 2021/6/9 9:25
 **/
@Schema
@Data
public class CrossGoodsInfoNest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键ID")
	private String crossGoodsInfoId;

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
	@Field(type = FieldType.Keyword)
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
     * 排序用创建时间
     */
    @Schema(description = "排序用创建时间")
    private Long sortCreateTime;


	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;


}