package com.wanmi.sbc.marketing.pluginconfig.model.entry;

import com.wanmi.sbc.marketing.bean.dto.MarketingPluginConfigDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.pluginconfig.model.root.MarketingPluginConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingPluginConfigChange
 * @description
 * @date 2021/6/9 17:40
 **/
public class MarketingPluginConfigConvert {

    /**
     * bean转dto
     * @param bean
     * @return
     */
    public static MarketingPluginConfigDTO toDTO(MarketingPluginConfig bean){
        MarketingPluginConfigDTO dto = new MarketingPluginConfigDTO();
        dto.setMarketingType(bean.getMarketingType());
        dto.setSort(bean.getSort());
        dto.setDescription(bean.getDescription());
        if( StringUtils.isNotEmpty(bean.getCoexist())){
            String[] strs = bean.getCoexist().split("\\,");
            List<MarketingPluginType> list = new ArrayList<>();
            for(String str : strs){
                list.add(MarketingPluginType.fromValue(str));
            }
            dto.setCoexist(list);
        }


        return dto;
    }

    /**
     * dto转bean
     * @param dto
     * @return
     */
    public static MarketingPluginConfig toBean(MarketingPluginConfigDTO dto){
        MarketingPluginConfig bean = new MarketingPluginConfig();
        bean.setMarketingType(dto.getMarketingType());
        bean.setSort(dto.getSort());
        bean.setDescription(dto.getDescription());
        if (CollectionUtils.isNotEmpty(dto.getCoexist())) {
            bean.setCoexist(StringUtils.join(dto.getCoexist(),","));
        }

        return bean;
    }

    /**
     * beanList 转dtolist
     * @param list
     * @return
     */
    public static List<MarketingPluginConfigDTO> toDTOList(List<MarketingPluginConfig> list){
        List<MarketingPluginConfigDTO> dtos = new ArrayList<>();
        for(MarketingPluginConfig bean : list){
            dtos.add(toDTO(bean));
        }
        return dtos;
    }

    /**
     * dtolist转beanlist
     * @param list
     * @return
     */
    public static List<MarketingPluginConfig> toBeanList(List<MarketingPluginConfigDTO> list){
        List<MarketingPluginConfig> beans = new ArrayList<>();
        for(MarketingPluginConfigDTO dto : list){
            beans.add(toBean(dto));
        }
        return beans;
    }
}
