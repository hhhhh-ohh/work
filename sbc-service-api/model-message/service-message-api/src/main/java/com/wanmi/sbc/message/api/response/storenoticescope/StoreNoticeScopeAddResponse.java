package com.wanmi.sbc.message.api.response.storenoticescope;

import com.wanmi.sbc.message.bean.vo.StoreNoticeScopeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家公告发送范围新增结果</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeScopeAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商家公告发送范围信息
     */
    @Schema(description = "已新增的商家公告发送范围信息")
    private StoreNoticeScopeVO storeNoticeScopeVO;
}
