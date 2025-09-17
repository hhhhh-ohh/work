package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.provider.storeevaluate.StoreEvaluateQueryProvider;
import com.wanmi.sbc.customer.api.provider.storeevaluatesum.StoreEvaluateSumQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsListRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.storeevaluate.StoreEvaluateListRequest;
import com.wanmi.sbc.customer.api.request.storeevaluatesum.StoreEvaluateSumQueryRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerIdsListResponse;
import com.wanmi.sbc.customer.api.response.storeevaluate.StoreEvaluateListResponse;
import com.wanmi.sbc.customer.bean.enums.ScoreCycle;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailWithImgVO;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateSumVO;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.customer.EsStoreEvaluateSumProvider;
import com.wanmi.sbc.elastic.api.provider.goodsevaluate.EsGoodsEvaluateProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateInitRequest;
import com.wanmi.sbc.goods.api.provider.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluate.GoodsEvaluateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluate.GoodsEvaluateSaveProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluateimage.GoodsEvaluateImageQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluateimage.GoodsEvaluateImageSaveProvider;
import com.wanmi.sbc.goods.api.provider.goodstobeevaluate.GoodsTobeEvaluateSaveProvider;
import com.wanmi.sbc.goods.api.provider.storetobeevaluate.StoreTobeEvaluateSaveProvider;
import com.wanmi.sbc.goods.api.request.customergoodsevaluatepraise.CustomerGoodsEvaluatePraiseQueryRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.EsEvaluateAddResponse;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateAddRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateCountRequset;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateListRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluatePageRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateQueryRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImageAddRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImageDelByEvaluateIdRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImageListRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImagePageRequest;
import com.wanmi.sbc.goods.api.request.goodstobeevaluate.GoodsTobeEvaluateQueryRequest;
import com.wanmi.sbc.goods.api.request.storetobeevaluate.StoreTobeEvaluateQueryRequest;
import com.wanmi.sbc.goods.api.response.goodsevaluate.GoodsEvaluateCountResponse;
import com.wanmi.sbc.goods.api.response.goodsevaluate.GoodsEvaluateListResponse;
import com.wanmi.sbc.goods.api.response.goodsevaluate.GoodsEvaluatePageResponse;
import com.wanmi.sbc.goods.api.response.goodsevaluateimage.GoodsEvaluateImageListResponse;
import com.wanmi.sbc.goods.api.response.goodsevaluateimage.GoodsEvaluateImagePageResponse;
import com.wanmi.sbc.goods.api.response.goodstobeevaluate.GoodsTobeEvaluateByIdResponse;
import com.wanmi.sbc.goods.api.response.storetobeevaluate.StoreTobeEvaluateAddResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.CustomerGoodsEvaluatePraiseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import com.wanmi.sbc.goods.request.EvaluateAddRequest;
import com.wanmi.sbc.goods.request.EvaluateModifyRequest;
import com.wanmi.sbc.goods.request.GoodsEvaluateImgPageReq;
import com.wanmi.sbc.goods.request.GoodsEvaluateQueryGoodsAndStoreRequest;
import com.wanmi.sbc.goods.response.EvaluateInfoResponse;
import com.wanmi.sbc.goods.response.GoodsDetailEvaluateResp;
import com.wanmi.sbc.goods.response.GoodsEvaluateQueryGoodsAndStoreResponse;
import com.wanmi.sbc.goods.service.GoodsEvaluateService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.SensitiveWordsQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.SensitiveWordsListRequest;
import com.wanmi.sbc.setting.api.response.AuditConfigListResponse;
import com.wanmi.sbc.setting.api.response.SensitiveWordsListResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.bean.vo.SensitiveWordsVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: jiaojiao
 * @Date: 2019/3/7 09:45
 * @Description:商品评价
 */
@RestController
@Validated
@RequestMapping("/goodsEvaluate")
@Tag(name = "GoodsEvaluateController", description = "S2B web公用-商品评价API")
public class GoodsEvaluateController {

    @Autowired
    GoodsEvaluateSaveProvider goodsEvaluateSaveProvider;

