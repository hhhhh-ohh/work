package com.wanmi.sbc.recommend.goodsrelatedrecommend;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.goodsrelatedrecommend.GoodsRelatedRecommendProvider;
import com.wanmi.sbc.vas.api.provider.recommend.goodsrelatedrecommend.GoodsRelatedRecommendQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.goodsrelatedrecommend.*;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Tag(name =  "商品相关性推荐管理API", description =  "GoodsRelatedRecommendController")
@RestController
@Validated
@RequestMapping(value = "/goodsrelatedrecommend")
public class GoodsRelatedRecommendController {

    @Autowired
    private GoodsRelatedRecommendQueryProvider goodsRelatedRecommendQueryProvider;

    @Autowired
    private GoodsRelatedRecommendProvider goodsRelatedRecommendProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询商品相关性推荐")
    @PostMapping("/page")
    public BaseResponse<GoodsRelatedRecommendPageResponse> getPage(@RequestBody @Valid GoodsRelatedRecommendPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return goodsRelatedRecommendQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询商品相关性推荐")
    @PostMapping("/list")
    public BaseResponse<GoodsRelatedRecommendListResponse> getList(@RequestBody @Valid GoodsRelatedRecommendListRequest listReq) {
        return goodsRelatedRecommendQueryProvider.list(listReq);
    }

    @Operation(summary = "列表查询商品相关性推荐")
    @PostMapping("/getGoodsRelatedRecommendInfoList")
    public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendInfoList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest request) {
        if(Objects.nonNull(request.getGoodsCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(request.getGoodsCateId());
            request.setGoodsCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getGoodsCateIds())) {
                request.setGoodsCateId(null);
            }
        }
        return goodsRelatedRecommendQueryProvider.getGoodsRelatedRecommendInfoList(request);
    }

    @Operation(summary = "列表查询商品相关性推荐详情")
    @PostMapping("/getGoodsRelatedRecommendDetailInfoList")
    public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendDetailInfoList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest request) {
        return goodsRelatedRecommendQueryProvider.getGoodsRelatedRecommendDetailInfoList(request);
    }

    @Operation(summary = "列表查询商品相关性推荐--关联商品设置列表")
    @PostMapping("/getGoodsRelatedRecommendDataInfoList")
    public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendDataInfoList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest request) {
        return goodsRelatedRecommendQueryProvider.getGoodsRelatedRecommendDataInfoList(request);
    }

    @Operation(summary = "列表查询商品相关性推荐--关联选择商品设置列表")
    @PostMapping("/getGoodsRelatedRecommendChooseList")
    public BaseResponse<GoodsRelatedRecommendInfoPageResponse> getGoodsRelatedRecommendChooseList(@RequestBody @Valid GoodsRelatedRecommendInfoListRequest request) {
        if(Objects.nonNull(request.getGoodsCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(request.getGoodsCateId());
            request.setGoodsCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getGoodsCateIds())) {
                request.setGoodsCateId(null);
            }
        }
        return goodsRelatedRecommendQueryProvider.getGoodsRelatedRecommendChooseList(request);
    }

    @Operation(summary = "根据id查询商品相关性推荐")
    @GetMapping("/{id}")
    public BaseResponse<GoodsRelatedRecommendByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsRelatedRecommendByIdRequest idReq = new GoodsRelatedRecommendByIdRequest();
        idReq.setId(id);
        return goodsRelatedRecommendQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增商品相关性推荐")
    @PostMapping("/add")
    public BaseResponse<GoodsRelatedRecommendAddResponse> add(@RequestBody @Valid GoodsRelatedRecommendAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return goodsRelatedRecommendProvider.add(addReq);
    }

    @Operation(summary = "批量新增商品相关性推荐")
    @PostMapping("/addList")
    public BaseResponse<GoodsRelatedRecommendListResponse> addList(@RequestBody @Valid GoodsRelatedRecommendAddListRequest addListReq) {
        GoodsRelatedRecommendListRequest listReq = new GoodsRelatedRecommendListRequest();
        List<String> relatedGoodsIds = new ArrayList<>();
        addListReq.getAddGoodsRelatedRecommendAddList().forEach(goodsRelatedRecommendAddRequest -> {
            goodsRelatedRecommendAddRequest.setCreatePerson(commonUtil.getOperatorId());
            goodsRelatedRecommendAddRequest.setCreateTime(LocalDateTime.now());
            relatedGoodsIds.add(goodsRelatedRecommendAddRequest.getRelatedGoodsId());
            listReq.setGoodsId(goodsRelatedRecommendAddRequest.getGoodsId());
        });
        listReq.setRelatedGoodsIds(relatedGoodsIds);
        GoodsRelatedRecommendListResponse goodsRelatedRecommendListResponse =
                goodsRelatedRecommendQueryProvider.list(listReq).getContext();
        if(Objects.nonNull(goodsRelatedRecommendListResponse.getGoodsRelatedRecommendVOList())&&
                CollectionUtils.isEmpty(goodsRelatedRecommendListResponse.getGoodsRelatedRecommendVOList())){
            goodsRelatedRecommendProvider.addList(addListReq);
        }
        return BaseResponse.success(goodsRelatedRecommendListResponse);
    }

    @Operation(summary = "修改商品相关性推荐")
    @PutMapping("/modify")
    public BaseResponse<GoodsRelatedRecommendModifyResponse> modify(@RequestBody @Valid GoodsRelatedRecommendModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return goodsRelatedRecommendProvider.modify(modifyReq);
    }

    @Operation(summary = "修改商品相关性推荐")
    @PutMapping("/updateWeight")
    public BaseResponse updateWeight(@RequestBody @Valid GoodsRelatedRecommendUpdateWeightRequest updateWeightRequest) {
        return goodsRelatedRecommendProvider.updateWeight(updateWeightRequest);
    }

    @Operation(summary = "根据id删除商品相关性推荐")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsRelatedRecommendDelByIdRequest delByIdReq = new GoodsRelatedRecommendDelByIdRequest();
        delByIdReq.setId(id);
        return goodsRelatedRecommendProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "导出商品相关性推荐列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        GoodsRelatedRecommendListRequest listReq = JSON.parseObject(decrypted, GoodsRelatedRecommendListRequest.class);
        List<GoodsRelatedRecommendVO> dataRecords = goodsRelatedRecommendQueryProvider.list(listReq).getContext().getGoodsRelatedRecommendVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("商品相关性推荐列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            exportDataList(dataRecords, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 导出列表数据具体实现
     */
    private void exportDataList(List<GoodsRelatedRecommendVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("商品id", new SpelColumnRender<GoodsRelatedRecommendVO>("goodsId")),
            new Column("关联商品id", new SpelColumnRender<GoodsRelatedRecommendVO>("relatedGoodsId")),
            new Column("提升度", new SpelColumnRender<GoodsRelatedRecommendVO>("lift")),
            new Column("权重", new SpelColumnRender<GoodsRelatedRecommendVO>("weight")),
            new Column("类型，0：关联分析，1：手动关联", new SpelColumnRender<GoodsRelatedRecommendVO>("type"))
        };
        excelHelper.addSheet("商品相关性推荐列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
