package com.wanmi.sbc.crm.api.request.customgroup;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.crm.bean.vo.CustomGroupSortVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * @ClassName CustomerGroupSortListRequest
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/2/23 14:17
 * @Version 1.0
 **/
@Schema
@Data
public class CustomGroupBatchSortRequest extends BaseRequest {

    @Schema(description = "排序列表")
    @NotNull
    private List<CustomGroupSortVO> sortList;

    @Schema(description = "操作人id",hidden = true)
 //   @JsonIgnore
    private String operatorId;
}
