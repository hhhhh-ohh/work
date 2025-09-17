package com.wanmi.sbc.goods.api.response.enterprise;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 审核企业购商品时返回的标志
 *
 * @author CHENLI
 * @dateTime 2019/3/26 上午9:33
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseGoodsAuditResponse extends BasicResponse {

    private static final long serialVersionUID = -7632546894734146296L;

    /**
     * 成功标志
     */
    @Schema(description = "成功标志")
    private String backErrorCode;
}
