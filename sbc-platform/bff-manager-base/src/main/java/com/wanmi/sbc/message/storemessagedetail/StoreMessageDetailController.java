package com.wanmi.sbc.message.storemessagedetail;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.authority.AuthBaseService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditByIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardIdsByGoodsIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.message.api.provider.storemessagedetail.StoreMessageDetailProvider;
import com.wanmi.sbc.message.api.provider.storemessagedetail.StoreMessageDetailQueryProvider;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendQueryProvider;
import com.wanmi.sbc.message.api.request.storemessagedetail.*;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendByIdRequest;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailByIdResponse;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailPageResponse;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailTopListResponse;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;
import com.wanmi.sbc.message.bean.vo.StoreMessageDetailVO;
import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import com.wanmi.sbc.setting.api.provider.storemessagenode.StoreMessageNodeQueryProvider;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeListRequest;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "商家消息/公告管理API", description =  "StoreMessageDetailController")
@RestController
@Validated
@RequestMapping(value = "/storeMessage")
public class StoreMessageDetailController {

    @Autowired
    private StoreMessageDetailQueryProvider storeMessageDetailQueryProvider;

    @Autowired
    private StoreNoticeSendQueryProvider storeNoticeSendQueryProvider;

    @Autowired
    private StoreMessageDetailProvider storeMessageDetailProvider;

    @Autowired
    private StoreMessageNodeQueryProvider storeMessageNodeQueryProvider;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired private StandardGoodsQueryProvider standardGoodsQueryProvider;

    @Autowired
    private AuthBaseService authBaseService;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询商家消息/公告")
    @PostMapping("/page")
    public BaseResponse<StoreMessageDetailPageResponse> getPage(@RequestBody StoreMessageDetailPageRequest pageReq) {
        pageReq.setStoreId(getStoreId());
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("isRead", "asc");
        pageReq.putSort("sendTime", "desc");
        if (StoreMessageType.MESSAGE == pageReq.getMessageType()) {
            // 仅筛选消息时，需要填充节点权限
            List<Long> nodeIdListByAuth = this.getNodeIdListByAuth();
            if (CollectionUtils.isEmpty(nodeIdListByAuth)) {
                // 无任何权限时，直接返回空
                return BaseResponse.success(new StoreMessageDetailPageResponse(new MicroServicePage<>()));
            }
            pageReq.setJoinIdList(nodeIdListByAuth);
        }
        return storeMessageDetailQueryProvider.page(pageReq);
    }

    @Operation(summary = "查询商家消息/公告顶部消息")
    @PostMapping("/topList")
    public BaseResponse<StoreMessageDetailTopListResponse> getTopList() {
        StoreMessageDetailTopListRequest topListRequest = new StoreMessageDetailTopListRequest();
        topListRequest.setStoreId(getStoreId());
        List<Long> nodeIdListByAuth = this.getNodeIdListByAuth();
        if (CollectionUtils.isEmpty(nodeIdListByAuth)) {
            // 无任何权限时，直接返回空
            StoreMessageDetailTopListResponse emptyResponse = new StoreMessageDetailTopListResponse();
            emptyResponse.setMessageDetailVOList(Collections.emptyList());
            emptyResponse.setUnReadCount(Constants.NUM_0L);
            return BaseResponse.success(emptyResponse);
        }
        topListRequest.setJoinIdList(nodeIdListByAuth);
        return storeMessageDetailQueryProvider.topList(topListRequest);
    }

