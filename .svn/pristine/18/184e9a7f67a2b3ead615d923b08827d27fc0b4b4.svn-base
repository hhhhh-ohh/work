package com.wanmi.sbc.elastic.mqconsumer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddListByActivityIdRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceGenerateRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.coupon.service.EsCouponActivityService;
import com.wanmi.sbc.elastic.customer.model.root.EsStoreEvaluateSum;
import com.wanmi.sbc.elastic.customer.repository.EsStoreEvaluateSumRepository;
import com.wanmi.sbc.elastic.customer.service.EsCustomerDetailService;
import com.wanmi.sbc.elastic.goods.service.EsGoodsElasticServiceInterface;
import com.wanmi.sbc.elastic.goodsevaluate.model.root.EsGoodsEvaluate;
import com.wanmi.sbc.elastic.goodsevaluate.model.root.EsGoodsEvaluateImage;
import com.wanmi.sbc.elastic.goodsevaluate.repository.EsGoodsEvaluateRepository;
import com.wanmi.sbc.elastic.operationlog.model.root.EsOperationLog;
import com.wanmi.sbc.elastic.operationlog.service.EsOperationLogService;
import com.wanmi.sbc.elastic.orderinvoice.service.EsOrderInvoiceService;
import com.wanmi.sbc.elastic.pointsgoods.serivce.EsPointsGoodsService;
import com.wanmi.sbc.elastic.standard.service.EsStandardService;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import com.wanmi.sbc.setting.api.provider.OperationLogProvider;
import com.wanmi.sbc.setting.api.request.OperationLogAddRequest;
import com.wanmi.sbc.setting.api.response.OperationLogAddResponse;
import com.wanmi.sbc.setting.bean.vo.OperationLogVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lvzhenwei
 * @className EsMqConsumerService
 * @description mq消费者消费方法业务处理
 * @date 2021/8/11 2:43 下午
 **/
@Slf4j
@Service
public class EsMqConsumerService {

    @Autowired
    private EsStoreEvaluateSumRepository storeEvaluateSumRepository;

    @Autowired
    private EsGoodsEvaluateRepository esGoodsEvaluateRepository;

    @Autowired
    private EsCustomerDetailService esCustomerDetailService;

    @Autowired
    private EsPointsGoodsService esPointsGoodsService;

    @Autowired
    private EsCouponActivityService esCouponActivityService;

    @Autowired
    private OperationLogProvider operationLogProvider;

    @Autowired
    private EsOperationLogService esOperationLogService;

    @Autowired
    private EsStandardService esStandardService;

    @Autowired
    private EsOrderInvoiceService esOrderInvoiceService;

