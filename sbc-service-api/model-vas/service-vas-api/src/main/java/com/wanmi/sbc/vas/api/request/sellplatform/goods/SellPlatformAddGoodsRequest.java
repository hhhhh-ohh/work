package com.wanmi.sbc.vas.api.request.sellplatform.goods;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformGoodsDescInfoVO;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description  添加商品
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformAddGoodsRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @NotEmpty
    @Schema(description = "SPUId")
    private String out_product_id;

    @Schema(description = "商品标题")
    private String title;

    @Schema(description = "绑定的小程序商品路径")
    private String path;

    @Schema(description = "商品立即购买链接")
    private String direct_path;

    @Schema(description = "主图，多张，列表，图片类型，最多不超过9张")
    private List<String> head_img;

    @Schema(description = "商品资质图片，图片类型，最多不超过5张")
    private List<String> qualification_pics;

    @Schema(description = "商品详情图文，字符类型，最长不超过2000")
    private SellPlatformGoodsDescInfoVO desc_info;

    @Schema(description = "第三级类目ID")
    private Integer third_cat_id;

    @Schema(description = "品牌id   暂未接入品牌，默认写死：2100000000")
    private Integer brand_id;

    @Schema(description = "sku数组")
    private List<SellPlatformGoodsInfoVO> skus;

    @Schema(description = "商品使用场景,1:视频号，3:订单中心")
    private List<Integer> scene_group_list;

}
