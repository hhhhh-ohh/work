package com.wanmi.sbc.goods.api.request.priceadjustmentrecorddetail;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.dto.PriceAdjustmentRecordDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>调价单详情表批量新增参数</p>
 * @author chenli
 * @date 2020-12-09 19:55:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordDetailAddBatchRequest extends GoodsBaseRequest {

	private static final long serialVersionUID = 6726940162763240690L;

	/**
	 * 调价详情集合
	 */
	@Schema(description = "调价详情集合")
	private List<PriceAdjustmentRecordDetailDTO> dataList;
}