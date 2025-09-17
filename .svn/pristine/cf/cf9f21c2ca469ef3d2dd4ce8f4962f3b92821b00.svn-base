package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:03 2019/5/24
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrouponActivityingByGoodsInfoIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -4553374123124168032L;

    /**
     * 单品ids
     */
    @Schema(description = "活动ID")
    @NotEmpty
    private List<String> goodsInfoIds;

}
