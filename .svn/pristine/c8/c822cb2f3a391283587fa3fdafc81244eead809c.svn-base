package com.wanmi.sbc.livegoods;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.empower.bean.enums.WxLiveErrorCodeEnum;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.livegoods.LiveGoodsProvider;
import com.wanmi.sbc.goods.api.provider.livegoods.LiveGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.livegoods.*;
import com.wanmi.sbc.goods.api.response.livegoods.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoLiveGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.MiniProgramUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "直播商品管理API", description =  "LiveGoodsController")
@RestController
@Validated
@RequestMapping(value = "/livegoods")
public class LiveGoodsController {

    @Autowired
    private LiveGoodsQueryProvider liveGoodsQueryProvider;

    @Autowired
    private LiveGoodsProvider liveGoodsProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private MiniProgramUtil miniProgramUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;


    @Operation(summary = "分页查询直播商品")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<LiveGoodsPageNewResponse> getPage(@RequestBody @Valid LiveGoodsQueryRequest pageReq) {
        //根据所属店铺名称模糊查询
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("createTime", "desc");
        if (StringUtils.isBlank(pageReq.getStoreName())){
            pageReq.setStoreId(commonUtil.getStoreId());
        }
        BaseResponse<LiveGoodsPageNewResponse> liveGoodsPageNewResponseBaseResponse = liveGoodsQueryProvider.pageNew(pageReq);
        //填充供应商
        this.populateProviderName(liveGoodsPageNewResponseBaseResponse.getContext());

        //填充marketingGoodsStatus
        this.populateMarketingGoodsStatus(liveGoodsPageNewResponseBaseResponse.getContext());


        return liveGoodsPageNewResponseBaseResponse;
//        if(StringUtils.isNotBlank(pageReq.getStoreName())){
//
//            //查询所有匹配的storeId
//           List<StoreSimpleInfo> storeSimpleInfos = storeQueryProvider.listByStoreName(new ListStoreByNameRequest(pageReq.getStoreName())).getContext().getStoreSimpleInfos();
//           Map<Long, String> storeIdAndNameMap = storeSimpleInfos.stream().filter(c -> c.getStoreId() != null).collect(Collectors.toMap(StoreSimpleInfo::getStoreId, c -> {
//               String name = c.getStoreName();
//               if (StringUtils.isEmpty(name)) {
//                   name = "-";
//               }
//               return name;
//           }, (oldValue, newValue) -> newValue));
//           //用于获取所属店铺名称
//           Map<Long, String> storeName =new HashMap<>();
//           //查询所有结果
//           LiveGoodsListRequest request = KsBeanUtil.convert(pageReq, LiveGoodsListRequest.class);
//           List<LiveGoodsVO> liveGoodsVOList = liveGoodsQueryProvider.list(request).getContext().getLiveGoodsVOList();
//           //映射
//           List<LiveGoodsVO> collect = new ArrayList<>();
//
//           for (LiveGoodsVO liveGoodsVO : liveGoodsVOList) {
//               if (storeIdAndNameMap.keySet().contains(liveGoodsVO.getStoreId())){
//                   collect.add(liveGoodsVO);
//                   //获取店铺所属名称
//                   storeName.put(liveGoodsVO.getStoreId(),storeIdAndNameMap.get(liveGoodsVO.getStoreId()));
//               }
//           }
//
//           //实时获取商品库存和goodId（非微信端商品id）
//           collect.stream().filter(Objects::nonNull).forEach(c -> {
//               if (StringUtils.isNotBlank(c.getGoodsInfoId())) {
//                   GoodsInfoVO goodsInfo = goodsInfoQueryProvider.getById(new GoodsInfoByIdRequest(c.getGoodsInfoId(), c.getStoreId())).getContext();
//                   if (Objects.nonNull(goodsInfo) && Objects.nonNull(goodsInfo.getStock() )) {
//                       c.setStock(goodsInfo.getStock());
//                   }else {
//                       c.setStock(0L);
//                   }
//                   if (Objects.nonNull(goodsInfo) && Objects.nonNull(goodsInfo.getGoodsId())) {
//                       c.setGoodsIdForDetails(goodsInfo.getGoodsId());
//                   }
//
//               }
//           });
//           //查询规格信息
//           /*Map<String, List<GoodsInfoVO>> listMap = collect.stream().filter(Objects::nonNull).collect(Collectors.toMap(LiveGoodsVO::getGoodsInfoId, c -> {
//               GoodsInfoViewPageRequest goodsInfoViewPageRequest = new GoodsInfoViewPageRequest();
//               goodsInfoViewPageRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
//               goodsInfoViewPageRequest.setGoodsId(c.getGoodsIdForDetails());
//               return goodsInfoQueryProvider.pageView(goodsInfoViewPageRequest).getContext().getGoodsInfoPage().getContent();
//           }, (oldValue, newValue) -> newValue));*/
//            List<String> goodsInfoList = collect.stream().filter(Objects::nonNull).map(LiveGoodsVO::getGoodsInfoId).collect(Collectors.toList());
//            GoodsInfoViewPageRequest goodsInfoViewPageRequest = new GoodsInfoViewPageRequest();
//            goodsInfoViewPageRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
//            goodsInfoViewPageRequest.setGoodsInfoIds(goodsInfoList);
//            List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.pageView(goodsInfoViewPageRequest).getContext().getGoodsInfoPage().getContent();
//           LiveGoodsPageResponse response = new LiveGoodsPageResponse();
//           PageImpl<LiveGoodsVO> newPage = new PageImpl<>(collect, pageReq.getPageable(),collect.size());
//           MicroServicePage<LiveGoodsVO> microPage = new MicroServicePage<>(newPage, pageReq.getPageable());
//           response.setLiveGoodsVOPage(microPage);
//           response.setStoreName(storeName);
//           response.setGoodsInfoList(goodsInfoVOList);
//           return BaseResponse.success(response);
//       }else {
//            Long storeId = commonUtil.getStoreId();
//           pageReq.setStoreId(storeId);
//           MicroServicePage<LiveGoodsVO> liveGoodsVOPage = liveGoodsQueryProvider.page(pageReq).getContext().getLiveGoodsVOPage();
//           //实时获取商品库存和goodId（非微信端商品id）
//           liveGoodsVOPage.getContent().stream().forEach(c -> {
//               if (StringUtils.isNotBlank(c.getGoodsInfoId())) {
//                   GoodsInfoVO goodsInfo = goodsInfoQueryProvider.getById(new GoodsInfoByIdRequest(c.getGoodsInfoId(), c.getStoreId())).getContext();
//                   if (Objects.nonNull(goodsInfo) && Objects.nonNull(goodsInfo.getStock() )) {
//                       c.setStock(goodsInfo.getStock());
//                   }else {
//                       c.setStock(0L);
//                   }
//                   if (Objects.nonNull(goodsInfo) && Objects.nonNull(goodsInfo.getGoodsId())) {
//                       c.setGoodsIdForDetails(goodsInfo.getGoodsId());
//                   }
//
//               }
//           });
//
//           //根据storeId查询店铺名称
//           Map<Long, String> storeName = liveGoodsVOPage.getContent().stream().filter(liveGoodsVO -> liveGoodsVO.getStoreId() != null).collect(Collectors.toMap(LiveGoodsVO::getStoreId, c -> {
//                       String name = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(c.getStoreId())).getContext().getStoreVO().getStoreName();
//                       if (StringUtils.isEmpty(name)) {
//                           name = "-";
//                       }
//                       return name;
//                   }, (oldValue, newValue) -> newValue)
//           );
//
//           //查询规格信息
//          /* Map<String, List<GoodsInfoVO>> listMap =  liveGoodsVOPage.getContent().stream().filter(Objects::nonNull).collect(Collectors.toMap(LiveGoodsVO::getGoodsInfoId, c -> {
//               GoodsInfoViewPageRequest goodsInfoViewPageRequest = new GoodsInfoViewPageRequest();
//               goodsInfoViewPageRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
//               goodsInfoViewPageRequest.setGoodsId(c.getGoodsIdForDetails());
//               return goodsInfoQueryProvider.pageView(goodsInfoViewPageRequest).getContext().getGoodsInfoPage().getContent();
//           }, (oldValue, newValue) -> newValue));*/
//           List<String> goodsInfoList = liveGoodsVOPage.getContent().stream().filter(Objects::nonNull).map(LiveGoodsVO::getGoodsInfoId).collect(Collectors.toList());
//           GoodsInfoViewPageRequest goodsInfoViewPageRequest = new GoodsInfoViewPageRequest();
//           goodsInfoViewPageRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
//           goodsInfoViewPageRequest.setGoodsInfoIds(goodsInfoList);
//            List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.pageView(goodsInfoViewPageRequest).getContext().getGoodsInfoPage().getContent();
//            LiveGoodsPageResponse response = new LiveGoodsPageResponse();
//           response.setLiveGoodsVOPage(liveGoodsVOPage);
//           response.setStoreName(storeName);
//           response.setGoodsInfoList(goodsInfoVOList);
//           return BaseResponse.success(response);
//       }
    }

