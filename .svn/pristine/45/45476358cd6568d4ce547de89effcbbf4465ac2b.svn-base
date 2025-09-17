package com.wanmi.sbc.setting.api.response.storewechatminiprogramconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.StoreWechatMiniProgramConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）门店微信小程序配置信息response</p>
 * @author tangLian
 * @date 2020-01-16 11:47:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWechatMiniProgramConfigByCacheResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 门店微信小程序配置信息
     */
    @Schema(description = "门店微信小程序配置信息")
    private StoreWechatMiniProgramConfigVO storeWechatMiniProgramConfigVO;
}
