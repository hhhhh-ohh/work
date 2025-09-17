package com.wanmi.sbc.vas.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.VASEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 17:22 2020/2/28
 * @Description: 购买的增值服务
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VASSettingResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 购买的增值服务列表
     */
    @Schema(description = "购买的增值服务列表")
    private List<VASEntity> services = new ArrayList<>();

}

