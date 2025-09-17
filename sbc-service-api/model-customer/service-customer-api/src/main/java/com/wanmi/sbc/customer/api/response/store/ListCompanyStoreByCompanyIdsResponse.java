package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.MiniCompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.MiniStoreVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据companyIds查询未删除店铺列表response</p>
 * Created by of628-wenzhi on 2018-09-12-下午5:23.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ListCompanyStoreByCompanyIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺列表
     */
    @Schema(description = "店铺列表")
    private List<MiniStoreVO> storeVOList;

    /**
     * 公司列表
     */
    @Schema(description = "公司列表")
    private List<MiniCompanyInfoVO> companyInfoVOList;
}
