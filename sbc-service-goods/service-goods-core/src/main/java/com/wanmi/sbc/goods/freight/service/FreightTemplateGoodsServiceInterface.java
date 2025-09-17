package com.wanmi.sbc.goods.freight.service;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.common.plugin.annotation.Routing;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsSaveRequest;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * 单品运费模板服务接口
 * @className FreightTemplateGoodsServiceInterface
 * @author zhengyang
 * @date 2021/8/13 16:26
 **/
public interface FreightTemplateGoodsServiceInterface {

    @Transactional(rollbackFor = Exception.class)
    void renewalFreightTemplateGoods(FreightTemplateGoodsSaveRequest request);

    void freightTemplateNameIsRepetition(Long storeId, String freightTempName, boolean isCopy);

    @Transactional(rollbackFor = Exception.class)
    void updateFreightTemplateGoods(FreightTemplateGoodsSaveRequest request);

    List<FreightTemplateGoods> queryAll(Long storeId);

    @MasterRouteOnly
    List<FreightTemplateGoods> queryAllByIds(List<Long> ids);

    FreightTemplateGoods queryById(Long freightTempId);

    void hasFreightTemp(Long freightTempId);

    @Transactional(rollbackFor = Exception.class)
    void delById(Long id, Long storeId, PluginType pluginType);

    @Transactional(rollbackFor = Exception.class)
    void copyFreightTemplateGoods(Long freightTempId, Long storeId);

    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    void initFreightTemplate(Long storeId);

    FreightTemplateGoods queryByDefaultByStoreId(Long storeId);
}
