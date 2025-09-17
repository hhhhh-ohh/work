package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerAddDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @description 批量会员新增请求类
 * @author  daiyitian
 * @date 2021/4/25 16:02
 **/
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class CustomerBatchAddRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -3854269857592932191L;

    /**
     * 批量会员新增信息列表
     */
    @NotEmpty
    @Schema(description = "批量会员新增信息列表")
    private List<CustomerAddDTO> customerAddList;



}
