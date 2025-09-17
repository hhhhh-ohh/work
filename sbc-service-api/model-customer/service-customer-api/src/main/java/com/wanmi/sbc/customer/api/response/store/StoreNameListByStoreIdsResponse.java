package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreNameVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午2:06 2019/5/23
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreNameListByStoreIdsResponse extends BasicResponse {

    /**
     * 店铺名称列表
     */
    @Schema(description = "店铺名称列表")
    private List<StoreNameVO> storeNameList;

}
