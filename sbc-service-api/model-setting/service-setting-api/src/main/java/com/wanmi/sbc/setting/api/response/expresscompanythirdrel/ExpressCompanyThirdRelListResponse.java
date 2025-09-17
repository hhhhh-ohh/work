package com.wanmi.sbc.setting.api.response.expresscompanythirdrel;

import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelVO;
import com.wanmi.sbc.setting.bean.vo.ThirdExpressCompanyVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 第三方平台物流公司列表
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpressCompanyThirdRelListResponse implements Serializable {

    private static final long serialVersionUID = -7014174263074953392L;

    /**
     * 物流公司映射列表
     */
    @Schema(description = "平台与第三方物流映射关系列表")
    private List<ExpressCompanyThirdRelVO> thirdRelVOList;
}

