package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.standard.StandardImportGoodsResponse
 * 商品导入商品库请求对象
 * @author lipeng
 * @dateTime 2018/11/9 下午2:48
 */
@Schema
@Data
public class StandardImportStandardResponse extends BasicResponse {

    private static final long serialVersionUID = 7839898586258059772L;

    @Schema(description = "商品库ids")
    private List<String> standardIds;
}
