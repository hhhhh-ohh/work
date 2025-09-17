package com.wanmi.sbc.setting.api.response.storemessagenodesetting;

import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家消息节点新增结果</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeSettingAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商家消息节点信息
     */
    @Schema(description = "已新增的商家消息节点信息")
    private StoreMessageNodeSettingVO storeMessageNodeSettingVO;
}