    @Autowired
    private EsGoodsElasticServiceInterface esGoodsElasticServiceInterface;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @param json
     * @return void
     * @description ES商品评价
     * @author lvzhenwei
     * @date 2021/8/11 2:43 下午
     **/
    public void addGoodsEvaluate(String json) {
        try {
            Object parse = JSONObject.parse(json);
            GoodsEvaluateVO goodsEvaluateVO = JSONObject.parseObject(parse.toString(), GoodsEvaluateVO.class);
            List<GoodsEvaluateImageVO> evaluateImageList = goodsEvaluateVO.getEvaluateImageList();
            List<EsGoodsEvaluateImage> esGoodsEvaluateImages = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(evaluateImageList)) {
                esGoodsEvaluateImages = KsBeanUtil.convertList(evaluateImageList, EsGoodsEvaluateImage.class);
            }
            EsGoodsEvaluate esGoodsEvaluate = EsGoodsEvaluate.builder().build();
            BeanUtils.copyProperties(goodsEvaluateVO, esGoodsEvaluate);
            esGoodsEvaluate.setGoodsEvaluateImages(esGoodsEvaluateImages);
            esGoodsEvaluateRepository.save(esGoodsEvaluate);
            log.info("商品评价添加成功!");
        } catch (Exception e) {
            log.error("商品评价添加异常! param={}", json, e);
        }
    }

    /**
     * @param json
     * @return void
     * @description ES商家评价
     * @author lvzhenwei
     * @date 2021/8/11 2:45 下午
     **/
    public void addStoreEvaluate(String json) {
        try {
            List<EsStoreEvaluateSum> storeEvaluateSum = JSONArray.parseArray(json, EsStoreEvaluateSum.class);
            Integer scoreCycle = storeEvaluateSum.get(0).getScoreCycle();
            log.info("=====商家评价周期{}开始=====", scoreCycle);
//            DeleteByQueryRequest queryRequest = new DeleteByQueryRequest(EsConstants.STORE_EVALUATE_SUM);
//            queryRequest.setQuery(new TermQueryBuilder("scoreCycle", scoreCycle));
//            BulkByScrollResponse bulkByScrollResponse = elasticsearchTemplate.deleteByQuery(queryRequest, RequestOptions.DEFAULT);
//            List<BulkItemResponse.Failure> bulkFailures = bulkByScrollResponse.getBulkFailures();
//            if (CollectionUtils.isNotEmpty(bulkFailures)) {
//                bulkFailures.forEach(err -> log.error("商家评价周期{}，删除ES异常{}", scoreCycle ,err.getMessage()));
//                return;
//            }
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            ByQueryResponse bulkByScrollResponse = elasticsearchTemplate.delete(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("scoreCycle").value(scoreCycle))).build(), EsStoreEvaluateSum.class);
            if (CollectionUtils.isNotEmpty(bulkByScrollResponse.getFailures())){
                bulkByScrollResponse.getFailures().forEach(f -> log.error("商家评价周期{}，删除ES异常",
                        scoreCycle, f.getCause()));
            }
            //storeEvaluateSumRepository.deleteAll();
            storeEvaluateSumRepository.saveAll(storeEvaluateSum);
            log.info("=====商家评价周期{}结束=====", scoreCycle);
        } catch (Exception e) {
            log.error("商家评价添加异常! param={}", json, e);
        }
    }

    /**
     * @param json
     * @return void
     * @description 会员注册成功，发送订单支付MQ消息，同步ES
     * @author lvzhenwei
     * @date 2021/8/11 4:10 下午
     **/
    public void customerRegister(String json) {
        try {
            EsCustomerDetailDTO request = JSONObject.parseObject(JSON.parse(json).toString(), EsCustomerDetailDTO.class);
            esCustomerDetailService.save(request);
            log.info("========会员注册，保存会员ES信息成功=============");
        } catch (Exception e) {
            log.error("ES会员注册，生成ES数据发生异常! param={}", json, e);
        }
    }

    /**
     * @description 积分商品-修改上下架同步es
     * @author  lvzhenwei
     * @date 2021/8/11 4:28 下午
     * @param json
     * @return void
     **/
    public void pointsGoodsModifyAdded(String json){
        try {
            JSONObject object = JSONObject.parseObject(json);
            esPointsGoodsService.modifyAddedFlag(object.getJSONArray("goodsIds").toJavaList(String.class), object.getInteger("addedFlag"));
            log.info("ES积分商品批量修改上下架成功");
        } catch (Exception e) {
            log.error("ES积分商品批量修改上下架发生异常! param={}", json, e);
        }
    }

    /**
     * @description 新增积分兑换券，同步ES
     * @author  lvzhenwei
     * @date 2021/8/11 4:38 下午
     * @param json
     * @return void
     **/
    public void couponAddPointsCoupon(String json) {
        try {
            EsCouponActivityAddListByActivityIdRequest request = JSONObject.parseObject(JSON.parse(json).toString(), EsCouponActivityAddListByActivityIdRequest.class);
            esCouponActivityService.saveAllById(request);
            log.info("========新增积分兑换券，同步ES信息成功=============");
        } catch (Exception e) {
            log.error("======新增积分兑换券，同步ES数据发生异常! param={}", json, e);
        }
    }

    /**
     * @description 商品-更新店铺信息,同步es
     * @author  lvzhenwei
     * @date 2021/8/11 4:52 下午
     * @param json
     * @return void
     **/
    public void goodsModifyStoreState(String json) {
        try {
            EsGoodsStoreInfoModifyRequest request = JSONObject.parseObject(json, EsGoodsStoreInfoModifyRequest.class);
            esGoodsElasticServiceInterface.updateStoreStateByStoreId(request);
            log.info("ES商品更新店铺信息成功");
        } catch (Exception e) {
            log.error("ES商品更新店铺信息异常! param={}", json, e);
        }
    }

    /**
     * @description 记录操作日志mq消费方法
     * @author  lvzhenwei
     * @date 2021/8/11 5:03 下午
     * @param msg
     * @return void
     **/
    public void operateLogAdd(String msg) {
        OperationLogAddRequest addRequest = JSONObject.parseObject(msg, OperationLogAddRequest.class);
        if (Objects.isNull(addRequest.getCompanyInfoId())) {
            addRequest.setCompanyInfoId(0L);
        }
        OperationLogAddResponse logAddResponse = operationLogProvider.add(addRequest).getContext();
        OperationLogVO operationLogVO = logAddResponse.getOperationLogVO();
        EsOperationLog esOperationLog = new EsOperationLog();
        BeanUtils.copyProperties(operationLogVO, esOperationLog);
        esOperationLogService.add(esOperationLog);
        log.info("操作日志添加成功！");
    }

    /**
     * @description 修改会员基本信息成功，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 10:38 上午
     * @param json
     * @return void
     **/
    public void customerModifyBaseInfo(String json) {
        try {
            EsCustomerDetailDTO request = JSONObject.parseObject(JSON.parse(json).toString(), EsCustomerDetailDTO.class);
            esCustomerDetailService.modifyBaseInfo(request);
            log.info("========修改会员基本信息，同步ES会员信息成功=============");
        } catch (Exception e) {
            log.error("修改会员基本信息，同步ES数据发生异常! param={}", json, e);
        }
    }

    /**
     * @description 修改会员账号成功，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 10:50 上午
     * @param json
     * @return void
     **/
    public void modifyCustomerAccount(String json) {
        try {
            EsCustomerDetailDTO request = JSONObject.parseObject(JSON.parse(json).toString(), EsCustomerDetailDTO.class);
            esCustomerDetailService.modifyCustomerAccount(request);
            log.info("========修改会员账号，同步ES会员信息成功=============");
        } catch (Exception e) {
            log.error("修改会员账号，同步ES数据发生异常! param={}", json, e);
        }
    }

    /**
     * @description elastic模块-标品库-初始化
     * @author  lvzhenwei
     * @date 2021/8/12 1:38 下午
     * @param json
     * @return void
     **/
    public void esStandardInit(String json) {
        try {
            EsStandardInitRequest request = JSONObject.parseObject(json, EsStandardInitRequest.class);
            esStandardService.init(request);
            log.info("ES商品库更新成功");
        } catch (Exception e) {
            log.error("ES商品库更新发生异常! param={}", json, e);
        }
    }

    /**
     * @description elastic模块-商品-初始化
     * @author  lvzhenwei
     * @date 2021/8/12 1:53 下午
     * @param json
     * @return void
     **/
    public void esGoodsInit(String json) {
        try {
            log.info("ES商品更新开始");
            EsGoodsInfoRequest request = JSONObject.parseObject(json, EsGoodsInfoRequest.class);
            esGoodsElasticServiceInterface.initEsGoods(request);
            log.info("ES商品更新成功");
        } catch (Exception e) {
            log.error("ES商品更新发生异常! param={}", json.substring(0, 50), e);
        }
    }

    /**
     * @description elastic模块-积分商品-增加销量
     * @author  lvzhenwei
     * @date 2021/8/12 3:30 下午
     * @param json
     * @return void
     **/
    public void pointsGoodsAddSales(String json) {
        try {
            JSONObject object = JSONObject.parseObject(json);
            esPointsGoodsService.addSales(object.getString("pointsGoodsId"), object.getLong("sales"));
            log.info("ES积分商品增加销量成功");
        } catch (Exception e) {
            log.error("ES积分商品增加销量发生异常! param={}", json, e);
        }
    }

    /**
     * @description 更新会员是否分销员字段，发送订单支付MQ消息，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 3:44 下午
     * @param json
     * @return void
     **/
    public void modifyCustomerIsDistributor(String json) {
        try {
            EsCustomerDetailDTO request = JSONObject.parseObject(JSON.parse(json).toString(), EsCustomerDetailDTO.class);
            esCustomerDetailService.updateCustomerToDistributor(request);
            log.info("========修改会员是否分销员字段，同步ES会员信息成功=============");
        } catch (Exception e) {
            log.error("修改会员是否分销员字段，同步ES数据发生异常! param={}", json, e);
        }
    }

    /**
     * @description 新增会员等級，同步ES
     * @author  lvzhenwei
     * @date 2021/8/12 4:17 下午
     * @param json
     * @return void
     **/
    public void customerLevelDetailAdd(String json) {
        try {
            EsCustomerDetailDTO request = JSONObject.parseObject(json, EsCustomerDetailDTO.class);
            esCustomerDetailService.updateCustomerLevelAvailable(request);
            log.info("========修改会员等级字段，同步ES会员信息成功=============");
        } catch (Exception e) {
            log.error("修改会员等级字段，同步ES数据发生异常! param={}", json, e);
        }
    }

    /**
     * @description 订单开票数据新增同步es
     * @author  lvzhenwei
     * @date 2021/8/12 4:45 下午
     * @param json
     * @return void
     **/
    public void addOrderInvoice(String json) {
        try {
            EsOrderInvoiceGenerateRequest request = JSONObject.parseObject(json, EsOrderInvoiceGenerateRequest.class);
            esOrderInvoiceService.addEsOrderInvoice(request);
            log.info("ES保存订单开票，成功");
        } catch (Exception e) {
            log.error("ES保存订单开票, 发生异常！param={}", json, e);
        }
    }

    /**
     * @description 同步订单状态到开票的es数据中
     * @author  lvzhenwei
     * @date 2021/8/12 4:56 下午
     * @param json
     * @return void
     **/
    public void updateFlowStateOrderInvoice(String json) {
        try {
            EsOrderInvoiceGenerateRequest request = JSONObject.parseObject(json, EsOrderInvoiceGenerateRequest.class);
            log.info(
                    "ES同步开票的订单状态orderInvoiceId={}, flowState={}",
                    request.getOrderInvoiceId(),
                    request.getFlowState());
            esOrderInvoiceService.updateFlowStateOrderInvoice(request);
            log.error("ES同步开票的订单状态，成功，param={}", json);
        } catch (Exception e) {
            log.error("ES同步开票的订单状态, 发生异常！param={}", json, e);
        }
    }

}
