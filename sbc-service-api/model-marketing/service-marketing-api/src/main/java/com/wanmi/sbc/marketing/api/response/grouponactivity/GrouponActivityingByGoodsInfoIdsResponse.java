package com.wanmi.sbc.marketing.api.response.grouponactivity;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:07 2019/5/24
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrouponActivityingByGoodsInfoIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -6192424993678489828L;

    /**
     * 拼团商品map
     */
    @Schema(description = "拼团商品map")
    private Map<String, GrouponGoodsInfoVO> grouponGoodsInfoMap;

}
