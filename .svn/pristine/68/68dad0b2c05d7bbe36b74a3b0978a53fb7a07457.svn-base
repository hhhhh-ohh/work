package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 标准库导出模板响应结构
 * @author daiyitian
 * @dateTime 2018/11/6 下午2:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsBatchAddResponse extends BasicResponse {

    private static final long serialVersionUID = 7768606637625508639L;

    @Schema(description = "商品库id")
    private List<String> standardIds;
}
