package com.wanmi.sbc.customer.api.request.department;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @className ModifyEmployeeNumRequest
 * @description TODO
 * @author 黄昭
 * @date 2021/10/25 13:58
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyEmployeeNumRequest extends BaseRequest {
    private static final long serialVersionUID = -8212932653121685686L;

    /**
     * key:部门id
     * value:部门人员数量
     */
    private Map<String,Integer> modifyEmployeeNumMap;
}
