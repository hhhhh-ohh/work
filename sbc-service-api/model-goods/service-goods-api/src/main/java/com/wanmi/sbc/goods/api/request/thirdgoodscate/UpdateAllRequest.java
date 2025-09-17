package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class UpdateAllRequest extends BaseRequest {

    private static final long serialVersionUID = 6021121896864314814L;

    @Valid
    private List<ThirdGoodsCateDTO> thirdGoodsCateDTOS;
}
