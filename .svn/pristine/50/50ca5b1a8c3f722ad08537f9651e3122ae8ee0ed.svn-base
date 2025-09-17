package com.wanmi.sbc.goods.goodscatethirdcaterel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>平台类目和第三方平台类目映射DAO</p>
 * @author 
 * @date 2020-08-18 19:51:55
 */
@Repository
public interface GoodsCateThirdCateRelRepository extends JpaRepository<GoodsCateThirdCateRel, Long>,
        JpaSpecificationExecutor<GoodsCateThirdCateRel> {

    /**
     * 单个删除平台类目和第三方平台类目映射
     * @author 
     */
    @Modifying
    @Query("update GoodsCateThirdCateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    Optional<GoodsCateThirdCateRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    @Modifying
    @Query("update GoodsCateThirdCateRel SET delFlag=com.wanmi.sbc.common.enums.DeleteFlag.YES WHERE delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND thirdCateId IN ?1 AND thirdPlatformType=?2")
    int deleteInThirdCateIds( List<Long> thirdCateIds, ThirdPlatformType thirdPlatformType);

    GoodsCateThirdCateRel findByThirdCateIdAndThirdPlatformTypeAndDelFlag(
            Long ThirdCateId, ThirdPlatformType thirdPlatformType, DeleteFlag delFlag);

    @Query("from GoodsCateThirdCateRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and thirdPlatformType = ?2 and thirdCateId in ?1")
    List<GoodsCateThirdCateRel> findByThirdCateIdsAndThirdPlatformType(List<Long> ThirdCateIds, ThirdPlatformType thirdPlatformType);

    @Query("select cateId from GoodsCateThirdCateRel  where thirdPlatformType = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and thirdCateId not in ?2")
    List<Long> selectCateIdByThirdPlatformType(ThirdPlatformType thirdPlatformType,List<Long> thirdCateId);

}
