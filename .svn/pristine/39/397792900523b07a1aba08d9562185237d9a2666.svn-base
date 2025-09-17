package com.wanmi.sbc.order.quickorder;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;
import com.wanmi.sbc.order.api.request.purchase.CartGoodsInfoRequest;
import com.wanmi.sbc.order.api.request.purchase.PurchaseInfoRequest;
import com.wanmi.sbc.order.api.request.quickorder.QuickOrderGoodsListRequest;
import com.wanmi.sbc.order.api.response.purchase.GoodsCartListResponse;
import com.wanmi.sbc.order.bean.dto.QuickOrderGoodsQueryDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.GoodsInfoCartSimpleVO;
import com.wanmi.sbc.order.purchase.Purchase;
import com.wanmi.sbc.order.purchase.service.CartAdaptor;
import com.wanmi.sbc.order.purchase.service.CartInterface;
import com.wanmi.sbc.order.util.mapper.GoodsMapper;
import com.wanmi.sbc.order.util.mapper.StoreMapper;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className CartAdaptor
 * @description TODO
 * @date 2021/12/27 3:42 下午
 */
@Service
public class QuickOrderService {

    @Autowired GoodsMapper goodsMapper;

    @Autowired StoreMapper storeMapper;

    private CartInterface cartInterface;

    @Autowired
    private CartAdaptor cartAdaptor;

    @Resource
    protected GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    /***
     * set方法注入，为了方便子类注入自己的实现类
     * 不可改为属性注入
     * @param cartInterface 购物车实现类
     */
    @Autowired
    public void setCartInterface(CartInterface cartInterface) {
        this.cartInterface = cartInterface;
    }

    /**
     * @description 查询快速下单商品列表
     * @author  edz
     * @date 2023/6/5 16:59
     **/
    public GoodsCartListResponse getQuickOrderGoods(QuickOrderGoodsListRequest request) {
        //1.判断快速下单开关是否打开
        TradeConfigGetByTypeRequest configQueryRequest = new TradeConfigGetByTypeRequest();
        configQueryRequest.setConfigType(ConfigType.ORDER_SETTING_QUICK_ORDER);
        TradeConfigGetByTypeResponse auditConfigListResponse = auditQueryProvider.getTradeConfigByType(configQueryRequest).getContext();
        if(Objects.isNull(auditConfigListResponse) ||
                !Integer.valueOf(1).equals(auditConfigListResponse.getStatus())){
            //快速下单开关未打开，抛异常码
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050174);
        }

        //数量负数处理, 订货号转大写, 数量0转1
        request.getOrderGoodsNoList().stream().forEach(o -> {
            if(Objects.nonNull(o.getNum()) && o.getNum() < 0){
                o.setQuickOrderNo(o.getQuickOrderNo() + " " + o.getNum());
                o.setNum(null);
            } else if(Long.valueOf(0).equals(o.getNum())){
                o.setNum(1L);
            }
            o.setQuickOrderNo(o.getQuickOrderNo().toUpperCase());
        });

        CustomerVO customer = request.getCustomer();
        GoodsCartListResponse response = new GoodsCartListResponse();

        //2.根据前端订货号查询商品
        List<Purchase> purchaseList = null;
        List<String> goodsInfoIds = null;
        String customerId = Objects.nonNull(customer) ? customer.getCustomerId() : null;
        //前端传参的所有订货号和数量
        List<QuickOrderGoodsQueryDTO> orderGoodsNoList = request.getOrderGoodsNoList();
        //提取所有订货号
        List<String> allQuickOrderNos = orderGoodsNoList.stream().map(QuickOrderGoodsQueryDTO::getQuickOrderNo).distinct().collect(Collectors.toList());
        List<String> whiteSpaceQuickOrderNos = orderGoodsNoList.stream().map(q -> q.getQuickOrderNo().trim()).distinct().collect(Collectors.toList());

