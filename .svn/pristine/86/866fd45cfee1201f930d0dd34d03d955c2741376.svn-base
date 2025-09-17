package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformPayCompanyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 插件可用支付方式出参
 * @author malianfeng
 * @date 2021/8/4 12:46
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformPayCompanyListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /** 支付企业列表 */
    @Schema(description = "支付企业列表")
    List<PlatformPayCompanyVO> platformPayCompanyList;
}
