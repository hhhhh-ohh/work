package com.wanmi.sbc.empower.bean.vo.sellplatform.goods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className WxChannelsGetGoodsAuditVO
 * @description 商品审核信息
 * @date 2022/4/7 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformGetGoodsAuditVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 上一次提交时间, yyyy-MM-dd HH:mm:ss
     */
    @Schema(description = "上一次提交时间, yyyy-MM-dd HH:mm:ss")
    private String submit_time;

    /**
     *  上一次审核时间, yyyy-MM-dd HH:mm:ss
     */
    @Schema(description = "上一次审核时间, yyyy-MM-dd HH:mm:ss")
    private String audit_time;

    /**
     *  拒绝理由，只有edit_status为3时出现
     */
    @Schema(description = "拒绝理由，只有edit_status为3时出现")
    private String reject_reason;

    /**
     *  审核单id
     */
    @Schema(description = "审核单id")
    private String audit_id;

}