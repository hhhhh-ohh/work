package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.ElectronicGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className ElectronicGoodsPageReponse
 * @description 关联商品分页查询结果
 * @date 2022/1/27 3:35 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicGoodsPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 关联商品分页结果
     */
    @Schema(description = "关联商品分页结果")
    private MicroServicePage<ElectronicGoodsVO> electronicGoodsVOPage;
}