        //根据订货号(订货号、订货号+数量)查询商品
        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setAddedFlag(AddedFlag.YES.toValue());
        esGoodsInfoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        esGoodsInfoQueryRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        esGoodsInfoQueryRequest.setStoreState(StoreState.OPENING.toValue());
        esGoodsInfoQueryRequest.setIsOutOfStockShow(0);
        List<EsGoodsInfoVO> esGoodsInfoVOListAll = new ArrayList<>();
        if(request.getSearchType() == 0){
            //批量搜索
            esGoodsInfoQueryRequest.setQuickOrderNos(whiteSpaceQuickOrderNos);
            esGoodsInfoQueryRequest.setPageSize(whiteSpaceQuickOrderNos.size());
            esGoodsInfoVOListAll =
                    esGoodsInfoElasticQueryProvider.getEsBaseInfoByParams(esGoodsInfoQueryRequest).getContext().getEsGoodsInfoVOList();
            if(esGoodsInfoVOListAll.size() != allQuickOrderNos.stream().distinct().collect(Collectors.toList()).size()){
                List<String> otherQueryQuickOrderNos = esGoodsInfoVOListAll.stream().map(e ->
                        e.getGoodsInfo().getQuickOrderNo()).collect(Collectors.toList());
                //拼接：订货号+数量查询
                List<String> noNums = orderGoodsNoList.stream()
                        .filter(o -> Objects.nonNull(o.getNum())
                                && !otherQueryQuickOrderNos.contains(o.getQuickOrderNo())
                                && !otherQueryQuickOrderNos.contains(o.getQuickOrderNo() + " " + o.getNum()))
                        .map(o -> o.getQuickOrderNo() + " " + o.getNum()).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(noNums)){
                    esGoodsInfoQueryRequest.setQuickOrderNos(noNums);
                    List<EsGoodsInfoVO> otherList =
                            esGoodsInfoElasticQueryProvider.getEsBaseInfoByParams(esGoodsInfoQueryRequest).getContext().getEsGoodsInfoVOList();
                    if(CollectionUtils.isNotEmpty(otherList)){
                        esGoodsInfoVOListAll.addAll(otherList);
                    }
                }
            }
        } else {
            Integer pageSize = 10;
            String likeQuickOrderNo = allQuickOrderNos.get(0);
            if(likeQuickOrderNo.length() > 30){
                likeQuickOrderNo = likeQuickOrderNo.substring(0, 30);
            }
            //精准查询
            esGoodsInfoQueryRequest.setQuickOrderNo(likeQuickOrderNo);
            esGoodsInfoQueryRequest.setPageNum(0);
            esGoodsInfoQueryRequest.setPageSize(pageSize);
            List<EsGoodsInfoVO> esGoodsInfoVOList =
                    esGoodsInfoElasticQueryProvider.skuPage(esGoodsInfoQueryRequest).getContext().getEsGoodsInfoPage().getContent();
            if(CollectionUtils.isNotEmpty(esGoodsInfoVOList)){
                esGoodsInfoVOListAll.addAll(esGoodsInfoVOList);
            }
            //模糊查询
            esGoodsInfoQueryRequest.setQuickOrderNo(null);
            esGoodsInfoQueryRequest.setLikeQuickOrderNo(likeQuickOrderNo);
            esGoodsInfoQueryRequest.setPageNum(0);
            esGoodsInfoQueryRequest.setPageSize(pageSize);
            esGoodsInfoQueryRequest.putSort("createTime", "desc");
            List<EsGoodsInfoVO> esGoodsInfoVOListLike =
                    esGoodsInfoElasticQueryProvider.skuPage(esGoodsInfoQueryRequest).getContext().getEsGoodsInfoPage().getContent();
            if(CollectionUtils.isNotEmpty(esGoodsInfoVOListLike)){
                esGoodsInfoVOListLike = esGoodsInfoVOListLike.stream().filter(e -> !allQuickOrderNos.get(0).equals(e.getGoodsInfo().getQuickOrderNo())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(esGoodsInfoVOListLike)){
                    esGoodsInfoVOListAll.addAll(esGoodsInfoVOListLike);
                }
            }
        }

        //3.查询商品为空，返回错误订货号列表
        if(CollectionUtils.isEmpty(esGoodsInfoVOListAll)){
            GoodsCartListResponse goodsCartListResponse = new GoodsCartListResponse();
            if(Integer.valueOf(0).equals(request.getSearchType())){
                Map<String, Long> quickOrderNumMap = IteratorUtils.groupBy(orderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
                List<GoodsInfoCartSimpleVO> goodsInfos = quickOrderNumMap.entrySet().stream().map(o -> {
                    GoodsInfoCartSimpleVO goodsInfoCartSimpleVO = new GoodsInfoCartSimpleVO();
                    goodsInfoCartSimpleVO.setQuickOrderNo(o.getKey());
                    goodsInfoCartSimpleVO.setErrorQuickOrderNo(Boolean.TRUE);
                    goodsInfoCartSimpleVO.setBuyCount(o.getValue());
                    return goodsInfoCartSimpleVO;
                }).collect(Collectors.toList());
                goodsCartListResponse.setGoodsInfos(goodsInfos);
            }
            return goodsCartListResponse;
        }

        //提取商品id
        goodsInfoIds = esGoodsInfoVOListAll.stream().map(e -> e.getGoodsInfo().getGoodsInfoId()).distinct().collect(Collectors.toList());
        if (Objects.nonNull(customer)) {
            List<QuickOrderGoodsQueryDTO> newOrderGoodsNoList = orderGoodsNoList.stream().map(o -> {
                QuickOrderGoodsQueryDTO oq = QuickOrderGoodsQueryDTO.builder().quickOrderNo(o.getQuickOrderNo()).num(o.getNum()).build();
                return oq;
            }).collect(Collectors.toList());
            purchaseList = this.wrapperPurchase(newOrderGoodsNoList, esGoodsInfoVOListAll, request);
            goodsInfoIds = purchaseList.stream().map(e -> e.getGoodsInfoId()).collect(Collectors.toList());
            response.setPointsAvailable(customer.getPointsAvailable());
        }

        //验证商品信息是否为空, 为空返回错误订货号列表
        if(CollectionUtils.isEmpty(goodsInfoIds)) {
            GoodsCartListResponse goodsCartListResponse = new GoodsCartListResponse();
            if(Integer.valueOf(0).equals(request.getSearchType())){
                Map<String, Long> quickOrderNumMap = IteratorUtils.groupBy(orderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
                List<GoodsInfoCartSimpleVO> goodsInfos = quickOrderNumMap.entrySet().stream().map(o -> {
                    GoodsInfoCartSimpleVO goodsInfoCartSimpleVO = new GoodsInfoCartSimpleVO();
                    goodsInfoCartSimpleVO.setQuickOrderNo(o.getKey());
                    goodsInfoCartSimpleVO.setErrorQuickOrderNo(Boolean.TRUE);
                    goodsInfoCartSimpleVO.setBuyCount(o.getValue());
                    return goodsInfoCartSimpleVO;
                }).collect(Collectors.toList());
                goodsCartListResponse.setGoodsInfos(goodsInfos);
            }
            return goodsCartListResponse;
        }

        if (Objects.nonNull(customer)) {
            response.setPointsAvailable(customer.getPointsAvailable());
        }

        //4.以下逻辑来自PC查询购物车接口
        List<StoreVO> storeVOS = cartInterface.getStoreInfoBeforeGoodsInfo(PurchaseInfoRequest.builder().build());

        // 获取商品信息
        List<GoodsInfoCartVO> goodsInfoCartVOList =
                cartInterface.getGoodsInfo(goodsInfoIds, customer, request.getStoreId());

        //PC不展示周期购商品
        if (TerminalSource.PC.equals(request.getTerminalSource())) {
            List<String> cycleIds = goodsInfoCartVOList.stream()
                    .filter(goodsInfoCartVO -> Constants.yes.equals(goodsInfoCartVO.getIsBuyCycle()))
                    .map(GoodsInfoCartVO::getGoodsInfoId).collect(Collectors.toList());
            goodsInfoCartVOList = goodsInfoCartVOList.stream()
                    .filter(goodsInfoCartVO -> !cycleIds.contains(goodsInfoCartVO.getGoodsInfoId()))
                    .collect(Collectors.toList());
            if (purchaseList != null) {
                purchaseList = purchaseList.stream()
                        .filter(purchase -> !cycleIds.contains(purchase.getGoodsInfoId()))
                        .collect(Collectors.toList());
            }
        }

        if (CollectionUtils.isEmpty(goodsInfoCartVOList)) {
            GoodsCartListResponse goodsCartListResponse = new GoodsCartListResponse();
            if(Integer.valueOf(0).equals(request.getSearchType())){
                Map<String, Long> quickOrderNumMap = IteratorUtils.groupBy(orderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
                List<GoodsInfoCartSimpleVO> goodsInfos = quickOrderNumMap.entrySet().stream().map(o -> {
                    GoodsInfoCartSimpleVO goodsInfoCartSimpleVO = new GoodsInfoCartSimpleVO();
                    goodsInfoCartSimpleVO.setQuickOrderNo(o.getKey());
                    goodsInfoCartSimpleVO.setErrorQuickOrderNo(Boolean.TRUE);
                    goodsInfoCartSimpleVO.setBuyCount(o.getValue());
                    return goodsInfoCartSimpleVO;
                }).collect(Collectors.toList());
                goodsCartListResponse.setGoodsInfos(goodsInfos);
            }
            return goodsCartListResponse;
        }
        // 获取店铺信息，如果是SBC则上面方法获取不到店铺信息
        // 在此处查询店铺
        if(WmCollectionUtils.isEmpty(storeVOS)) {
            storeVOS = cartInterface.getStoreInfo(goodsInfoCartVOList, purchaseList);
        }

        //设置信息校验
        goodsInfoCartVOList = cartInterface.checkSetting(goodsInfoCartVOList, customer, storeVOS, request.getInviteeId());

        //设置库存
        goodsInfoCartVOList = cartInterface.setStock(goodsInfoCartVOList, request.getAddress(), request.getStoreId());

        List<CartGoodsInfoRequest> cartGoodsInfoRequests = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(purchaseList)){
            //登录
            for(Purchase p: purchaseList){
                cartGoodsInfoRequests.add(CartGoodsInfoRequest.builder().goodsInfoId(p.getGoodsInfoId()).buyCount(p.getGoodsNum()).build());
            }
        } else {
            List<QuickOrderGoodsQueryDTO> whiteSpaceOrderGoodsNoList = orderGoodsNoList.stream().map(o -> {
                QuickOrderGoodsQueryDTO oq = QuickOrderGoodsQueryDTO.builder().quickOrderNo(o.getQuickOrderNo().trim()).num(o.getNum()).build();
                return oq;
            }).collect(Collectors.toList());
            //订货号聚合
            Map<String, Long> quickOrderNumMap = IteratorUtils.groupBy(whiteSpaceOrderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
            //拼接数量的订货号聚合
            List<QuickOrderGoodsQueryDTO> newOrderGoodsNoList = orderGoodsNoList.stream().map(o -> {
                QuickOrderGoodsQueryDTO oq = QuickOrderGoodsQueryDTO.builder().quickOrderNo(o.getQuickOrderNo()).build();
                if(Objects.nonNull(o.getNum())){
                    oq.setQuickOrderNo(o.getQuickOrderNo() + " " + o.getNum());
                    oq.setNum(1L);
                    return oq;
                }
                return null;
            }).collect(Collectors.toList());
            newOrderGoodsNoList = newOrderGoodsNoList.stream().filter(n -> Objects.nonNull(n)).collect(Collectors.toList());
            Map<String, Long> quickOrderNumMap2 = IteratorUtils.groupBy(newOrderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
            if(CollectionUtils.isNotEmpty(newOrderGoodsNoList)){
                IteratorUtils.zip(quickOrderNumMap.entrySet(), quickOrderNumMap2.entrySet(),
                        (goods, no) ->
                                goods.getKey().equals(no.getKey())
                        ,
                        (goods, no) -> {
                            goods.setValue((Objects.nonNull(goods.getValue()) ? goods.getValue() : 1) + no.getValue());
                        });
            }

            cartGoodsInfoRequests = goodsInfoCartVOList.stream().map(g -> {
                CartGoodsInfoRequest cartGoodsInfoRequest = new CartGoodsInfoRequest();
                cartGoodsInfoRequest.setGoodsInfoId(g.getGoodsInfoId());
                cartGoodsInfoRequest.setBuyCount(quickOrderNumMap.getOrDefault(g.getQuickOrderNo().trim(), quickOrderNumMap2.getOrDefault(g.getQuickOrderNo(), 1L)));
                return cartGoodsInfoRequest;
            }).collect(Collectors.toList());
        }
        //获取营销
        goodsInfoCartVOList =
                cartInterface.getMarketing(goodsInfoCartVOList, customer, request.getStoreId(), cartGoodsInfoRequests);

        //设置购买数量 未登录用户无需设置购买量
        if(Objects.nonNull(purchaseList)) {
            goodsInfoCartVOList = cartInterface.setBuyCount(goodsInfoCartVOList, purchaseList);
        } else {
            Map<String,Long> skuNumMap = cartGoodsInfoRequests.stream().collect(Collectors.toMap(
                    CartGoodsInfoRequest::getGoodsInfoId,
                    CartGoodsInfoRequest::getBuyCount));
            goodsInfoCartVOList.forEach(
                    goodsInfoCartVO -> {
                            goodsInfoCartVO.setBuyCount(skuNumMap.get(goodsInfoCartVO.getGoodsInfoId()));
                        });
        }

        //设置营销选择
        List<GoodsMarketingVO> goodsMarketingVOS =
                cartInterface.setGoodsMarketing(goodsInfoCartVOList, customerId, 1);

        if (CollectionUtils.isNotEmpty(purchaseList)) {
            //设置降价金额
            cartInterface.populateReductionPrice(goodsInfoCartVOList, purchaseList, customer);
        }

        //后置处理逻辑
        goodsInfoCartVOList = cartInterface.afterProcess(goodsInfoCartVOList, customer);

        //数量大于库存，取库存
        goodsInfoCartVOList = goodsInfoCartVOList.stream().map(g -> {
            if(g.getBuyCount() > g.getStock()){
                g.setBuyCount(g.getStock());
            }
            return g;
        }).collect(Collectors.toList());

        //组装返回参数
        response.setStores(cartInterface.perfectStore(goodsInfoCartVOList, storeMapper.storeVOsToMiniStoreVOs(storeVOS)));
        response.setGoodsMarketings(goodsMarketingVOS);
        response.setMarketingDetail(cartAdaptor.getMarketingDetail(goodsInfoCartVOList));
        response.setGoodsInfos(
                goodsMapper.goodsInfoCartVOsToGoodsInfoCartSimpleVOs(goodsInfoCartVOList));
        response.setFreightCartVOS(cartInterface.setFreight(goodsInfoCartVOList, storeVOS, request.getAddress()));
        //设置商品属性
        IteratorUtils.zip(response.getGoodsInfos(), esGoodsInfoVOListAll
                , (chart, quick) ->
                        quick.getGoodsInfo().getGoodsInfoId().equals(chart.getGoodsInfoId())
                , (chart, quick) -> {
                    chart.setGoodsPropRelNests(quick.getGoodsPropRelNests());
                });
        //批量查询时，入参订货号和查询商品数量不一致，补充错误订货号商品
        if(Integer.valueOf(0).equals(request.getSearchType()) && request.getOrderGoodsNoList().size() != response.getGoodsInfos().size()){
            List<String> exists = response.getGoodsInfos().stream().map(GoodsInfoCartSimpleVO::getQuickOrderNo).collect(Collectors.toList());
            List<String> whiterSpaceExists = response.getGoodsInfos().stream().map(g -> g.getQuickOrderNo().trim()).collect(Collectors.toList());
            List<QuickOrderGoodsQueryDTO> filterOrderGoodsNoList = request.getOrderGoodsNoList().stream()
                    .filter(o -> !exists.contains(o.getQuickOrderNo().trim()) && !whiterSpaceExists.contains(o.getQuickOrderNo().trim()) && !exists.contains(o.getQuickOrderNo() + " " + o.getNum()))
                    .collect(Collectors.toList());
            Map<String, Long> quickOrderNumMap = IteratorUtils.groupBy(filterOrderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
            quickOrderNumMap.entrySet().stream().forEach(o -> {
                GoodsInfoCartSimpleVO goodsInfoCartSimpleVO = new GoodsInfoCartSimpleVO();
                goodsInfoCartSimpleVO.setQuickOrderNo(o.getKey());
                goodsInfoCartSimpleVO.setErrorQuickOrderNo(Boolean.TRUE);
                goodsInfoCartSimpleVO.setBuyCount(o.getValue());
                response.getGoodsInfos().add(goodsInfoCartSimpleVO);
            });
        }
        return response;
    }

    /**
     * @description 商品数据转购物车数据结构
     * @author  edz
     * @date 2023/6/5 16:53
     * @param
     * @return
     **/
    public List<Purchase> wrapperPurchase(List<QuickOrderGoodsQueryDTO> orderGoodsNoList, List<EsGoodsInfoVO> quickOrderGoods, QuickOrderGoodsListRequest request){
        List<Purchase> purchaseList = new ArrayList<>();
        List<QuickOrderGoodsQueryDTO> whiteSapceOrderGoodsNoList = orderGoodsNoList.stream().map(o -> {
            QuickOrderGoodsQueryDTO oq = QuickOrderGoodsQueryDTO.builder().quickOrderNo(o.getQuickOrderNo().trim()).num(o.getNum()).build();
            return oq;
        }).collect(Collectors.toList());
        //订货号聚合
        Map<String, Long> quickOrderNumMap = IteratorUtils.groupBy(whiteSapceOrderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
        //拼接数量的订货号聚合
        List<QuickOrderGoodsQueryDTO> newOrderGoodsNoList = orderGoodsNoList.stream().map(o -> {
            QuickOrderGoodsQueryDTO oq = QuickOrderGoodsQueryDTO.builder().quickOrderNo(o.getQuickOrderNo()).build();
            if(Objects.nonNull(o.getNum())){
                oq.setQuickOrderNo(o.getQuickOrderNo() + " " + o.getNum());
                oq.setNum(1L);
                return oq;
            }
            return null;
        }).collect(Collectors.toList());
        newOrderGoodsNoList = newOrderGoodsNoList.stream().filter(n -> Objects.nonNull(n)).collect(Collectors.toList());
        Map<String, Long> quickOrderNumMap2 = IteratorUtils.groupBy(newOrderGoodsNoList, QuickOrderGoodsQueryDTO::getQuickOrderNo).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToLong(q -> Objects.nonNull(q.getNum()) ? q.getNum() : 1).sum()));
        if(CollectionUtils.isNotEmpty(newOrderGoodsNoList)){
            IteratorUtils.zip(quickOrderNumMap.entrySet(), quickOrderNumMap2.entrySet(),
                    (goods, no) ->
                            goods.getKey().equals(no.getKey())
                    ,
                    (goods, no) -> {
                        goods.setValue((Objects.nonNull(goods.getValue()) ? goods.getValue() : 1) + no.getValue());
                    });
        }
        String customerId = Objects.nonNull(request.getCustomer()) ? request.getCustomer().getCustomerId() : null;
        for (EsGoodsInfoVO esGoodsInfoVO : quickOrderGoods) {
            Purchase purchase = new Purchase();
            purchase.setGoodsInfoId(esGoodsInfoVO.getGoodsInfo().getGoodsInfoId());
            purchase.setGoodsNum(quickOrderNumMap.getOrDefault(esGoodsInfoVO.getGoodsInfo().getQuickOrderNo().trim(), quickOrderNumMap2.getOrDefault(esGoodsInfoVO.getGoodsInfo().getQuickOrderNo(), 1L)));
            purchase.setStoreId(esGoodsInfoVO.getGoodsInfo().getStoreId());
            purchase.setCustomerId(customerId);
            purchase.setGoodsId(esGoodsInfoVO.getGoodsId());
            purchase.setCompanyInfoId(esGoodsInfoVO.getGoodsInfo().getCompanyInfoId());
            purchase.setCateId(esGoodsInfoVO.getGoodsCate().getCateId());
            purchase.setTerminalSource(request.getTerminalSource());
            purchase.setPluginType(esGoodsInfoVO.getGoodsInfo().getPluginType());
            purchase.setFirstPurchasePrice(esGoodsInfoVO.getGoodsInfo().getMarketPrice());
            purchase.setCreateTime(LocalDateTime.now());
            purchaseList.add(purchase);
        }
        return purchaseList;
    }
}
