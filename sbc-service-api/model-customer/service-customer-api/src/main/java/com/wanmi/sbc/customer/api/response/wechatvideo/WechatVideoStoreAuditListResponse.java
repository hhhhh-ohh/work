package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.customer.bean.vo.WechatVideoStoreAuditVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频带货申请列表结果</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频带货申请列表结果
     */
    @Schema(description = "视频带货申请列表结果")
    private List<WechatVideoStoreAuditVO> wechatVideoStoreAuditVOList;
}
