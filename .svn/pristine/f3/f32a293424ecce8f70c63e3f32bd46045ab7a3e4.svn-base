package com.wanmi.sbc.ledgeraccount;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author xuyunpeng
 * @className LedgerAccountBaseService
 * @description
 * @date 2022/7/8 9:44 AM
 **/
@Slf4j
@Service
public class MobileLedgerAccountBaseService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取支付开关状态
     * @return true 开启
     */
    public Boolean getGatewayOpen() {
        PayGatewayVO payGatewayVO = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_PAY_SETTING), PayGatewayVO.class);
        if (payGatewayVO == null) {
            return Boolean.FALSE;
        }
        return IsOpen.YES.equals(payGatewayVO.getIsOpen());
    }

    /**
     * 检查支付开关是否开启
     */
    public void checkGatewayOpen(){
        Boolean open = getGatewayOpen();
        if (!open) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010055);
        }
    }

    /**
     * 图片压缩
     * @param file
     * @return
     * @throws IOException
     */
    public byte[] reSizeImage(MultipartFile file) throws IOException {
        byte[] data;

        File dir = new File("ledgerImage/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileExt = ImageUtils.getImageType(file.getOriginalFilename());
        String path = dir.getAbsolutePath() + "/IMAGE:" + DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3)  + "." + fileExt;
        File tempFile = new File(path);
        file.transferTo(tempFile);
        try (FileInputStream fis = new FileInputStream(tempFile);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            if (ImageIO.read(tempFile).getHeight() > Constants.NUM_500 || ImageIO.read(tempFile).getWidth() > Constants.NUM_500) {
                ImageUtils.reSize(tempFile, tempFile, Constants.NUM_500, Constants.NUM_500, true);
            }

            byte[] b = new byte[1024];
            int n;
            while((n = fis.read(b)) != -1 ){
                bos.write(b, 0, n);
            }
            data = bos.toByteArray();
        } catch (Exception e) {
            throw new SbcRuntimeException(e);
        } finally{
            if (tempFile != null && tempFile.exists()) {
                boolean delete = tempFile.delete();
                if (!delete) {
                    log.error("分账图片删除失败");
                }
            }
        }
        return data;
    }

    /**
     * 获取拉卡拉收银台支付开关状态
     * @return true 开启
     */
    public Boolean getLaKaLaCasherPayOpen() {
        PayGatewayVO payGatewayVO = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING), PayGatewayVO.class);
        if (Objects.nonNull(payGatewayVO) && IsOpen.YES.equals(payGatewayVO.getIsOpen())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
