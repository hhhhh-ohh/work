package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.customer.bean.vo.WechatVideoStoreAuditVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频带货申请修改结果</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的视频带货申请信息
     */
    @Schema(description = "已修改的视频带货申请信息")
    private WechatVideoStoreAuditVO wechatVideoStoreAuditVO;
}
