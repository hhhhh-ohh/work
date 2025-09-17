package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodspropcaterel.GoodsPropCateRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.prop.GoodsPropQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListCateByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.request.prop.GoodsPropListAllByCateIdRequest;
import com.wanmi.sbc.goods.api.response.prop.GoodsPropListAllByCateIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.enums.CateParentTop;
import com.wanmi.sbc.goods.bean.vo.ContractCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 商品分类服务
 * Created by chenli on 17/10/30.
 */
@Tag(name = "GoodsCateController", description = "商品分类服务")
@RestController
@Validated
@RequestMapping("/goods")
public class GoodsCateController {

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsPropQueryProvider goodsPropQueryProvider;

    @Autowired
    private GoodsPropCateRelQueryProvider goodsPropCateRelQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;


    /**
     * 查询有效分类平台分类（有3级分类的）
     * @return
     */
    @Operation(summary = "查询有效分类平台分类（有3级分类的）")
    @RequestMapping(value = "/goodsCatesTree", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> queryGoodsCate(){
        return BaseResponse.success(goodsCateQueryProvider.list().getContext().getGoodsCateVOList());
    }

    /**
     * 查询有效分类平台分类（有3级分类的）
     * @return
     */
    @Operation(summary = "查询有效分类平台分类（有3级分类的）")
    @RequestMapping(value = "/goodsCatesTreeForStore", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> queryGoodsCateForStore(){
        Platform platform = commonUtil.getOperator().getPlatform();
        if (platform.equals(Platform.SUPPLIER)) {
            ContractCateListCateByStoreIdRequest request = new ContractCateListCateByStoreIdRequest();
            request.setStoreId(commonUtil.getStoreId());
            List<GoodsCateVO> goodsCateList = contractCateQueryProvider.listCateByStoreId(request).getContext().getGoodsCateList();
            List<GoodsCateVO> treeList = this.recursiveTree(goodsCateList, (long) (CateParentTop.ZERO.toValue()));
            if (CollectionUtils.isNotEmpty(treeList)) {
                List<GoodsCateVO> tpList = new ArrayList<>();
                List<GoodsCateVO> chList = new ArrayList<>();
                for (GoodsCateVO t : treeList) {
                    List<GoodsCateVO> cates = t.getGoodsCateList();
                    for (GoodsCateVO i : cates) {
                        if (i.getGoodsCateList().isEmpty()) {
                            chList.add(i);
                        }
                    }
                    cates.removeAll(chList);
                    if (cates.isEmpty()) {
                        tpList.add(t);
                    }
                }
                treeList.removeAll(tpList);
            }
            return BaseResponse.success(treeList);
        }
        return BaseResponse.success(goodsCateQueryProvider.list().getContext().getGoodsCateVOList());
    }


    //递归->树形结构
    private List<GoodsCateVO> recursiveTree(List<GoodsCateVO> source, Long parentId) {
        List<GoodsCateVO> res = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(source)) {
            source.stream().filter(goodsCate -> Objects.equals(parentId, goodsCate.getCateParentId())).forEach(goodsCate -> {
                goodsCate.setGoodsCateList(recursiveTree(source, goodsCate.getCateId()));
                res.add(goodsCate);
            });
        }
        return res;
    }

    /**
     * 查询有效分类平台分类,标识是否映射微信类目
     * @return
     */
    @Operation(summary = "查询有效分类平台分类,标识是否映射微信类目")
    @RequestMapping(value = "/queryForMapWechat", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> queryForMapWechat(){
        return BaseResponse.success(goodsCateQueryProvider.queryForMapWechat().getContext());
    }

    /**
     * 查询有效分类平台分类（所有的）
     * @return
     */
    @Operation(summary = "查询有效分类平台分类（有3级分类的）")
    @RequestMapping(value = "/goodsCatesTreeAll", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> queryGoodsCateAll(){
        return BaseResponse.success(goodsCateQueryProvider.listAll().getContext().getGoodsCateVOList());
    }

    /**
     * 查询分类下所有的属性信息
     * @return
     */
    @Operation(summary = "查询分类下所有的属性信息")
    @Parameter(name = "cateId", description = "分类ID", required = true)
    @RequestMapping(value = "/goodsProp/{cateId}",method = RequestMethod.GET)
    public BaseResponse<List<GoodsPropVO>> list(@PathVariable Long cateId) {
        BaseResponse<GoodsPropListAllByCateIdResponse> baseResponse  = goodsPropQueryProvider.listAllByCateId(new GoodsPropListAllByCateIdRequest(cateId));
        GoodsPropListAllByCateIdResponse response = baseResponse.getContext();
        if (Objects.isNull(response)){
            return BaseResponse.success(Collections.emptyList());
        }
        return BaseResponse.success(response.getGoodsPropVOList());
    }

    /**
     * 分页查询商品属性
     *
     * @param cateId
     * @return
     */
    @Operation(summary = "商品属性值")
    @GetMapping(value = "/prop-detail/{cateId}")
    public BaseResponse<List<GoodsPropertyDetailRelDTO>> findGoodsPropertyPage(@PathVariable("cateId") Long cateId) {
        //越权校验
        if (!Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
            ContractCateListByConditionRequest request = new ContractCateListByConditionRequest();
            request.setStoreId(commonUtil.getStoreId());
            request.setCateId(cateId);
            List<ContractCateVO> contractCateList = contractCateQueryProvider.listByCondition(request)
                    .getContext()
                    .getContractCateList();
            if (CollectionUtils.isEmpty(contractCateList)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
        GoodsPropCateRelListRequest request = GoodsPropCateRelListRequest.builder()
                .cateId(cateId)
                .build();
        BaseResponse<List<GoodsPropertyDetailRelDTO>> response = goodsPropCateRelQueryProvider.findCateDetailProp(request);

        return response;
    }
}
