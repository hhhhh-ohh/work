package com.wanmi.sbc.setting.api.response.presetsearch;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.PresetSearchTermsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * <p>预置搜索词VO</p>
 * @author weiwenhao
 * @date 2020-04-16
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresetSearchTermsQueryResponse extends BasicResponse {
    /**
     * 新增的预置搜索词信息
     */
    @Schema(description = "新增预置搜索词信息")
    private List<PresetSearchTermsVO> presetSearchTermsVO;

}
