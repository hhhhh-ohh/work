package com.wanmi.sbc.goods.api.response.restrictedrecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.RestrictedRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）限售信息response</p>
 * @author 限售记录
 * @date 2020-04-11 15:59:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestrictedRecordByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 限售信息
     */
    @Schema(description = "限售信息")
    private RestrictedRecordVO restrictedRecordVO;
}
