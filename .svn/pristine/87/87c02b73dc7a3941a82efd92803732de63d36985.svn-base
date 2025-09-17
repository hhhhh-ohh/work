package com.wanmi.sbc.distribution;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.distributionmatter.EsDistributionGoodsMatterProvider;
import com.wanmi.sbc.elastic.api.provider.distributionmatter.EsDistributionGoodsMatterQueryProvider;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDeleteByIdListRequest;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatteAddRequest;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatterPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionmatter.EsDistributionGoodsMatterPageResponse;
import com.wanmi.sbc.goods.api.provider.distributionmatter.DistributionGoodsMatterProvider;
import com.wanmi.sbc.goods.api.provider.distributionmatter.DistributionGoodsMatterQueryProvider;
import com.wanmi.sbc.goods.api.request.distributionmatter.DeleteByIdListRequest;
import com.wanmi.sbc.goods.api.request.distributionmatter.DistributionGoodsMatteAddRequest;
import com.wanmi.sbc.goods.api.request.distributionmatter.DistributionGoodsMatterModifyRequest;
import com.wanmi.sbc.goods.api.request.distributionmatter.DistributionGoodsMatterPageRequest;
import com.wanmi.sbc.goods.api.request.distributionmatter.QueryByIdListRequest;
import com.wanmi.sbc.goods.api.response.distributionmatter.DistributionGoodsMatterPageResponse;
import com.wanmi.sbc.goods.bean.enums.MatterType;
import com.wanmi.sbc.goods.bean.vo.DistributionGoodsMatterPageVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Tag(name = "DistributionGoodsMatterController", description = "分销商品素材")
@RestController
@Validated
@RequestMapping("/distribution/goods-matter")
public class DistributionGoodsMatterController {

    @Autowired
    private DistributionGoodsMatterQueryProvider distributionGoodsMatterQueryProvider;

    @Autowired
    private DistributionGoodsMatterProvider distributionGoodsMatterProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private DistributionCacheService distributionCacheService;
    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EsDistributionGoodsMatterProvider esDistributionGoodsMatterProvider;

    @Autowired
    private EsDistributionGoodsMatterQueryProvider esDistributionGoodsMatterQueryProvider;

