package com.wanmi.sbc.goods.api.request.goodsaudit;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.*;

/**
 * <p>商品审核分页查询请求参数</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-goodsIdList
	 */
	@Schema(description = "批量查询-goodsIdList")
	private List<String> goodsIdList;

	/**
	 * goodsId
	 */
	@Schema(description = "goodsId")
	private String goodsId;

	/**
	 * 旧商品Id
	 */
	@Schema(description = "旧商品Id")
	private String oldGoodsId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsName;

	/**
	 * 供应商名称
	 */
	@Schema(description = "供应商名称")
	private String providerName;

	/**
	 * SPU编码
	 */
	@Schema(description = "SPU编码")
	private String goodsNo;

	/**
	 * SPU编码
	 */
	@Schema(description = "SPU编码")
	private String likeGoodsNo;

	/**
	 * 模糊条件-SKU编码
	 */
	@Schema(description = "SKU编码")
	private String likeGoodsInfoNo;

	/**
	 * 店铺分类
	 */
	@Schema(description = "店铺分类")
	private Long storeCateId;

	/**
	 * 品牌Id
	 */
	@Schema(description = "品牌Id")
	private Long brandId;

	/**
	 * 销售类别(0:批发,1:零售)
	 */
	@Schema(description = "销售类别(0:批发,1:零售)")
	private Integer saleType;

	/**
	 * 平台类目
	 */
	@Schema(description = "平台类目")
	private Long cateId;

	/**
	 * 标签id，以逗号拼凑
	 */
	@Schema(description = "标签id，以逗号拼凑")
	private String labelId;

	/**
	 * 上下架状态,0:下架1:上架2:部分上架
	 */
	@Schema(description = "上下架状态,0:下架1:上架2:部分上架")
	private Integer addedFlag;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String supplierName;

	/**
	 * 审核状态
	 */
	@Schema(description = "审核状态")
	private Integer auditStatus;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	private Long companyInfoId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private Integer delFlag;

	/**
	 * 是否显示购买积分
	 */
	@Schema(description = "是否显示购买积分 true:显示")
	private Boolean showPointFlag;

	/**
	 * 商品来源，0供应商，1商家,2linkedmall
	 */
	@Schema(description = "商品来源，0供应商，1商家,2linkedmall")
	private Integer goodsSource;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

	/**
	 * 商家类型 0、平台自营 1、第三方商家
	 */
	@Schema(description = "商家类型")
	private BoolFlag companyType;

	/**
	 * 商品来源，0品牌商城，1商家
	 */
	@Schema(description = "商品来源，0品牌商城，1商家")
	private GoodsSource searchGoodsSource;

}