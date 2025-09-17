package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 商品修改请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsInfoModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 6636321075674610130L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    @NotNull
    private GoodsInfoDTO goodsInfo;

    @Override
    public void checkParam() {
        if (StringUtils.isBlank(goodsInfo.getGoodsInfoNo())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (Objects.isNull(goodsInfo.getStock())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }


}
