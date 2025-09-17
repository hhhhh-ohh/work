package com.wanmi.sbc.groupon;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuIdListReponse;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoListRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.groupongoodsinfo.GrouponGoodsInfoListResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPageSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponsetting.GrouponSettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponsetting.GrouponSettingSaveProvider;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityListRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityPageRequest;
import com.wanmi.sbc.marketing.api.request.grouponsetting.GrouponAuditFlagSaveRequest;
import com.wanmi.sbc.marketing.api.request.grouponsetting.GrouponPosterSaveRequest;
import com.wanmi.sbc.marketing.api.request.grouponsetting.GrouponRuleSaveRequest;
import com.wanmi.sbc.marketing.api.request.grouponsetting.GrouponSettingModifyRequest;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityPage4MangerResponse;
import com.wanmi.sbc.marketing.api.response.grouponsetting.GrouponSettingGoodsResponse;
import com.wanmi.sbc.marketing.api.response.grouponsetting.GrouponSettingPageResponse;
import com.wanmi.sbc.marketing.bean.enums.GrouponTabTypeStatus;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityForManagerVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponSettingGoodsVO;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by feitingting on 2019/5/16.
 */

/**
 * 拼团设置控制器
 */
@RestController
@Validated
@RequestMapping("/groupon/setting")
@Tag(name =  "S2B拼团设置", description =  "GrouponSettingController")
public class GrouponSettingController {

    @Autowired
    private GrouponSettingQueryProvider grouponSettingQueryProvider;

    @Autowired
    private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired
    private GrouponSettingSaveProvider grouponSettingSaveProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsSpuQueryProvider esSpuQueryProvider;

    @Autowired
    private GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Operation(summary = "查询拼团设置")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<GrouponSettingPageResponse> findOne() {
       return grouponSettingQueryProvider.getSetting();
    }


    @Operation(summary = "查询生效的拼团活动下的spu商品列表")
    @RequestMapping(value="/valid/goods", method = RequestMethod.POST)
    public BaseResponse<GrouponSettingGoodsResponse> grouponValidGoods(@RequestBody @Valid GrouponActivityPageRequest
                                                                         pageRequest) {
        //先查询出已生效或即将生效的拼团活动
        pageRequest.setDelFlag(DeleteFlag.NO);
        //正咋进行中的团活动
        pageRequest.setTabType(GrouponTabTypeStatus.STARTED);
        GrouponActivityPage4MangerResponse response = grouponActivityQueryProvider.page4Manager(pageRequest).getContext();
        //获取符合条件的活动的所有spuId
        List<String> spuIds = response.getGrouponActivityVOPage()
                .getContent().stream()
                .map(GrouponActivityForManagerVO::getGoodsId)
                .collect(Collectors.toList());
        GoodsListByIdsRequest request = new GoodsListByIdsRequest();
        request.setGoodsIds(spuIds);
        //聚合活动信息和商品信息
        List<GrouponSettingGoodsVO> list = response.getGrouponActivityVOPage().getContent().stream().map((vo)->{
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(vo.getGoodsId());
            GoodsByIdResponse goodsByIdResponse = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            GrouponSettingGoodsVO grouponSettingGoodsVO = new GrouponSettingGoodsVO();
            grouponSettingGoodsVO.setGoodsId(vo.getGoodsId());
            grouponSettingGoodsVO.setGoodsNo(vo.getGoodsNo());
            grouponSettingGoodsVO.setGoodsInfoId(vo.getGoodsInfoId());
            grouponSettingGoodsVO.setGoodsName(vo.getGoodsName());
            grouponSettingGoodsVO.setGoodsImg(goodsByIdResponse.getGoodsImg());
            grouponSettingGoodsVO.setStartTime(vo.getStartTime());
            grouponSettingGoodsVO.setEndTime(vo.getEndTime());
            grouponSettingGoodsVO.setGrouponPrice(vo.getGrouponPrice());
            return  grouponSettingGoodsVO;
        }).collect(Collectors.toList());
        //填充分页信息
        GrouponSettingGoodsResponse grouponSettingGoodsResponse = new GrouponSettingGoodsResponse();
        grouponSettingGoodsResponse.setGrouponSettingGoodsVOList(list);
        grouponSettingGoodsResponse.setTotal(response.getGrouponActivityVOPage().getTotal());
        grouponSettingGoodsResponse.setNumber(response.getGrouponActivityVOPage().getNumber());
        return BaseResponse.success(grouponSettingGoodsResponse);
    }

