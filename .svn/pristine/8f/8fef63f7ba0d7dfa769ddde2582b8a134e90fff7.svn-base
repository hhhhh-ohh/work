package com.wanmi.sbc.setting.api.request.pickupsetting;/**
 * @author 黄昭
 * @create 2021/9/3 13:50
 */

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author 黄昭
 * @className PickupSettingAuditRequest
 * @description 订单自提设置
 * @date 2021/9/3 13:50
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingAuditRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 2566912883693818021L;
    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull(message = "自提点Id不可为空")
    private Long id;

    @Schema(description = "是否启用 1:启用 0:停用")
    @Max(127)
    private Integer enableStatus;

    @Schema(description = "审核状态,0:未审核1 审核通过2审核失败")
    @Max(127)
    private Integer auditStatus;

    @Schema(description = "驳回理由")
    @Length(max = 255)
    private String auditReason;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    @Override
    public void checkParam() {
        if (enableStatus == null && auditStatus == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (enableStatus != null && auditStatus != null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (auditStatus != null
                && (AuditStatus.NOT_PASS.equals(AuditStatus.fromValue(auditStatus)) && StringUtils.isEmpty(auditReason))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
