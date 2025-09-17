package com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.repository;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.model.root.WechatCateAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>微信类目审核状态DAO</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@Repository
public interface WechatCateAuditRepository extends JpaRepository<WechatCateAudit, Long>,
        JpaSpecificationExecutor<WechatCateAudit> {

    @Modifying
    @Query("update WechatCateAudit set auditStatus = com.wanmi.sbc.common.enums.AuditStatus.NOT_PASS ,rejectReason = ?1 ,updateTime = now() where auditId = ?2")
    int dealNotPass(String rejectReason,String auditId);

    @Modifying
    @Query("update WechatCateAudit set auditStatus = com.wanmi.sbc.common.enums.AuditStatus.CHECKED ,cateIds = '',updateTime = now() where auditId = ?1")
    int dealChecked(String auditId);

    @Modifying
    @Query("update WechatCateAudit set cateIds = ?1,updateTime = now() where wechatCateId = ?2")
    int updateCateIds(String cateIds,Long wechatCateId);

    @Query("select count(*) from WechatCateAudit where auditStatus = com.wanmi.sbc.common.enums.AuditStatus.CHECKED and wechatCateId in ?1")
    int countChecked(List<Long> wechatCateId);

    @Query("select count(*) from WechatCateAudit where auditStatus = com.wanmi.sbc.common.enums.AuditStatus.WAIT_CHECK and wechatCateId in ?1")
    int countWaitCheck(List<Long> wechatCateId);

    @Query("from WechatCateAudit where wechatCateId in ?1 and auditStatus = ?2")
    List<WechatCateAudit> findByWechatCateIdsAndAuditStatus(List<Long> wechatCateIds, AuditStatus auditStatus);

    @Query("select cateIds from WechatCateAudit where auditStatus <> 1 and wechatCateId not in ?1")
    List<String> selectCateIds(List<Long> cateId);

    @Query("select cateIds from WechatCateAudit where auditStatus <> ?1")
    List<String> selectCateIds(AuditStatus auditStatus);

    @Modifying
    @Query("delete from WechatCateAudit where wechatCateId in ?1")
    int deleteAllByWechatCateIds(List<Long> wechatCateId);

    @Query("select new com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO(t.cateId,t.cateName,t.cateGrade,t.thirdPlatformType,t.cateParentId,t.qualificationType,t.qualification,t.productQualificationType,t.productQualification,t.catePath,a.rejectReason,a.auditStatus,a.cateIds,a.productQualificationUrls) from WechatCateAudit a inner join ThirdGoodsCate t on a.wechatCateId = t.cateId where t.thirdPlatformType = com.wanmi.sbc.common.enums.ThirdPlatformType.WECHAT_VIDEO and t.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and a.auditStatus = ?1")
    List<WechatCateDTO> getByAuditStatus(AuditStatus auditStatus);

    @Query("select new com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO(t.cateId,t.cateName,t.cateGrade,t.thirdPlatformType,t.cateParentId,t.qualificationType,t.qualification,t.productQualificationType,t.productQualification,t.catePath,a.rejectReason,a.auditStatus,a.cateIds,a.productQualificationUrls) from WechatCateAudit a inner join ThirdGoodsCate t on a.wechatCateId = t.cateId where t.thirdPlatformType = com.wanmi.sbc.common.enums.ThirdPlatformType.WECHAT_VIDEO and t.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and t.cateId = ?1 and a.auditStatus = com.wanmi.sbc.common.enums.AuditStatus.NOT_PASS")
    WechatCateDTO joinGoodsCateByCateId(Long cateId);

    @Modifying
    @Query("delete from WechatCateAudit where wechatCateId in ?1")
    int delByCateIds(List<Long> cateIds);

}
