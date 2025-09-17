package com.wanmi.sbc.goods.api.request.livegoods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.util.List;

/**
 * <p>直播商品新增参数</p>
 * @author zwb
 * @date 2020-06-10 11:05:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsSupplierAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 商品列表
	 */
	@Schema(description = "商品列表")
	private List<LiveGoodsVO> goodsInfoVOList;



}