package com.wanmi.sbc.setting.api.response.popupadministration;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.setting.bean.vo.PopupAdministrationVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>弹窗管理结果</p>
 * @author weiwenhao
 * @date 2020-04-22
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageManagementResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 应用页名称
     */
    @Schema(description = "应用页名称")
    private String pageManagementName;

    /**
     * 弹窗管理分页列表
     */
    @Schema(description = "弹窗管理分页列表")
    private List<PopupAdministrationVO> popupAdministrationVOS;


}
