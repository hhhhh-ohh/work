package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GrouponGoodsInfoByActivityIdAndGoodsIdDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>根据拼团活动ID、SPU编号集合查询拼团价格最小的拼团SKU信息</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoBatchByActivityIdAndGoodsIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	@NotNull
	private List<GrouponGoodsInfoByActivityIdAndGoodsIdDTO> list;
}