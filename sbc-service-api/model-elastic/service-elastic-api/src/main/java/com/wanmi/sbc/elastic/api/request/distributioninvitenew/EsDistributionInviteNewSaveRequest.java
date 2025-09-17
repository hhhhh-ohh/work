package com.wanmi.sbc.elastic.api.request.distributioninvitenew;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/1/7 10:30
 * @description <p> 邀新记录响应实体 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class EsDistributionInviteNewSaveRequest extends BaseRequest {

    /**
     * 邀新记录
     */
    @Schema(description = "邀新记录信息")
    List<DistributionInviteNewRecordVO> inviteNewRecordVOList;
}