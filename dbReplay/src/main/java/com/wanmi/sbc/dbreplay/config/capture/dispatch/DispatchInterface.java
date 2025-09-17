package com.wanmi.sbc.dbreplay.config.capture.dispatch;

import com.wanmi.sbc.dbreplay.bean.capture.OplogData;

import java.util.Collection;

public interface DispatchInterface {

    String beforeProcess(OplogData source);

}