    @Operation(summary = "分页分销商品素材")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<DistributionGoodsMatterPageResponse> page(@RequestBody @Valid DistributionGoodsMatterPageRequest distributionGoodsMatterPageRequest) {
        BaseResponse<DistributionGoodsMatterPageResponse> response =
                distributionGoodsMatterQueryProvider.page(distributionGoodsMatterPageRequest);
        return response;
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "新增分销商品素材")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseResponse add(@RequestBody @Valid DistributionGoodsMatteAddRequest distributionGoodsMatteAddRequest) {
        String operatorId = commonUtil.getOperatorId();
        distributionGoodsMatteAddRequest.setOperatorId(operatorId);
        // 商品素材，需要将分销开关及店铺状态，店铺签约结束日期加入到实体中
        if (distributionGoodsMatteAddRequest.getMatterType() == MatterType.GOODS){
            Long storeId = distributionGoodsMatteAddRequest.getStoreId();
            distributionGoodsMatteAddRequest.setOpenFlag(distributionCacheService.queryStoreOpenFlag(String.valueOf(storeId)));
            StoreVO store =
                    storeQueryProvider
                            .getNoDeleteStoreById(
                                    new NoDeleteStoreByIdRequest(storeId))
                            .getContext()
                            .getStoreVO();
            distributionGoodsMatteAddRequest.setStoreState(store.getStoreState());
            distributionGoodsMatteAddRequest.setContractEndDate(store.getContractEndDate());
        }
        BaseResponse<String> response = distributionGoodsMatterProvider.add(distributionGoodsMatteAddRequest);

        //同步es
        EsDistributionGoodsMatteAddRequest addRequest = KsBeanUtil.convert(distributionGoodsMatteAddRequest, EsDistributionGoodsMatteAddRequest.class);
        addRequest.setId(response.getContext());
        esDistributionGoodsMatterProvider.add(addRequest);
        //记录操作日志
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("分销商品素材", "新增分销商品素材", "SKU编码：" + distributionGoodsMatteAddRequest.getGoodsInfoId());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "修改分销商品素材")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public BaseResponse modify(@RequestBody @Valid DistributionGoodsMatterModifyRequest distributionGoodsMatterModifyRequest) {
        // 商品素材，需要将分销开关及店铺状态，店铺签约结束日期加入到实体中
        if (distributionGoodsMatterModifyRequest.getMatterType() == MatterType.GOODS){
            Long storeId = distributionGoodsMatterModifyRequest.getStoreId();
            distributionGoodsMatterModifyRequest.setOpenFlag(distributionCacheService.queryStoreOpenFlag(String.valueOf(storeId)));
            StoreVO store =
                    storeQueryProvider
                            .getNoDeleteStoreById(
                                    new NoDeleteStoreByIdRequest(storeId))
                            .getContext()
                            .getStoreVO();
            distributionGoodsMatterModifyRequest.setStoreState(store.getStoreState());
            distributionGoodsMatterModifyRequest.setContractEndDate(store.getContractEndDate());
        }
        //如果是营销素材，配置了素材链接的，跟着的小程序码得重新生成
        BaseResponse response = distributionGoodsMatterProvider.modify(distributionGoodsMatterProvider.updataQrcode(distributionGoodsMatterModifyRequest).getContext());


        //同步es
        EsDistributionGoodsMatteAddRequest addRequest = KsBeanUtil.convert(distributionGoodsMatterModifyRequest, EsDistributionGoodsMatteAddRequest.class);
        esDistributionGoodsMatterProvider.modify(addRequest);

        //记录操作日志
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("分销商品素材", "修改分销商品素材", "SKU编码：" + distributionGoodsMatterModifyRequest.getGoodsInfoId());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量删除分销商品素材")
    @RequestMapping(value = "/delete-list", method = RequestMethod.POST)
    public BaseResponse deleteList(@RequestBody @Valid DeleteByIdListRequest deleteByIdListRequest) {
        BaseResponse response = distributionGoodsMatterProvider.deleteList(deleteByIdListRequest);


        //删除es数据
        EsDeleteByIdListRequest esDeleteByIdListRequest = new EsDeleteByIdListRequest();
        esDeleteByIdListRequest.setIds(deleteByIdListRequest.getIds());
        esDistributionGoodsMatterProvider.deleteList(esDeleteByIdListRequest);
        //记录操作日志
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("分销商品素材", "批量删除分销商品素材", "SKU编码集合：" + deleteByIdListRequest.getIds());
        }
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "查询分销商品素材")
    @Parameter(name = "id", description = "id", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<DistributionGoodsMatterPageVO> query(@PathVariable String id) {
        QueryByIdListRequest request = new QueryByIdListRequest();
        List ids = new ArrayList();
        ids.add(id);
        request.setIds(ids);
        List<DistributionGoodsMatterPageVO> list = distributionGoodsMatterQueryProvider.queryByIds(request).getContext().getDistributionGoodsMatterList();
        if (list.size() > 0) {
            return BaseResponse.success(list.get(0));
        }
        //不存在则删除ES
        EsDeleteByIdListRequest esDeleteByIdListRequest = new EsDeleteByIdListRequest();
        esDeleteByIdListRequest.setIds(Collections.singletonList(id));
        esDistributionGoodsMatterProvider.deleteList(esDeleteByIdListRequest);
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "分页查询分销商品素材")
    @RequestMapping(value = "/page/new", method = RequestMethod.POST)
    public BaseResponse<EsDistributionGoodsMatterPageResponse> pageNew(@RequestBody EsDistributionGoodsMatterPageRequest distributionGoodsMatterPageRequest) {
        //BaseResponse<DistributionGoodsMatterPageResponse> response = distributionGoodsMatterQueryProvider.pageNewForBoss(distributionGoodsMatterPageRequest);

        return esDistributionGoodsMatterQueryProvider.pageNewForBoss(distributionGoodsMatterPageRequest);
    }
}
