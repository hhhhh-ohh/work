package com.wanmi.sbc.goods.api.response.enterprise;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加企业购商品时返回的不符合条件的结果
 *
 * @author CHENLI
 * @dateTime 2019/3/26 上午9:33
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseGoodsAddResponse extends BasicResponse {

    private static final long serialVersionUID = 616005378190844092L;

    /**
     * 添加企业购商品时返回的不符合条件的结果，skuIds
     */
    @Schema(description = "不符合条件的skuIds")
    private List<String> goodsInfoIds = new ArrayList<>();
}
