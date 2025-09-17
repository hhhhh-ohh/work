package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateRelDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.stream.StreamFilter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>VO</p>
 * @author 
 * @date 2020-08-17 14:46:43
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdGoodsCateRelVO extends ThirdGoodsCateRelDTO implements Serializable {
	private static final long serialVersionUID = -660973159312983340L;
	/**
	 * 类目名称全路径
	 */
	@Schema(description = "类目名称全路径")
	private String path;
	@Schema(description = "子类目")
	private List<ThirdGoodsCateRelVO> children;

}