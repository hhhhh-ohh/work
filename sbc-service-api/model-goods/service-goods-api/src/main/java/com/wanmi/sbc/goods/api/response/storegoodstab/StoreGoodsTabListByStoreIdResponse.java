package com.wanmi.sbc.goods.api.response.storegoodstab;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StoreGoodsTabVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:33 2018/12/13
 * @Description:
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreGoodsTabListByStoreIdResponse extends BasicResponse {

    /**
     * 商品模板列表
     */
    @Schema(description = "商品模板列表")
    private List<StoreGoodsTabVO> storeGoodsTabs;

}
