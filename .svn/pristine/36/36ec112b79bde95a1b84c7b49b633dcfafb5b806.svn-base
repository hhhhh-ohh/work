package com.wanmi.sbc.empower.api.response.customerservice;

import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingItemVO;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据id查询任意（包含已删除）在线客服配置信息response</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceSettingByStoreIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 在线客服配置信息(qq)
     */
    @Schema(description = "在线客服配置信息(qq)")
    private CustomerServiceSettingVO qqOnlineServerRop;

    /**
     * 在线客服配置信息(企微客服)
     */
    @Schema(description = "在线客服配置信息(企微客服)")
    private CustomerServiceSettingVO weChatOnlineServerRop;

    /**
     * 座席列表
     */
    @Schema(description = "座席列表")
    private List<CustomerServiceSettingItemVO> qqOnlineServerItemRopList;

    /**
     * 座席列表
     */
    @Schema(description = "座席列表")
    private List<CustomerServiceSettingItemVO> weChatOnlineServerItemRopList;

    /**
     * 在线客服配置信息(网易七鱼)
     */
    @Schema(description = "在线客服配置信息(网易七鱼)")
    private CustomerServiceSettingVO qiYuOnlineServerRop;
}
