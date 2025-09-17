package com.wanmi.sbc.elastic.api.response.storeInformation;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yangzhen
 * @Description //查询账期内有效店铺，自动关联5条信息response
 * @Date 10:40 2020/12/17
 * @Param
 * @return
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsListStoreByNameForAutoCompleteResponse extends BasicResponse {


    /**
     * 店铺列表
     */
    @Schema(description = "店铺列表")
    private List<StoreVO> storeVOList;
}