    @Operation(summary = "查询生效的拼团活动下的spu商品列表")
    @RequestMapping(value="/valid/goods-list", method = RequestMethod.POST)
    public BaseResponse<GrouponSettingGoodsResponse> grouponValidGoodsPage(@RequestBody @Valid GrouponActivityPageRequest
                                                                                       pageRequest) {
        if(ObjectUtils.anyNotNull(pageRequest.getMinGoodsSalesNum(),pageRequest.getMaxGoodsSalesNum(),
                pageRequest.getMinMarketPrice(), pageRequest.getMaxMarketPrice(),
                pageRequest.getMinStock(), pageRequest.getMaxStock())){
            List<String> spuIdList = this.getSpuIdList(pageRequest);
            pageRequest.setSpuIdList(spuIdList);
        }
        //先查询出已生效或即将生效的拼团活动
        pageRequest.setDelFlag(DeleteFlag.NO);
        //正咋进行中的团活动
        pageRequest.setTabType(GrouponTabTypeStatus.STARTED);
        GrouponActivityPage4MangerResponse response = grouponActivityQueryProvider.page4Manager(pageRequest).getContext();
        //获取符合条件的活动的所有spuId
        List<String> spuIds = response.getGrouponActivityVOPage()
                .getContent().stream()
                .map(GrouponActivityForManagerVO::getGoodsId)
                .collect(Collectors.toList());
        GoodsListByIdsRequest request = new GoodsListByIdsRequest();
        request.setGoodsIds(spuIds);
        //聚合活动信息和商品信息
        List<GrouponSettingGoodsVO> list = response.getGrouponActivityVOPage().getContent().stream().map((vo)->{
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(vo.getGoodsId());
            GoodsByIdResponse goodsByIdResponse = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            GrouponSettingGoodsVO grouponSettingGoodsVO = new GrouponSettingGoodsVO();
            grouponSettingGoodsVO.setGoodsId(vo.getGoodsId());
            grouponSettingGoodsVO.setGoodsNo(vo.getGoodsNo());
            grouponSettingGoodsVO.setGoodsInfoId(vo.getGoodsInfoId());
            grouponSettingGoodsVO.setGoodsName(vo.getGoodsName());
            grouponSettingGoodsVO.setGoodsImg(goodsByIdResponse.getGoodsImg());
            grouponSettingGoodsVO.setStartTime(vo.getStartTime());
            grouponSettingGoodsVO.setEndTime(vo.getEndTime());
            grouponSettingGoodsVO.setGrouponPrice(vo.getGrouponPrice());
            return  grouponSettingGoodsVO;
        }).collect(Collectors.toList());
        //填充分页信息
        GrouponSettingGoodsResponse grouponSettingGoodsResponse = new GrouponSettingGoodsResponse();
        grouponSettingGoodsResponse.setGrouponSettingGoodsVOList(list);
        grouponSettingGoodsResponse.setTotal(response.getGrouponActivityVOPage().getTotal());
        grouponSettingGoodsResponse.setNumber(response.getGrouponActivityVOPage().getNumber());
        return BaseResponse.success(grouponSettingGoodsResponse);
    }


    @Operation(summary = "保存拼团活动商品审核开关设置")
    @RequestMapping(value="/save/audit", method = RequestMethod.PUT)
    public BaseResponse saveAudit(@RequestBody @Valid GrouponAuditFlagSaveRequest request){
        BaseResponse response = grouponSettingSaveProvider.saveAudit(request);

        // 记录日志
        operateLogMQUtil.convertAndSend("营销", "保存拼团活动商品审核",
                DefaultFlag.YES.equals(request.getAuditFlag()) ? "开启" : "关闭");
        return response;
    }


