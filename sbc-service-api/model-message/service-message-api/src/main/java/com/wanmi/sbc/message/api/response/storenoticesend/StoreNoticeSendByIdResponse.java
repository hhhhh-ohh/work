package com.wanmi.sbc.message.api.response.storenoticesend;

import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商家公告信息response</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeSendByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家公告信息
     */
    @Schema(description = "商家公告信息")
    private StoreNoticeSendVO storeNoticeSendVO;
}
