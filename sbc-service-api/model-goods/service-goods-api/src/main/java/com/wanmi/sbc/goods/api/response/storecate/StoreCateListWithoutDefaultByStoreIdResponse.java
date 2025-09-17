package com.wanmi.sbc.goods.api.response.storecate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Author: bail
 * Time: 2017/11/13.10:25
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateListWithoutDefaultByStoreIdResponse extends BasicResponse {

    private static final long serialVersionUID = -2421581998842497652L;

    @Schema(description = "店铺分类")
    private List<StoreCateResponseVO> storeCateResponseVOList;
}
