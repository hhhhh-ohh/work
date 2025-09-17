package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSelectStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsPageRequest
 * 商品分页请求对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午9:33
 */
@Schema
@Data
public class GoodsQueryNeedSynRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -7972557462976673056L;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

}
