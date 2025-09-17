package com.wanmi.sbc.mq.report.osd;

import com.wanmi.ares.enums.AresErrorCodeEnum;
import com.wanmi.ares.exception.AresRuntimeException;
import com.wanmi.osd.ExceptionHandle.OSDException;
import com.wanmi.osd.OsdClient;
import com.wanmi.osd.bean.OsdClientParam;
import com.wanmi.osd.bean.OsdResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.response.yunservice.YunAvailableConfigResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 对象存储服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
@Slf4j
public class OsdService {

    @Autowired
    private YunServiceProvider yunServiceProvider;

    /**
     * 上传OSD
     * @param baos 缓存数据流
     * @param key 指定资源key
     * @return
     * @throws AresRuntimeException
     */
    public void uploadExcel(ByteArrayOutputStream baos, String key) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())){
            YunAvailableConfigResponse configResponse = yunServiceProvider.getAvailableYun().getContext();
            OsdClientParam osdClientParam = KsBeanUtil.convert(configResponse,OsdClientParam.class);
            OsdClient.instance().putObject(osdClientParam, OsdResource.builder()
                    .osdInputStream(bais)
                    .osdResourceKey(key)
                    .build());
        } catch (OSDException osd) {
            log.error("上传文件-OSD服务器端错误->错误码:{},描述:{}", osd.getErrorCode(), osd.getMessage());
            throw new AresRuntimeException(AresErrorCodeEnum.K130001, osd);
        } catch (IOException io) {
            throw new AresRuntimeException(AresErrorCodeEnum.K130001, io);
        }
    }

    /**
     * 上传OSD
     * @param data 数据
     * @param key 指定资源key
     * @return
     * @throws AresRuntimeException
     */
    public void uploadExcel(String data, String key) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))){
            YunAvailableConfigResponse configResponse = yunServiceProvider.getAvailableYun().getContext();
            OsdClientParam osdClientParam = KsBeanUtil.convert(configResponse,OsdClientParam.class);
            OsdClient.instance().putObject(osdClientParam, OsdResource.builder()
                    .osdInputStream(bais)
                    .osdResourceKey(key)
                    .build());
        } catch (OSDException osd) {
            log.error("上传文件-OSD服务器端错误->错误码:{},描述:{}", osd.getErrorCode(), osd.getMessage());
            throw new AresRuntimeException(AresErrorCodeEnum.K130001, osd);
        } catch (IOException io) {
            throw new AresRuntimeException(AresErrorCodeEnum.K130001, io);
        }
    }
}
