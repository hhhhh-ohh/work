package com.wanmi.sbc.goods.api.response.groupongoodsinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>拼团活动spu列表返回对象</p>
 *
 * @author chenli
 * @date 2019-05-21 14:49:12
 */

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoSimplePageResponse extends BasicResponse {

    private static final long serialVersionUID = -9058748573965597919L;

    /**
     * 我的优惠券列表
     */
    @Schema(description = "拼团活动spu列表")
    private MicroServicePage<GrouponGoodsVO> grouponGoodsVOS = new MicroServicePage<>(new ArrayList<>());
}