package com.wanmi.sbc.recommend.manualrecommendgoods;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.ConfigUpdateRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.manualrecommendgoods.ManualRecommendGoodsProvider;
import com.wanmi.sbc.vas.api.provider.recommend.manualrecommendgoods.ManualRecommendGoodsQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.*;
import com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods.*;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsVO;
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


@Tag(name =  "手动推荐商品管理管理API", description =  "ManualRecommendGoodsController")
@RestController
@Validated
@RequestMapping(value = "/manualrecommendgoods")
public class ManualRecommendGoodsController {

    @Autowired
    private ManualRecommendGoodsQueryProvider manualRecommendGoodsQueryProvider;

    @Autowired
    private ManualRecommendGoodsProvider manualRecommendGoodsProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询手动推荐商品管理")
    @PostMapping("/page")
    public BaseResponse<ManualRecommendGoodsPageResponse> getPage(@RequestBody @Valid ManualRecommendGoodsPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return manualRecommendGoodsQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询手动推荐商品管理")
    @PostMapping("/list")
    public BaseResponse<ManualRecommendGoodsListResponse> getList(@RequestBody @Valid ManualRecommendGoodsListRequest listReq) {
        return manualRecommendGoodsQueryProvider.list(listReq);
    }

    @Operation(summary = "设置手动推荐开关")
    @PostMapping("/updateManualRecommendGoodsConfig")
    public BaseResponse updateManualRecommendGoodsConfig(@RequestBody @Valid ConfigUpdateRequest request){
        request.setConfigKey("manual_recommend_goods_config");
        systemConfigSaveProvider.update(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "获取手动推荐开关配置")
    @PostMapping("/findByConfigTypeAndDelFlag")
    public BaseResponse<SystemConfigTypeResponse> findByConfigTypeAndDelFlag(@RequestBody @Valid ConfigQueryRequest request){
        request.setConfigKey("manual_recommend_goods_config");
        request.setConfigType("manual_recommend_goods_config");
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(request);
    }

    @Operation(summary = "列表查询手动推荐商品管理")
    @PostMapping("/getManualRecommendGoodsInfoList")
    public BaseResponse<ManualRecommendGoodsInfoListResponse> getManualRecommendGoodsInfoList(@RequestBody @Valid ManualRecommendGoodsInfoListRequest listReq) {
        if(Objects.nonNull(listReq.getGoodsCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(listReq.getGoodsCateId());
            listReq.setGoodsCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(listReq.getGoodsCateIds())) {
                listReq.setGoodsCateId(null);
            }
        }
        return manualRecommendGoodsQueryProvider.getManualRecommendGoodsInfoList(listReq);
    }

    @Operation(summary = "列表查询手动推荐商品选择商品列表管理")
    @PostMapping("/getManualRecommendChooseGoodsList")
    public BaseResponse<ManualRecommendGoodsInfoListResponse> getManualRecommendChooseGoodsList(@RequestBody @Valid ManualRecommendGoodsInfoListRequest listReq) {
        if(Objects.nonNull(listReq.getGoodsCateId())){
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(listReq.getGoodsCateId());
            listReq.setGoodsCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(listReq.getGoodsCateIds())) {
                listReq.setGoodsCateId(null);
            }
        }
        return manualRecommendGoodsQueryProvider.getManualRecommendChooseGoodsList(listReq);
    }

    @Operation(summary = "根据id查询手动推荐商品管理")
    @GetMapping("/{id}")
    public BaseResponse<ManualRecommendGoodsByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ManualRecommendGoodsByIdRequest idReq = new ManualRecommendGoodsByIdRequest();
        idReq.setId(id);
        return manualRecommendGoodsQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增手动推荐商品管理")
    @PostMapping("/add")
    public BaseResponse<ManualRecommendGoodsAddResponse> add(@RequestBody @Valid ManualRecommendGoodsAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return manualRecommendGoodsProvider.add(addReq);
    }

    @Operation(summary = "新增手动推荐商品管理")
    @PostMapping("/addList")
    public BaseResponse addList(@RequestBody @Valid ManualRecommendGoodsAddListRequest addReq) {
        //解析新增数据是否包含重复数据
        List<String> goodsIds = new ArrayList<>();
        addReq.getManualRecommendGoodsList().forEach(manualRecommendGoods->{
            goodsIds.add(manualRecommendGoods.getGoodsId());
        });
        List<ManualRecommendGoodsVO> manualRecommendGoodsVOList = manualRecommendGoodsQueryProvider.list(ManualRecommendGoodsListRequest
                .builder().goodsIds(goodsIds).build()).getContext().getManualRecommendGoodsVOList();
        List<String> existGoodIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(manualRecommendGoodsVOList)){
            manualRecommendGoodsVOList.forEach(manualRecommendGoodsVO -> {
                if(!existGoodIds.contains(manualRecommendGoodsVO.getGoodsId())){
                    existGoodIds.add(manualRecommendGoodsVO.getGoodsId());
                }
            });
        } else {
            manualRecommendGoodsProvider.addList(addReq);
        }
        return BaseResponse.success(existGoodIds);
    }

    @Operation(summary = "修改手动推荐商品管理")
    @PutMapping("/modify")
    public BaseResponse<ManualRecommendGoodsModifyResponse> modify(@RequestBody @Valid ManualRecommendGoodsModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return manualRecommendGoodsProvider.modify(modifyReq);
    }

    @Operation(summary = "修改手动推荐商品管理")
    @PutMapping("/updateWeight")
    public BaseResponse updateWeight(@RequestBody @Valid ManualRecommendGoodsUpdateWeightRequest request) {
        return manualRecommendGoodsProvider.updateWeight(request);
    }

    @Operation(summary = "根据id删除手动推荐商品管理")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ManualRecommendGoodsDelByIdRequest delByIdReq = new ManualRecommendGoodsDelByIdRequest();
        delByIdReq.setId(id);
        return manualRecommendGoodsProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除手动推荐商品管理")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid ManualRecommendGoodsDelByIdListRequest delByIdListReq) {
        return manualRecommendGoodsProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出手动推荐商品管理列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        ManualRecommendGoodsListRequest listReq = JSON.parseObject(decrypted, ManualRecommendGoodsListRequest.class);
        List<ManualRecommendGoodsVO> dataRecords = manualRecommendGoodsQueryProvider.list(listReq).getContext().getManualRecommendGoodsVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("手动推荐商品管理列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<ManualRecommendGoodsVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
            new Column("商品id", new SpelColumnRender<ManualRecommendGoodsVO>("goodsId")),
            new Column("权重", new SpelColumnRender<ManualRecommendGoodsVO>("weight"))
        };
        excelHelper.addSheet("手动推荐商品管理列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }

}
