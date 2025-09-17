package com.wanmi.sbc.goods.api.response.flashsalecate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.FlashSaleCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>秒杀分类列表结果</p>
 * @author yxz
 * @date 2019-06-11 10:11:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleCateListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀分类列表结果
     */
    @Schema(description = "秒杀分类列表结果")
    private List<FlashSaleCateVO> flashSaleCateVOList;
}
