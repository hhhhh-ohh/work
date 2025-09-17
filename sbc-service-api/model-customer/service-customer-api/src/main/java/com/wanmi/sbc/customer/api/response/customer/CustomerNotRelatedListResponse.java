package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNotRelatedListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "会员信息列表")
    private List<Map<String, Object>> customers;
}
