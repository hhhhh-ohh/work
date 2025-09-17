package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.goods.bean.dto.GoodsPropRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsPropListInitSortRequest extends GoodsPropRequestDTO implements Serializable {
    private static final long serialVersionUID = 4548061358428840657L;
}
