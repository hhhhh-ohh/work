package com.wanmi.sbc.goods.provider.impl.prop;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.dto.GoodsPropDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsPropRequestDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropVO;
import com.wanmi.sbc.goods.prop.model.root.GoodsProp;
import com.wanmi.sbc.goods.prop.model.root.GoodsPropDetail;
import com.wanmi.sbc.goods.prop.request.GoodsPropRequest;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wanggang
 * @createDate: 2018/11/7 19:20
 * @version: 1.0
 */
public class GoodsPropConvert {

    private GoodsPropConvert(){

    }

    /**
     * List<GoodsPropDTO> 对象 转换成 List<GoodsProp> 对象
     * @param goodsPropDTOList
     * @return
     */
    public static List<GoodsProp> toGoodsPropList(List<GoodsPropDTO> goodsPropDTOList){
        if (CollectionUtils.isEmpty(goodsPropDTOList)){
            return null;
        }
       return goodsPropDTOList.stream().map(goodsPropDTO -> {
            GoodsProp goodsProp = new GoodsProp();
            KsBeanUtil.copyPropertiesThird(goodsPropDTO,goodsProp);
            return goodsProp;
        }).collect(Collectors.toList());
    }

    /**
     * extends GoodsPropRequestDTO 对象 转换成 GoodsPropRequest 对象
     * @param t extends GoodsPropRequestDTO
     * @return
     */
    public static <T extends GoodsPropRequestDTO> GoodsPropRequest toGoodsPropRequest(T t){
        GoodsPropRequest goodsPropRequest = new GoodsPropRequest();
        goodsPropRequest.setLastPropId(t.getLastPropId());
        goodsPropRequest.setGoodsProp(t.getGoodsProp());
        goodsPropRequest.setGoodsProps(t.getGoodsProps());
        return goodsPropRequest;
    }

    /**
     * extends GoodsPropDTO 对象 转换成 GoodsProp 对象
     * @param t extends GoodsPropDTO
     * @return
     */
    public static <T extends GoodsPropDTO> GoodsProp toGoodsProp(T t){
        GoodsProp goodsProp = new GoodsProp();
        KsBeanUtil.copyPropertiesThird(t,goodsProp);
        return goodsProp;
    }

}
