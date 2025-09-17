package com.wanmi.sbc.account.api.response.bank;

import com.wanmi.sbc.account.bean.vo.BankVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankListResponse {

    /**
     * 银行列表数据 {@link BankVO}
     */
    @Schema(description = "银行列表数据")
    private List<BankVO> bankVOList;

}
