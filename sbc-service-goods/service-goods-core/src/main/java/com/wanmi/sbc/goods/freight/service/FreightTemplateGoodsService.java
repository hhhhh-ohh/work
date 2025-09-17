package com.wanmi.sbc.goods.freight.service;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.common.plugin.annotation.Routing;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.freight.CollectPageInfoRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsSaveRequest;
import com.wanmi.sbc.goods.api.request.freight.GetFreightInGoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import com.wanmi.sbc.goods.api.response.freight.GetFreightInGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.dto.FreightTemplateGoodsExpressSaveDTO;
import com.wanmi.sbc.goods.bean.dto.FreightTemplateGoodsFreeSaveDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoodsExpress;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoodsFree;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateStore;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsExpressRepository;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsFreeRepository;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsRepository;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateStoreRepository;
import com.wanmi.sbc.goods.freight.vo.FreightPackageGoodsPriceVO;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;

import io.seata.spring.annotation.GlobalTransactional;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 单品运费模板服务
 * Created by sunkun on 2018/5/2.
 */
@Service
public class FreightTemplateGoodsService extends FreightTemplateService implements FreightTemplateGoodsServiceInterface {

    @Resource
    private FreightTemplateGoodsRepository freightTemplateGoodsRepository;

    @Resource
    private FreightTemplateGoodsExpressRepository freightTemplateGoodsExpressRepository;

    @Resource
    private FreightTemplateGoodsFreeRepository freightTemplateGoodsFreeRepository;

    @Resource
    private FreightTemplateStoreRepository freightTemplateStoreRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsAuditRepository goodsAuditRepository;

    @Resource
    private StoreQueryProvider storeQueryProvider;


    /**
     * 保存单品运费模板
     *
     * @param request 单品运费模板信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void renewalFreightTemplateGoods(FreightTemplateGoodsSaveRequest request) {
        FreightTemplateGoods freightTemplateGoods = null;
        if (request.getFreightTempId() == null) {
            int count = freightTemplateGoodsRepository.countByStoreIdAndDelFlag(request.getStoreId(), DeleteFlag.NO);
            if (count >= Constants.FREIGHT_GOODS_MAX_SIZE) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //校验模板名称是否重复
            this.freightTemplateNameIsRepetition(request.getStoreId(), request.getFreightTempName(),false);
            freightTemplateGoods = new FreightTemplateGoods();
            BeanUtils.copyProperties(request, freightTemplateGoods);
            freightTemplateGoods.setCreateTime(LocalDateTime.now());
            freightTemplateGoods.setDelFlag(DeleteFlag.NO);
        } else {
            freightTemplateGoods = freightTemplateGoodsRepository.findById(request.getFreightTempId()).orElse(null);
            if (freightTemplateGoods == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //修改名称，校验模板名称是否重复
            if (!StringUtils.equals(request.getFreightTempName(), freightTemplateGoods.getFreightTempName())) {
                this.freightTemplateNameIsRepetition(freightTemplateGoods.getStoreId(), request.getFreightTempName(),false);
            }
            //组装并保存单品运费模板
            freightTemplateGoods.setStreetId(request.getStreetId());
            freightTemplateGoods.setAreaId(request.getAreaId());
            freightTemplateGoods.setCityId(request.getCityId());
            freightTemplateGoods.setProvinceId(request.getProvinceId());
            if (Objects.equals(freightTemplateGoods.getDefaultFlag(), DefaultFlag.NO)) {
                freightTemplateGoods.setFreightTempName(request.getFreightTempName());
            }
            freightTemplateGoods.setFreightFreeFlag(request.getFreightFreeFlag());
            freightTemplateGoods.setValuationType(request.getValuationType());
            freightTemplateGoods.setSpecifyTermFlag(request.getSpecifyTermFlag());
        }
        FreightTemplateGoods rFreightTemplateGoods = freightTemplateGoodsRepository.save(freightTemplateGoods);

        //过滤出单品运费模板快递运送下所有区域
        List<String> expressAreas = request.getFreightTemplateGoodsExpressSaveRequests().stream()
                .filter(info->Objects.equals(DeleteFlag.NO,info.getDelFlag())).map(info -> {
            if (info.getDestinationArea() == null || info.getDestinationArea().length == 0) {
                return null;
            }
            return StringUtils.join(info.getDestinationArea(), ",");
        }).filter(Objects::nonNull).collect(Collectors.toList());
        //过滤出单品运费模板指定包邮条件下所有区域
        List<String> freeAreas = request.getFreightTemplateGoodsFreeSaveRequests().stream()
                .filter(info->Objects.equals(DeleteFlag.NO,info.getDelFlag())).map(info -> {
            if (info.getDestinationArea() == null || info.getDestinationArea().length == 0) {
                return null;
            }
            return StringUtils.join(info.getDestinationArea(), ",");
        }).filter(Objects::nonNull).collect(Collectors.toList());
        //校验是否有重复区域
        if (this.verifyAreaRepetition(expressAreas) || this.verifyAreaRepetition(freeAreas)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030030);
        }
        //保存单品运费模板
        this.batchRenewalFreightTemplateGoodsExpress(rFreightTemplateGoods, request.getFreightTemplateGoodsExpressSaveRequests());
        //保存单品运费模板指定包邮条件
        this.batchRenewalFreightTemplateGoodsFree(rFreightTemplateGoods, request.getFreightTemplateGoodsFreeSaveRequests());
    }

    /**
     * 校验名称重复
     *
     * @param storeId 店铺id
     * @param freightTempName 单品运费模板名称
     * @param isCopy 是否复制
     */
    @Override
    public void freightTemplateNameIsRepetition(Long storeId, String freightTempName, boolean isCopy) {
        FreightTemplateGoods freightTemplateGoods = freightTemplateGoodsRepository.queryByFreighttemplateName(storeId, freightTempName);
        if (freightTemplateGoods != null) {
            GoodsErrorCodeEnum errorCode = GoodsErrorCodeEnum.K030031;
            if(isCopy){
                errorCode = GoodsErrorCodeEnum.K030029;
            }
            throw new SbcRuntimeException(errorCode);
        }
    }

