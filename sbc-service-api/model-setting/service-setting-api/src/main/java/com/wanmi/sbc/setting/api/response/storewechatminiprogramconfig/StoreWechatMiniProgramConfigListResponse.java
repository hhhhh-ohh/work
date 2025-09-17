package com.wanmi.sbc.setting.api.response.storewechatminiprogramconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.StoreWechatMiniProgramConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>门店微信小程序配置列表结果</p>
 * @author tangLian
 * @date 2020-01-16 11:47:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWechatMiniProgramConfigListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 门店微信小程序配置列表结果
     */
    @Schema(description = "门店微信小程序配置列表结果")
    private List<StoreWechatMiniProgramConfigVO> storeWechatMiniProgramConfigVOList;
}