    @Operation(summary = "根据id查询商家消息/公告")
    @GetMapping("/{id}")
    public BaseResponse<StoreMessageDetailByIdResponse> getById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreMessageDetailByIdRequest idReq = new StoreMessageDetailByIdRequest();
        idReq.setId(id);
        idReq.setStoreId(getStoreId());
        BaseResponse<StoreMessageDetailByIdResponse> response = storeMessageDetailQueryProvider.getById(idReq);
        StoreMessageDetailVO messageDetail = response.getContext().getStoreMessageDetailVO();
        if (Objects.isNull(messageDetail)) {
            return response;
        }
        if (StoreMessageType.NOTICE == messageDetail.getMessageType()) {
            // 若消息类型为公告，则查询并填充公告内容
            StoreNoticeSendVO notice = storeNoticeSendQueryProvider.getById(StoreNoticeSendByIdRequest.builder()
                    .id(messageDetail.getJoinId()).build()).getContext().getStoreNoticeSendVO();
            messageDetail.setContent(notice.getContent());
        } else {
            // 路由参数
            JSONObject routeParam = JSONObject.parseObject(messageDetail.getRouteParam());
            if (Objects.nonNull(routeParam)) {
                String nodeCode = routeParam.getString("nodeCode");
                // 特殊处理一些审核类消息的路由参数
                // 1.1 商家商品待审核，二审记录不存在时，需要给前端标识，跳转至商品列表详情页
                // 1.2 供应商商品待审核，二审记录不存在时，需要给前端标识，跳转至供货商商品列表详情页
                if (BossMessageNode.SUPPLIER_GOODS_WAIT_AUDIT.getCode().equals(nodeCode)
                        || BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT.getCode().equals(nodeCode)) {
                    String newGoodsId = routeParam.getString("newGoodsId");
                    String oldGoodsId = routeParam.getString("oldGoodsId");
                    if (StringUtils.isNotBlank(newGoodsId)) {
                        GoodsAuditVO goodsAuditVO = goodsAuditQueryProvider.getById(
                                GoodsAuditByIdRequest.builder().goodsId(newGoodsId).build()).getContext().getGoodsAuditVO();
                        if (Objects.isNull(goodsAuditVO) || DeleteFlag.YES == goodsAuditVO.getDelFlag()) {
                            // 审核记录不存在或已被删除，填充标识
                            routeParam.put("auditDelFlag", true);
                            if (Objects.nonNull(goodsAuditVO) && Objects.isNull(oldGoodsId)) {
                                // 审核记录存在，主goodsId不存在时，根据goodsNo查询（这里可能不精准，因为goodsNo有可能会改变）
                                EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
                                goodsInfoQueryRequest.setLikeGoodsNo(goodsAuditVO.getGoodsNo());
                                goodsInfoQueryRequest.setStoreId(goodsAuditVO.getStoreId());
                                List<GoodsVO> goodsList = goodsQueryProvider.listByCondition(GoodsByConditionRequest.builder()
                                        .goodsNo(goodsAuditVO.getGoodsNo())
                                        .storeId(goodsAuditVO.getStoreId())
                                        .build()).getContext().getGoodsVOList();
                                if (CollectionUtils.isNotEmpty(goodsList)) {
                                    oldGoodsId = goodsList.get(0).getGoodsId();
                                    routeParam.put("oldGoodsId", oldGoodsId);
                                }
                            }
                            if (BossMessageNode.PROVIDER_GOODS_WAIT_AUDIT.getCode().equals(nodeCode) && Objects.nonNull(oldGoodsId)) {
                                // 供应商商品，要额外查询供应商品库的standardId
                                List<String> standardIds = standardGoodsQueryProvider.listStandardIdsByGoodsIds(StandardIdsByGoodsIdsRequest.builder()
                                        .goodsIds(Collections.singletonList(oldGoodsId))
                                        .build())
                                        .getContext().getStandardIds();
                                routeParam.put("standardId", standardIds.stream().findFirst().orElse(null));
                            }
                        }
                    }
                }
                // 回填路由参数
                messageDetail.setRouteParam(routeParam.toJSONString());
            }
        }
        return response;
    }

    @Operation(summary = "批量已读商家消息/公告")
    @PutMapping("/batchRead")
    public BaseResponse batchRead(@RequestBody StoreMessageDetailListRequest batchReadRequest) {
        batchReadRequest.setStoreId(getStoreId());
        batchReadRequest.setDelFlag(DeleteFlag.NO);
        batchReadRequest.setIsRead(ReadFlag.NO);
        batchReadRequest.setUpdatePerson(commonUtil.getOperatorId());
        if (StoreMessageType.MESSAGE == batchReadRequest.getMessageType()) {
            // 仅筛选消息时，需要填充节点权限
            List<Long> nodeIdListByAuth = this.getNodeIdListByAuth();
            if (CollectionUtils.isEmpty(nodeIdListByAuth)) {
                // 无任何权限时，直接返回
                return BaseResponse.SUCCESSFUL();
            }
            batchReadRequest.setJoinIdList(nodeIdListByAuth);
        }
        return storeMessageDetailProvider.batchRead(batchReadRequest);
    }

    @Operation(summary = "根据id删除商家消息/公告")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreMessageDetailDelByIdRequest delByIdReq = new StoreMessageDetailDelByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setStoreId(getStoreId());
        return storeMessageDetailProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据id已读商家消息/公告")
    @PutMapping("/read/{id}")
    public BaseResponse readById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreMessageDetailReadByIdRequest readByIdRequest = new StoreMessageDetailReadByIdRequest();
        readByIdRequest.setId(id);
        readByIdRequest.setStoreId(getStoreId());
        readByIdRequest.setUpdatePerson(commonUtil.getOperatorId());
        return storeMessageDetailProvider.readById(readByIdRequest);
    }

    @Operation(summary = "根据idList批量删除商家消息/公告")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody StoreMessageDetailDelByIdListRequest delByIdListReq) {
        delByIdListReq.setStoreId(getStoreId());
        return storeMessageDetailProvider.deleteByIdList(delByIdListReq);
    }

    /**
     * 获取商家ID
     * @return
     */
    private Long getStoreId() {
        return commonUtil.getOperator().getPlatform() == Platform.PLATFORM ? Constants.BOSS_DEFAULT_STORE_ID : commonUtil.getStoreId();
    }

    /**
     * 获取登录账号有权限访问的节点id列表
     * @return
     */
    private List<Long> getNodeIdListByAuth() {
        // 获取当前平台下所有的消息节点
        StoreMessageNodeListRequest nodeListReq = new StoreMessageNodeListRequest();
        nodeListReq.setPlatformType(StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform()));
        nodeListReq.setDelFlag(DeleteFlag.NO);
        List<StoreMessageNodeVO> nodeVOList = storeMessageNodeQueryProvider.list(nodeListReq).getContext().getStoreMessageNodeVOList();
        // 收集节点功能权限列表
        List<String> allFunctionNames = nodeVOList.stream().map(StoreMessageNodeVO::getFunctionName).collect(Collectors.toList());
        // 判断登录用户是否具备这些功能
        List<String> todoFunctionIds = authBaseService.findTodoFunctionIds(allFunctionNames);
        return nodeVOList.stream().filter(item -> todoFunctionIds.contains(item.getFunctionName()))
                .map(StoreMessageNodeVO::getId).collect(Collectors.toList());
    }

}