    /**
     * 修改单品运费模板
     *
     * @param request 单品运费模板信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFreightTemplateGoods(FreightTemplateGoodsSaveRequest request) {
        FreightTemplateGoods freightTemplateGoods = freightTemplateGoodsRepository.findById(request.getFreightTempId()).orElse(null);
        if (freightTemplateGoods == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //组装并保存单品运费模板
        freightTemplateGoods.setStreetId(request.getStreetId());
        freightTemplateGoods.setAreaId(request.getAreaId());
        freightTemplateGoods.setCityId(request.getCityId());
        freightTemplateGoods.setProvinceId(request.getProvinceId());
        freightTemplateGoods.setFreightTempName(request.getFreightTempName());
        freightTemplateGoods.setFreightFreeFlag(request.getFreightFreeFlag());
        freightTemplateGoods.setValuationType(request.getValuationType());
        freightTemplateGoods.setSpecifyTermFlag(request.getSpecifyTermFlag());
        freightTemplateGoodsRepository.save(freightTemplateGoods);
        //批量更新单品运费模板快递运送
        this.batchRenewalFreightTemplateGoodsExpress(freightTemplateGoods, request.getFreightTemplateGoodsExpressSaveRequests());
        //批量更新单品运费模板指定包邮条件
        this.batchRenewalFreightTemplateGoodsFree(freightTemplateGoods, request.getFreightTemplateGoodsFreeSaveRequests());
    }

    /**
     * 查询所有单品运费模板列表
     *
     * @param storeId 店铺id
     * @return 单品运费模板列表
     */
    @Override
    public List<FreightTemplateGoods> queryAll(Long storeId) {
        List<FreightTemplateGoods> list = freightTemplateGoodsRepository.queryAll(storeId, DeleteFlag.NO);
        List<Long> ids = list.stream().map(FreightTemplateGoods::getFreightTempId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            List<FreightTemplateGoodsExpress> freightTemplateGoodsExpresses = freightTemplateGoodsExpressRepository.findByFreightTempIds(ids);
            if (CollectionUtils.isNotEmpty(freightTemplateGoodsExpresses)) {
                list.forEach(info -> {
                    info.setFreightTemplateGoodsExpresses(freightTemplateGoodsExpresses.stream().filter(express ->
                            info.getFreightTempId().equals(express.getFreightTempId())).collect(Collectors.toList()));
                });
            }
        }
        return list;
    }


    /**
     * 根据主键列表查询单品运费模板列表
     *
     * @param ids 单品运费模板ids
     * @return 单品运费模板列表
     */
    @Override
    @MasterRouteOnly
    public List<FreightTemplateGoods> queryAllByIds(List<Long> ids) {
        List<FreightTemplateGoods> list = freightTemplateGoodsRepository.queryByFreightTempIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        List<FreightTemplateGoodsExpress> freightTemplateGoodsExpresses = freightTemplateGoodsExpressRepository.findByFreightTempIds(ids);
        List<FreightTemplateGoodsFree> freightTemplateGoodsFrees = freightTemplateGoodsFreeRepository.findByFreightTempIds(ids);
        list.forEach(info -> {
            info.setFreightTemplateGoodsExpresses(freightTemplateGoodsExpresses.stream().filter(express ->
                    info.getFreightTempId().equals(express.getFreightTempId())).collect(Collectors.toList()));
            info.setFreightTemplateGoodsFrees(freightTemplateGoodsFrees.stream().filter(frees ->
                    info.getFreightTempId().equals(frees.getFreightTempId())).collect(Collectors.toList()));
        });
        return list;
    }

    /**
     * 查询单品运费模板
     *
     * @param freightTempId 单品运费模板id
     * @return 单品运费模板
     */
    @Override
    public FreightTemplateGoods queryById(Long freightTempId) {
        FreightTemplateGoods freightTemplateGoods = freightTemplateGoodsRepository.queryById(freightTempId);
        if (freightTemplateGoods == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        freightTemplateGoods.setFreightTemplateGoodsExpresses(freightTemplateGoodsExpressRepository.findByFreightTempIdAndDelFlag(freightTempId, DeleteFlag.NO));
        freightTemplateGoods.setFreightTemplateGoodsFrees(freightTemplateGoodsFreeRepository.findByFreightTempIdAndDelFlag(freightTempId, DeleteFlag.NO));
        return freightTemplateGoods;
    }

    /**
     * 根据运费模板ID判断模板是否存在
     * @param freightTempId 单品运费模板id
     */
    @Override
    public void hasFreightTemp(Long freightTempId) {
        FreightTemplateGoods freightTemplateGoods = freightTemplateGoodsRepository.queryById(freightTempId);
        if (Objects.isNull(freightTemplateGoods)) {
            //运费模板不存在异常，待common拆分后再改异常编号
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030032);
        }
    }

