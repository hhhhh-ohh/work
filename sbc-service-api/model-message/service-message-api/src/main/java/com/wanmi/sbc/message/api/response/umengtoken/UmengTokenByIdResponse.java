package com.wanmi.sbc.message.api.response.umengtoken;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.UmengTokenVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）友盟推送设备与会员关系信息response</p>
 * @author bob
 * @date 2020-01-06 11:36:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmengTokenByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 友盟推送设备与会员关系信息
     */
    @Schema(description = "友盟推送设备与会员关系信息")
    private UmengTokenVO umengTokenVO;
}
