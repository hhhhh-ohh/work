package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.goodsevaluate.EsGoodsEvaluateProvider;
import com.wanmi.sbc.elastic.api.provider.goodsevaluate.EsGoodsEvaluateQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluatePageRequest;
import com.wanmi.sbc.elastic.api.response.goodsevaluate.EsGoodsEvaluatePageResponse;
import com.wanmi.sbc.goods.api.provider.goodsevaluate.GoodsEvaluateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluate.GoodsEvaluateSaveProvider;
import com.wanmi.sbc.goods.api.provider.goodsevaluateimage.GoodsEvaluateImageSaveProvider;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateAnswerRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateByIdRequest;
import com.wanmi.sbc.goods.api.response.goodsevaluate.GoodsEvaluateAnswerResponse;
import com.wanmi.sbc.goods.api.response.goodsevaluate.GoodsEvaluateByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liutao
 * @date 2019/2/25 3:35 PM
 */
@Tag(name =  "boss商品评价Api", description =  "BossGoodsEvaluateController")
@RestController
@Validated
@RequestMapping("/goods/evaluate")
public class GoodsEvaluateController {

    @Autowired
    private GoodsEvaluateQueryProvider goodsEvaluateQueryProvider;

    @Autowired
    private GoodsEvaluateSaveProvider goodsEvaluateSaveProvider;

    @Autowired
    GoodsEvaluateImageSaveProvider goodsEvaluateImageSaveProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsGoodsEvaluateProvider esGoodsEvaluateProvider;

    @Autowired
    private EsGoodsEvaluateQueryProvider esGoodsEvaluateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 分页查询商品评价列表
     *
     * @param goodsEvaluatePageRequest
     * @return
     */
    @Operation(summary = "分页查询商品评价列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<EsGoodsEvaluatePageResponse> page(@RequestBody @Valid EsGoodsEvaluatePageRequest
                                                                goodsEvaluatePageRequest) throws ParseException {
        goodsEvaluatePageRequest.setStoreId(commonUtil.getStoreId());
        if (StringUtils.isNotBlank(goodsEvaluatePageRequest.getEndTime())) {
            goodsEvaluatePageRequest.setEndTime(DateUtil.plusDayForStr(goodsEvaluatePageRequest.getEndTime(), 1));
        }

        //goodsEvaluateQueryProvider.page(goodsEvaluatePageRequest)
        BaseResponse<EsGoodsEvaluatePageResponse> page = esGoodsEvaluateQueryProvider.page(goodsEvaluatePageRequest);
        List<String> customerIds = page.getContext().getGoodsEvaluateVOPage().getContent()
                .stream()
                .map(GoodsEvaluateVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContext().getGoodsEvaluateVOPage().getContent().forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return page;
    }

    /**
     * 获取商品评价详情
     *
     * @param goodsEvaluateByIdRequest
     * @return
     */
    @Operation(summary = "获取商品评价详情信息")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluateByIdResponse> info(@RequestBody @Valid GoodsEvaluateByIdRequest
                                                                goodsEvaluateByIdRequest) {
        BaseResponse<GoodsEvaluateByIdResponse> response = goodsEvaluateQueryProvider.getById(goodsEvaluateByIdRequest);
        if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
            if (!Objects.equals(commonUtil.getStoreId(),response.getContext().getGoodsEvaluateVO().getStoreId())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
        //获取客户注销状态
        response.getContext()
                .getGoodsEvaluateVO()
                .setLogOutStatus(
                        customerCacheService
                                .getCustomerLogOutStatus(
                                        response
                                                .getContext()
                                                .getGoodsEvaluateVO()
                                                .getCustomerId()
                                )
                );
        return response;
    }

    /**
     * 商品评价回复
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "回复商品评价")
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluateAnswerResponse> answer(@RequestBody @Valid GoodsEvaluateAnswerRequest
                                                                    request) {
        if(StringUtils.isBlank(request.getEvaluateAnswer())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setEvaluateAnswerAccountName(commonUtil.getAccountName());
        request.setEvaluateAnswerEmployeeId(commonUtil.getOperator().getAdminId());
        BaseResponse<GoodsEvaluateAnswerResponse> response = goodsEvaluateSaveProvider.answer(request);
        GoodsEvaluateVO goodsEvaluateVO = response.getContext().getGoodsEvaluateVO();
        //更新es信息中的评价数
        if (Objects.nonNull(response.getContext()) && Objects.nonNull(goodsEvaluateVO)) {
            if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
                if (!Objects.equals(commonUtil.getStoreId(),response.getContext().getGoodsEvaluateVO().getStoreId())){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                }
            }
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsEvaluateVO.getGoodsId()).build());
            EsGoodsEvaluateAnswerRequest saveRequest = EsGoodsEvaluateAnswerRequest.builder().goodsEvaluateVO(goodsEvaluateVO).build();
            esGoodsEvaluateProvider.add(saveRequest);
        }
        return response;
    }

    /**
     * 是否展示商品评价
     *
     * @param request
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "是否展示商品评价")
    @RequestMapping(value = "/isShow", method = RequestMethod.POST)
    public BaseResponse<GoodsEvaluateAnswerResponse> isShow(@RequestBody @Valid GoodsEvaluateAnswerRequest
                                                                    request) {

        request.setEvaluateAnswerAccountName(commonUtil.getAccountName());
        request.setEvaluateAnswerEmployeeId(commonUtil.getOperator().getAdminId());
        BaseResponse<GoodsEvaluateAnswerResponse> response = goodsEvaluateSaveProvider.answer(request);
        GoodsEvaluateVO goodsEvaluateVO = response.getContext().getGoodsEvaluateVO();
        //更新es信息中的评价数
        if (Objects.nonNull(response.getContext()) && Objects.nonNull(goodsEvaluateVO)) {
            if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
                if (!Objects.equals(commonUtil.getStoreId(),response.getContext().getGoodsEvaluateVO().getStoreId())){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                }
            }
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsEvaluateVO.getGoodsId()).build());
            EsGoodsEvaluateAnswerRequest saveRequest = EsGoodsEvaluateAnswerRequest.builder().goodsEvaluateVO(goodsEvaluateVO).build();
            esGoodsEvaluateProvider.add(saveRequest);
        }
        return response;
    }


}