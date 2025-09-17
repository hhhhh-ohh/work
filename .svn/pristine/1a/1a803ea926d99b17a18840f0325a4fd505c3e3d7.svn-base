package com.wanmi.sbc.customer.api.response.level;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 批量客户等级响应
 * @Author: daiyitian
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 公司信息Response
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLevelMapGetResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 客户在各店铺相对应的会员等级
     * 内容<店铺ID,会员等级>
     */
    @Schema(description = "客户在各店铺相对应的会员等级")
    private HashMap<Long, CommonLevelVO> commonLevelVOMap;

}
