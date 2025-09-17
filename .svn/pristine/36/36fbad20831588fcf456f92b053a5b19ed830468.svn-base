package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: gaomuwei
 * Created In 下午3:43 2018/12/13
 */
@Schema
@Data
public class StandardGoodsListUsedGoodsIdRequest extends BaseRequest {

    private static final long serialVersionUID = -6513634163487270489L;

    @Schema(description = "商品库Id")
    private List<String> standardIds;

    @Schema(description = "店铺Id")
    private List<Long> storeIds;
}
