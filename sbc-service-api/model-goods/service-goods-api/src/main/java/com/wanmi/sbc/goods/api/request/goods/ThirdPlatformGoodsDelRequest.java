package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.ThirdPlatformGoodsDelDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformGoodsDelRequest extends BaseRequest {

    private static final long serialVersionUID = 6890507331205597051L;

    private List<ThirdPlatformGoodsDelDTO> thirdPlatformGoodsDelDTOS;

    private Boolean deleteAllSku = Boolean.FALSE;
}

