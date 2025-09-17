package com.wanmi.sbc.setting.api.request.pickupsetting;/**
 * @author 黄昭
 * @create 2021/9/3 13:50
 */

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class PickupSettingDefaultAddressRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 2566912883693818021L;
    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull(message = "自提点Id不可为空")
    private Long id;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

}