    /**
     * @description 填充营销商品状态
     * @Author qiyuanzhao
     * @Date 2022/10/21 10:12
     * @param
     * @return
     **/
    private void populateMarketingGoodsStatus(LiveGoodsPageNewResponse context) {
        MicroServicePage<LiveGoodsVO> goodsVOPage = context.getLiveGoodsVOPage();
        List<LiveGoodsVO> liveGoodsVOS = context.getLiveGoodsVOPage().getContent();
        if (CollectionUtils.isEmpty(liveGoodsVOS)){
            return;
        }
        List<String> goodsInfoIds = liveGoodsVOS.stream().map(LiveGoodsVO::getGoodsInfoId).collect(Collectors.toList());

        //填充营销商品状态
        Map<String, MarketingGoodsStatus> statusMap = goodsBaseService.getMarketingGoodsStatusListByGoodsInfoIds(goodsInfoIds);

        for (LiveGoodsVO liveGoodsVO : liveGoodsVOS){
            String goodsInfoId = liveGoodsVO.getGoodsInfoId();
            MarketingGoodsStatus status = statusMap.get(goodsInfoId);
            liveGoodsVO.setMarketingGoodsStatus(status);
        }
        goodsVOPage.setContent(liveGoodsVOS);
        context.setLiveGoodsVOPage(goodsVOPage);
    }

