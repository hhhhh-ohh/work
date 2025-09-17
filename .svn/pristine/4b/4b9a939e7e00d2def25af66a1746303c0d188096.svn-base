package com.wanmi.sbc.setting.api.response.thirdaddress;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressPageVO;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>第三方地址映射列表</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAddressListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方地址映射列表
     */
    @Schema(description = "第三方地址映射列表")
    private List<ThirdAddressVO> thirdAddressList;
}
