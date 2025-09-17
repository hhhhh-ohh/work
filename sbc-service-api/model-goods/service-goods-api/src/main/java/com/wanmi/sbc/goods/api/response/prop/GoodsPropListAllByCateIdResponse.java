package com.wanmi.sbc.goods.api.response.prop;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/10/31 14:53
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropListAllByCateIdResponse extends BasicResponse {

    private static final long serialVersionUID = 7006307138227812143L;

    @Schema(description = "商品属性")
    private List<GoodsPropVO> goodsPropVOList;
}
