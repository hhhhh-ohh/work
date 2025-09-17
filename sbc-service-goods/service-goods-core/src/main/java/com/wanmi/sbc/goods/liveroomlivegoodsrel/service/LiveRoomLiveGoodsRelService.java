package com.wanmi.sbc.goods.liveroomlivegoodsrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.LiveGoodsByWeChatVO;
import com.wanmi.sbc.goods.api.request.liveroomlivegoodsrel.LiveRoomLiveGoodsRelListByRoomIdRequest;
import com.wanmi.sbc.goods.livegoods.model.root.LiveGoods;
import com.wanmi.sbc.goods.livegoods.service.LiveGoodsService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.goods.liveroomlivegoodsrel.repository.LiveRoomLiveGoodsRelRepository;
import com.wanmi.sbc.goods.liveroomlivegoodsrel.model.root.LiveRoomLiveGoodsRel;
import com.wanmi.sbc.goods.api.request.liveroomlivegoodsrel.LiveRoomLiveGoodsRelQueryRequest;
import com.wanmi.sbc.goods.bean.vo.LiveRoomLiveGoodsRelVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>直播房间和直播商品关联表业务逻辑</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Service("LiveRoomLiveGoodsRelService")
public class LiveRoomLiveGoodsRelService {
	@Autowired
	private LiveRoomLiveGoodsRelRepository liveRoomLiveGoodsRelRepository;

	@Autowired
	private LiveGoodsService liveGoodsService;

	/**
	 * 新增直播房间和直播商品关联表
	 * @author zwb
	 */
	@Transactional
	public LiveRoomLiveGoodsRel add(LiveRoomLiveGoodsRel entity) {
		liveRoomLiveGoodsRelRepository.save(entity);
		return entity;
	}

	/**
	 * 修改直播房间和直播商品关联表
	 * @author zwb
	 */
	@Transactional
	public LiveRoomLiveGoodsRel modify(LiveRoomLiveGoodsRel entity) {
		liveRoomLiveGoodsRelRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除直播房间和直播商品关联表
	 * @author zwb
	 */
	@Transactional
	public void deleteById(LiveRoomLiveGoodsRel entity) {
		liveRoomLiveGoodsRelRepository.save(entity);
	}

	/**
	 * 批量删除直播房间和直播商品关联表
	 * @author zwb
	 */
	@Transactional
	public void deleteByIdList(List<LiveRoomLiveGoodsRel> infos) {
		liveRoomLiveGoodsRelRepository.saveAll(infos);
	}

	/**
	 * 单个查询直播房间和直播商品关联表
	 * @author zwb
	 */
	public LiveRoomLiveGoodsRel getOne(Long id){
		return liveRoomLiveGoodsRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "直播房间和直播商品关联表不存在"));
	}

	/**
	 * 分页查询直播房间和直播商品关联表
	 * @author zwb
	 */
	public Page<LiveRoomLiveGoodsRel> page(LiveRoomLiveGoodsRelQueryRequest queryReq){
		return liveRoomLiveGoodsRelRepository.findAll(
				LiveRoomLiveGoodsRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询直播房间和直播商品关联表
	 * @author zwb
	 */
	public List<LiveRoomLiveGoodsRel> list(LiveRoomLiveGoodsRelQueryRequest queryReq){
		return liveRoomLiveGoodsRelRepository.findAll(LiveRoomLiveGoodsRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zwb
	 */
	public LiveRoomLiveGoodsRelVO wrapperVo(LiveRoomLiveGoodsRel liveRoomLiveGoodsRel) {
		if (liveRoomLiveGoodsRel != null){
			LiveRoomLiveGoodsRelVO liveRoomLiveGoodsRelVO = KsBeanUtil.convert(liveRoomLiveGoodsRel, LiveRoomLiveGoodsRelVO.class);
			return liveRoomLiveGoodsRelVO;
		}
		return null;
	}

	public List<LiveRoomLiveGoodsRel> getByRoomId(Long roomId) {

         return liveRoomLiveGoodsRelRepository.findByRoomIdAndDelFlag(roomId, DeleteFlag.NO);
	}


	/**
	 * 获取直播商品图片
	 * @param request
	 * @return
	 */
	public Map<Long, List<LiveGoodsByWeChatVO>> listByLiveGoods(LiveRoomLiveGoodsRelListByRoomIdRequest request){
		Map<Long, Long> roomIdAndStoreIdMap = request.getRoomIdAndStoreIdMap();
		List<Long> roomIds = new ArrayList<>(roomIdAndStoreIdMap.keySet());
		List<LiveRoomLiveGoodsRel> liveRoomLiveGoodsRels = liveRoomLiveGoodsRelRepository.findByRoomIdInAndDelFlag(roomIds, DeleteFlag.NO);
		List<Long> liveGoodsList =  liveRoomLiveGoodsRels.stream().map(LiveRoomLiveGoodsRel::getGoodsId).collect(Collectors.toList());
		List<LiveGoods> liveGoods = liveGoodsService.findByGoodsIdList(liveGoodsList);
		Map<Long, LiveGoods> longLiveGoodsMap =  liveGoods.stream().collect(Collectors.toMap(LiveGoods::getGoodsId, Function.identity()));

		//将房间ID和查询出的商品列表关联起来，key：房间roomId，value：房间对应的商品列表
		Map<Long, List<LiveGoods>> roomRelGoodsIdMap = new HashMap<>();
		for (LiveRoomLiveGoodsRel rel : liveRoomLiveGoodsRels) {
			Long roomId = rel.getRoomId();
			Long goodsId = rel.getGoodsId();
			if (!roomRelGoodsIdMap.containsKey(roomId)) {
				roomRelGoodsIdMap.put(roomId, new ArrayList<>());
			}
			List<LiveGoods> goodsList = roomRelGoodsIdMap.get(roomId);
			if (longLiveGoodsMap.get(goodsId) != null) {
				goodsList.add(longLiveGoodsMap.get(goodsId));
			}
		}
		//转换成VO对象
		Map<Long, List<LiveGoodsByWeChatVO>> resMap = new HashMap<>();
		for (Map.Entry<Long, List<LiveGoods>> room : roomRelGoodsIdMap.entrySet()) {
			Long roomId = room.getKey();
			List<LiveGoods> goodsList = room.getValue();
			resMap.put(roomId, KsBeanUtil.convertList(goodsList, LiveGoodsByWeChatVO.class));
		}
		return resMap;

	}
}

