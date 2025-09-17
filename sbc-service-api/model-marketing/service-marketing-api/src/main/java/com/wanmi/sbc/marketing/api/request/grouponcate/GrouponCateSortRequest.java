package com.wanmi.sbc.marketing.api.request.grouponcate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.vo.GrouponCateSortVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: chenli
 * @Date: 2019/5/16
 * @Description: 拼团分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrouponCateSortRequest extends BaseRequest {
    private static final long serialVersionUID = 8576061174590308935L;

    /**
     * 拼团分类排序
     */
    @Schema(description = "拼团分类排序")
    @NotNull
    List<GrouponCateSortVO> grouponCateSortVOList;
}
