package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.standard.StandardImportGoodsRequest
 * 商品库导入商品请求对象
 *
 * @author lipeng
 * @dateTime 2018/11/9 下午2:47
 */
@Schema
@Data
public class StandardImportGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 2125165045370663365L;

    /**
     * 商品库编号
     */
    @Schema(description = "商品库编号")
    private List<String> goodsIds;

    /**
     * 公司信息编号
     */
    @Schema(description = "公司信息编号")
    private Long companyInfoId;

    /**
     * 商户类型
     */
    @Schema(description = "商户类型，0:平台自营, 1: 第三方商家")
    private BoolFlag companyType;

    /**
     * 店铺编号
     */
    @Schema(description = "店铺编号")
    private Long storeId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家类型 0供应商 1商家,2:O2O,3:跨境商家
     */
    @Schema(description = "商家类型 0供应商 1商家,2:O2O,3:跨境商家")
    private StoreType storeType;
}
