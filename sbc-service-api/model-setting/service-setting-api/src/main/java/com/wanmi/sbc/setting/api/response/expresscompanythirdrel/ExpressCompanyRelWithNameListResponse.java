package com.wanmi.sbc.setting.api.response.expresscompanythirdrel;

import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelWithNameVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 带有快递公司名称的映射列表
 * @author malianfeng
 * @date 2022/4/26 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpressCompanyRelWithNameListResponse implements Serializable {

    private static final long serialVersionUID = -4897292513505297567L;

    /**
     * 物流公司列表
     */
    @Schema(description = "带有快递公司名称的映射列表")
    private List<ExpressCompanyThirdRelWithNameVO> thirdRelVOList;
}

