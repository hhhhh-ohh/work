package com.wanmi.sbc.setting.api.response.storemessagenode;

import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家消息节点列表结果</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家消息节点列表结果
     */
    @Schema(description = "商家消息节点列表结果")
    private List<StoreMessageNodeVO> storeMessageNodeVOList;
}
