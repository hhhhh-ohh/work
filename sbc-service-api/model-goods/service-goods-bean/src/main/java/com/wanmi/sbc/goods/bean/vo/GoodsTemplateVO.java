package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>GoodsTemplateVO</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@Data
public class GoodsTemplateVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String name;

	/**
	 * 展示位置 0:顶部 1:底部 2:全选
	 */
	@Schema(description = "展示位置 0:顶部 1:底部 2:全选")
	private Integer position;

	/**
	 * 顶部内容
	 */
	@Schema(description = "顶部内容")
	private String topContent;

	/**
	 * 底部内容
	 */
	@Schema(description = "底部内容")
	private String downContent;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 编辑时间
	 */
	@Schema(description = "编辑时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id")
	private Long storeId;
}