package com.wanmi.sbc.message.handle;


import com.wanmi.sbc.common.base.MessageMQRequest;


/**
 * 消息处理接口
 * @author xuyunpeng
 */
public interface MessageDelivery {

     void handle(MessageMQRequest request);

     /***
      * 获得消息处理中文名
      * 用于打印日志
      * @return
      */
     default String messageHandlerName(){
          return null;
     }
}
