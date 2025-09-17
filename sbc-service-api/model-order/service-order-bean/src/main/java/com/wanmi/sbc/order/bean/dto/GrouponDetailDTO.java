package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.GrouponDetailOptType;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: lq
 * @CreateTime:2019-05-18 14:32
 * @Description:todo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrouponDetailDTO {
    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;


    /**
     * sku编号
     */
    @Schema(description = "sku编号")
    private String goodsInfoId;


    /**
     * spu编号
     */
    @Schema(description = "spu编号")
    private String goodsId;

    /**
     * skus编号
     */
    @Schema(description = "sku编号")
    private String goodsInfoIds;

    /**
     * 是否团长
     */
    @Schema(description = "是：开团 否：参团")
    private Boolean leader;


    /**
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;

    /**
     * 业务入口
     */
    private GrouponDetailOptType optType;



    /**
     * 团活动
     */
    private GrouponActivityVO grouponActivity;


    @Schema(description = "商品信息")
    private List<GoodsInfoVO> goodsInfoList;


    @Schema(description = "拼团商品信息")
    private List<GrouponGoodsInfoVO> grouponGoodsInfoVOList;


}
