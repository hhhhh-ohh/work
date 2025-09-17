package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformGoodsDelDTO implements Serializable {

    private static final long serialVersionUID = -6716977837962165606L;
    //        时间戳
    private long gmt_create;
    private Long itemId;
    private String lmItemId;
    private List<Long> skuIdList;
    private GoodsSource goodsSource;
    private ThirdPlatformType thirdPlatformType;
}
