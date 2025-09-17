package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品库SKU查询请求
 *
 * @author daiyitian
 * Created by daiyitian on
 * 2017/3/24.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardSkuListQueryRequest extends BaseRequest {

    /** 批量SKU编号 */
    private List<String> goodsInfoIds;

    /** SPU编号 */
    private String goodsId;

    /** 批量SPU编号 */
    private List<String> goodsIds;

    /** 品牌编号 */
    private Long brandId;

    /** 分类编号 */
    private Long cateId;

    /** 模糊条件-商品名称 */
    private String likeGoodsName;

    /** 删除标记 */
    private Integer delFlag;

    /** 非GoodsId */
    private String notGoodsId;

    /** 非GoodsInfoId */
    private String notGoodsInfoId;

    private String likeGoodsInfoNo;

    /** 第三方平台的spuId */
    private String thirdPlatformSpuId;

    /** 第三方平台的skuId */
    private String thirdPlatformSkuId;
}