    @Autowired
    GoodsEvaluateImageSaveProvider imageSaveProvider;

    @Autowired
    GoodsEvaluateQueryProvider goodsEvaluateQueryProvider;

    @Autowired
    StoreQueryProvider storeQueryProvider;

    @Autowired
    TradeQueryProvider tradeQueryProvider;

    @Autowired
    StoreEvaluateSumQueryProvider storeEvaluateSumQueryProvider;

    @Autowired
    GoodsTobeEvaluateSaveProvider goodsTobeEvaluateSaveProvider;

    @Autowired
    StoreTobeEvaluateSaveProvider storeTobeEvaluateSaveProvider;

    @Autowired
    StoreEvaluateQueryProvider storeEvaluateQueryProvider;

    @Autowired
    GoodsEvaluateImageQueryProvider imageQueryProvider;

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @Autowired
    private CustomerGoodsEvaluatePraiseQueryProvider customerGoodsEvaluatePraiseQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Autowired
    private EsGoodsEvaluateProvider esGoodsEvaluateProvider;

    @Autowired
    private EsStoreEvaluateSumProvider esStoreEvaluateSumProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private SensitiveWordsQueryProvider sensitiveWordsQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;


    /**
     * 查询商品和店铺信息 展示在评价页面
     *
     * @param queryGoodsAndStoreRequest
     * @return
     */
    @Operation(summary = "查询商品评价")
    @RequestMapping(value = "/getGoodsAndStore", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluateQueryGoodsAndStoreResponse> getGoodsAndStore(@RequestBody
                                                                                          GoodsEvaluateQueryGoodsAndStoreRequest
                                                                                          queryGoodsAndStoreRequest) {
        return BaseResponse.success(this.get(queryGoodsAndStoreRequest));
    }

    private GoodsEvaluateQueryGoodsAndStoreResponse get(GoodsEvaluateQueryGoodsAndStoreRequest
                                                                queryGoodsAndStoreRequest) {
        //订单信息
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(queryGoodsAndStoreRequest.getTid()).build())
                        .getContext();

        Optional<TradeItemVO> detail =
                tradeGetByIdResponse.getTradeVO().getTradeItems().stream().filter(vo -> vo.getSkuId()
                        .equals(queryGoodsAndStoreRequest.getSkuId())).findFirst();

        //店铺评分聚合信息
        StoreEvaluateSumQueryRequest request =
                StoreEvaluateSumQueryRequest.builder().storeId(queryGoodsAndStoreRequest.getStoreId()).scoreCycle(ScoreCycle.ONE_HUNDRED_AND_EIGHTY.toValue()).build();
        StoreEvaluateSumVO storeEvaluateSumVO =
                storeEvaluateSumQueryProvider.getByStoreId(request).getContext().getStoreEvaluateSumVO();

        StoreVO storeVO =
                storeQueryProvider.getById(StoreByIdRequest.builder().storeId(request.getStoreId()).build()).getContext().getStoreVO();

        //订单是否已评分
        GoodsTobeEvaluateByIdResponse goodsTobeResponse =
                goodsTobeEvaluateSaveProvider.query(GoodsTobeEvaluateQueryRequest.builder()
                        .orderNo(queryGoodsAndStoreRequest.getTid()).goodsInfoId(queryGoodsAndStoreRequest.getSkuId())
                        .build()).getContext();

        //商家服务是否已评分
        StoreTobeEvaluateAddResponse storeTobeResponse =
                storeTobeEvaluateSaveProvider.query(StoreTobeEvaluateQueryRequest.builder()
                        .orderNo(queryGoodsAndStoreRequest.getTid()).storeId(queryGoodsAndStoreRequest.getStoreId())
                        .build()).getContext();

        GoodsEvaluateQueryGoodsAndStoreResponse response = new GoodsEvaluateQueryGoodsAndStoreResponse();

        response.setTradeVO(detail.orElseGet(() -> new TradeItemVO()));
        response.setStoreEvaluateSumVO(storeEvaluateSumVO);
        response.setTid(tradeGetByIdResponse.getTradeVO().getId());
        response.setStoreVO(storeVO);
        if (Objects.nonNull(tradeGetByIdResponse.getTradeVO())
                && Objects.nonNull(tradeGetByIdResponse.getTradeVO().getTradeState())) {
            response.setCreateTime(DateUtil.format(tradeGetByIdResponse.getTradeVO().getTradeState().getCreateTime(),
                    "yyyy-MM-dd HH:mm:ss"));
        }


        if (Objects.isNull(goodsTobeResponse.getGoodsTobeEvaluateVO())) {
            response.setGoodsTobe(1);
        }

        if (Objects.isNull(storeTobeResponse.getStoreTobeEvaluateVO())) {
            response.setStoreTobe(1);
        }

        return response;
    }

