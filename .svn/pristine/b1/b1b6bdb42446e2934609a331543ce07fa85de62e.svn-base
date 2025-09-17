package com.wanmi.sbc.account.api.response.company;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.CompanyAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商家收款账户列表响应
 * Created by daiyitian on 2017/11/30.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountListResponse extends BasicResponse {

    private static final long serialVersionUID = -2359661857117094201L;
    /**
     * 收款账户列表数据 {@link CompanyAccountVO}
     */
    @Schema(description = "收款账户列表数据")
    private List<CompanyAccountVO> companyAccountVOList;
}
