package com.wanmi.sbc.customer.api.response.ledgerfile;

import com.wanmi.sbc.customer.bean.vo.LedgerFileVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账文件新增结果</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFileAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的分账文件信息
     */
    @Schema(description = "已新增的分账文件信息")
    private String fileId;
}
