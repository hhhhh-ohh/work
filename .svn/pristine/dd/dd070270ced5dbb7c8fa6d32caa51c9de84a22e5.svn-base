package com.wanmi.sbc.empower.api.response.sellplatform.goods;

import com.wanmi.sbc.empower.bean.vo.sellplatform.goods.PlatformGetGoodsAuditVO;
import com.wanmi.sbc.empower.bean.vo.sellplatform.goods.PlatformGetGoodsDescVO;
import com.wanmi.sbc.empower.bean.vo.sellplatform.goods.PlatformGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ChannelsAddGoodsResponse
 * @description TODO
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformGetGoodsResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 小商店内部商品ID
     */
    @Schema(description = "小商店内部商品ID")
    private Integer product_id;

    /**
     * 商家自定义商品ID
     */
    @Schema(description = "商家自定义商品ID")
    private String out_product_id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 绑定的小程序商品路径
     */
    @Schema(description = "绑定的小程序商品路径")
    private String path;

    /**
     * 主图,多张,列表,最多9张,每张不超过2MB
     */
    @Schema(description = "主图,多张,列表,最多9张,每张不超过2MB")
    private List<String> head_img;

    /**
     * 商品详情
     */
    @Schema(description = "商品详情")
    private PlatformGetGoodsDescVO desc_info;

    @Schema(description = "商品审核信息")
    private PlatformGetGoodsAuditVO audit_info;

    /**
     * 商品线上状态
     * 0：初始值
     * 5：上架
     * 6：回收站
     * 9：删除逻辑
     * 11：自主下架
     * 12：售尽下架
     * 13：违规下架/风控系统下架
     */
    @Schema(description = "商品线上状态")
    private Integer status;

    /**
     * 审核状态
     * 0. 初始
     * 1. 编辑中
     * 2. 审核中
     * 3. 审核失败
     * 4. 审核成功
     */
    @Schema(description = "商品草稿状态")
    private Integer edit_status;

    @Schema(description = "第三级类目ID")
    private Integer third_cat_id;

    @Schema(description = "品牌id")
    private Integer brand_id;

    @Schema(description = "创建时间")
    private String create_time;

    @Schema(description = "更新时间")
    private String update_time;

    @Schema(description = "预留字段，用于版本控制")
    private String info_version;

    @Schema(description = "关联的SKUId")
    private List<PlatformGoodsInfoVO> skus;

}