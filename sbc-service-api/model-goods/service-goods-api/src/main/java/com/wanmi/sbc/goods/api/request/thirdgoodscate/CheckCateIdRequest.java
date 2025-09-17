package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateDTO;
import lombok.Data;

import java.util.List;

/**
 * @author EDZ
 * @className CheckCateIdRequest
 * @description 检查类目是否存在
 * @date 2021/5/12 13:49
 **/
@Data
public class CheckCateIdRequest extends BaseRequest {
    /**
     * 需检查的类目
     */
    private List<ThirdGoodsCateDTO> thirdGoodsCateDTOS;
}
