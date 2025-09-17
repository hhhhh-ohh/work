package com.wanmi.sbc.message.api.response.storemessagedetail;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.StoreMessageDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家消息/公告分页结果</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家消息/公告分页结果
     */
    @Schema(description = "商家消息/公告分页结果")
    private MicroServicePage<StoreMessageDetailVO> storeMessageDetailVOPage;
}