    private void populateProviderName(LiveGoodsPageNewResponse context) {
        if (CollectionUtils.isEmpty(context.getGoodsInfoList())){
            return;
        }
        List<String> goodsInfoIds = context.getGoodsInfoList().stream()
                .map(GoodsInfoLiveGoodsVO::getGoodsInfoId)
                .collect(Collectors.toList());
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider
                .getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder()
                        .goodsInfoIds(goodsInfoIds).build()).getContext().getGoodsInfos();
        //填充供应商名称
        storeBaseService.populateProviderName(goodsInfos);

        List<GoodsInfoLiveGoodsVO> goodsInfoLiveGoodsVOS = KsBeanUtil.convert(goodsInfos, GoodsInfoLiveGoodsVO.class);
        context.setGoodsInfoList(goodsInfoLiveGoodsVOS);
    }


    @Operation(summary = "列表查询直播商品")
    @PostMapping("/list")
    public BaseResponse<LiveGoodsListResponse> getList(@RequestBody @Valid LiveGoodsListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("createTime", "desc");
        return liveGoodsQueryProvider.list(listReq);
    }

    @Operation(summary = "根据goodid查询直播商品详情")
    @RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<LiveGoodsByIdResponse> getById(@PathVariable Long goodsId) {
        //判断直播开关开关是否开启
        this.isOpen();
        if (goodsId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        LiveGoodsByIdResponse response = new LiveGoodsByIdResponse();
        LiveGoodsByIdRequest idReq = new LiveGoodsByIdRequest();
        idReq.setGoodsId(goodsId);
        LiveGoodsVO liveGoodsVO = liveGoodsQueryProvider.getById(idReq).getContext().getLiveGoodsVO();
        //根据goodsInfoId查询商品详情
        if (StringUtils.isNotBlank(liveGoodsVO.getGoodsInfoId())) {
            GoodsInfoVO goodsInfo = goodsInfoQueryProvider.getById(new GoodsInfoByIdRequest(liveGoodsVO.getGoodsInfoId(), liveGoodsVO.getStoreId())).getContext();
            response.setGoodsInfoVO(goodsInfo);
        }

        response.setLiveGoodsVO(liveGoodsVO);

        return BaseResponse.success(response);
    }

    @Operation(summary = "直播间添加直播商品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponse<LiveGoodsAddResponse> add(@RequestBody @Valid LiveGoodsAddRequest addReq) {
        //判断直播开关开关是否开启
        this.isOpen();
        //获取accessToken
        String accessToken = miniProgramUtil.getToken();
        addReq.setAccessToken(accessToken);
        BaseResponse<LiveGoodsAddResponse> response = null;
        try {
            response = liveGoodsProvider.add(addReq);
        } catch (SbcRuntimeException e) {
            //如果token失效则重新获取调用
            if(Constants.STR_42001.equals(e.getErrorCode()) || Constants.STR_40001.equals(e.getErrorCode())) {
                addReq.setAccessToken(miniProgramUtil.getTokenCleanRedis());
                response = liveGoodsProvider.add(addReq);
            } else {
                throw e;
//                throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return response;
    }

    @Operation(summary = "supplier端添加直播商品")
    @RequestMapping(value = "/supplier", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse<LiveGoodsSupplierAddResponse> supplier(@RequestBody @Valid LiveGoodsSupplierAddRequest supplierAddReq) {
        //判断直播开关开关是否开启
        this.isOpen();
        return liveGoodsProvider.supplier(supplierAddReq);
    }

    @Operation(summary = "修改直播商品")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public BaseResponse<LiveGoodsModifyResponse> modify(@RequestBody @Valid LiveGoodsModifyRequest modifyReq) {
        modifyReq.setUpdateTime(LocalDateTime.now());

        return liveGoodsProvider.modify(modifyReq);
    }

    @Operation(summary = "修改直播商品(例如：驳回)")
    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    public BaseResponse<LiveGoodsModifyResponse> status(@RequestBody @Valid LiveGoodsUpdateRequest updateRequest) {
        updateRequest.setUpdateTime(LocalDateTime.now());
        updateRequest.setDelFlag(DeleteFlag.NO);
        return liveGoodsProvider.status(updateRequest);
    }

    @Operation(summary = "根据id删除直播商品（微信接口端）")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public BaseResponse deleteByGoodsId(@RequestBody @Valid LiveGoodsDelByIdRequest delByIdReq) {
        if (delByIdReq.getId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取accessToken
        String accessToken = miniProgramUtil.getToken();
        delByIdReq.setAccessToken(accessToken);
        BaseResponse response = null;
        try{
            response = liveGoodsProvider.deleteById(delByIdReq);
        } catch (SbcRuntimeException e) {
            //如果token失效则重新获取调用
            if(Constants.STR_42001.equals(e.getErrorCode()) || Constants.STR_40001.equals(e.getErrorCode())) {
                delByIdReq.setAccessToken(miniProgramUtil.getTokenCleanRedis());
                response = liveGoodsProvider.deleteById(delByIdReq);
            } else {
                throw e;
//                throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return response;
    }

    @Operation(summary = "根据idList批量删除直播商品")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid LiveGoodsDelByIdListRequest delByIdListReq) {
        return liveGoodsProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出直播商品列表")
    @GetMapping("/export/{encrypted}")
    public void exportData(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        LiveGoodsListRequest listReq = JSON.parseObject(decrypted, LiveGoodsListRequest.class);
        listReq.setDelFlag(DeleteFlag.NO);
        // listReq.portSort("goodsId", "desc");
        List<LiveGoodsVO> dataRecords = liveGoodsQueryProvider.list(listReq).getContext().getLiveGoodsVOList();

        try {
            String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String fileName = URLEncoder.encode(String.format("直播商品列表_%s.xls", nowStr), StandardCharsets.UTF_8.name());
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
    private void exportDataList(List<LiveGoodsVO> dataRecords, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = {
                new Column("商品标题", new SpelColumnRender<LiveGoodsVO>("name")),
                new Column("填入mediaID", new SpelColumnRender<LiveGoodsVO>("coverImgUrl")),
                new Column("价格类型，1：一口价，2：价格区间，3：显示折扣价", new SpelColumnRender<LiveGoodsVO>("priceType")),
                new Column("直播商品价格左边界", new SpelColumnRender<LiveGoodsVO>("price")),
                new Column("直播商品价格右边界", new SpelColumnRender<LiveGoodsVO>("price2")),
                new Column("商品详情页的小程序路径", new SpelColumnRender<LiveGoodsVO>("url")),
                new Column("库存", new SpelColumnRender<LiveGoodsVO>("stock")),
                new Column("商品详情id", new SpelColumnRender<LiveGoodsVO>("goodsInfoId")),
                new Column("店铺标识", new SpelColumnRender<LiveGoodsVO>("storeId")),
                new Column("提交审核时间", new SpelColumnRender<LiveGoodsVO>("submitTime")),
                new Column("审核单ID", new SpelColumnRender<LiveGoodsVO>("auditId")),
                new Column("审核状态,0:未审核1 审核通过2审核失败3禁用中", new SpelColumnRender<LiveGoodsVO>("auditStatus")),
                new Column("审核原因", new SpelColumnRender<LiveGoodsVO>("auditReason")),
                new Column("删除时间", new SpelColumnRender<LiveGoodsVO>("deleteTime")),
                new Column("删除人", new SpelColumnRender<LiveGoodsVO>("deletePerson"))
        };
        excelHelper.addSheet("直播商品列表", columns, dataRecords);
        excelHelper.write(outputStream);
    }


    @Operation(summary = "直播商品提审")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse audit(@RequestBody @Valid LiveGoodsAuditRequest auditReq) {
        List<LiveGoodsVO> goodsInfoVOList = auditReq.getGoodsInfoVOList();
        goodsInfoVOList.stream().forEach(c -> {
            /*if (c.getCoverImgUrl()==null) {
                //获取直播商品详情（图片）
                GoodsInfoVO goodsInfo = goodsInfoQueryProvider.getById(new GoodsInfoByIdRequest(c.getGoodsInfoId(), c.getStoreId())).getContext();
                c.setCoverImgUrl(goodsInfo.getGoodsInfoImg());
            }*/
            c.setSubmitTime(LocalDateTime.now());
        });
        //获取accessToken
        String accessToken = miniProgramUtil.getToken();
        auditReq.setAccessToken(accessToken);
        BaseResponse response = null;
        try{
            response = liveGoodsProvider.audit(auditReq);
        } catch (SbcRuntimeException e) {
            //如果token失效则重新获取调用
            if(Constants.STR_42001.equals(e.getErrorCode()) || Constants.STR_40001.equals(e.getErrorCode())) {
                auditReq.setAccessToken(miniProgramUtil.getTokenCleanRedis());
                response = liveGoodsProvider.audit(auditReq);
            } else {
                throw e;
//                throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return response;
    }

    /**
     * 判断直播开关是否开启
     */
    public void isOpen() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setDelFlag(0);
        configQueryRequest.setConfigKey("liveSwitch");
        configQueryRequest.setConfigType("liveSwitch");
        Integer status = systemConfigQueryProvider.findByConfigKeyAndDelFlag(configQueryRequest).getContext().getConfigVOList().get(0).getStatus();
        if (status == 0) {
            throw new SbcRuntimeException(WxLiveErrorCodeEnum.T040232, Objects.requireNonNull(WxLiveErrorCodeEnum.parseOldCode("10001")));
        }
    }

}
