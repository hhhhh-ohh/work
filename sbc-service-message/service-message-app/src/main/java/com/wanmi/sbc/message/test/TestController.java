package com.wanmi.sbc.message.test;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.bean.enums.NodeType;
import com.wanmi.sbc.message.pushsendnode.model.root.PushSendNode;
import com.wanmi.sbc.message.pushsendnode.service.PushSendNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-14
 * \* Time: 18:34
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
@Slf4j
public class TestController {
////    @Autowired
////    private CustomGroupGenerateService customGroupGenerateService;
////
////    @GetMapping("/testJob/customGroupJob")
////    public BaseResponse customGroupJob(String param,String statDate){
////        LocalDate localDate = LocalDate.now();
////        if(StringUtils.isNotEmpty(statDate)){
////            localDate = DateUtil.parseDay(statDate).toLocalDate();
////        }
////        switch (param){
////            case "1":
////                this.customGroupGenerateService.generateCustomerBaseInfoStatistics();
////                log.info("会员基本信息统计完成！");
////                return BaseResponse.success("会员基本信息统计完成");
////            case "2":
////                this.customGroupGenerateService.generateCustomerRecentParamStatistics(localDate);
////                log.info("会员最近指标数据统计完成！");
////                return BaseResponse.success("会员最近指标数据统计完成");
////            case "3":
////                this.customGroupGenerateService.generateCustomerTradeStatistics(localDate);
////                log.info("会员订单数据统计完成！");
////                return BaseResponse.success("会员订单数据统计完成");
////            case "4":
////                this.customGroupGenerateService.generateCustomGroupCustomerRelStatistics();
////                log.info("自定义人群会员分组完成！");
////                return BaseResponse.success("自定义人群会员分组完成！");
////            case "5":
////                this.customGroupGenerateService.generateCustomGroupStatistics(localDate);
////                log.info("自定义人群人数统计完成！");
////                return BaseResponse.success("自定义人群人数统计完成！");
////
////        }
////        return BaseResponse.SUCCESSFUL();
//    }

    @Autowired
    private PushSendNodeService pushSendNodeService;

    @GetMapping("/test")
    public BaseResponse test(){
        PushSendNode one = pushSendNodeService.getOne(1L);
        PushSendNode node = pushSendNodeService.findByNodeTypeAndCode(NodeType.ORDER_PROGRESS_RATE.toValue(), "ORDER_COMMIT_SUCCESS");
        log.info("=======one:{},", one);
        log.info("=======node：{}", node);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据IP地址获取mac地址
     * @param ipAddress 127.0.0.1
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static String getLocalMac(String ipAddress) throws SocketException,
            UnknownHostException {
        // TODO Auto-generated method stub
        String str = "";
        String macAddress = "";
        final String LOOPBACK_ADDRESS = "127.0.0.1";
        // 如果为127.0.0.1,则获取本地MAC地址。
        if (LOOPBACK_ADDRESS.equals(ipAddress)) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            // 貌似此方法需要JDK1.6。
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
                    .getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            macAddress = sb.toString().trim().toUpperCase();
            return macAddress;
        } else {
            // 获取非本地IP的MAC地址
            try {
                System.out.println(ipAddress);
                Process p = Runtime.getRuntime()
                        .exec("nbtstat -A " + ipAddress);
                System.out.println("===process=="+p);
                InputStreamReader ir = new InputStreamReader(p.getInputStream());

                BufferedReader br = new BufferedReader(ir);

                while ((str = br.readLine()) != null) {
                    if(str.indexOf("MAC")>1){
                        macAddress = str.substring(str.indexOf("MAC")+9, str.length());
                        macAddress = macAddress.trim();
                        System.out.println("macAddress:" + macAddress);
                        break;
                    }
                }
                p.destroy();
                br.close();
                ir.close();
            } catch (IOException ex) {
            }
            return macAddress;
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        String mac = getLocalMac("172.19.4.114");
        System.out.println(mac);
    }
}
