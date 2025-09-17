package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.WechatVideoStoreAuditVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频带货申请分页结果</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频带货申请分页结果
     */
    @Schema(description = "视频带货申请分页结果")
    private MicroServicePage<WechatVideoStoreAuditVO> wechatVideoStoreAuditVOPage;
}
