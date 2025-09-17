package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>VO</p>
 * @author 
 * @date 2020-08-17 14:46:43
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdGoodsCateRelDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Schema(description = "主键")
	private Long id;
	@Schema(description = "第三方类目id")
	private Long thirdCateId;
	@Schema(description = "第三方类目名称")
	private String thirdCateName;
	@Schema(description = "第三方类目父id")
	private Long thirdCateParentId;
	@Schema(description = "渠道来源")
	private ThirdPlatformType thirdPlatformType;
	@Schema(description = "平台类目id")
	private Long cateId;
	@Schema(description = "平台类目名称")
	private String cateName;
    @Schema(description = "三方类目等级")
    private Integer thirdCateGrade;
}