package com.wanmi.sbc.goods.api.response.buycyclegoodsinfo;

import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>周期购sku表列表结果</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 周期购sku表列表结果
     */
    @Schema(description = "周期购sku表列表结果")
    private List<BuyCycleGoodsInfoPageVO> buyCycleGoodsInfoList;
}
