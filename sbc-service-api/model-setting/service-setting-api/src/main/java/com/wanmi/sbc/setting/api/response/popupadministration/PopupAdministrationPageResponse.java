package com.wanmi.sbc.setting.api.response.popupadministration;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.PopupAdministrationVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>弹窗管理结果</p>
 * @author weiwenhao
 * @date 2020-04-21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopupAdministrationPageResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 弹窗管理分页列表
     */
    @Schema(description = "弹窗管理分页列表")
    private MicroServicePage<PopupAdministrationVO> popupAdministrationVOS;
}
