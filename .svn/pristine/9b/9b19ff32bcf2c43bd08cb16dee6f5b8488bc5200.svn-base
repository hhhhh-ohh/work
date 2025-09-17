package com.wanmi.sbc.goods.images.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.wanmi.sbc.goods.bean.vo.GoodsImageVO;
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
@Service("GoodsImageService")
public class GoodsImageService {
	@Autowired
	private GoodsImageRepository goodsImageRepository;

	/**
	 * 根据商品ID查询
	 * @author liutao
	 */
	public List<GoodsImage> findImageByGoodsId(String goodsId){
		return goodsImageRepository.findByGoodsId(goodsId);
	}

	/**
	 * 根据商品ID查询
	 * @author liutao
	 */
	public List<GoodsImageVO> findByGoodsId(String goodsId){
		List<GoodsImage> goodsImageList = goodsImageRepository.findByGoodsId(goodsId);
		if (CollectionUtils.isEmpty(goodsImageList)){
			return null;
		}
		List<GoodsImageVO> goodsImageVOList = goodsImageList.stream().map(goodsImage -> this.wrapperVo(goodsImage)).collect(Collectors.toList());
		return goodsImageVOList;
	}
	/**
	 * 根据商品ID集合批量查询
	 * @author liutao
	 */
	public List<GoodsImageVO> findByGoodsIds(List<String> goodsIds){
		List<GoodsImage> goodsImageList = goodsImageRepository.findByGoodsIds(goodsIds);
		if (CollectionUtils.isEmpty(goodsImageList)){
			return null;
		}
		List<GoodsImageVO> goodsImageVOList = goodsImageList.stream().map(goodsImage -> this.wrapperVo(goodsImage)).collect(Collectors.toList());
		return goodsImageVOList;
	}


	/**
	 * 将实体包装成VO
	 * @author liutao
	 */
	public GoodsImageVO wrapperVo(GoodsImage goodsImage) {
		if (goodsImage != null){
			GoodsImageVO goodsImageVO=new GoodsImageVO();
			KsBeanUtil.copyPropertiesThird(goodsImage,goodsImageVO);
			return goodsImageVO;
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
        List<GoodsImage> oldImages = goodsImageRepository.findByGoodsId(goodsId);
        // 更新图片
        if (CollectionUtils.isNotEmpty(newImages)) {
            Set<String> oldSet = new HashSet<>();
            if (CollectionUtils.isNotEmpty(oldImages)) {
                oldSet.addAll(
                        oldImages.stream()
                                .map(GoodsImage::getArtworkUrl)
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
            List<GoodsImage> newImageList =
                    newImages.stream()
                            .filter(i -> !oldSet.contains(i))
                            .map(
                                    img -> {
                                        GoodsImage image = new GoodsImage();
                                        image.setArtworkUrl(img);
                                        image.setGoodsId(goodsId);
                                        image.setCreateTime(LocalDateTime.now());
                                        image.setUpdateTime(image.getCreateTime());
                                        image.setDelFlag(DeleteFlag.NO);
                                        return image;
                                    })
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(newImageList)) {
                goodsImageRepository.saveAll(newImageList);
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
