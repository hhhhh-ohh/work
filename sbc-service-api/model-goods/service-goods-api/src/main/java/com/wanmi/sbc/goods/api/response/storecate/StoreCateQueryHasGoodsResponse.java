package com.wanmi.sbc.goods.api.response.storecate;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wanggang
 * @createDate: 2018/12/5 11:43
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateQueryHasGoodsResponse extends BasicResponse {
    private static final long serialVersionUID = -8211843394358166012L;

    @Schema(description = "是否有子类，com.wanmi.sbc.common.enums.DefaultFlag")
    private Integer result;
}