    /**
     * 添加商品评价
     *
     * @param evaluateAddRequest
     * @return
     */
    @Operation(summary = "添加商品评价")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse<EsEvaluateAddResponse> addGoodsEvaluate(@Valid @RequestBody EvaluateAddRequest evaluateAddRequest) {

        List<String> sensitiveWordsList = this.getSensitiveWordsList();
        GoodsEvaluateAddRequest goodsEvaluateAddRequest = evaluateAddRequest.getGoodsEvaluateAddRequest();
        //获取品论内容
        String evaluateContent = StringUtils.trim(goodsEvaluateAddRequest.getEvaluateContent());
        boolean anyMatch = sensitiveWordsList.stream().anyMatch(words -> StringUtils.contains(evaluateContent, StringUtils.trim(words)));
        if(anyMatch){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030154);
        }
        //订单信息
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(evaluateAddRequest.getGoodsEvaluateAddRequest().getOrderNo()).build())
                        .getContext();
        TradeVO tradeVO = tradeGetByIdResponse.getTradeVO();

        // 验证订单是否是当前用户的订单
        if (Objects.isNull(tradeVO)
                || Objects.isNull(tradeVO.getBuyer())
                || !Objects.equals(commonUtil.getOperatorId(), tradeVO.getBuyer().getId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        Integer isShow = this.getShowConfig();

        GoodsEvaluateAddRequest addRequest = evaluateAddRequest.getGoodsEvaluateAddRequest();
        addRequest.setIsShow(isShow);
        //evaluateAddRequest.setGoodsEvaluateAddRequest(addRequest);
        if (FlowState.VOID.equals(tradeVO.getTradeState().getFlowState())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030100);
        }
        BaseResponse<EsEvaluateAddResponse> response = goodsEvaluateService.addGoodsEvaluate(evaluateAddRequest, tradeVO);
        EsEvaluateAddResponse res = response.getContext();

        if (Objects.isNull(res)) {
            return response;
        }
        //商家评价同步到es
        StoreEvaluateVO storeEvaluateVO = res.getStoreEvaluateVO();
        if (Objects.nonNull(storeEvaluateVO)) {
            EsStoreEvaluateSumAnswerRequest esStoreEvaluateSumAnswerRequest =
                    EsStoreEvaluateSumAnswerRequest.builder().storeEvaluateVO(storeEvaluateVO).build();
            esStoreEvaluateSumProvider.add(esStoreEvaluateSumAnswerRequest);
        }
        //商品评价同步到es
        GoodsEvaluateVO goodsEvaluateVO = res.getGoodsEvaluateVO();
        if (Objects.nonNull(goodsEvaluateVO)) {
            EsGoodsEvaluateAnswerRequest esGoodsEvaluateAnswerRequest =
                    EsGoodsEvaluateAnswerRequest.builder().goodsEvaluateVO(goodsEvaluateVO).build();
            esGoodsEvaluateProvider.add(esGoodsEvaluateAnswerRequest);
        }
        return response;
    }


    /**
     * 修改商品评价
     *
     * @param modifyRequest
     * @return
     */
    @Operation(summary = "修改商品评价")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse editGoodsEvaluate(@RequestBody EvaluateModifyRequest modifyRequest) {
        Operator operator = commonUtil.getOperator();
        //查询原本的评价内容
        GoodsEvaluateByIdRequest byIdRequest = new GoodsEvaluateByIdRequest();
        byIdRequest.setEvaluateId(modifyRequest.getGoodsEvaluateModifyRequest().getEvaluateId());
        GoodsEvaluateVO goodsEvaluateVO = goodsEvaluateQueryProvider.getById(byIdRequest).getContext().getGoodsEvaluateVO();

        // 验证原评价信息是否是当前用户
        if (Objects.isNull(goodsEvaluateVO)
                || !Objects.equals(operator.getUserId(), goodsEvaluateVO.getCreatePerson())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //把原本的评价内容赋值给 新的bean的history
        GoodsEvaluateModifyRequest goodsEvaluateModifyRequest = modifyRequest.getGoodsEvaluateModifyRequest();
        goodsEvaluateModifyRequest.setHistoryEvaluateTime(goodsEvaluateVO.getCreateTime());
        goodsEvaluateModifyRequest.setHistoryEvaluateContent(goodsEvaluateVO.getHistoryEvaluateContent());
        goodsEvaluateModifyRequest.setHistoryEvaluateScore(goodsEvaluateVO.getEvaluateScore());
        goodsEvaluateModifyRequest.setHistoryEvaluateAnswer(goodsEvaluateVO.getEvaluateAnswer());
        goodsEvaluateModifyRequest.setHistoryEvaluateAnswerAccountName(goodsEvaluateVO.getCustomerAccount());
        goodsEvaluateModifyRequest.setHistoryEvaluateAnswerEmployeeId(goodsEvaluateVO.getEvaluateAnswerEmployeeId());
        goodsEvaluateModifyRequest.setHistoryEvaluateAnswerTime(goodsEvaluateVO.getEvaluateAnswerTime());
        goodsEvaluateModifyRequest.setIsEdit(1);
        goodsEvaluateModifyRequest.setUpdatePerson(operator.getUserId());
        goodsEvaluateModifyRequest.setUpdateTime(LocalDateTime.now());
        modifyRequest.setGoodsEvaluateModifyRequest(goodsEvaluateModifyRequest);
        goodsEvaluateSaveProvider.modify(goodsEvaluateModifyRequest);
        //晒单
        List<GoodsEvaluateImageAddRequest> imageAddRequestList = modifyRequest.getGoodsEvaluateImageAddRequestList();
        //删除原本的晒单图片 根据
        GoodsEvaluateImageDelByEvaluateIdRequest delByEvaluateIdRequest = new
                GoodsEvaluateImageDelByEvaluateIdRequest();
        delByEvaluateIdRequest.setEvaluateId(modifyRequest.getGoodsEvaluateModifyRequest().getEvaluateId());
        imageSaveProvider.deleteByEvaluateId(delByEvaluateIdRequest);
        if (Objects.nonNull(imageAddRequestList)) {
            for (GoodsEvaluateImageAddRequest imageAddRequest : imageAddRequestList) {
                imageSaveProvider.add(imageAddRequest);
            }
        }
        //初始化商品评价ES
        esGoodsEvaluateProvider.init(EsGoodsEvaluateInitRequest.builder()
                .idList(Collections.singletonList(byIdRequest.getEvaluateId())).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 分页查询商品评价列表
     *
     * @param goodsEvaluatePageRequest
     * @return
     */
    @Operation(summary = "分页查询商品评价列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluatePageResponse> page(@RequestBody @Valid GoodsEvaluatePageRequest
                                                                goodsEvaluatePageRequest) {
        goodsEvaluatePageRequest.setCustomerId(commonUtil.getOperatorId());
        goodsEvaluatePageRequest.setDelFlag(DeleteFlag.NO.toValue());
        goodsEvaluatePageRequest.putSort("isSys", SortType.ASC.toValue());
        goodsEvaluatePageRequest.putSort("evaluateTime", SortType.DESC.toValue());
        return goodsEvaluateQueryProvider.page(goodsEvaluatePageRequest);
    }

    /**
     * 获取已评价商品数量
     *
     * @return
     */
    @Operation(summary = "获取已评价商品数量")
    @RequestMapping(value = "/getGoodsEvaluateNum", method = RequestMethod.GET)
    public BaseResponse<Long> getGoodsEvaluateNum() {
        GoodsEvaluateQueryRequest queryReq = new GoodsEvaluateQueryRequest();
        queryReq.setCustomerId(commonUtil.getOperatorId());
        queryReq.setDelFlag(DeleteFlag.NO.toValue());
        return goodsEvaluateQueryProvider.getGoodsEvaluateNum(queryReq);
    }

    /**
     * @param request {@link GoodsEvaluatePageRequest}
     * @Description: 分页查询spu评价列表--登陆状态
     * @Author: Bob
     * @Date: 2019-04-09 10:50
     */
    @Operation(summary = "分页查询spu评价列表")
    @RequestMapping(value = "/spuGoodsEvaluatePageLogin", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluatePageResponse> spuGoodsEvaluatePageLogin(@RequestBody @Valid GoodsEvaluatePageRequest
                                                                                     request) {
        return BaseResponse.success(getGoodsEvaluatePageResponse(request, true));
    }

    /**
     * @param request {@link GoodsEvaluatePageRequest}
     * @Description: 分页查询spu评价列表--未登陆状态
     * @Author: Bob
     * @Date: 2019-04-09 10:50
     */
    @Operation(summary = "分页查询spu评价列表")
    @RequestMapping(value = "/spuGoodsEvaluatePage", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluatePageResponse> spuGoodsEvaluatePage(@RequestBody @Valid GoodsEvaluatePageRequest
                                                                                request) {
        return BaseResponse.success(getGoodsEvaluatePageResponse(request, false));
    }

    /**
     * @return com.wanmi.sbc.goods.api.response.goodsevaluate.GoodsEvaluatePageResponse
     * @Author lvzhenwei
     * @Description 分页查询spu评价列表
     * @Date 15:36 2019/5/7
     * @Param [request, isLogin]
     **/
    private GoodsEvaluatePageResponse getGoodsEvaluatePageResponse(GoodsEvaluatePageRequest
                                                                           request, boolean isLogin) {
        request.setDelFlag(DeleteFlag.NO.toValue());
        request.setIsShow(1);
        request.putSort("isSys", SortType.ASC.toValue());
        request.putSort("evaluateTime", SortType.DESC.toValue());
        GoodsEvaluatePageResponse response = goodsEvaluateQueryProvider.page(request).getContext();
        List<String> customerIds = Lists.newArrayList();
        response.getGoodsEvaluateVOPage().getContent().forEach(goodsEvaluateVO -> {
            customerIds.add(goodsEvaluateVO.getCustomerId());
            goodsEvaluateUtil(goodsEvaluateVO);
            if (isLogin) {
                CustomerGoodsEvaluatePraiseQueryRequest queryRequest = new CustomerGoodsEvaluatePraiseQueryRequest();
                queryRequest.setCustomerId(commonUtil.getOperatorId());
                queryRequest.setGoodsEvaluateId(goodsEvaluateVO.getEvaluateId());
                Optional<CustomerGoodsEvaluatePraiseVO> customerGoodsEvaluatePraiseOpt = Optional.ofNullable(
                        customerGoodsEvaluatePraiseQueryProvider.getCustomerGoodsEvaluatePraise(queryRequest).getContext().getCustomerGoodsEvaluatePraiseVO());
                customerGoodsEvaluatePraiseOpt.ifPresent(customerGoodsEvaluatePraiseInfo -> {
                    goodsEvaluateVO.setIsPraise(1);
                });
            }
        });
        // 注销用户匿名处理
        if (CollectionUtils.isNotEmpty(customerIds)){
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
            response.getGoodsEvaluateVOPage().getContent().forEach(goodsEvaluateVO -> {
                if (map.containsKey(goodsEvaluateVO.getCustomerId())){
                    if (LogOutStatus.LOGGED_OUT == map.get(goodsEvaluateVO.getCustomerId())) {
                        goodsEvaluateVO.setCustomerName(Constants.ANONYMOUS);
                    }
                }
            });
        }
        // 评价其他信息-用户头像
        wrapEvaluateInfo(response.getGoodsEvaluateVOPage().getContent());

        return response;
    }


    private void goodsEvaluateUtil(GoodsEvaluateVO goodsEvaluateVO) {
        goodsEvaluateVO.setCustomerAccount("");
        String name = goodsEvaluateVO.getCustomerName();
        if (name.length() == Constants.NUM_11) {
            try {
                String beg = name.substring(0, 3);
                String end = name.substring(6, 10);
                goodsEvaluateVO.setCustomerName(beg + "****" + end);
            } catch (NumberFormatException e) {
                String beg = name.substring(0, 1);
                String end = name.substring(9, 10);
                goodsEvaluateVO.setCustomerName(beg + "****" + end);
            }

        } else if (name.length() == Constants.TWO) {
            String beg = name.substring(0, 1);
            goodsEvaluateVO.setCustomerName(beg + "****");
        } else if (name.length() != 1) {
            String beg = name.substring(0, 1);
            String end = name.substring(name.length() - 1, name.length());
            goodsEvaluateVO.setCustomerName(beg + "****" + end);
        }
    }

    /**
     * @param goodsId 商品ID（spu）
     * @Description: 该spu商品评价总数、晒单、好评率
     * @Author: Bob
     * @Date: 2019-04-09 10:59
     */
    @Operation(summary = "获取某店铺某商品评价总数数量")
    @RequestMapping(value = "/getStoreGoodsEvaluateNum/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsDetailEvaluateResp> getStoreGoodsEvaluateNum(@PathVariable String goodsId) {
        GoodsEvaluateCountRequset request =
                GoodsEvaluateCountRequset.builder().goodsId(goodsId).build();
        GoodsEvaluateCountResponse response = goodsEvaluateQueryProvider.getGoodsEvaluateSum(request).getContext();
        GoodsEvaluateImagePageRequest pageRequest = new GoodsEvaluateImagePageRequest();
        pageRequest.setSortColumn("createTime");
        pageRequest.setSortRole(SortType.DESC.toValue());
        pageRequest.setGoodsId(goodsId);
        pageRequest.setIsShow(1);
        GoodsEvaluateImagePageResponse imagePageResponse = imageQueryProvider.page(pageRequest).getContext();
        return BaseResponse.success(GoodsDetailEvaluateResp.builder().countResponse(response)
                .imagePageResponse(imagePageResponse).build());
    }

    @Operation(summary = "查看商品评价")
    @RequestMapping(value = "/getEvaluate", method = RequestMethod.POST)
    public BaseResponse<EvaluateInfoResponse> evaluateInfo(@RequestBody
                                                                   GoodsEvaluateQueryGoodsAndStoreRequest request) {

        EvaluateInfoResponse infoResponse = new EvaluateInfoResponse();
        StoreEvaluateListResponse response =
                storeEvaluateQueryProvider.list(StoreEvaluateListRequest.builder().storeId(request.getStoreId())
                        .orderNo(request.getTid()).delFlag(0).build()).getContext();

        if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getStoreEvaluateVOList())) {
            StoreEvaluateVO vo = response.getStoreEvaluateVOList().get(0);
            infoResponse.setStoreEvaluateVO(vo);
        }

        GoodsEvaluateListResponse listResponse =
                goodsEvaluateQueryProvider.list(GoodsEvaluateListRequest.builder().orderNo(request.getTid())
                        .goodsInfoId(request.getSkuId()).delFlag(0).build()).getContext();
        if (Objects.nonNull(listResponse) && CollectionUtils.isNotEmpty(listResponse.getGoodsEvaluateVOList())) {
            GoodsEvaluateVO vo = listResponse.getGoodsEvaluateVOList().get(0);
            infoResponse.setGoodsEvaluateVO(vo);

            GoodsEvaluateImageListResponse imageListResponse =
                    imageQueryProvider.list(GoodsEvaluateImageListRequest.builder().evaluateId(vo.getEvaluateId())
                            .build()).getContext();
            if (Objects.nonNull(imageListResponse)) {
                infoResponse.setGoodsEvaluateImageVOS(imageListResponse.getGoodsEvaluateImageVOList());
            }

        }

        GoodsEvaluateQueryGoodsAndStoreResponse goodsresponse = this.get(request);
        infoResponse.setTradeVO(goodsresponse.getTradeVO());
        infoResponse.setStoreEvaluateSumVO(goodsresponse.getStoreEvaluateSumVO());
        infoResponse.setStoreVO(goodsresponse.getStoreVO());
        infoResponse.setTid(goodsresponse.getTid());
        infoResponse.setCreateTime(goodsresponse.getCreateTime());
        return BaseResponse.success(infoResponse);
    }

    /**
     * @param
     * @Description: 商品详情晒图列表
     * @Author: Bob
     * @Date: 2019-05-14 11:27
     */
    @Operation(summary = "商品详情晒图列表")
    @RequestMapping(value = "/getGoodsEvaluateImg", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluateImagePageResponse> getGoodsEvaluateImg(@RequestBody @Valid GoodsEvaluateImgPageReq req) {
        GoodsEvaluateImagePageRequest pageRequest = KsBeanUtil.convert(req, GoodsEvaluateImagePageRequest.class);
        pageRequest.setSortColumn("createTime");
        pageRequest.setSortRole(SortType.DESC.toValue());
        pageRequest.setIsShow(1);
        GoodsEvaluateImagePageResponse response = imageQueryProvider.page(pageRequest).getContext();
        List<String> customerIds = Lists.newArrayList();
        response.getGoodsEvaluateImageVOPage().getContent().forEach(goodsEvaluateImageVO -> {
            GoodsEvaluateVO goodsEvaluateVO = goodsEvaluateImageVO.getGoodsEvaluate();
            customerIds.add(goodsEvaluateVO.getCustomerId());
            goodsEvaluateUtil(goodsEvaluateVO);
        });
        List<GoodsEvaluateVO> goodsEvaluateVOS = response.getGoodsEvaluateImageVOPage().getContent().stream()
                .map(GoodsEvaluateImageVO::getGoodsEvaluate).collect(Collectors.toList());
        // 注销用户匿名处理
        if (CollectionUtils.isNotEmpty(customerIds)){
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
            response.getGoodsEvaluateImageVOPage().getContent().forEach(goodsEvaluateImageVO -> {
                GoodsEvaluateVO goodsEvaluateVO = goodsEvaluateImageVO.getGoodsEvaluate();
                if (map.containsKey(goodsEvaluateVO.getCustomerId())){
                    if (LogOutStatus.LOGGED_OUT == map.get(goodsEvaluateVO.getCustomerId())) {
                        goodsEvaluateVO.setCustomerName(Constants.ANONYMOUS);
                    }
                }
            });
        }
        // 评价其他信息-用户头像
        wrapEvaluateInfo(goodsEvaluateVOS);
        return BaseResponse.success(response);
    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO>
     * @Author lvzhenwei
     * @Description 获取商品评价详情
     * @Date 11:43 2019/5/28
     * @Param [goodsEvaluateByIdRequest]
     **/
    @Operation(summary = "获取商品评价详情")
    @RequestMapping(value = "/getCustomerGoodsEvaluate", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluateVO> getCustomerGoodsEvaluate(@RequestBody GoodsEvaluateByIdRequest goodsEvaluateByIdRequest) {
        GoodsEvaluateVO goodsEvaluateVO = goodsEvaluateQueryProvider.getById(goodsEvaluateByIdRequest).getContext().getGoodsEvaluateVO();
        CustomerGoodsEvaluatePraiseQueryRequest queryRequest = new CustomerGoodsEvaluatePraiseQueryRequest();
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        queryRequest.setGoodsEvaluateId(goodsEvaluateByIdRequest.getEvaluateId());
        Optional<CustomerGoodsEvaluatePraiseVO> customerGoodsEvaluatePraiseOpt = Optional.ofNullable(
                customerGoodsEvaluatePraiseQueryProvider.getCustomerGoodsEvaluatePraise(queryRequest).getContext().getCustomerGoodsEvaluatePraiseVO());
        customerGoodsEvaluatePraiseOpt.ifPresent(customerGoodsEvaluatePraiseInfo -> {
            goodsEvaluateVO.setIsPraise(1);
        });
        return BaseResponse.success(goodsEvaluateVO);
    }


    /**
     * 会员头像
     * 1：现状-评价商品信息 会员名称等信息是在提交评论时持久化的，弊端 1 不能获取最新会员信 2 有新的展示项需要不断扩充数据结构
     * 后期增加需求显示会员头像，考虑到如果再要显示标签等信息直接拓展数据库字段也不合适，所以这里的头像是实时获取的
     * 2：如果以后会员名称需要获取最新会员信息直接在这里添加就可以了
     */
    private void wrapEvaluateInfo(List<GoodsEvaluateVO> goodsEvaluateVOList) {
        if (CollectionUtils.isNotEmpty(goodsEvaluateVOList)) {
            //获取用户信息-头像、昵称
            List<String> customerIds = goodsEvaluateVOList.stream().map(GoodsEvaluateVO::getCustomerId).collect
                    (Collectors.toList());
            CustomerIdsListRequest customerIdsListRequest = new CustomerIdsListRequest();
            customerIdsListRequest.setCustomerIds(customerIds);
            BaseResponse<CustomerIdsListResponse> listByCustomerIds = thirdLoginRelationQueryProvider
                    .listWithImgByCustomerIds(customerIdsListRequest);
            List<CustomerDetailWithImgVO> customerVOList = listByCustomerIds.getContext().getCustomerVOList();
            if (CollectionUtils.isNotEmpty(customerVOList)) {
                goodsEvaluateVOList.forEach(vo -> {
                    CustomerDetailWithImgVO customerDetailWithImgVO = customerVOList.stream().filter(ivo -> ivo
                            .getCustomerId().equals(vo.getCustomerId())).findFirst().orElse(new CustomerDetailWithImgVO());
                    //头像
                    vo.setHeadimgurl(customerDetailWithImgVO.getHeadimgurl());
                    //会员标签
                    vo.setCustomerLabelList(customerDetailWithImgVO.getCustomerLabelList());
                });
            }
        }
    }


    /**
     * 获取配置信息
     * @return
     */
    private Integer getShowConfig(){
        //查询评价展示配置
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigKey(ConfigKey.GOODS_SETTING.toValue());
        BaseResponse<AuditConfigListResponse> configResponse = auditQueryProvider.getByConfigKey(configQueryRequest);
        List<ConfigVO> configVOList = configResponse.getContext().getConfigVOList();
        Optional<ConfigVO> confOptional = Optional.ofNullable(configVOList)
                .orElseGet(Collections::emptyList).stream()
                .filter(config ->
                        StringUtils.equals(config.getConfigType(), ConfigType.GOODS_EVALUATE_SETTING.toValue()))
                .findFirst();
        if(!confOptional.isPresent()){
            return NumberUtils.INTEGER_ZERO;
        }
        ConfigVO configVO = confOptional.get();
        String context = configVO.getContext();
        if (StringUtils.isBlank(context)) {
            //配置为空返回0，表示关闭
            return 0;
        }
        JSONObject jsonObject = JSONObject.parseObject(context);
        return jsonObject.getInteger("isShow");
    }

    /**
     * 获取敏感词信息
     * @return
     */
    private List<String> getSensitiveWordsList(){
        SensitiveWordsListRequest request = SensitiveWordsListRequest.builder()
                .delFlag(DeleteFlag.NO)
                .build();
        SensitiveWordsListResponse listResponse = sensitiveWordsQueryProvider.list(request).getContext();
        List<SensitiveWordsVO> sensitiveWordsVOList = listResponse.getSensitiveWordsVOList();
        if(CollectionUtils.isEmpty(sensitiveWordsVOList)){
            return Collections.emptyList();
        }
        return sensitiveWordsVOList.stream()
                .map(SensitiveWordsVO::getSensitiveWords)
                .collect(Collectors.toList());
    }


}
