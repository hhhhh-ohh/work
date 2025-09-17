package com.wanmi.sbc.message.api.response.storenoticescope;

import com.wanmi.sbc.message.bean.vo.StoreNoticeScopeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商家公告发送范围信息response</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeScopeByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家公告发送范围信息
     */
    @Schema(description = "商家公告发送范围信息")
    private StoreNoticeScopeVO storeNoticeScopeVO;
}
