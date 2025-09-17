package com.wanmi.sbc.setting.api.response.expresscompanythirdrel;

import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 平台与第三方物流映射关系详情列表
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyThirdRelDetailListResponse implements Serializable {

    private static final long serialVersionUID = -7014174263074953392L;

    /**
     * 平台与第三方物流映射关系详情列表
     */
    @Schema(description = "平台与第三方物流映射关系详情列表")
    private List<ExpressCompanyThirdRelDetailVO> thirdRelDetailList;
}

