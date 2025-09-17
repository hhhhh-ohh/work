package com.wanmi.sbc.setting.api.response.country;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformCountryVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/4/26 15:46
 * @description <p> </p>
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatformCountryListResponse extends BasicResponse {

    @Schema(description = "国家地区实体")
    private List<PlatformCountryVO> platformCountryVOList;
}
