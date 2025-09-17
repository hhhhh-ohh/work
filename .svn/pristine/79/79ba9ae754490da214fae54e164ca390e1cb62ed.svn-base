package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.ElectronicCardDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>电子卡密表新增参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardBatchAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 卡密数据
	 */
	@Schema(description = "卡密数据")
	private List<ElectronicCardDTO> dtoList;

}