    @Operation(summary = "保存拼团广告设置")
    @RequestMapping(value="/save/poster", method = RequestMethod.PUT)
    public BaseResponse savePoster(@RequestBody @Valid GrouponPosterSaveRequest request ){
        BaseResponse response = grouponSettingSaveProvider.savePoster(request);

        // 记录日志
        operateLogMQUtil.convertAndSend("营销", "保存拼团广告设置", "保存拼团广告设置");
        return response;
    }


    @Operation(summary="保存拼团规则设置")
    @RequestMapping(value="/save/rule",method = RequestMethod.PUT)
    public BaseResponse saveRule(@RequestBody @Valid GrouponRuleSaveRequest request){
        BaseResponse response = grouponSettingSaveProvider.saveRule(request);

        // 记录日志
        operateLogMQUtil.convertAndSend("营销", "保存拼团规则设置", "保存拼团规则设置");
        return  response;
    }

    /**
     * 保存拼团设置
     * @param request
     * @return
     */
    @Operation(summary="保存拼团设置")
    @RequestMapping(value="/save",method = RequestMethod.PUT)
    public BaseResponse saveGroupon(@RequestBody @Valid GrouponSettingModifyRequest request){
        BaseResponse response = grouponSettingSaveProvider.modify(request);

        // 记录日志
        operateLogMQUtil.convertAndSend("营销", "保存拼团规则设置", "保存拼团规则设置");
        return  response;
    }

    @Operation(summary="查询参团的商家数")
    @RequestMapping(value="/query/supplier/num",method = RequestMethod.GET)
    public int querySupplierNum(){
        GrouponActivityListRequest request = new GrouponActivityListRequest();
        request.setStatus("1");
        return  grouponActivityQueryProvider.querySupplierNum(request).getContext().getSupplierNum();
    }


    private List<String> getSpuIdList(GrouponActivityPageRequest pageRequest){
        List<String> spuIdList = Lists.newArrayList();
        if(ObjectUtils.anyNotNull(pageRequest.getMinStock(),pageRequest.getMaxStock(),pageRequest.getMinGoodsSalesNum(), pageRequest.getMaxGoodsSalesNum())){
            EsSpuPageRequest request = new EsSpuPageRequest();
            request.setDelFlag(DeleteFlag.NO.toValue());
            request.setMinStock(pageRequest.getMinStock());
            request.setMaxStock(pageRequest.getMaxStock());
            request.setMinGoodsSalesNum(pageRequest.getMinGoodsSalesNum());
            request.setMaxGoodsSalesNum(pageRequest.getMaxGoodsSalesNum());
            request.setPageNum(0);
            //es 默认size大小为10，window窗口若没修改过 则最大为20000
            request.setPageSize(20000);
            BaseResponse<EsSpuIdListReponse> spuIdPage = esSpuQueryProvider.spuIdList(request);
            spuIdList = spuIdPage.getContext().getSpuIdList();
        }
        if(ObjectUtils.anyNotNull(pageRequest.getMinMarketPrice(), pageRequest.getMaxMarketPrice())){
            GrouponGoodsInfoListRequest listRequest = new GrouponGoodsInfoListRequest();
            listRequest.setMinGrouponPrice(pageRequest.getMinMarketPrice());
            listRequest.setMaxGrouponPrice(pageRequest.getMaxMarketPrice());
            listRequest.setGoodsIdList(spuIdList);
            listRequest.setStarted(Boolean.TRUE);
            BaseResponse<GrouponGoodsInfoListResponse> response = grouponGoodsInfoQueryProvider.list(listRequest);
            List<GrouponGoodsInfoVO> goodsInfoVOList = response.getContext().getGrouponGoodsInfoVOList();
            if(CollectionUtils.isEmpty(goodsInfoVOList)){
                return Collections.emptyList();
            }
            return goodsInfoVOList.stream().map(GrouponGoodsInfoVO::getGoodsId).collect(Collectors.toList());
        }

        return  Optional.ofNullable(spuIdList).orElseGet(Collections::emptyList);
    }
}
