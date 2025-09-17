package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>秒杀设置VO</p>
 * @author yxz
 * @date 2019-06-11 13:48:53
 */
@Schema
@Data
public class FlashSaleSettingVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀设置主键
	 */
	@Schema(description = "秒杀设置主键")
	private Long id;

	/**
	 * 每日场次整点时间
	 */
	@Schema(description = "每日场次整点时间")
	private String time;

	/**
	 * 是否启用 0：停用，1：启用
	 */
	@Schema(description = "是否启用 0：停用，1：启用")
	private EnableStatus status;

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
	 * 删除标识,0: 未删除 1: 已删除
	 */
	@Schema(description = "删除标识,0: 未删除 1: 已删除")
	private DeleteFlag delFlag;

	/**
	 * 是否关联未结束的秒杀商品
	 */
	@Schema(description = "是否关联未结束的秒杀商品")
	private Boolean isFlashSale;

	/**
	 * 轮播海报json
	 */
	@Schema(description = "轮播海报json")
	private String imgJSON;

	/**
	 * 预热时间
	 */
	@Schema(description = "预热时间")
	private Integer preTime;
}