package com.wanmi.sbc.setting.api.response.platformaddress;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.AddressJsonInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>地址json数据VO</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@Data
public class AddressJsonResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 省份信息
     */
    @Schema(description = "省份信息")
    private ArrayList<AddressJsonInfoVO> provinces;

    /**
     * 城市信息
     */
    @Schema(description = "城市信息")
    private Map<String,List<AddressJsonInfoVO>>  cities;

    /**
     * 区域信息
     */
    @Schema(description = "区域信息")
    private Map<String,List<AddressJsonInfoVO>> areas;

}
