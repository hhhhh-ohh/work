package com.wanmi.sbc.goodstemplate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodstemplate.GoodsTemplateProvider;
import com.wanmi.sbc.goods.api.provider.goodstemplate.GoodsTemplateQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goodstemplate.*;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfRequest;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateAddResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateByIdResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateModifyResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplatePageResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "GoodsTemplate管理API", description =  "GoodsTemplateController")
@RestController
@Validated
@RequestMapping(value = "/goodstemplate")
public class GoodsTemplateController {

    @Autowired
    private GoodsTemplateQueryProvider goodsTemplateQueryProvider;

    @Autowired
    private GoodsTemplateProvider goodsTemplateProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private EsSpuQueryProvider esSpuQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Operation(summary = "分页查询GoodsTemplate")
    @PostMapping("/page")
    public BaseResponse<GoodsTemplatePageResponse> getPage(@RequestBody @Valid GoodsTemplatePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("updateTime", "desc");
        pageReq.setStoreId(commonUtil.getStoreId());
        return goodsTemplateQueryProvider.page(pageReq);
    }

    @Operation(summary = "根据id查询GoodsTemplate")
    @GetMapping("/{id}")
    public BaseResponse<GoodsTemplateByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsTemplateByIdRequest idReq = new GoodsTemplateByIdRequest();
        idReq.setId(id);
        BaseResponse<GoodsTemplateByIdResponse> response = goodsTemplateQueryProvider.getById(idReq);
        //越权校验
        commonUtil.checkStoreId(response.getContext().getGoodsTemplateVO().getStoreId());
        return response;
    }

    @Operation(summary = "新增GoodsTemplate")
    @PostMapping("/add")
    public BaseResponse<GoodsTemplateAddResponse> add(@RequestBody @Valid GoodsTemplateAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setStoreId(commonUtil.getStoreId());
        BaseResponse<GoodsTemplateAddResponse> response = goodsTemplateProvider.add(addReq);
        operateLogMqUtil.convertAndSend("商品",
                "商品模版新增",
                "模版名称:" + response.getContext().getGoodsTemplateVO().getName());
        return response;
    }

    @Operation(summary = "修改GoodsTemplate")
    @PutMapping("/modify")
    public BaseResponse<GoodsTemplateModifyResponse> modify(@RequestBody @Valid GoodsTemplateModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setStoreId(commonUtil.getStoreId());
        BaseResponse<GoodsTemplateModifyResponse> response = goodsTemplateProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("商品",
                "商品模版编辑",
                "模版Id:" + response.getContext().getGoodsTemplateVO().getId());
        return response;
    }

    @Operation(summary = "根据id删除GoodsTemplate")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsTemplateDelByIdRequest delByIdReq = new GoodsTemplateDelByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setUpdatePerson(commonUtil.getOperatorId());
        delByIdReq.setStoreId(commonUtil.getStoreId());
        goodsTemplateProvider.deleteById(delByIdReq);
        operateLogMqUtil.convertAndSend("商品",
                "商品模版删除",
                "模版Id:" + id);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "根据id查询关联的商品")
    @PostMapping ("/join/goods/page")
    public BaseResponse<EsSpuPageResponse> joinGoodsDetails(@RequestBody @Valid GoodsTemplateJoinPageRequest request) {
        //越权校验
        getById(request.getTemplateId());
        List<String> goodsIdList = goodsTemplateQueryProvider
                .joinGoodsDetails(GoodsTemplateByIdRequest.builder().id(request.getTemplateId()).build())
                .getContext()
                .getGoodsIdList();
        if (CollectionUtils.isNotEmpty(goodsIdList)){
            EsSpuPageRequest esSpuPageRequest = KsBeanUtil.convert(request, EsSpuPageRequest.class);
            //补充店铺分类
            if(esSpuPageRequest.getStoreCateId() != null) {
                StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse cateIdAndIsHaveSelfResponse = storeCateQueryProvider
                        .listGoodsRelByStoreCateIdAndIsHaveSelf(
                                new StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfRequest(esSpuPageRequest.getStoreCateId(), true)
                        ).getContext();
                if (Objects.nonNull(cateIdAndIsHaveSelfResponse)) {
                    List<StoreCateGoodsRelaVO> relas = cateIdAndIsHaveSelfResponse.getStoreCateGoodsRelaVOList();
                    if (org.apache.commons.collections.CollectionUtils.isEmpty(relas)) {
                        EsSpuPageResponse response = new EsSpuPageResponse();
                        response.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), esSpuPageRequest.getPageable(), 0));
                        return BaseResponse.success(response);
                    }
                    esSpuPageRequest.setStoreCateGoodsIds(relas.stream().map(StoreCateGoodsRelaVO::getGoodsId).collect(Collectors.toList()));
                }else{
                    EsSpuPageResponse response = new EsSpuPageResponse();
                    response.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), esSpuPageRequest.getPageable(), 0));
                    return BaseResponse.success(response);
                }
            }
            esSpuPageRequest.setGoodsIds(goodsIdList);
            esSpuPageRequest.setDelFlag(Constants.no);
            esSpuPageRequest.setStoreId(commonUtil.getStoreId());
            BaseResponse<EsSpuPageResponse> page = esSpuQueryProvider.page(esSpuPageRequest);
            page.getContext().setGoodsIdList(goodsIdList);
            return page;
        }
        return BaseResponse.success(new EsSpuPageResponse());
    }

    @Operation(summary = "根据id关联商品")
    @PutMapping ("/join/goods")
    public BaseResponse joinGoods(@RequestBody @Valid GoodsTemplateJoinRequest request) {
        //越权校验
        getById(request.getTemplateId());
        List<String> goodsNameByIds = goodsQueryProvider.findGoodsNameByIds(request.getJoinGoodsIdList()).getContext();
        request.setJoinGoodsNameList(goodsNameByIds);
        if (CollectionUtils.isNotEmpty(request.getJoinGoodsIdList())){
            //商品越权校验
            List<GoodsVO> list = goodsQueryProvider
                    .listByCondition(GoodsByConditionRequest
                            .builder()
                            .goodsIds(request.getJoinGoodsIdList())
                            .delFlag(Constants.no)
                            .build())
                    .getContext()
                    .getGoodsVOList();
            if (CollectionUtils.isEmpty(list) || list.size() != request.getJoinGoodsIdList().size()){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            List<Long> storeIds = list.stream().map(GoodsVO::getStoreId).distinct().collect(Collectors.toList());
            if (storeIds.size() != Constants.ONE){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            commonUtil.checkStoreId(storeIds.get(0));
        }

        request.setUserId(commonUtil.getOperatorId());
        goodsTemplateProvider.joinGoods(request);
        operateLogMqUtil.convertAndSend("商品",
                "商品模版关联",
                "模版Id:" + request.getTemplateId()+"关联商品名称:"+request.getJoinGoodsNameList());
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "根据商品id删除关联商品")
    @DeleteMapping ("/goods/{goodsId}")
    public BaseResponse joinGoodsDel(@PathVariable("goodsId")String goodsId) {
        GoodsTemplateByGoodsIdRequest request = GoodsTemplateByGoodsIdRequest
                .builder()
                .goodsId(goodsId)
                .build();
        BaseResponse<GoodsTemplateByIdResponse> baseResponse = goodsTemplateQueryProvider.getByGoodsId(request);
        if (Objects.nonNull(baseResponse.getContext().getGoodsTemplateVO())){
            commonUtil.checkStoreId(baseResponse.getContext().getGoodsTemplateVO().getStoreId());
        }
        goodsTemplateProvider.deleteByGoodsId(request);
        operateLogMqUtil.convertAndSend("商品",
                "删除模版关联商品",
                "模版Id:" + baseResponse.getContext().getGoodsTemplateVO().getId()+"关联商品Id:"+goodsId);
        return BaseResponse.SUCCESSFUL();
    }

}
