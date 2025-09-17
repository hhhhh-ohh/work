package com.wanmi.sbc.customer.api.response.ledgerfile;

import com.wanmi.sbc.customer.bean.vo.LedgerFileVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账文件列表结果</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFileListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分账文件列表结果
     */
    @Schema(description = "分账文件列表结果")
    private List<LedgerFileVO> ledgerFileVOList;
}
