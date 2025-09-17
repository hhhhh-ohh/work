package com.wanmi.sbc.marketing.api.response.appointmentsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）预约抢购信息详情response</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGoodsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预约抢购商品信息
     */
    @Schema(description = "预约抢购商品信息")
    private MicroServicePage<AppointmentSaleGoodsVO> page;
}
