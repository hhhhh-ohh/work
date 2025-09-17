package com.wanmi.sbc.customer.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>我的足迹列表VO</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Data
public class GoodsFootmarkInfoListVO extends GoodsInfoListVO {

	private static final long serialVersionUID = 1L;

	/**
	 * footmarkId
	 */
	@Schema(description = "footmarkId")
	private Long footmarkId;

	/**
	 * footmarkIdStr
	 */
	@Schema(description = "footmarkIdStr")
	private String footmarkIdStr;

	/**
	 * 排序时间
	 */
	@Schema(description = "排序时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate sortTime;
}