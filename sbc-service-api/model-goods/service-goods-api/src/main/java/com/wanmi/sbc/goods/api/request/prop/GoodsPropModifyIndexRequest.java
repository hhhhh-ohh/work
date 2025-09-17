package com.wanmi.sbc.goods.api.request.prop;


import com.wanmi.sbc.goods.bean.dto.GoodsPropDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsPropModifyIndexRequest extends GoodsPropDTO implements Serializable {
    private static final long serialVersionUID = 7836921146848320764L;
}
