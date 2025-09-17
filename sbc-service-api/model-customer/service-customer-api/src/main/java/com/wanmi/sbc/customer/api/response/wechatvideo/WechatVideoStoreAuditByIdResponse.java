package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.customer.bean.vo.WechatVideoStoreAuditVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）视频带货申请信息response</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频带货申请信息
     */
    @Schema(description = "视频带货申请信息")
    private WechatVideoStoreAuditVO wechatVideoStoreAuditVO;
}
