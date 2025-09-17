package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 需要es删除的商家商品
 * @Date: Created In 下午4:01 2018/12/13
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsDeleteProviderGoodsIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 5931273820720319524L;

    @Schema(description = "spu Id")
    private List<String> goodsIds;
}
