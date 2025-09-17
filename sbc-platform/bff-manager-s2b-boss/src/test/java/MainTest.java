import com.wanmi.sbc.order.api.request.trade.TradePayOnlineCallBackRequest;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;

import org.junit.jupiter.api.Test;

/**
 * @author hanwei
 * @className MainTest
 * @description TODO
 * @date 2021/4/19 19:16
 **/
public class MainTest {

    public static void main(String[] args){
    }

    @Test
    public void testEnumEquals(){
        TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest = TradePayOnlineCallBackRequest.builder()
                .payCallBackType(PayCallBackType.WECAHT)
                .build();
        System.out.println(tradePayOnlineCallBackRequest.getPayCallBackType() == PayCallBackType.WECAHT);
    }
}
