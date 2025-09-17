package com.wanmi.sbc.goods.api.response.brand;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 商品品牌excel导入返回Response
 * @author malianfeng
 * @date 2022/8/30 15:00
 */
@Schema
@Data
public class GoodsBrandExcelImportResponse extends BasicResponse {

    private static final long serialVersionUID = -2359469662369356934L;

    /**
     * 是否正确上传成功
     */
    @Schema(description = "是否正确上传成功")
    private Boolean flag;
}
