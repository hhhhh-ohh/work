package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.goods.bean.vo.FreightTemplateGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询单品运费模板响应
 * Created by daiyitian on 2018/10/31.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateGoodsByIdResponse extends FreightTemplateGoodsVO implements Serializable {
    private static final long serialVersionUID = -7555585485512765581L;
}
