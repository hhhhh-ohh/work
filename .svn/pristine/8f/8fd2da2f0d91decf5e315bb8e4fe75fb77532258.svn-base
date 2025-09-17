package com.wanmi.sbc.contract;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreContractAuditRequest;
import com.wanmi.sbc.customer.api.request.store.StoreInfoByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsBrandDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoStateModifyRequest;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandProvider;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.contract.ContractProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandAuditListRequest;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListVerifyByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandTransferByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateAuditListRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateDelVerifyRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListCateByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditCheckRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractSaveRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.ContractBrandAuditVO;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;
import com.wanmi.sbc.goods.bean.vo.ContractCateAuditVO;
import com.wanmi.sbc.goods.bean.vo.ContractCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商家签约信息(分类，品牌)
 * Created by sunkun on 2017/11/20.
 */
@RestController
@Validated
@RequestMapping("/contract")
@Tag(name = "BossContractController", description = "S2B 平台端-商家签约信息(分类，品牌)管理API")
public class BossContractController {

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private ContractBrandProvider contractBrandProvider;

    @Autowired
    private ContractProvider contractProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private ContractBrandAuditQueryProvider contractBrandAuditQueryProvider;

    @Autowired
    private ContractCateAuditQueryProvider contractCateAuditQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 签约信息新增修改删除(签约分类，签约品牌)
     *
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "签约信息新增修改删除(签约分类，签约品牌)")
    @RequestMapping(value = "/renewal", method = RequestMethod.POST)
    public BaseResponse renewalAll(@Valid @RequestBody ContractSaveRequest contractRequest) {
        if (contractRequest.getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询店铺的二次签约审核状态
        StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(
                StoreInfoByIdRequest.builder().storeId(contractRequest.getStoreId()).build()).getContext();

        if(Objects.nonNull(storeInfoResponse.getContractAuditState()) &&
                storeInfoResponse.getContractAuditState().equals(CheckState.WAIT_CHECK)){
            //存在待审核的签约信息 禁止编辑保存
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030010);
        }

        List<Long> ids =  contractProvider.save(contractRequest).getContext().getBrandIds();
        //取消签约品牌的时候更新es
        if (CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(contractRequest.getDelBrandIds())) {
            esGoodsInfoElasticProvider.delBrandIds(EsBrandDeleteByIdsRequest.builder()
                            .deleteIds(ids).storeId(contractRequest.getStoreId()).build());
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 二次签约审核操作
     * @param contractRequest
     * @return
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "二次签约信息审核(签约分类，签约品牌)")
    @RequestMapping(value = "/audit/renewal", method = RequestMethod.POST)
    public BaseResponse contractAuditCheck(@Valid @RequestBody ContractAuditCheckRequest contractRequest){
        //校验参数 店铺id不能为空
        if (contractRequest.getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (!Platform.PLATFORM.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        //检查店铺的审核状态
        //查询店铺的二次签约审核状态
        StoreInfoResponse storeInfoResponse = storeQueryProvider.getStoreInfoById(
                StoreInfoByIdRequest.builder().storeId(contractRequest.getStoreId()).build()).getContext();

        if(Objects.isNull(storeInfoResponse.getContractAuditState())
                || !storeInfoResponse.getContractAuditState().equals(CheckState.WAIT_CHECK)){
            //当前签约审核状态非待审核
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030011);
        }

        //检查店铺的二次签约信息
        if(contractRequest.getContractAuditState().equals(CheckState.CHECKED)){
            //审核通过
            operateLogMqUtil.convertAndSend("商家", "二次签约审核通过",
                    "审核店铺：" + storeInfoResponse.getStoreName());
        }else{
            //校验参数 驳回状态 原因不能为空
            if (Strings.isBlank(contractRequest.getContractAuditReason())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            operateLogMqUtil.convertAndSend("商家", "二次签约审核驳回",
                    "审核店铺：" + storeInfoResponse.getStoreName());
        }

        //审核操作
        contractProvider.checkAudit(contractRequest);

        //修改店铺的二次签约状态
        StoreContractAuditRequest storeContractAuditRequest = new StoreContractAuditRequest();
        storeContractAuditRequest.setStoreId(contractRequest.getStoreId());
        storeContractAuditRequest.setContractAuditState(contractRequest.getContractAuditState());
        storeContractAuditRequest.setContractAuditReason(contractRequest.getContractAuditReason());
        storeProvider.updateContractAuditState(storeContractAuditRequest);

        //更新店铺es数据
        StoreInfoStateModifyRequest storeInfoStateModifyRequest = new StoreInfoStateModifyRequest();
        storeInfoStateModifyRequest.setStoreId(contractRequest.getStoreId());
        storeInfoStateModifyRequest.setContractAuditState(contractRequest.getContractAuditState().toValue());
        storeInfoStateModifyRequest.setContractAuditReason(contractRequest.getContractAuditReason());
        storeInfoStateModifyRequest.setCompanyInfoId(storeInfoResponse.getCompanyInfoId());
        esStoreInformationProvider.modifyContractAuditState(storeInfoStateModifyRequest);
        if(!Objects.equals(contractRequest.getContractAuditState(),CheckState.WAIT_CHECK)){
            redisUtil.setString(RedisKeyConstant.IS_KNOW_SHOW_KEY.concat(String.valueOf(storeInfoResponse.getStoreId())),
                    String.valueOf(Boolean.TRUE));
        }
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 校验签约分类是否有关联商品
     *
     * @param cateId
     * @param storeId
     * @return
     */
    @Operation(summary = "校验签约分类是否有关联商品")
    @Parameters({
            @Parameter(name = "cateId", description = "分类Id", required = true),
            @Parameter(name = "storeId", description = "店铺Id", required = true)
    })
    @RequestMapping(value = "/cate/del/verify/{cateId}/{storeId}", method = RequestMethod.GET)
    public BaseResponse cateDelVerify(@PathVariable Long cateId, @PathVariable Long storeId) {
        ContractCateDelVerifyRequest request = new ContractCateDelVerifyRequest();
        request.setStoreId(storeId);
        request.setCateId(cateId);
        contractCateQueryProvider.cateDelVerify(request);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 获取商家签约分类列表
     *
     * @return
     */
    @Operation(summary = "获取商家签约分类列表")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/cate/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<ContractCateVO>> cateList(@PathVariable Long storeId) {
        ContractCateListRequest contractCateQueryRequest = new ContractCateListRequest();
        contractCateQueryRequest.setStoreId(storeId);
        return BaseResponse.success(contractCateQueryProvider.list(contractCateQueryRequest).getContext().getContractCateList()

        );
    }

    /**
     * 获取商家签约品牌列表
     *
     * @return
     */
    @Operation(summary = "获取商家签约品牌列表")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/brand/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<ContractBrandVO>> brandList(@PathVariable Long storeId) {
        ContractBrandListRequest contractBrandQueryRequest = new ContractBrandListRequest();
        contractBrandQueryRequest.setStoreId(storeId);
        return BaseResponse.success(contractBrandQueryProvider.list(contractBrandQueryRequest).getContext().getContractBrandVOList());
    }


    /**
     * 获取商家签约分类列表
     *
     * @return
     */
    @Operation(summary = "获取商家二次签约分类列表")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/cate/audit/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<ContractCateAuditVO>> cateAuditList(@PathVariable Long storeId) {
        ContractCateAuditListRequest contractCateQueryRequest = new ContractCateAuditListRequest();
        contractCateQueryRequest.setStoreId(storeId);
        contractCateQueryRequest.setDeleteFlag(DeleteFlag.NO);
        return BaseResponse.success(
                contractCateAuditQueryProvider.list(contractCateQueryRequest).getContext().getContractCateList());
    }

    /**
     * 获取商家签约品牌列表
     *
     * @return
     */
    @Operation(summary = "获取商家二次签约品牌列表")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/brand/audit/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<ContractBrandAuditVO>> brandAuditList(@PathVariable Long storeId) {
        ContractBrandAuditListRequest contractBrandQueryRequest = new ContractBrandAuditListRequest();
        contractBrandQueryRequest.setStoreId(storeId);
        return BaseResponse.success(contractBrandAuditQueryProvider.list(contractBrandQueryRequest).getContext().getContractBrandVOList());
    }


    /**
     * 校验商家自定义品牌是否与平台重复
     *
     * @param storeId
     * @return
     */
    @Operation(summary = "校验商家自定义品牌是否与平台重复")
    @Parameter(name = "storeId", description= "店铺Id", required = true)
    @RequestMapping(value = "/brand/list/verify/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<ContractBrandVO>> brandListVerify(@PathVariable Long storeId) {
        return BaseResponse.success(contractBrandQueryProvider.listVerifyByStoreId(
                ContractBrandListVerifyByStoreIdRequest.builder().storeId(storeId).build()
        ).getContext().getContractBrandVOList());
    }


    /**
     * 直接关联品牌
     * @param storeId
     * @return
     */
    @Operation(summary = "直接关联品牌")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/brand/relevance/{storeId}",method = RequestMethod.GET)
    public BaseResponse brandRelevance(@PathVariable Long storeId) {
         contractBrandProvider.transferByStoreId(
                ContractBrandTransferByStoreIdRequest.builder().storeId(storeId).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询商家签约的平台类目列表，包含所有的父级类目
     *
     * @return 平台类目列表
     */
    @Operation(summary = "查询商家签约的平台类目列表，包含所有的父级类目")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/goods/cate/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> listCate(@PathVariable Long storeId) {
        ContractCateListCateByStoreIdRequest request = new ContractCateListCateByStoreIdRequest();
        request.setStoreId(storeId);
        return BaseResponse.success(contractCateQueryProvider.listCateByStoreId(request).getContext().getGoodsCateList());
    }

    /**
     * 查询商家签约的平台类目列表，包含所有的父级类目
     *
     * @return 平台类目列表
     */
    @Operation(summary = "查询商家签约的平台类目列表，包含所有的父级类目")
    @RequestMapping(value = "/goods/cate/list", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> listCate() {
        ContractCateListCateByStoreIdRequest request = new ContractCateListCateByStoreIdRequest();
        request.setStoreId(commonUtil.getStoreId());
        return BaseResponse.success(contractCateQueryProvider.listCateByStoreId(request).getContext().getGoodsCateList());
    }

    /**
     * 查询商家签约的平台品牌列表
     *
     * @return 商家签约的平台品牌列表
     */
    @Operation(summary = "查询商家签约的平台品牌列表")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/goods/brand/list/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List<GoodsBrandVO>> listBrand(@PathVariable Long storeId) {
        ContractBrandListRequest request = new ContractBrandListRequest();
        request.setStoreId(storeId);
        List<GoodsBrandVO> brands = contractBrandQueryProvider.list(request).getContext().getContractBrandVOList()
                .stream().map(ContractBrandVO::getGoodsBrand).filter(Objects::nonNull).collect(Collectors.toList());
        return BaseResponse.success(brands);
    }

    /**
     * 查询商家签约的平台品牌列表
     *
     * @return 商家签约的平台品牌列表
     */
    @Operation(summary = "查询商家签约的平台品牌列表")
    @RequestMapping(value = "/goods/brand/list", method = RequestMethod.GET)
    public BaseResponse<List<GoodsBrandVO>> listBrand() {
        ContractBrandListRequest request = new ContractBrandListRequest();
        request.setStoreId(commonUtil.getStoreId());
        List<GoodsBrandVO> brands = contractBrandQueryProvider.list(request).getContext().getContractBrandVOList()
                .stream().map(ContractBrandVO::getGoodsBrand)
                .filter(Objects::nonNull).collect(Collectors.toList());
        return BaseResponse.success(brands);
    }
}
