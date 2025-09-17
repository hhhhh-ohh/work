package com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.service;

import com.github.pagehelper.PageHelper;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendAddListRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendInfoListRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendQueryRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend.GoodsRelatedRecommendUpdateWeightRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsRelatedRecommendVO;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsSpecTextVO;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.mapper.GoodsRelatedRecommendMapper;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.model.root.GoodsRelatedRecommend;
import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.repository.GoodsRelatedRecommendRepository;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.mapper.ManualRecommendGoodsMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>商品相关性推荐业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Service("GoodsRelatedRecommendService")
public class GoodsRelatedRecommendService {
	@Autowired
	private GoodsRelatedRecommendRepository goodsRelatedRecommendRepository;

	@Autowired
	private GoodsRelatedRecommendMapper goodsRelatedRecommendMapper;

	@Autowired
	private ManualRecommendGoodsMapper manualRecommendGoodsMapper;

	/**
	 * 新增商品相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public GoodsRelatedRecommend add(GoodsRelatedRecommend entity) {
		goodsRelatedRecommendRepository.save(entity);
		return entity;
	}

	/**
	 * 新增商品相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public void addList(GoodsRelatedRecommendAddListRequest request) {
		List<GoodsRelatedRecommend> goodsRelatedRecommendList = new ArrayList<>();
		request.getAddGoodsRelatedRecommendAddList().forEach(goodsRelatedRecommendAddRequest -> {
			GoodsRelatedRecommend goodsRelatedRecommend = KsBeanUtil.convert(goodsRelatedRecommendAddRequest, GoodsRelatedRecommend.class);
			goodsRelatedRecommendList.add(goodsRelatedRecommend);
		});
		goodsRelatedRecommendRepository.saveAll(goodsRelatedRecommendList);
	}

	/**
	 * 修改商品相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public GoodsRelatedRecommend modify(GoodsRelatedRecommend entity) {
		goodsRelatedRecommendRepository.save(entity);
		return entity;
	}

	@Transactional
	public void updateWeight(GoodsRelatedRecommendUpdateWeightRequest request){
		if(Objects.nonNull(request.getWeight())){
			goodsRelatedRecommendRepository.updateWeight(request.getWeight(),request.getId());
		} else {
			goodsRelatedRecommendRepository.updateWeightNull(request.getId());
		}
	}

	/**
	 * 单个删除商品相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		goodsRelatedRecommendRepository.deleteById(id);
	}

	/**
	 * 单个查询商品相关性推荐
	 * @author lvzhenwei
	 */
	public GoodsRelatedRecommend getOne(Long id){
		return goodsRelatedRecommendRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品相关性推荐不存在"));
	}

