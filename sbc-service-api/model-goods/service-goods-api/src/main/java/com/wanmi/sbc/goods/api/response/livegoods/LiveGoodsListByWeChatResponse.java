package com.wanmi.sbc.goods.api.response.livegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>直播商品列表结果</p>
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsListByWeChatResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;


    private Integer errcode;


    private Integer total;



    /**
     * 直播商品列表结果
     */
    @Schema(description = "直播商品列表结果")
    private List<LiveGoodsVO> goods;


}
