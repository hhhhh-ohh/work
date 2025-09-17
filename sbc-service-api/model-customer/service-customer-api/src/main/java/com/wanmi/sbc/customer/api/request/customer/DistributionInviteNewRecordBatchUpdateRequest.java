package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.DistributionInviteNewRecordDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInviteNewRecordBatchUpdateRequest extends BaseRequest {

    private List<DistributionInviteNewRecordDTO> list;

}