	/**
	 * 分页查询商品相关性推荐
	 * @author lvzhenwei
	 */
	public Page<GoodsRelatedRecommend> page(GoodsRelatedRecommendQueryRequest queryReq){
		return goodsRelatedRecommendRepository.findAll(
				GoodsRelatedRecommendWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商品相关性推荐
	 * @author lvzhenwei
	 */
	public List<GoodsRelatedRecommend> list(GoodsRelatedRecommendQueryRequest queryReq){
		return goodsRelatedRecommendRepository.findAll(GoodsRelatedRecommendWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * @Author lvzhenwei
	 * @Description 分页列表查询商品相关性推荐
	 * @Date 17:28 2020/11/24
	 * @Param [request]
	 * @return org.springframework.data.domain.Page<com.wanmi.sbc.crm.bean.vo.GoodsRelatedRecommendInfoVO>
	 **/
	public Page<GoodsRelatedRecommendInfoVO> getGoodsRelatedRecommendInfoList(GoodsRelatedRecommendInfoListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<GoodsRelatedRecommendInfoVO> recommendGoodsManageInfoVOList = goodsRelatedRecommendMapper.getGoodsRelatedRecommendInfoList(request);
		Long pageTotal = goodsRelatedRecommendMapper.getGoodsRelatedRecommendNum(request);
		return new PageImpl<>(recommendGoodsManageInfoVOList,request.getPageable(),pageTotal);
	}

	/**
	 * @Author lvzhenwei
	 * @Description 分页列表查询商品相关性推荐
	 * @Date 17:28 2020/11/24
	 * @Param [request]
	 * @return org.springframework.data.domain.Page<com.wanmi.sbc.crm.bean.vo.GoodsRelatedRecommendInfoVO>
	 **/
	public Page<GoodsRelatedRecommendInfoVO> getGoodsRelatedRecommendDetailInfoList(GoodsRelatedRecommendInfoListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<GoodsRelatedRecommendInfoVO> recommendGoodsManageInfoVOList = goodsRelatedRecommendMapper.getGoodsRelatedRecommendInfoDetailList(request);
		Long pageTotal = goodsRelatedRecommendMapper.getGoodsRelatedRecommendDetailNum(request);
		return new PageImpl<>(recommendGoodsManageInfoVOList,request.getPageable(),pageTotal);
	}

	/**
	 * @Author lvzhenwei
	 * @Description 分页列表查询商品相关性推荐关联商品设置
	 * @Date 17:28 2020/11/24
	 * @Param [request]
	 * @return org.springframework.data.domain.Page<com.wanmi.sbc.crm.bean.vo.GoodsRelatedRecommendInfoVO>
	 **/
	public Page<GoodsRelatedRecommendInfoVO> getGoodsRelatedRecommendDataInfoList(GoodsRelatedRecommendInfoListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<GoodsRelatedRecommendInfoVO> recommendGoodsManageInfoVOList = goodsRelatedRecommendMapper.getGoodsRelatedRecommendDataDetailList(request);
		Long pageTotal = goodsRelatedRecommendMapper.getGoodsRelatedRecommendDataDetailNum(request);
		return new PageImpl<>(recommendGoodsManageInfoVOList,request.getPageable(),pageTotal);
	}

	/**
	 * @Author lvzhenwei
	 * @Description 分页列表查询商品相关性推荐关联选择商品设置
	 * @Date 17:28 2020/11/24
	 * @Param [request]
	 * @return org.springframework.data.domain.Page<com.wanmi.sbc.crm.bean.vo.GoodsRelatedRecommendInfoVO>
	 **/
	public Page<GoodsRelatedRecommendInfoVO> getGoodsRelatedRecommendChooseList(GoodsRelatedRecommendInfoListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<GoodsRelatedRecommendInfoVO> recommendGoodsManageInfoVOList = goodsRelatedRecommendMapper.getGoodsRelatedRecommendChooseList(request);
		Map<String, String> specTextMap = this.specTextMap(recommendGoodsManageInfoVOList);
		List<GoodsRelatedRecommendInfoVO> newList = recommendGoodsManageInfoVOList.stream()
				.peek(recommendGoodsManageInfoVO -> {
					String goodsId = recommendGoodsManageInfoVO.getGoodsId();
					String specText = specTextMap.get(goodsId);
					recommendGoodsManageInfoVO.setSpecText(specText);
				}).collect(Collectors.toList());
		Long pageTotal = goodsRelatedRecommendMapper.getGoodsRelatedRecommendChooseNum(request);
		return new PageImpl<>(newList,request.getPageable(),pageTotal);
	}

	/**
	 * 根据goodsId获取规格值
	 * @param recommendGoodsManageInfoVOList
	 * @return
	 */
	private Map<String,String> specTextMap(List<GoodsRelatedRecommendInfoVO> recommendGoodsManageInfoVOList){
		if(CollectionUtils.isEmpty(recommendGoodsManageInfoVOList)){
			return Collections.emptyMap();
		}
		List<String> goodsIdList = recommendGoodsManageInfoVOList.stream()
				.map(GoodsRelatedRecommendInfoVO::getGoodsId)
				.collect(Collectors.toList());
		List<GoodsSpecTextVO> goodsSpecTextList = manualRecommendGoodsMapper.getGoodsSpecTextList(goodsIdList);
		if(CollectionUtils.isEmpty(goodsSpecTextList)){
			return Collections.emptyMap();
		}
		return goodsSpecTextList.stream()
				.collect(Collectors.groupingBy(GoodsSpecTextVO::getGoodsId,
						Collectors.mapping(GoodsSpecTextVO::getSpecText,Collectors.joining(" "))));
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public GoodsRelatedRecommendVO wrapperVo(GoodsRelatedRecommend goodsRelatedRecommend) {
		if (goodsRelatedRecommend != null){
			GoodsRelatedRecommendVO goodsRelatedRecommendVO = KsBeanUtil.convert(goodsRelatedRecommend, GoodsRelatedRecommendVO.class);
			return goodsRelatedRecommendVO;
		}
		return null;
	}
}

