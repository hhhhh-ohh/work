package com.wanmi.sbc.goods.mainimages.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.mainimages.GoodsMainImage;
import com.wanmi.sbc.goods.mainimages.GoodsMainImageRepository;
import com.wanmi.sbc.goods.mainimages.GoodsMainImage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.wanmi.sbc.goods.bean.vo.GoodsMainImageVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>商品图片业务逻辑</p>
 * @author liutao
 * @date 2019-02-26 10:35:57
 */
@Service("GoodsMainImageService")
public class GoodsMainImageService {
	@Autowired
	private GoodsMainImageRepository GoodsMainImageRepository;

	/**
	 * 根据商品ID查询
	 * @author liutao
	 */
	public List<GoodsMainImage> findImageByGoodsId(String goodsId){
		return GoodsMainImageRepository.findByGoodsId(goodsId);
	}

	/**
	 * 根据商品ID查询
	 * @author liutao
	 */
	public List<GoodsMainImageVO> findByGoodsId(String goodsId){
		List<GoodsMainImage> GoodsMainImageList = GoodsMainImageRepository.findByGoodsId(goodsId);
		if (CollectionUtils.isEmpty(GoodsMainImageList)){
			return null;
		}
		List<GoodsMainImageVO> GoodsMainImageVOList = GoodsMainImageList.stream().map(GoodsMainImage -> this.wrapperVo(GoodsMainImage)).collect(Collectors.toList());
		return GoodsMainImageVOList;
	}
	/**
	 * 根据商品ID集合批量查询
	 * @author liutao
	 */
	public List<GoodsMainImageVO> findByGoodsIds(List<String> goodsIds){
		List<GoodsMainImage> GoodsMainImageList = GoodsMainImageRepository.findByGoodsIds(goodsIds);
		if (CollectionUtils.isEmpty(GoodsMainImageList)){
			return null;
		}
		List<GoodsMainImageVO> GoodsMainImageVOList = GoodsMainImageList.stream().map(GoodsMainImage -> this.wrapperVo(GoodsMainImage)).collect(Collectors.toList());
		return GoodsMainImageVOList;
	}


	/**
	 * 将实体包装成VO
	 * @author liutao
	 */
	public GoodsMainImageVO wrapperVo(GoodsMainImage GoodsMainImage) {
		if (GoodsMainImage != null){
			GoodsMainImageVO GoodsMainImageVO=new GoodsMainImageVO();
			KsBeanUtil.copyPropertiesThird(GoodsMainImage,GoodsMainImageVO);
			return GoodsMainImageVO;
		}
		return null;
	}

    /**
     * @description 更新图片
     * @author daiyitian
     * @date 2021/4/12 16:17
     * @param goodsId spuId
     * @param newImages 新图片
     */
    @Transactional
    public void updateImages(String goodsId, List<String> newImages) {
        List<GoodsMainImage> oldImages = GoodsMainImageRepository.findByGoodsId(goodsId);
        // 更新图片
        if (CollectionUtils.isNotEmpty(newImages)) {
            Set<String> oldSet = new HashSet<>();
            if (CollectionUtils.isNotEmpty(oldImages)) {
                oldSet.addAll(
                        oldImages.stream()
                                .map(GoodsMainImage::getArtworkUrl)
                                .distinct()
                                .collect(Collectors.toSet()));

                oldImages.stream()
                        .filter(i -> !newImages.contains(i.getArtworkUrl()))
                        .forEach(
                                i -> {
                                    i.setDelFlag(DeleteFlag.YES);
                                    i.setUpdateTime(LocalDateTime.now());
                                });
            }
            List<GoodsMainImage> newImageList =
                    newImages.stream()
                            .filter(i -> !oldSet.contains(i))
                            .map(
                                    img -> {
                                        GoodsMainImage image = new GoodsMainImage();
                                        image.setArtworkUrl(img);
                                        image.setGoodsId(goodsId);
                                        image.setCreateTime(LocalDateTime.now());
                                        image.setUpdateTime(image.getCreateTime());
                                        image.setDelFlag(DeleteFlag.NO);
                                        return image;
                                    })
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(newImageList)) {
                GoodsMainImageRepository.saveAll(newImageList);
            }
        } else if (CollectionUtils.isNotEmpty(oldImages)) {
        	//不存在则删除
            oldImages.forEach(
                    i -> {
                        i.setDelFlag(DeleteFlag.YES);
                        i.setUpdateTime(LocalDateTime.now());
                    });
        }
    }
}
