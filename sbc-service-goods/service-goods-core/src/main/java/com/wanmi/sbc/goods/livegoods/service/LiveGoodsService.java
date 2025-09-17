package com.wanmi.sbc.goods.livegoods.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.StoreNameListByStoreIdsResquest;
import com.wanmi.sbc.customer.api.response.liveroom.LiveRoomDeleteResponse;
import com.wanmi.sbc.customer.api.response.liveroom.LiveRoomGoodsAddResponse;
import com.wanmi.sbc.customer.api.response.store.StoreListForDistributionResponse;
import com.wanmi.sbc.customer.api.response.store.StoreNameListByStoreIdsResponse;
import com.wanmi.sbc.customer.bean.vo.StoreNameVO;
import com.wanmi.sbc.customer.bean.vo.StoreSimpleInfo;
import com.wanmi.sbc.empower.bean.enums.WxLiveErrorCodeEnum;
import com.wanmi.sbc.goods.api.request.livegoods.LiveGoodsAuditRequest;
import com.wanmi.sbc.goods.api.request.livegoods.LiveGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.livegoods.LiveGoodsUpdateRequest;
import com.wanmi.sbc.goods.api.response.livegoods.LiveGoodsPageNewResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoLiveGoodsVO;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoLiveGoods;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.livegoods.model.root.LiveGoods;
import com.wanmi.sbc.goods.livegoods.repository.LiveGoodsRepository;
import com.wanmi.sbc.goods.liveroomlivegoodsrel.model.root.LiveRoomLiveGoodsRel;
import com.wanmi.sbc.goods.liveroomlivegoodsrel.repository.LiveRoomLiveGoodsRelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>直播商品业务逻辑</p>
 *
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Service("LiveGoodsService")
public class LiveGoodsService {
    @Autowired
    private LiveGoodsRepository liveGoodsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LiveRoomLiveGoodsRelRepository liveRoomLiveGoodsRelRepository;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;


    private static final Logger log = LoggerFactory.getLogger(LiveGoodsService.class);

    private String deleteGoodsUrl = "https://api.weixin.qq.com/wxaapi/broadcast/goods/delete?access_token=";

    private String auditGoodsUrl = "https://api.weixin.qq.com/wxaapi/broadcast/goods/add?access_token=";


    private String importCommodity = "https://api.weixin.qq.com/wxaapi/broadcast/room/addgoods?access_token=";

    /**
     * 直播间导入商品
     *
     * @author zwb
     */
    @Transactional
    public void add(Long roomId, List<Long> goodsIds, String accessToken) {
        //拼接Url
        String url = importCommodity + accessToken;
        Map<String, Object> map = new HashMap<>();
        map.put("ids", goodsIds);
        map.put("roomId", roomId);
        //调用微信导入商品接口
        String result = restTemplate.postForObject(url, map, String.class);
        LiveRoomGoodsAddResponse resp = JSONObject.parseObject(result, LiveRoomGoodsAddResponse.class);
        if (resp.getErrcode() != 0) {
            log.error("直播间导入直播商品异常，返回信息：{}",resp);
            throw new SbcRuntimeException(Objects.requireNonNull(WxLiveErrorCodeEnum.parseOldCode(resp.getErrcode().toString())));
        }
        //存中间表
        ArrayList entityList = new ArrayList();
        goodsIds.stream().forEach(id -> {
            LiveRoomLiveGoodsRel entity = new LiveRoomLiveGoodsRel();
            entity.setRoomId(roomId);
            entity.setDelFlag(DeleteFlag.NO);
            entity.setCreateTime(LocalDateTime.now());
            entity.setGoodsId(id);
            entityList.add(entity);
        });
        liveRoomLiveGoodsRelRepository.saveAll(entityList);
    }

