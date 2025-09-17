package com.wanmi.sbc.recommend.caterelatedrecommend;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.caterelatedrecommend.CateRelatedRecommendProvider;
import com.wanmi.sbc.vas.api.provider.recommend.caterelatedrecommend.CateRelatedRecommendQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Tag(name =  "分类相关性推荐管理API", description =  "CateRelatedRecommendController")
@RestController
@Validated
@RequestMapping(value = "/caterelatedrecommend")
public class CateRelatedRecommendController {

    @Autowired
    private CateRelatedRecommendQueryProvider cateRelatedRecommendQueryProvider;

    @Autowired
    private CateRelatedRecommendProvider cateRelatedRecommendProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Operation(summary = "分页查询分类相关性推荐")
    @PostMapping("/page")
    public BaseResponse<CateRelatedRecommendPageResponse> getPage(@RequestBody @Valid CateRelatedRecommendPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return cateRelatedRecommendQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询分类相关性推荐")
    @PostMapping("/list")
    public BaseResponse<CateRelatedRecommendListResponse> getList(@RequestBody @Valid CateRelatedRecommendListRequest listReq) {
        return cateRelatedRecommendQueryProvider.list(listReq);
    }

    @Operation(summary = "基于商品相关性推荐--按类目查看--合并")
    @PostMapping("/getCateRelateRecommendInfoList")
    public BaseResponse<CateRelatedRecommendInfoListResponse> getCateRelateRecommendInfoList(@RequestBody @Valid CateRelatedRecommendInfoListRequest request) {
        if(Objects.nonNull(request.getCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(request.getCateId());
            request.setCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.setCateId(null);
            }
        }
        return cateRelatedRecommendQueryProvider.getCateRelateRecommendInfoList(request);
    }

    @Operation(summary = "基于商品相关性推荐--按类目查看--逐条")
    @PostMapping("/getCateRelateRecommendDetailList")
    public BaseResponse<CateRelatedRecommendDetailListResponse> getCateRelateRecommendDetailList(@RequestBody @Valid CateRelatedRecommendDetailListRequest request) {
        return cateRelatedRecommendQueryProvider.getCateRelateRecommendDetailList(request);
    }

    @Operation(summary = "根据id查询分类相关性推荐")
    @GetMapping("/{id}")
    public BaseResponse<CateRelatedRecommendByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CateRelatedRecommendByIdRequest idReq = new CateRelatedRecommendByIdRequest();
        idReq.setId(id);
        return cateRelatedRecommendQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增分类相关性推荐")
    @PostMapping("/add")
    public BaseResponse<CateRelatedRecommendAddResponse> add(@RequestBody @Valid CateRelatedRecommendAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return cateRelatedRecommendProvider.add(addReq);
    }

    @Operation(summary = "新增分类相关性推荐")
    @PostMapping("/addList")
    public BaseResponse<CateRelatedRecommendListResponse> addList(@RequestBody @Valid CateRelatedRecommendAddListRequest addListReq) {
        CateRelatedRecommendListRequest listReq = new CateRelatedRecommendListRequest();
        List<String> relatedCateIds = new ArrayList<>();
        addListReq.getCateRelatedRecommendAddRequestList().forEach(addReq->{
            addReq.setCreatePerson(commonUtil.getOperatorId());
            addReq.setCreateTime(LocalDateTime.now());
            relatedCateIds.add(addReq.getRelatedCateId());
            listReq.setCateId(addReq.getCateId());
        });
        listReq.setRelatedCateIds(relatedCateIds);
        CateRelatedRecommendListResponse cateRelatedRecommendListResponse =
                cateRelatedRecommendQueryProvider.list(listReq).getContext();
        if(Objects.nonNull(cateRelatedRecommendListResponse)&& CollectionUtils.isEmpty(cateRelatedRecommendListResponse.getCateRelatedRecommendVOList())){
            cateRelatedRecommendProvider.addList(addListReq);
        }
        return BaseResponse.success(cateRelatedRecommendListResponse);
    }

    @Operation(summary = "修改分类相关性推荐")
    @PutMapping("/modify")
    public BaseResponse<CateRelatedRecommendModifyResponse> modify(@RequestBody @Valid CateRelatedRecommendModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return cateRelatedRecommendProvider.modify(modifyReq);
    }

    @Operation(summary = "修改分类相关性推荐权重")
    @PutMapping("/updateWeight")
    public BaseResponse<CateRelatedRecommendModifyResponse> updateWeight(@RequestBody @Valid CateRelatedRecommendUpdateWeightRequest request) {
        // 权重不为null，校验范围0-1000
        if (Objects.nonNull(request.getWeight())){
            if (request.getWeight().compareTo(BigDecimal.ZERO)<0 || request.getWeight().compareTo(new BigDecimal(Constants.NUM_1000))>0){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return cateRelatedRecommendProvider.updateWeight(request);
    }

    @Operation(summary = "根据id删除分类相关性推荐")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CateRelatedRecommendDelByIdRequest delByIdReq = new CateRelatedRecommendDelByIdRequest();
        delByIdReq.setId(id);
        return cateRelatedRecommendProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "导出分类相关性推荐列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        CateRelatedRecommendListRequest listReq = JSON.parseObject(decrypted, CateRelatedRecommendListRequest.class);
        List<CateRelatedRecommendVO> dataRecords = cateRelatedRecommendQueryProvider.list(listReq).getContext().getCateRelatedRecommendVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("分类相关性推荐列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<CateRelatedRecommendVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("分类id", new SpelColumnRender<CateRelatedRecommendVO>("cateId")),
            new Column("关联分类id", new SpelColumnRender<CateRelatedRecommendVO>("relatedCateId")),
            new Column("提升度", new SpelColumnRender<CateRelatedRecommendVO>("lift")),
            new Column("权重", new SpelColumnRender<CateRelatedRecommendVO>("weight")),
            new Column("类型，0：关联分析，1：手动关联", new SpelColumnRender<CateRelatedRecommendVO>("type"))
        };
        excelHelper.addSheet("分类相关性推荐列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
