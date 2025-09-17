package com.wanmi.sbc.message.test;

import lombok.Data;

/**
 * @author zhanggaolei
 * @className UserDTO
 * @description
 * @date 2022/5/7 19:22
 **/
@Data
public class UserDTO {
    public UserDTO(){

    }
    public UserDTO(String userId,String userName){
        this.userId = userId;
        this.userName = userName;
    }
    String userId;
    String userName;
}
