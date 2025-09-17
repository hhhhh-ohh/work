package com.wanmi.sbc.recommend.recommendgoodsmanage;

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
import com.wanmi.sbc.vas.api.provider.recommend.recommendgoodsmanage.RecommendGoodsManageProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendgoodsmanage.RecommendGoodsManageQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendgoodsmanage.*;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageVO;
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
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Tag(name =  "商品推荐管理管理API", description =  "RecommendGoodsManageController")
@RestController
@Validated
@RequestMapping(value = "/recommendgoodsmanage")
public class RecommendGoodsManageController {

    @Autowired
    private RecommendGoodsManageQueryProvider recommendGoodsManageQueryProvider;

    @Autowired
    private RecommendGoodsManageProvider recommendGoodsManageProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询商品推荐管理")
    @PostMapping("/page")
    public BaseResponse<RecommendGoodsManagePageResponse> getPage(@RequestBody @Valid RecommendGoodsManagePageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return recommendGoodsManageQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询商品推荐管理")
    @PostMapping("/list")
    public BaseResponse<RecommendGoodsManageListResponse> getList(@RequestBody @Valid RecommendGoodsManageListRequest listReq) {
        return recommendGoodsManageQueryProvider.list(listReq);
    }

    @Operation(summary = "列表查询商品推荐管理")
    @PostMapping("/getRecommendGoodsInfoList")
    public BaseResponse<RecommendGoodsManageInfoListResponse> getRecommendGoodsInfoList(@RequestBody @Valid RecommendGoodsManageListRequest request){
        if(Objects.nonNull(request.getGoodsCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(request.getGoodsCateId());
            request.setGoodsCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getGoodsCateIds())) {
                request.setGoodsCateId(null);
            }
        }
        return recommendGoodsManageQueryProvider.getRecommendGoodsInfoList(request);
    }

    @Operation(summary = "根据id查询商品推荐管理")
    @GetMapping("/{id}")
    public BaseResponse<RecommendGoodsManageByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendGoodsManageByIdRequest idReq = new RecommendGoodsManageByIdRequest();
        idReq.setId(id);
        return recommendGoodsManageQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增商品推荐管理")
    @PostMapping("/add")
    public BaseResponse<RecommendGoodsManageAddResponse> add(@RequestBody @Valid RecommendGoodsManageAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return recommendGoodsManageProvider.add(addReq);
    }

    @Operation(summary = "批量新增商品推荐管理禁推状态")
    @PostMapping("/addList")
    public BaseResponse addList(@RequestBody @Valid RecommendGoodsManageAddListRequest recommendGoodsManageAddListRequest) {
        return recommendGoodsManageProvider.addList(recommendGoodsManageAddListRequest);
    }

    @Operation(summary = "修改商品推荐管理")
    @PutMapping("/modify")
    public BaseResponse<RecommendGoodsManageModifyResponse> modify(@RequestBody @Valid RecommendGoodsManageModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return recommendGoodsManageProvider.modify(modifyReq);
    }

    @Operation(summary = "修改商品推荐管理禁推状态")
    @PutMapping("/updateNoPush")
    public BaseResponse updateNoPush(@RequestBody @Valid RecommendGoodsManageUpdateNoPushRequest updateNoPushRequest) {
        return recommendGoodsManageProvider.updateNoPush(updateNoPushRequest);
    }

    @Operation(summary = "修改商品推荐管理权重")
    @PutMapping("/updateWeight")
    public BaseResponse updateWeight(@RequestBody @Valid RecommendGoodsManageUpdateWeightRequest updateWeightRequest) {
        return recommendGoodsManageProvider.updateWeight(updateWeightRequest);
    }

    @Operation(summary = "根据id删除商品推荐管理")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendGoodsManageDelByIdRequest delByIdReq = new RecommendGoodsManageDelByIdRequest();
        delByIdReq.setId(id);
        return recommendGoodsManageProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除商品推荐管理")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid RecommendGoodsManageDelByIdListRequest delByIdListReq) {
        return recommendGoodsManageProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出商品推荐管理列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        RecommendGoodsManageListRequest listReq = JSON.parseObject(decrypted, RecommendGoodsManageListRequest.class);
        List<RecommendGoodsManageVO> dataRecords = recommendGoodsManageQueryProvider.list(listReq).getContext().getRecommendGoodsManageVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("商品推荐管理列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<RecommendGoodsManageVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("商品id", new SpelColumnRender<RecommendGoodsManageVO>("goodsId")),
            new Column("权重", new SpelColumnRender<RecommendGoodsManageVO>("weight")),
            new Column("禁推标识 0：可推送；1:禁推", new SpelColumnRender<RecommendGoodsManageVO>("noPushType"))
        };
        excelHelper.addSheet("商品推荐管理列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
