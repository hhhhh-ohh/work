package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.goods.bean.dto.GoodsCateThirdCateRelDTO;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;

/**
 * <p>平台类目和第三方平台类目映射VO</p>
 * @author 
 * @date 2020-08-18 19:51:55
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class GoodsCateThirdCateRelVO extends GoodsCateThirdCateRelDTO implements Serializable {
	private static final long serialVersionUID = -6925021478934674450L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

}