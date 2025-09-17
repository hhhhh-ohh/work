package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.goods.bean.vo.StandardSkuVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 商品库Sku编辑响应
 * Created by daiyitian on 2017/3/24.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class StandardSkuModifyResponse extends StandardSkuVO implements Serializable {

    private static final long serialVersionUID = 8313620400385033888L;
}
