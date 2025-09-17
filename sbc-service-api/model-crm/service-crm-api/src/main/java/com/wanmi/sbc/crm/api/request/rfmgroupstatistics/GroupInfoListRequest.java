package com.wanmi.sbc.crm.api.request.rfmgroupstatistics;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName GroupInfoListRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/1/17 13:37
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoListRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 分群名称
     */
    @Schema(description = "分群名称")
    private String groupName;

    /**
     * 返回列表长度
     */
    @Schema(description = "返回列表长度")
    private Integer limit;
}
