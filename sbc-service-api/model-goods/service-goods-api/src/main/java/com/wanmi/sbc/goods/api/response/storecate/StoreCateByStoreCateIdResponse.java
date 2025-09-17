package com.wanmi.sbc.goods.api.response.storecate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author: bail
 * Time: 2017/11/13.10:25
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateByStoreCateIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1217422810450919786L;

    @Schema(description = "店铺分类")
    private StoreCateResponseVO storeCateResponseVO;
}