    /**
     * 修改直播商品
     *
     * @author zwb
     */
    @Transactional
    public LiveGoods modify(LiveGoods entity) {
        liveGoodsRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除直播商品（微信端）
     *
     * @author zwb
     */
    @Transactional
    public void deleteById(Long id, Long goodsId, String accessToken) {

        if (goodsId != null) {
            //拼接Url
            String url = deleteGoodsUrl + accessToken;
            Map<String, Long> map = new HashMap<>();
            map.put("goodsId", goodsId);
            //调用删除接口，删除直播商品
            String result = restTemplate.postForObject(url, map, String.class);
            LiveRoomDeleteResponse resp = JSONObject.parseObject(result, LiveRoomDeleteResponse.class);
            if (resp.getErrcode() != 0) {
                log.error("微信删除直播商品异常，返回信息：{}",resp);
                throw new SbcRuntimeException(Objects.requireNonNull(WxLiveErrorCodeEnum.parseOldCode(resp.getErrcode().toString())));
            }
            List<LiveRoomLiveGoodsRel> liveGoodsRelList = liveRoomLiveGoodsRelRepository.findByGoodsIdAndDelFlag(goodsId, DeleteFlag.NO);
            if (CollectionUtils.isNotEmpty(liveGoodsRelList)) {
                //删除中间表数据
                liveRoomLiveGoodsRelRepository.deleteByGoodsIdAndDelFlag(goodsId, DeleteFlag.NO);
            }
        }
        liveGoodsRepository.deleteById(id);
    }


    /**
     * 批量删除直播商品
     *
     * @author zwb
     */
    @Transactional
    public void deleteByIdList(List<Long> ids) {
        liveGoodsRepository.deleteByIdList(ids);
    }

    /**
     * 单个查询直播商品
     *
     * @author zwb
     */
    public LiveGoods getOne(Long id) {

        return liveGoodsRepository.findByGoodsIdAndDelFlag(id, DeleteFlag.NO)
                .orElseGet(LiveGoods::new);
    }

    /**
     * 分页查询直播商品
     *
     * @author zwb
     */
    public Page<LiveGoods> page(LiveGoodsQueryRequest queryReq) {
        return liveGoodsRepository.findAll(
                LiveGoodsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询直播商品
     *
     * @author zwb
     */
    public List<LiveGoods> list(LiveGoodsQueryRequest queryReq) {
        return liveGoodsRepository.findAll(LiveGoodsWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author zwb
     */
    public LiveGoodsVO wrapperVo(LiveGoods liveGoods) {
        if (liveGoods != null) {
            LiveGoodsVO liveGoodsVO = KsBeanUtil.convert(liveGoods, LiveGoodsVO.class);
            return liveGoodsVO;
        }
        return null;
    }

    /**
     * 直播商品提交审核
     *
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void audit(LiveGoodsAuditRequest liveGoodsAuditRequest) {
        String accessToken = liveGoodsAuditRequest.getAccessToken();
        //拼接Url
        String url = auditGoodsUrl + accessToken;
        Map<String, LiveGoods> map = new HashMap<>();
        List<LiveGoodsVO> goodsInfoVOList = liveGoodsAuditRequest.getGoodsInfoVOList();
        goodsInfoVOList.stream().forEach(c -> {
            //商品名称是否过长截取和补充
            String name = c.getName();
            try {
                if (name.getBytes("gbk").length < Constants.FIVE) {
                    name = name + "    ";
                }
                try {
                    c.setName(LiveGoodsService.substring(name, 28));
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
            String coverImgUrl = c.getCoverImgUrl();
            //获取mediaID 调用微信接口
            String mediaId = null;
            if (StringUtils.isNotEmpty(c.getCoverImgUrl())) {
                try {
                    //下载图片到本地，根据本地路径去微信接口查询 media_id
                    //将图片缩放至300*300
                    File uploadURL = new File(Objects.requireNonNull(MediaIdUtil.uploadURL(coverImgUrl)));
                    if (ImageIO.read(uploadURL).getHeight() > Constants.NUM_300 || ImageIO.read(uploadURL).getWidth() > Constants.NUM_300) {
                        ImageUtils.reSize(uploadURL, uploadURL, Constants.NUM_300, Constants.NUM_300, true);
                    }
                    mediaId = MediaIdUtil.uploadFile(uploadURL.toString(), accessToken, "image");
                    //删除本地图片
                    if (uploadURL.exists()) {
                      boolean result = uploadURL.delete();
                      if (!result){
                          log.error("删除本地图片失败");
                      }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            c.setCoverImgUrl(mediaId);

			/*try {
				c.setUrl(URLEncoder.encode(c.getUrl(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/

            LiveGoods liveGoods = KsBeanUtil.convert(c, LiveGoods.class);
            map.put("goodsInfo", liveGoods);

            String result = restTemplate.postForObject(url, map, String.class);

            JSONObject jsonObject = JSONObject.parseObject(result);
            final String errCode = "errcode";
            if (0 != (Integer) jsonObject.get(errCode)) {
                log.error("微信提审直播商品异常，返回信息：{}",jsonObject);
                throw new SbcRuntimeException(WxLiveErrorCodeEnum.T040228, Objects.requireNonNull(WxLiveErrorCodeEnum.parseOldCode("300013")));
            }
            //保存接口返回的结果
            Long goodsIdNew = Long.valueOf(jsonObject.getString("goodsId"));
            //Long auditId = Long.valueOf(jsonObject.getString("auditId"));
            //修改商品审核状态为待审核，修改商品goodsId
            liveGoodsRepository.updateGoodsIdAndAuditStatusById(goodsIdNew, 1, c.getId());
        });

    }

    /**
     * 定时任务更新商品状态
     *
     * @param req
     */
    @Transactional
    public void update(LiveGoodsUpdateRequest req) {

        /*List<Long> goodsIdList = liveGoodsRepository.findByGoodsIdList(req.getGoodsIdList(), DeleteFlag.NO).stream()
                .filter(Objects::nonNull)
                .map(LiveGoods::getGoodsId)
                .collect(Collectors.toList());*/
        //批量修改商品状态
        if (CollectionUtils.isNotEmpty(req.getGoodsIdList())) {
            liveGoodsRepository.updateByGoodsIdList(req.getAuditStatus(), req.getGoodsIdList());
        }
    }

    /**
     * supplier端添加商品
     *
     * @param goodsInfoVOList
     * @return
     */
    @Transactional
    public List<LiveGoodsVO> supplier(List<LiveGoodsVO> goodsInfoVOList) {
        List<LiveGoods> liveGoodsList = goodsInfoVOList.stream().map(entity -> {
            LiveGoods convert = KsBeanUtil.convert(entity, LiveGoods.class);
            convert.setAuditStatus(0);
            entity.setAuditStatus(0);
            convert.setDelFlag(DeleteFlag.NO);
            return convert;
        }).collect(Collectors.toList());
        liveGoodsRepository.saveAll(liveGoodsList);
        return goodsInfoVOList;
    }

    /**
     * 审核状态修改
     *
     * @param entity
     * @return
     */
    @Transactional
    public LiveGoods status(LiveGoods entity) {

        String auditReason = entity.getAuditReason();
        if (auditReason == null) {
            auditReason = "";
        }
        liveGoodsRepository.updateAuditStatusById(entity.getAuditStatus(), auditReason, entity.getId());

        return entity;
    }


    //判断是否是汉字throws UnsupportedEncodingException
    public static boolean isChineseChar(char c) {
        return String.valueOf(c).getBytes(StandardCharsets.UTF_8).length > 1;
    }

    //截取字符串方法
    public static String substring(String orignal, int count) throws UnsupportedEncodingException { // 原始字符不为null，也不是空字符串   
        if (orignal != null && !"".equals(orignal)) {

            // 要截取的字节数大于0，且小于原始字符串的字节数 
            int orignalLenth = orignal.getBytes("gbk").length;
            if ( count > 0 && count < orignalLenth) {
                StringBuilder buff = new StringBuilder();
                char c;
                for (int i = 0; i < count; i++) {
                    c = orignal.charAt(i);
                    if (i == count - 1 && LiveGoodsService.isChineseChar(c)) {
                        break;
                    }
                    buff.append(c);
                    if (LiveGoodsService.isChineseChar(c)) {
                        // 遇到中文汉字，截取字节总数减1   
                        --count;
                    }
                }
                return buff.toString();
            }
        }
        return orignal;
    }

    /**
     * 批量查询直播商品goodsId
     *
     * @author zwb
     */
    public List<LiveGoods> findByGoodsIdList(List<Long> goodsIdList) {
        return liveGoodsRepository.findByGoodsIdList(goodsIdList, DeleteFlag.NO);
    }

    public LiveGoodsPageNewResponse pageNew(LiveGoodsQueryRequest pageReq) {
        LiveGoodsPageNewResponse response = new LiveGoodsPageNewResponse();
        if (StringUtils.isNotBlank(pageReq.getStoreName())) {
            //查询所有匹配的storeId
            List<StoreSimpleInfo> storeSimpleInfos = storeQueryProvider.listByStoreName(ListStoreByNameRequest.builder().storeName(pageReq.getStoreName()).build()).getContext().getStoreSimpleInfos();
            Map<Long, String> storeIdAndNameMap = storeSimpleInfos.stream().filter(c -> c.getStoreId() != null).collect(Collectors.toMap(StoreSimpleInfo::getStoreId, c -> {
                String name = c.getStoreName();
                if (StringUtils.isEmpty(name)) {
                    name = "-";
                }
                return name;
            }, (oldValue, newValue) -> newValue));
            //用于获取所属店铺名称
            Map<Long, String> storeName = new HashMap<>();
            //查询所有结果
            List<LiveGoods> liveGoodsVOList = this.list(pageReq);
            //映射
            List<LiveGoods> collect = new ArrayList<>();

            for (LiveGoods liveGoodsVO : liveGoodsVOList) {
                if (storeIdAndNameMap.containsKey(liveGoodsVO.getStoreId())) {
                    collect.add(liveGoodsVO);
                    //获取店铺所属名称
                    storeName.put(liveGoodsVO.getStoreId(), storeIdAndNameMap.get(liveGoodsVO.getStoreId()));
                }
            }
            List<String> goodsInfoList = collect.stream().filter(Objects::nonNull).map(LiveGoods::getGoodsInfoId).collect(Collectors.toList());
            List<String> infoIdList = goodsInfoRepository.findGoodsInfoIdList(goodsInfoList,
                    pageReq.getMinGoodsSalesNum(), pageReq.getMaxGoodsSalesNum());
            if(CollectionUtils.isEmpty(infoIdList)){
                response.setLiveGoodsVOPage(new MicroServicePage<>());
                return response;
            }
            List<GoodsInfoLiveGoods> goodsInfoVOList = goodsInfoService.findGoodsInfoLiveGoodsByIds(infoIdList);
            WrapperLiveGoods(collect, goodsInfoVOList);
            PageImpl<LiveGoods> newPage = new PageImpl(collect, pageReq.getPageable(), collect.size());
            response.setLiveGoodsVOPage(KsBeanUtil.convertPage(newPage, LiveGoodsVO.class));
            response.setStoreName(storeName);
            response.setGoodsInfoList(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoLiveGoodsVO.class));
        } else {

            List<LiveGoods> liveGoodsVOList = this.list(pageReq);
            List<String> goodsInfoIdList = liveGoodsVOList.stream()
                    .filter(Objects::nonNull)
                    .map(LiveGoods::getGoodsInfoId)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
            List<String> infoIdList = goodsInfoRepository.findGoodsInfoIdList(goodsInfoIdList,
                    pageReq.getMinGoodsSalesNum(), pageReq.getMaxGoodsSalesNum());
            if(CollectionUtils.isEmpty(infoIdList)){
                response.setLiveGoodsVOPage(new MicroServicePage<>());
                return response;
            }
            Page<LiveGoods> liveGoodsVOPage = liveGoodsRepository.findByGoodsInfoIdInAndDelFlag(infoIdList,DeleteFlag.NO,pageReq.getPageRequest());
            //实时获取商品库存和goodId（非微信端商品id）
            List<LiveGoods> liveGoodsList = liveGoodsVOPage.getContent();
            List<String> goodsInfoList = liveGoodsList.stream().filter(Objects::nonNull).map(LiveGoods::getGoodsInfoId).collect(Collectors.toList());
            List<GoodsInfoLiveGoods> goodsInfoVOList = goodsInfoService.findGoodsInfoLiveGoodsByIds(goodsInfoList);
            WrapperLiveGoods(liveGoodsList, goodsInfoVOList);
            List<Long> storeIdList = liveGoodsList.stream().filter(liveGoodsVO -> liveGoodsVO.getStoreId() != null).map(LiveGoods::getStoreId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(storeIdList)) {
                List<StoreNameVO> storeNameList = storeQueryProvider.listStoreNameByStoreIds(new StoreNameListByStoreIdsResquest(storeIdList)).getContext().getStoreNameList();
                //根据storeId查询店铺名称
                Map<Long, String> storeName = storeNameList.stream().filter(storeNameVO -> storeNameVO.getStoreId() != null).collect(Collectors.toMap(StoreNameVO::getStoreId, c -> {
                            String name = c.getStoreName();
                            if (StringUtils.isEmpty(name)) {
                                name = "-";
                            }
                            return name;
                        })
                );
                response.setStoreName(storeName);
            }

            response.setLiveGoodsVOPage(KsBeanUtil.convertPage(liveGoodsVOPage, LiveGoodsVO.class));
            response.setGoodsInfoList(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoLiveGoodsVO.class));

        }
        return response;
    }

    private void WrapperLiveGoods(List<LiveGoods> liveGoodsList, List<GoodsInfoLiveGoods> goodsInfoVOList) {
        Map<String, GoodsInfoLiveGoods> goodsInfoLiveGoodsMap = goodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoLiveGoods::getGoodsInfoId, Function.identity()));
        //实时获取商品库存和goodId（非微信端商品id）
        liveGoodsList.stream().filter(Objects::nonNull).forEach(c -> {
            if (StringUtils.isNotBlank(c.getGoodsInfoId())) {
                GoodsInfoLiveGoods goodsInfo = goodsInfoLiveGoodsMap.get(c.getGoodsInfoId());
                if (Objects.nonNull(goodsInfo) && Objects.nonNull(goodsInfo.getStock())) {
                    c.setStock(goodsInfo.getStock());
                } else {
                    c.setStock(0L);
                }
                if (Objects.nonNull(goodsInfo) && Objects.nonNull(goodsInfo.getGoodsId())) {
                    c.setGoodsIdForDetails(goodsInfo.getGoodsId());
                }

            }
        });
    }

    /**
     * 分页查询直播商品列表(逻辑重构，老逻辑：pageNew方法)
     * @param pageReq
     * @return
     */
    public LiveGoodsPageNewResponse findLiveGoodsPage(LiveGoodsQueryRequest pageReq) {
        String storeName = pageReq.getStoreName();
        if(StringUtils.isNotBlank(storeName)){
            return this.getLiveGoodsPage(pageReq);
        }
        //查询条件中没有storeName
        Page<LiveGoods> liveGoodsPage = liveGoodsRepository.findLiveGoodsPage(pageReq,
                NumberUtils.INTEGER_ZERO, pageReq.getPageable());
        List<LiveGoods> liveGoodsList = liveGoodsPage.getContent();
        if(CollectionUtils.isEmpty(liveGoodsList)){
            return this.emptyResponse(pageReq);
        }
        Map<String, GoodsInfoLiveGoodsVO> goodsInfoMap = this.getGoodsInfoMap(liveGoodsList);
        Map<Long, String> storeInfoMap = this.getStoreInfoMap(liveGoodsList);
        MicroServicePage<LiveGoodsVO> microServicePage = this.copyPage(liveGoodsPage, goodsInfoMap);
        return LiveGoodsPageNewResponse.builder()
                .goodsInfoList(Lists.newArrayList(goodsInfoMap.values()))
                .liveGoodsVOPage(microServicePage)
                .storeName(storeInfoMap)
                .build();
    }

    /**
     * 带上storeName查询条件分页查询直播列表
     * @param pageReq
     * @return
     */
    private LiveGoodsPageNewResponse getLiveGoodsPage(LiveGoodsQueryRequest pageReq){
        String storeName = pageReq.getStoreName();
        Map<Long, String> storeInfoMap = this.getStoreInfoMap(storeName);
        if(MapUtils.isEmpty(storeInfoMap)){
           return this.emptyResponse(pageReq);
        }
        Set<Long> storeIdSet = storeInfoMap.keySet();
        pageReq.setStoreIdList(storeIdSet);
        Page<LiveGoods> liveGoodsPage = liveGoodsRepository.findLiveGoodsPage(pageReq,
                CollectionUtils.size(storeIdSet), pageReq.getPageable());
        List<LiveGoods> liveGoodsList = liveGoodsPage.getContent();
        if(CollectionUtils.isEmpty(liveGoodsList)){
            return this.emptyResponse(pageReq);
        }
        Map<String, GoodsInfoLiveGoodsVO> goodsInfoMap = this.getGoodsInfoMap(liveGoodsList);
        storeInfoMap = this.removeStoreInfo(liveGoodsList,storeInfoMap);
        MicroServicePage<LiveGoodsVO> microServicePage = this.copyPage(liveGoodsPage, goodsInfoMap);
        return LiveGoodsPageNewResponse.builder()
                .goodsInfoList(Lists.newArrayList(goodsInfoMap.values()))
                .liveGoodsVOPage(microServicePage)
                .storeName(storeInfoMap)
                .build();
    }

    /**
     * 根据storeName 获取store信息
     * @param storeName
     * @return
     */
    private Map<Long,String> getStoreInfoMap(String storeName){
        ListStoreByNameRequest request = ListStoreByNameRequest.builder()
                .storeName(storeName)
                .build();
        StoreListForDistributionResponse response = storeQueryProvider.listByStoreName(request).getContext();
        List<StoreSimpleInfo> storeSimpleInfoList = response.getStoreSimpleInfos();
        if(CollectionUtils.isEmpty(storeSimpleInfoList)){
            return Collections.emptyMap();
        }
        return storeSimpleInfoList.stream()
                .filter(store-> Objects.nonNull(store.getStoreId()))
                .collect(Collectors.toMap(StoreSimpleInfo::getStoreId,
                        source -> StringUtils.defaultIfBlank(source.getStoreName(),"-")));
    }

    /**
     * 根据storeIdList 获取store信息
     * @param liveGoodsList
     * @return
     */
    private Map<Long,String> getStoreInfoMap(List<LiveGoods> liveGoodsList){
        List<Long> storeIdList = liveGoodsList.stream()
                .map(LiveGoods::getStoreId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(storeIdList)){
            return Collections.emptyMap();
        }
        StoreNameListByStoreIdsResquest req = new StoreNameListByStoreIdsResquest(storeIdList);
        StoreNameListByStoreIdsResponse response = storeQueryProvider.listStoreNameByStoreIds(req).getContext();
        List<StoreNameVO> storeNameList = response.getStoreNameList();
        if(CollectionUtils.isEmpty(storeNameList)){
            return Collections.emptyMap();
        }
        return storeNameList.stream()
                .filter(store-> Objects.nonNull(store.getStoreId()))
                .collect(Collectors.toMap(StoreNameVO::getStoreId,
                        source -> StringUtils.defaultIfBlank(source.getStoreName(),"-")));
    }

    /**
     * Page<LiveGoods> 转VO
     * @param liveGoodsPage
     * @param goodsInfoMap
     * @return
     */
    private MicroServicePage<LiveGoodsVO> copyPage(Page<LiveGoods> liveGoodsPage,
                                                   Map<String, GoodsInfoLiveGoodsVO> goodsInfoMap ){
        Page<LiveGoods> goodsPage = liveGoodsPage.map(source -> {
            GoodsInfoLiveGoodsVO goodsInfoLiveGoodsVO = goodsInfoMap.get(source.getGoodsInfoId());
            source.setGoodsIdForDetails(goodsInfoLiveGoodsVO.getGoodsId());
            return source;
        });
        return KsBeanUtil.convertPage(goodsPage, LiveGoodsVO.class);
    }

    /**
     * 过滤无用店铺信息
     * @param liveGoodsList
     * @param storeInfoMap
     */
    private Map<Long, String> removeStoreInfo(List<LiveGoods> liveGoodsList,Map<Long, String> storeInfoMap){
        List<Long> storeIdList = liveGoodsList.stream()
                .map(LiveGoods::getStoreId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<Long,String> newStoreInfoMap = new HashMap<>(storeInfoMap.size());
        storeInfoMap.forEach((k,v)->{
            if(k != null && StringUtils.isNotBlank(v) && storeIdList.contains(k)){
                newStoreInfoMap.put(k,v);
            }
        });
        return newStoreInfoMap;
    }

    /**
     *
     * @param liveGoodsList
     * @return
     */
    private Map<String,GoodsInfoLiveGoodsVO> getGoodsInfoMap(List<LiveGoods> liveGoodsList){
        List<String> goodsInfoIdList = liveGoodsList.stream()
                .map(LiveGoods::getGoodsInfoId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        List<GoodsInfoLiveGoods> goodsInfoVOList = goodsInfoService.findGoodsInfoLiveGoodsByIds(goodsInfoIdList);
        if(CollectionUtils.isEmpty(goodsInfoVOList)){
            return Collections.emptyMap();
        }
        List<GoodsInfoLiveGoodsVO> liveGoodsVOList = KsBeanUtil.convert(goodsInfoVOList, GoodsInfoLiveGoodsVO.class);
        return liveGoodsVOList.stream()
                .collect(Collectors.toMap(GoodsInfoLiveGoodsVO::getGoodsInfoId,Function.identity()));
    }

    /**
     * 返回空数据
     * @param pageReq
     * @return
     */
    private LiveGoodsPageNewResponse emptyResponse(LiveGoodsQueryRequest pageReq){
        MicroServicePage<LiveGoodsVO> emptyPage = new MicroServicePage<>(pageReq.getPageable());
        return LiveGoodsPageNewResponse.builder()
                .goodsInfoList(Collections.emptyList())
                .storeName(Collections.emptyMap())
                .liveGoodsVOPage(emptyPage)
                .build();
    }
}

