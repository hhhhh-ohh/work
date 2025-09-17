package com.wanmi.sbc.recommend.recommendcatemanage;

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
import com.wanmi.sbc.vas.api.provider.recommend.recommendcatemanage.RecommendCateManageProvider;
import com.wanmi.sbc.vas.api.provider.recommend.recommendcatemanage.RecommendCateManageQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.*;
import com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage.*;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageVO;
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


@Tag(name =  "分类推荐管理管理API", description =  "RecommendCateManageController")
@RestController
@Validated
@RequestMapping(value = "/recommendcatemanage")
public class RecommendCateManageController {

    @Autowired
    private RecommendCateManageQueryProvider recommendCateManageQueryProvider;

    @Autowired
    private RecommendCateManageProvider recommendCateManageProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询分类推荐管理")
    @PostMapping("/page")
    public BaseResponse<RecommendCateManagePageResponse> getPage(@RequestBody @Valid RecommendCateManagePageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return recommendCateManageQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询分类推荐管理")
    @PostMapping("/list")
    public BaseResponse<RecommendCateManageListResponse> getList(@RequestBody @Valid RecommendCateManageListRequest listReq) {
        return recommendCateManageQueryProvider.list(listReq);
    }

    @Operation(summary = "列表查询分类推荐管理")
    @PostMapping("/getRecommendCateInfoList")
    public BaseResponse<RecommendCateManageInfoListResponse> getRecommendCateInfoList(@RequestBody @Valid RecommendCateManageInfoListRequest listReq) {
        if(Objects.nonNull(listReq.getCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(listReq.getCateId());
            listReq.setCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(listReq.getCateIds())) {
                listReq.setCateId(null);
            }
        }
        return recommendCateManageQueryProvider.getRecommendCateInfoList(listReq);
    }

    @Operation(summary = "根据id查询分类推荐管理")
    @GetMapping("/{id}")
    public BaseResponse<RecommendCateManageByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendCateManageByIdRequest idReq = new RecommendCateManageByIdRequest();
        idReq.setId(id);
        return recommendCateManageQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增分类推荐管理")
    @PostMapping("/add")
    public BaseResponse<RecommendCateManageAddResponse> add(@RequestBody @Valid RecommendCateManageAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return recommendCateManageProvider.add(addReq);
    }

    @Operation(summary = "新增分类推荐管理")
    @PostMapping("/addList")
    public BaseResponse addList(@RequestBody @Valid RecommendCateManageAddListRequest addListReq) {
        addListReq.getRecommendCateManageAddRequestList().forEach(recommendCateManageAddRequest -> {
            recommendCateManageAddRequest.setCreatePerson(commonUtil.getOperatorId());
            recommendCateManageAddRequest.setCreateTime(LocalDateTime.now());
        });
        return recommendCateManageProvider.addList(addListReq);
    }

    @Operation(summary = "修改分类推荐管理")
    @PutMapping("/modify")
    public BaseResponse<RecommendCateManageModifyResponse> modify(@RequestBody @Valid RecommendCateManageModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return recommendCateManageProvider.modify(modifyReq);
    }

    @Operation(summary = "修改分类推荐管理禁推状态")
    @PutMapping("/updateCateNoPushType")
    public BaseResponse updateCateNoPushType(@RequestBody @Valid RecommendCateManageUpdateNoPushTypeRequest request) {
        return recommendCateManageProvider.updateCateNoPushType(request);
    }

    @Operation(summary = "修改分类推荐管理禁推状态")
    @PutMapping("/updateCateWeight")
    public BaseResponse updateCateWeight(@RequestBody @Valid RecommendCateManageUpdateWeightRequest request) {
        return recommendCateManageProvider.updateCateWeight(request);
    }

    @Operation(summary = "根据id删除分类推荐管理")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendCateManageDelByIdRequest delByIdReq = new RecommendCateManageDelByIdRequest();
        delByIdReq.setId(id);
        return recommendCateManageProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除分类推荐管理")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid RecommendCateManageDelByIdListRequest delByIdListReq) {
        return recommendCateManageProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出分类推荐管理列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        RecommendCateManageListRequest listReq = JSON.parseObject(decrypted, RecommendCateManageListRequest.class);
        List<RecommendCateManageVO> dataRecords = recommendCateManageQueryProvider.list(listReq).getContext().getRecommendCateManageVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("分类推荐管理列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<RecommendCateManageVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("类目id", new SpelColumnRender<RecommendCateManageVO>("cateId")),
            new Column("权重", new SpelColumnRender<RecommendCateManageVO>("weight")),
            new Column("禁推标识 0：可推送；1:禁推", new SpelColumnRender<RecommendCateManageVO>("noPushType"))
        };
        excelHelper.addSheet("分类推荐管理列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
