package com.wanmi.sbc.message.api.response.storenoticesend;

import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家公告列表结果</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeSendListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家公告列表结果
     */
    @Schema(description = "商家公告列表结果")
    private List<StoreNoticeSendVO> storeNoticeSendVOList;
}