    /**
     * 根据主键和店铺id删除单品运费模板
     *
     * @param id 运费模板ID
     * @param storeId 店铺id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Routing(routingRule = MethodRoutingRule.PLUGIN_TYPE, pluginType= PluginType.NORMAL)
    public void delById(Long id, Long storeId,PluginType pluginType) {
        FreightTemplateGoods freightTemplateGoods = this.queryById(id);
        if (!Objects.equals(freightTemplateGoods.getStoreId(), storeId) ||
                Objects.equals(freightTemplateGoods.getDefaultFlag(), DefaultFlag.YES)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        freightTemplateGoods.setDelFlag(DeleteFlag.YES);
        freightTemplateGoodsRepository.save(freightTemplateGoods);
        FreightTemplateGoods defaultFreightTemplateGodos = freightTemplateGoodsRepository.queryByDefault(storeId);
        // 店铺下没有默认模板,系统数据错误
        Assert.assertNotNull(defaultFreightTemplateGodos, CommonErrorCodeEnum.K000009);
        modifyFreightRelGoods(id, defaultFreightTemplateGodos.getFreightTempId(), storeId);
    }

    /**
     * 复制单品运费模板
     *
     * @param freightTempId 运费模板ID
     * @param storeId 店铺id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyFreightTemplateGoods(Long freightTempId, Long storeId) {
        //单品运费模板上限20
        int count = freightTemplateGoodsRepository.countByStoreIdAndDelFlag(storeId, DeleteFlag.NO);
        if (count >= Constants.FREIGHT_GOODS_MAX_SIZE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //查询要复制单品运费模板
        FreightTemplateGoods freightTemplateGoods = this.queryById(freightTempId);
        if (!Objects.equals(freightTemplateGoods.getStoreId(), storeId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FreightTemplateGoods newFreightTemplateGoods = new FreightTemplateGoods();
        BeanUtils.copyProperties(freightTemplateGoods, newFreightTemplateGoods);
        //拼接新模板名称
        newFreightTemplateGoods.setFreightTempName(freightTemplateGoods.getFreightTempName() + "的副本");
        if (newFreightTemplateGoods.getFreightTempName().length() > Constants.FREIGHT_GOODS_MAX_SIZE) {
            //名称长度超出限制
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030028);
        }
        this.freightTemplateNameIsRepetition(storeId, newFreightTemplateGoods.getFreightTempName(),true);
        LocalDateTime date = LocalDateTime.now();
        newFreightTemplateGoods.setFreightTempId(null);
        newFreightTemplateGoods.setCreateTime(date);
        newFreightTemplateGoods.setDefaultFlag(DefaultFlag.NO);
        newFreightTemplateGoods.setSpecifyTermFlag(CollectionUtils.isEmpty(freightTemplateGoods.getFreightTemplateGoodsFrees()) ? DefaultFlag.NO : DefaultFlag.YES);
        //复制单品模板
        FreightTemplateGoods resultFreightTemplateGoods = freightTemplateGoodsRepository.save(newFreightTemplateGoods);
        //复制单品运费模板快递运送
        freightTemplateGoods.getFreightTemplateGoodsExpresses().forEach(info -> {
            FreightTemplateGoodsExpress newFreightTemplateGoodsExpress = new FreightTemplateGoodsExpress();
            BeanUtils.copyProperties(info, newFreightTemplateGoodsExpress);
            newFreightTemplateGoodsExpress.setId(null);
            newFreightTemplateGoodsExpress.setFreightTempId(resultFreightTemplateGoods.getFreightTempId());
            newFreightTemplateGoodsExpress.setCreateTime(date);
            freightTemplateGoodsExpressRepository.save(newFreightTemplateGoodsExpress);
        });
        //复制单品运费模板指定包邮条件
        freightTemplateGoods.getFreightTemplateGoodsFrees().forEach(info -> {
            FreightTemplateGoodsFree newFreightTemplateGoodsFree = new FreightTemplateGoodsFree();
            BeanUtils.copyProperties(info, newFreightTemplateGoodsFree);
            newFreightTemplateGoodsFree.setId(null);
            newFreightTemplateGoodsFree.setFreightTempId(resultFreightTemplateGoods.getFreightTempId());
            newFreightTemplateGoodsFree.setCreateTime(date);
            freightTemplateGoodsFreeRepository.save(newFreightTemplateGoodsFree);
        });
    }

    /**
     * 创建店铺后初始化运费模板
     *
     * @param storeId 店铺id
     */
    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void initFreightTemplate(Long storeId) {
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(storeId)).getContext().getStoreVO();
        if (store == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //组装单品运费模板数据
        FreightTemplateGoodsSaveRequest freightTemplateGoodsSaveRequest = new FreightTemplateGoodsSaveRequest();
        freightTemplateGoodsSaveRequest.setFreightTempName("默认模板");
        freightTemplateGoodsSaveRequest.setStoreId(storeId);
        freightTemplateGoodsSaveRequest.setCompanyInfoId(store.getCompanyInfo().getCompanyInfoId());
        freightTemplateGoodsSaveRequest.setDefaultFlag(DefaultFlag.YES);
        freightTemplateGoodsSaveRequest.setFreightFreeFlag(DefaultFlag.NO);
        freightTemplateGoodsSaveRequest.setSpecifyTermFlag(DefaultFlag.NO);
        freightTemplateGoodsSaveRequest.setValuationType(ValuationType.NUMBER);
        // 组装单品运费模板快递运送数据
        List<FreightTemplateGoodsExpressSaveDTO> list = new ArrayList<>();
        FreightTemplateGoodsExpressSaveDTO freightTemplateGoodsExpressSaveRequest = new FreightTemplateGoodsExpressSaveDTO();
        freightTemplateGoodsExpressSaveRequest.setDefaultFlag(DefaultFlag.YES);
        freightTemplateGoodsExpressSaveRequest.setDelFlag(DeleteFlag.NO);
        freightTemplateGoodsExpressSaveRequest.setDestinationArea(new String[]{});
        freightTemplateGoodsExpressSaveRequest.setDestinationAreaName(new String[]{"未被划分的配送地区自动归于默认运费"});
        BigDecimal defaultNum = BigDecimal.ZERO;
        freightTemplateGoodsExpressSaveRequest.setFreightStartNum(new BigDecimal(1));
        freightTemplateGoodsExpressSaveRequest.setFreightStartPrice(defaultNum);
        freightTemplateGoodsExpressSaveRequest.setFreightPlusNum(new BigDecimal(1));
        freightTemplateGoodsExpressSaveRequest.setFreightPlusPrice(defaultNum);
        list.add(freightTemplateGoodsExpressSaveRequest);
        freightTemplateGoodsSaveRequest.setFreightTemplateGoodsExpressSaveRequests(list);
        //保存单品运费模板
        this.renewalFreightTemplateGoods(freightTemplateGoodsSaveRequest);
        //组装店铺运费模板数据
        FreightTemplateStore freightTemplateStore = new FreightTemplateStore();
        freightTemplateStore.setFreightTempName("默认模板");
        freightTemplateStore.setDestinationAreaName("未被划分的配送地区自动归于默认运费");
        freightTemplateStore.setFixedFreight(defaultNum);
        freightTemplateStore.setSatisfyFreight(defaultNum);
        freightTemplateStore.setSatisfyPrice(defaultNum);
        freightTemplateStore.setStoreId(storeId);
        freightTemplateStore.setCompanyInfoId(store.getCompanyInfo().getCompanyInfoId());
        freightTemplateStore.setCreateTime(LocalDateTime.now());
        freightTemplateStore.setDefaultFlag(DefaultFlag.YES);
        freightTemplateStore.setDelFlag(DeleteFlag.NO);
        freightTemplateStore.setFreightType(DefaultFlag.YES);
        freightTemplateStore.setDeliverWay(DeliverWay.EXPRESS);
        freightTemplateStore.setDestinationArea("");
        freightTemplateStoreRepository.save(freightTemplateStore);
    }

    /**
     * 根据店铺id获取默认的单品运费模板
     * @param storeId 店铺id
     * @return 单品运费模板
     */
    @Override
    public FreightTemplateGoods queryByDefaultByStoreId(Long storeId){
        return freightTemplateGoodsRepository.queryByDefault(storeId);
    }

