package com.wanmi.sbc.message.api.response.storemessagedetail;

import com.wanmi.sbc.message.bean.vo.StoreMessageDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class StoreMessageDetailTopListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家消息/公告列表
     */
    @Schema(description = "商家消息/公告列表")
    private List<StoreMessageDetailVO> messageDetailVOList;

    /**
     * 未读数量
     */
    @Schema(description = "未读数量")
    private Long unReadCount;
}
