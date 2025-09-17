package com.wanmi.sbc.goods.api.response.prop;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wanggang
 * @createDate: 2018/12/5 11:47
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropDeleteByPropIdResponse extends BasicResponse {
    private static final long serialVersionUID = -7765987352026044808L;

    @Schema(description = "保存默认spu与默认属性的关联")
    private Boolean result;
}
