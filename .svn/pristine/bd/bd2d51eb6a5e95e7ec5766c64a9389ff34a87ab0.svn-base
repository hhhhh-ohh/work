package com.wanmi.sbc.setting.api.response.storemessagenode;

import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商家消息节点信息response</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家消息节点信息
     */
    @Schema(description = "商家消息节点信息")
    private StoreMessageNodeVO storeMessageNodeVO;
}