    /**
     * 批量更新单品运费模板快递运送
     *
     * @param freightTemplateGoods  单品运费模板
     * @param list 单品运费模板费用
     */
    private void batchRenewalFreightTemplateGoodsExpress(FreightTemplateGoods freightTemplateGoods,
                                                         List<FreightTemplateGoodsExpressSaveDTO> list) {
        list.forEach(info -> {
            FreightTemplateGoodsExpress freightTemplateGoodsExpress = null;
            if (info.getId() != null) {
                //编辑单品运费模板快递运送
                freightTemplateGoodsExpress = freightTemplateGoodsExpressRepository.findById(info.getId()).orElse(null);
                if (freightTemplateGoodsExpress != null) {
                    //默认模板不支持删除，非法输入
                    if (Objects.equals(info.getDefaultFlag(), DefaultFlag.YES) && Objects.equals(info.getDelFlag(), DeleteFlag.YES)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    freightTemplateGoodsExpress.setDelFlag(info.getDelFlag());
                    freightTemplateGoodsExpress.setFreightPlusNum(info.getFreightPlusNum());
                    freightTemplateGoodsExpress.setFreightPlusPrice(info.getFreightPlusPrice());
                    freightTemplateGoodsExpress.setFreightStartNum(info.getFreightStartNum());
                    freightTemplateGoodsExpress.setFreightStartPrice(info.getFreightStartPrice());
                }
            } else {
                //新增单品运费模板快递运送
                freightTemplateGoodsExpress = new FreightTemplateGoodsExpress();
                BeanUtils.copyProperties(info, freightTemplateGoodsExpress);
                freightTemplateGoodsExpress.setCreateTime(LocalDateTime.now());
                freightTemplateGoodsExpress.setDelFlag(DeleteFlag.NO);
                freightTemplateGoodsExpress.setFreightTempId(freightTemplateGoods.getFreightTempId());
            }
            if (freightTemplateGoodsExpress != null) {
                freightTemplateGoodsExpress.setValuationType(freightTemplateGoods.getValuationType());
                freightTemplateGoodsExpress.setDestinationArea(StringUtils.join(info.getDestinationArea(), ","));
                freightTemplateGoodsExpress.setDestinationAreaName(StringUtils.join(info.getDestinationAreaName(), ","));
                freightTemplateGoodsExpressRepository.save(freightTemplateGoodsExpress);
            }
        });
    }

    /**
     * 批量更新单品运费模板指定包邮条件
     *
     * @param freightTemplateGoods 单品运费模板
     * @param list 单品运费模板费用
     */
    private void batchRenewalFreightTemplateGoodsFree(FreightTemplateGoods freightTemplateGoods,
                                                      List<FreightTemplateGoodsFreeSaveDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(info -> {
            FreightTemplateGoodsFree freightTemplateGoodsFree = null;
            if (info.getId() != null) {
                //编辑单品运费模板指定包邮条件
                freightTemplateGoodsFree = freightTemplateGoodsFreeRepository.findById(info.getId()).orElseGet(FreightTemplateGoodsFree::new);
                freightTemplateGoodsFree.setConditionType(info.getConditionType());
                freightTemplateGoodsFree.setConditionOne(info.getConditionOne());
                freightTemplateGoodsFree.setConditionTwo(info.getConditionTwo());
                freightTemplateGoodsFree.setDelFlag(info.getDelFlag());
            } else {
                //新增单品运费模板指定包邮条件
                freightTemplateGoodsFree = new FreightTemplateGoodsFree();
                BeanUtils.copyProperties(info, freightTemplateGoodsFree);
                freightTemplateGoodsFree.setCreateTime(LocalDateTime.now());
                freightTemplateGoodsFree.setFreightTempId(freightTemplateGoods.getFreightTempId());
                freightTemplateGoodsFree.setDelFlag(DeleteFlag.NO);
            }
            if (freightTemplateGoodsFree != null) {
                freightTemplateGoodsFree.setDestinationArea(StringUtils.join(info.getDestinationArea(), ","));
                freightTemplateGoodsFree.setValuationType(freightTemplateGoods.getValuationType());
                freightTemplateGoodsFree.setDestinationAreaName(StringUtils.join(info.getDestinationAreaName(), ","));
                freightTemplateGoodsFreeRepository.save(freightTemplateGoodsFree);
            }
        });
    }


    /**
     * 校验区域是否有重复
     *
     * @param list 区域
     * @return true:有重复 false:无重复
     */
    private boolean verifyAreaRepetition(List<String> list) {
        Set<String> set = new HashSet<>();
        List<String> strLists = list.stream().map(info -> {
            if (StringUtils.isBlank(info)) {
                return null;
            }
            return Arrays.asList(info.split(","));
        }).filter(Objects::nonNull).flatMap(Collection::stream).map(str -> {
            set.add(str);
            return str;
        }).collect(Collectors.toList());
        return strLists.size() != set.size();
    }

    /***
     * 修改运费模板关联商品的运费模板
     * @param id                       被删除的运费模板主键
     * @param freightTempId            默认运费模板主键
     */
    protected void modifyFreightRelGoods(Long id, Long freightTempId, Long storeId) {
        //修改商品表
        goodsRepository.updateFreightTempId(id, freightTempId);
        //修改审核商品表
        goodsAuditRepository.updateFreightTempId(id, freightTempId);
    }

    /**
     * @description 查询商品详情运费信息
     * @author wur
     * @date: 2022/7/7 11:37
     * @param request
     * @param goodsInfo
     * @param storeVO
     * @return
     */
    public GetFreightInGoodsInfoResponse inGoodsInfoPage(
            GetFreightInGoodsInfoRequest request, GoodsInfo goodsInfo, StoreVO storeVO) {
        // 2.单品运费模板计算
        // 2.1.查询商品运费模板Id
        Optional<Goods> optional = goodsRepository.findById(goodsInfo.getGoodsId());
        if (!optional.isPresent()) {
            return null;
        }

        Goods goods = optional.get();
        FreightTemplateGoods freight = this.queryById(goods.getFreightTempId());
        if (Objects.isNull(freight)) {
            return null;
        }

        if (DefaultFlag.YES.equals(freight.getFreightFreeFlag())) {
            return GetFreightInGoodsInfoResponse.builder()
                    .freightDescribe("免运费")
                    .collectFlag(Boolean.FALSE)
                    .build();
        }

        // 2.2. 匹配是否有包邮
        GetFreightInGoodsInfoResponse freeResponse =
                geFreeFreightTemplate(freight.getFreightTemplateGoodsFrees(), request, goodsInfo);
        // 如果包邮直接返回
        if (Objects.nonNull(freeResponse)) {
            if (freeResponse.getCollectFlag()) {
                // 计算运费
                FreightTemplateGoodsExpress expTemp =
                        this.getExpress(
                                freight.getFreightTemplateGoodsExpresses(),
                                request.getProvinceId(),
                                request.getCityId());
                if (Objects.nonNull(expTemp)) {
                    Map<String, BigDecimal> goodsNum = new HashMap<>();
                    goodsNum.put(request.getGoodsInfoId(), new BigDecimal(request.getNum()));
                    BigDecimal totalFreight =
                            getSingleTemplateFreight(expTemp, Arrays.asList(goodsInfo), goodsNum);
                    if (BigDecimal.ZERO.compareTo(totalFreight) < 0) {
                        String body =
                                String.format("运费%s元，", totalFreight)
                                        + freeResponse.getFreightDescribe();
                        freeResponse.setFreightDescribe(body);
                    }
                }
            }
            return freeResponse;
        }

        // 2.3 匹配非包邮
        return getFreightTemplate(freight.getFreightTemplateGoodsExpresses(), request);
    }

    /**
     * @description 获取免邮模板
     * @author wur
     * @date: 2022/7/6 16:41
     * @param temps 模板信息
     * @param request
     * @param goodsInfo
     * @return 返回空 直接匹配非满免模板 如果返是需要凑单需要查询运费金额
     */
    private GetFreightInGoodsInfoResponse geFreeFreightTemplate(
            List<FreightTemplateGoodsFree> temps,
            GetFreightInGoodsInfoRequest request,
            GoodsInfo goodsInfo) {
        if (CollectionUtils.isEmpty(temps)) {
            return null;
        }
        Optional<FreightTemplateGoodsFree> expOpt =
                temps.stream()
                        .filter(
                                exp ->
                                        matchArea(
                                                exp.getDestinationArea(),
                                                request.getProvinceId(),
                                                request.getCityId()))
                        .findFirst();
        FreightTemplateGoodsFree free = expOpt.isPresent() ? expOpt.get() : null;
        if (Objects.isNull(free)) {
            return null;
        }
        // 默认包邮
        GetFreightInGoodsInfoResponse response = new GetFreightInGoodsInfoResponse();
        response.setFreightTemplateId(free.getFreightTempId());
        response.setFreeId(free.getId());
        response.setFreightTemplateType(DefaultFlag.YES);
        response.setFreightDescribe("免运费");
        response.setCollectFlag(Boolean.FALSE);
        BigDecimal price = request.getPrice().multiply(new BigDecimal(request.getNum()));
        if (ConditionType.MONEY.equals(free.getConditionType())) {
            if (price.compareTo(free.getConditionTwo()) < 0) {
                response.setFreightDescribe(String.format("满%s元免运费", free.getConditionTwo()));
                response.setCollectFlag(Boolean.TRUE);
            }
        } else if (ConditionType.VALUATION.equals(free.getConditionType())) {
            if (ValuationType.NUMBER.equals(free.getValuationType())) {
                // 不满足数量不包邮
                if (free.getConditionOne().compareTo(new BigDecimal(request.getNum())) > 0) {
                    response.setFreightDescribe(String.format("满%s件免运费", free.getConditionOne().intValue()));
                    response.setCollectFlag(Boolean.TRUE);
                }
            } else if (ValuationType.WEIGHT.equals(free.getValuationType())) {
                // 超过体重不包邮
                if (free.getConditionOne().compareTo(goodsInfo.getGoodsWeight()) < 0) {
                    return null;
                }
            } else if (ValuationType.VOLUME.equals(free.getValuationType())) {
                // 超过体积不包邮
                if (free.getConditionOne().compareTo(goodsInfo.getGoodsCubage()) < 0) {
                    return null;
                }
            }
        } else {
            boolean isConditionTwo = price.compareTo(free.getConditionTwo()) >= 0;
            if (ValuationType.NUMBER.equals(free.getValuationType())) {
                // 数量只要有一个条件不满足可去凑单
                if (free.getConditionOne().compareTo(new BigDecimal(request.getNum())) > 0
                        || !isConditionTwo) {
                    response.setFreightDescribe(
                            String.format(
                                    "满%s件且满%s元以上免运费",
                                    free.getConditionOne().intValue(), free.getConditionTwo()));
                    response.setCollectFlag(Boolean.TRUE);
                }
            } else if (ValuationType.WEIGHT.equals(free.getValuationType())) {
                // 在指定重量内切 金额不足则徐要凑单
                if (free.getConditionOne().compareTo(goodsInfo.getGoodsWeight()) < 0) {
                    return null;
                }
                if (!isConditionTwo) {
                    response.setFreightDescribe(
                            String.format(
                                    "在%skg内且满%s元以上免运费",
                                    free.getConditionOne(), free.getConditionTwo()));
                    response.setCollectFlag(Boolean.TRUE);
                }
            } else if (ValuationType.VOLUME.equals(free.getValuationType())) {
                // 在指定体积内切 金额不足则徐要凑单
                if (free.getConditionOne().compareTo(goodsInfo.getGoodsCubage()) < 0) {
                    return null;
                }
                if (!isConditionTwo) {
                    response.setFreightDescribe(
                            String.format(
                                    "在%sm³内且满%s元以上免运费",
                                    free.getConditionOne(), free.getConditionTwo()));
                    response.setCollectFlag(Boolean.TRUE);
                }
            }
        }
        return response;
    }

    /**
     * @description 获取普通模板信息
     * @author wur
     * @date: 2022/7/6 16:42
     * @param temps 运费模板
     * @param request
     * @return
     */
    private GetFreightInGoodsInfoResponse getFreightTemplate(
            List<FreightTemplateGoodsExpress> temps, GetFreightInGoodsInfoRequest request) {
        FreightTemplateGoodsExpress expTemp =
                this.getExpress(temps, request.getProvinceId(), request.getCityId());
        if (Objects.isNull(expTemp)) {
            return null;
        }
        // 如果都是零元则免运费
        if (expTemp.getFreightStartPrice().compareTo(BigDecimal.ZERO) <= 0
                && expTemp.getFreightPlusPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return GetFreightInGoodsInfoResponse.builder()
                    .freightDescribe("免运费")
                    .collectFlag(Boolean.FALSE)
                    .build();
        }
        String freightDescribe = this.getExpressDescribe(expTemp);
        return GetFreightInGoodsInfoResponse.builder()
                .freightTemplateId(expTemp.getFreightTempId())
                .freightDescribe(freightDescribe)
                .collectFlag(Boolean.FALSE)
                .build();
    }

    /**
     * @description 获取规则文案
     * @author wur
     * @date: 2022/7/13 16:17
     * @param expTemp
     * @return
     */
    private String getExpressDescribe(FreightTemplateGoodsExpress expTemp) {
        String unit = "";
        switch (expTemp.getValuationType()) {
            case NUMBER:
                unit = "件";
                break;
            case WEIGHT:
                unit = "kg";
                break;
            case VOLUME:
                unit = "m³";
                break;
            default:
                break;
        }
        return String.format(
                "%s%s内运费%s元，每续%s%s，增加%s元运费",
                Objects.equals(ValuationType.NUMBER,expTemp.getValuationType()) ? expTemp.getFreightStartNum().intValue():expTemp.getFreightStartNum(),
                unit,
                expTemp.getFreightStartPrice(),
                Objects.equals(ValuationType.NUMBER,expTemp.getValuationType()) ? expTemp.getFreightPlusNum().intValue():expTemp.getFreightPlusNum(),
                unit,
                expTemp.getFreightPlusPrice());
    }

    /**
     * @description 获取免邮规则文案
     * @author wur
     * @date: 2022/7/13 16:18
     * @param expTemp
     * @return
     */
    private String getFreeDescribe(FreightTemplateGoodsFree expTemp) {
        switch (expTemp.getConditionType()) {
            case MONEY:
                return String.format("满%s元免运费", expTemp.getConditionTwo());
            case VALUATION:
                switch (expTemp.getValuationType()) {
                    case NUMBER:
                        return String.format("满%s件免运费", expTemp.getConditionOne().intValue());
                    case WEIGHT:
                        return String.format("在%skg内免运费", expTemp.getConditionOne());
                    case VOLUME:
                        return String.format("在%sm³内免运费", expTemp.getConditionOne());
                    default:
                        return "";
                }
            case VALUATIONANDMONEY:
                switch (expTemp.getValuationType()) {
                    case NUMBER:
                        return String.format(
                                "满%s件且满%s元免运费",
                                expTemp.getConditionOne().intValue(), expTemp.getConditionTwo());
                    case WEIGHT:
                        return String.format(
                                "在%skg内且满%s元免运费",
                                expTemp.getConditionOne(), expTemp.getConditionTwo());
                    case VOLUME:
                        return String.format(
                                "在%sm³内且满%s元免运费",
                                expTemp.getConditionOne(), expTemp.getConditionTwo());
                    default:
                        return "";
                }
            default:
                return "";
        }
    }

    /**
     * @description 获取凑单页信息
     * @author wur
     * @date: 2022/7/8 15:15
     * @param request
     * @return
     */
    public CollectPageInfoResponse collectPageInfo(CollectPageInfoRequest request) {
        // 查询模板信息
        FreightTemplateGoods goodsFreight = this.queryById(request.getFreightTemplateId());
        if (Objects.isNull(goodsFreight)
                || CollectionUtils.isEmpty(goodsFreight.getFreightTemplateGoodsFrees())) {
            return CollectPageInfoResponse.builder().build();
        }
        Optional<FreightTemplateGoodsFree> opt =
                goodsFreight.getFreightTemplateGoodsFrees().stream()
                        .filter(free -> free.getId().equals(request.getFreeId()))
                        .findFirst();
        if (!opt.isPresent()) {
            return CollectPageInfoResponse.builder().build();
        }

        // 计算金额
        FreightPackageGoodsPriceVO priceVO =
                super.packageGoodsPrice(
                        request.getFreightGoodsInfoVOList(), request.getCustomer());
        List<GoodsInfoVO> goodsInfoList = priceVO.getGoodsInfoList();
        BigDecimal totalAmount = priceVO.getTotalAmount();
        CollectPageInfoResponse response = new CollectPageInfoResponse();
        Long totalCount =
                request.getFreightGoodsInfoVOList().stream()
                        .map(FreightGoodsInfoVO::getNum)
                        .reduce(0L, Long::sum);
        Map<String, BigDecimal> goodsMap =
                request.getFreightGoodsInfoVOList().stream()
                        .collect(
                                Collectors.toMap(
                                        FreightGoodsInfoVO::getGoodsInfoId,
                                        FreightGoodsInfoVO::getBigNum));
        response.setTotalNum(totalCount);
        response.setTotalAmount(totalAmount);
        response.setFreightGoodsInfoVOList(request.getFreightGoodsInfoVOList());
        response.setFreightFlag(CollectPageFreightFlag.FREE);
        FreightTemplateGoodsFree goodsFree = opt.get();
        if (ConditionType.MONEY.equals(goodsFree.getConditionType())) {
            response.setConditionAmount(goodsFree.getConditionTwo());
            // 不包邮
            if (totalAmount.compareTo(goodsFree.getConditionTwo()) < 0) {
                response.setFreightFlag(CollectPageFreightFlag.COLLECT);
                response.setConditionLack(goodsFree.getConditionTwo().subtract(totalAmount));
                response.setConditionUnit("元");
            }
        } else if (ConditionType.VALUATION.equals(goodsFree.getConditionType())) {
            response.setConditionAmount(goodsFree.getConditionOne());
            if (ValuationType.NUMBER.equals(goodsFree.getValuationType())) {
                // 小于指定件数不包邮
                if (goodsFree.getConditionOne().compareTo(new BigDecimal(totalCount)) > 0) {
                    response.setFreightFlag(CollectPageFreightFlag.COLLECT);
                    response.setConditionLack(
                            goodsFree.getConditionOne().subtract(new BigDecimal(totalCount)));
                    response.setConditionUnit("件");
                }
            } else {
                return null;
            }
        } else {
            response.setConditionAmount(goodsFree.getConditionOne());
            response.setConditionAmountTow(goodsFree.getConditionTwo());
            if (ValuationType.NUMBER.equals(goodsFree.getValuationType())) {
                // 小于指定件数不包邮
                if (goodsFree.getConditionOne().compareTo(new BigDecimal(totalCount)) > 0) {
                    response.setFreightFlag(CollectPageFreightFlag.COLLECT);
                    response.setConditionLack(
                            goodsFree.getConditionOne().subtract(new BigDecimal(totalCount)));
                    response.setConditionUnit("件");
                }
                if (totalAmount.compareTo(goodsFree.getConditionTwo()) < 0) {
                    response.setFreightFlag(CollectPageFreightFlag.COLLECT);
                    response.setConditionLackTow(goodsFree.getConditionTwo().subtract(totalAmount));
                }
            } else if (ValuationType.WEIGHT.equals(goodsFree.getValuationType())) {
                // 计算总重量
                BigDecimal totalWeight =
                        goodsInfoList.stream()
                                .map(
                                        goodsInfo ->
                                                goodsMap.getOrDefault(
                                                                goodsInfo.getGoodsInfoId(),
                                                                BigDecimal.ZERO)
                                                        .multiply(goodsInfo.getGoodsWeight()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                // 在指定条件内并且金额不满足凑单
                if (goodsFree.getConditionOne().compareTo(totalWeight) >= 0) {
                    if (totalAmount.compareTo(goodsFree.getConditionTwo()) < 0) {
                        response.setFreightFlag(CollectPageFreightFlag.COLLECT);
                        response.setConditionAmount(goodsFree.getConditionTwo());
                        response.setConditionLack(
                                goodsFree.getConditionTwo().subtract(totalAmount));
                        response.setConditionUnit("元");
                    }
                } else { // 超出指定重量
                    response.setFreightFlag(CollectPageFreightFlag.OUT);
                    response.setConditionLack(totalWeight.subtract(goodsFree.getConditionOne()));
                    response.setConditionUnit("kg");
                }
            } else if (ValuationType.VOLUME.equals(goodsFree.getValuationType())) {
                // 计算总体积
                BigDecimal totalCubage =
                        goodsInfoList.stream()
                                .map(
                                        goodsInfo ->
                                                goodsMap.getOrDefault(
                                                                goodsInfo.getGoodsInfoId(),
                                                                BigDecimal.ZERO)
                                                        .multiply(goodsInfo.getGoodsCubage()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                // 在指定条件内并且金额不满足凑单
                if (goodsFree.getConditionOne().compareTo(totalCubage) >= 0) {
                    if (totalAmount.compareTo(goodsFree.getConditionTwo()) < 0) {
                        response.setFreightFlag(CollectPageFreightFlag.COLLECT);
                        response.setConditionAmount(goodsFree.getConditionTwo());
                        response.setConditionLack(
                                goodsFree.getConditionTwo().subtract(totalAmount));
                        response.setConditionUnit("元");
                    }
                } else {
                    response.setFreightFlag(CollectPageFreightFlag.OUT);
                    response.setConditionLack(totalCubage.subtract(goodsFree.getConditionOne()));
                    response.setConditionUnit("m³");
                }
            }
        }
        return response;
    }

    /**
     * 计算某个单品模板的运费
     *
     * @param temp 单品运费模板
     * @param goodsInfoVOList 需要计算首件运费的配送地模板id
     * @return 模板的总运费
     */
    private BigDecimal getSingleTemplateFreight(
            FreightTemplateGoodsExpress temp,
            List<GoodsInfo> goodsInfoVOList,
            Map<String, BigDecimal> goodsInfoNum) {
        if (Objects.isNull(temp)) {
            return BigDecimal.ZERO;
        }
        switch (temp.getValuationType()) {
            case NUMBER: // 按件数
                BigDecimal totalNum = goodsInfoVOList.stream()
                        .map(
                                goodsInfo ->
                                        goodsInfoNum
                                                .getOrDefault(
                                                        goodsInfo.getGoodsInfoId(),
                                                        BigDecimal.ZERO))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return getStartAndPlusFreight(totalNum, temp);
            case WEIGHT: // 按重量
                BigDecimal totalWeight =
                        goodsInfoVOList.stream()
                                .map(
                                        goodsInfo ->
                                                goodsInfoNum
                                                        .getOrDefault(
                                                                goodsInfo.getGoodsInfoId(),
                                                                BigDecimal.ZERO)
                                                        .multiply(goodsInfo.getGoodsWeight()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                return getStartAndPlusFreight(totalWeight, temp);
            case VOLUME: // 按体积
                BigDecimal totalCubage =
                        goodsInfoVOList.stream()
                                .map(
                                        goodsInfo ->
                                                goodsInfoNum
                                                        .getOrDefault(
                                                                goodsInfo.getGoodsInfoId(),
                                                                BigDecimal.ZERO)
                                                        .multiply(goodsInfo.getGoodsCubage()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                return getStartAndPlusFreight(totalCubage, temp);
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * @description 匹配免邮规则
     * @author wur
     * @date: 2022/7/13 15:28
     * @param temps
     * @param provId
     * @param cityId
     * @return
     */
    private FreightTemplateGoodsFree getFree(
            List<FreightTemplateGoodsFree> temps, String provId, String cityId) {
        if (CollectionUtils.isEmpty(temps)) {
            return null;
        }
        Optional<FreightTemplateGoodsFree> expOpt =
                temps.stream()
                        .filter(exp -> matchArea(exp.getDestinationArea(), provId, cityId))
                        .findFirst();
        FreightTemplateGoodsFree free = expOpt.isPresent() ? expOpt.get() : null;
        return free;
    }

    /**
     * 根据收货地址匹配运费模板
     *
     * @param temps
     * @param provId
     * @param cityId
     * @return
     */
    private FreightTemplateGoodsExpress getExpress(
            List<FreightTemplateGoodsExpress> temps, String provId, String cityId) {
        Optional<FreightTemplateGoodsExpress> expOpt =
                temps.stream()
                        .filter(exp -> matchArea(exp.getDestinationArea(), provId, cityId))
                        .findFirst();
        FreightTemplateGoodsExpress expTemp =
                expOpt.orElseGet(
                        () ->
                                temps.stream()
                                        .filter(exp -> DefaultFlag.YES.equals(exp.getDefaultFlag()))
                                        .findFirst()
                                        .orElse(null));
        return expTemp;
    }

    /**
     * 计算 首件 + 续件 总费用
     *
     * @param itemCount
     * @param expTemplate
     * @return
     */
    private BigDecimal getStartAndPlusFreight(
            BigDecimal itemCount, FreightTemplateGoodsExpress expTemplate) {
        if (itemCount.compareTo(expTemplate.getFreightStartNum()) <= 0) {
            return expTemplate.getFreightStartPrice(); // 首件数以内,则只算首运费
        } else {
            // 总费用 = 首件费用 + 续件总费用
            BigDecimal sumFreight =
                    expTemplate
                            .getFreightStartPrice()
                            .add(
                                    getPlusFreight(
                                            itemCount.subtract(expTemplate.getFreightStartNum()),
                                            expTemplate));
            return sumFreight;
        }
    }

    /**
     * 计算续件总费用
     *
     * @param itemCount 商品数量
     * @param expTemplate 匹配的运费模板
     * @return 续件总费用
     */
    private BigDecimal getPlusFreight(
            BigDecimal itemCount, FreightTemplateGoodsExpress expTemplate) {
        // 商品数量/续件数量 * 续件金额
        BigDecimal plusFreight =
                itemCount
                        .divide(expTemplate.getFreightPlusNum(), 0, Constants.ZERO)
                        .multiply(expTemplate.getFreightPlusPrice());
        return plusFreight;
    }

    /**
     * @description 购物车 - 运费模板
     * @author wur
     * @date: 2022/7/13 16:16
     * @param goodsInfoList 购物车商品
     * @param storeVO 商家
     * @param address 收货地址
     * @param isProvider 是否是供应商
     * @return
     */
    public List<FreightTemplateCartVO> cartFreightList(
            List<GoodsInfoCartVO> goodsInfoList,
            StoreVO storeVO,
            PlatformAddress address,
            boolean isProvider) {
        // 根据运费模板Id分组
        List<Long> freightTempIdList =
                goodsInfoList.stream()
                        .map(GoodsInfoCartVO::getFreightTempId)
                        .collect(Collectors.toList());
        List<FreightTemplateGoods> templateGoodsList = this.queryAllByIds(freightTempIdList);
        if (CollectionUtils.isEmpty(templateGoodsList)) {
            return null;
        }
        templateGoodsList =
                templateGoodsList.stream()
                        .filter(
                                template ->
                                        DeleteFlag.NO.equals(template.getDelFlag())
                                                && template.getStoreId()
                                                        .equals(storeVO.getStoreId()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(templateGoodsList)) {
            return null;
        }

        Map<Long, List<GoodsInfoCartVO>> skuMap =
                goodsInfoList.stream()
                        .collect(Collectors.groupingBy(GoodsInfoCartVO::getFreightTempId));

        List<FreightTemplateCartVO> cartVOList = new ArrayList<>();
        for (FreightTemplateGoods templateGoods : templateGoodsList) {
            FreightTemplateCartVO cartVO = new FreightTemplateCartVO();
            List<GoodsInfoCartVO> skuList = skuMap.get(templateGoods.getFreightTempId());
            cartVO.setGoodsInfoId(
                    skuList.stream()
                            .map(GoodsInfoCartVO::getGoodsInfoId)
                            .collect(Collectors.toList()));
            if (isProvider) {
                cartVO.setProviderId(storeVO.getStoreId());
            }
            // 验证是否 商家承担运费
            if(DefaultFlag.YES.equals(templateGoods.getFreightFreeFlag())) {
                cartVO.setDescribe("免运费");
                cartVO.setFreeFlag(DeleteFlag.YES);
                cartVOList.add(cartVO);
                continue;
            }
            FreightTemplateGoodsCartVO freightTemplateGoodsCartVO =
                    new FreightTemplateGoodsCartVO();
            freightTemplateGoodsCartVO.setFreightTempId(templateGoods.getFreightTempId());
            // 匹配免运费
            FreightTemplateGoodsFree free =
                    this.getFree(
                            templateGoods.getFreightTemplateGoodsFrees(),
                            address.getProvinceId(),
                            address.getCityId());
            if (Objects.nonNull(free)) {
                FreightTemplateGoodsFreeCartVO freightTemplateGoodsFreeCateVO =
                        new FreightTemplateGoodsFreeCartVO();
                freightTemplateGoodsFreeCateVO.setFreeId(free.getId());
                freightTemplateGoodsFreeCateVO.setValuationType(free.getValuationType());
                freightTemplateGoodsFreeCateVO.setConditionType(free.getConditionType());
                freightTemplateGoodsFreeCateVO.setConditionOne(free.getConditionOne());
                freightTemplateGoodsFreeCateVO.setConditionTwo(free.getConditionTwo());
                freightTemplateGoodsCartVO.setFreightTemplateGoodsFreeCateVO(
                        freightTemplateGoodsFreeCateVO);
                cartVO.setDescribe(getFreeDescribe(free));
                cartVO.setFreightTemplateGoodsCartVO(freightTemplateGoodsCartVO);
            }
            FreightTemplateGoodsExpress express =
                    this.getExpress(
                            templateGoods.getFreightTemplateGoodsExpresses(),
                            address.getProvinceId(),
                            address.getCityId());
            // 判断是不是免邮
            if (express.getFreightStartPrice().compareTo(BigDecimal.ZERO) <= 0
                    && express.getFreightPlusPrice().compareTo(BigDecimal.ZERO) <= 0) {
                if (Objects.isNull(free)) {
                    cartVO.setDescribe("免运费");
                    cartVO.setFreeFlag(DeleteFlag.YES);
                }
            } else {
                FreightTemplateGoodsExpressCartVO freightTemplateGoodsExpressCateVO =
                        new FreightTemplateGoodsExpressCartVO();
                freightTemplateGoodsExpressCateVO.setExpressId(express.getId());
                freightTemplateGoodsExpressCateVO.setValuationType(express.getValuationType());
                freightTemplateGoodsExpressCateVO.setFreightStartNum(
                        express.getFreightStartNum());
                freightTemplateGoodsExpressCateVO.setFreightStartPrice(
                        express.getFreightStartPrice());
                freightTemplateGoodsExpressCateVO.setFreightPlusNum(
                        express.getFreightPlusNum());
                freightTemplateGoodsExpressCateVO.setFreightPlusPrice(
                        express.getFreightPlusPrice());
                freightTemplateGoodsCartVO.setFreightTemplateGoodsExpressCateVO(
                        freightTemplateGoodsExpressCateVO);
                cartVO.setDescribe(getExpressDescribe(express));
                cartVO.setFreightTemplateGoodsCartVO(freightTemplateGoodsCartVO);
            }
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }
}
