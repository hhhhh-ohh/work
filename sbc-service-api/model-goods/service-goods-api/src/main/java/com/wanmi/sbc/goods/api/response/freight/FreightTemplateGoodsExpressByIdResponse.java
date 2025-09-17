package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.goods.bean.vo.FreightTemplateGoodsExpressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateGoodsExpressByIdResponse extends FreightTemplateGoodsExpressVO implements Serializable {
    private static final long serialVersionUID = 794254080276708149L;
}
