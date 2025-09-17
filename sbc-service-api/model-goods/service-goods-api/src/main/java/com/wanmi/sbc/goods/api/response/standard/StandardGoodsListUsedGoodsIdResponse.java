package com.wanmi.sbc.goods.api.response.standard;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午4:01 2018/12/13
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsListUsedGoodsIdResponse extends BasicResponse {

    private static final long serialVersionUID = -7306962994523285115L;

    @Schema(description = "spu Id")
    private List<String> goodsIds;
}
