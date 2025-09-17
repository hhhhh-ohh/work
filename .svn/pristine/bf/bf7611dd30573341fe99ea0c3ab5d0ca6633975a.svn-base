UPDATE `sbc-message`.`message_send_node` SET `node_content` = '您有{当天到账优惠券张数}张优惠券到账，赶紧花掉他们吧~戳此查看>' WHERE `node_code` = 'COUPON_RECEIPT';
UPDATE `sbc-message`.`message_send_node` SET `node_content` = '您有{当天到账优惠券张数}张优惠券今日到期，赶紧花掉他们吧~戳此查看>' WHERE `node_code` = 'COUPON_EXPIRED';

UPDATE `sbc-message`.`push_send_node` SET `node_context` = '您有{当天到账优惠券张数}张优惠券到账，赶紧花掉他们吧~戳此查看>' WHERE `node_code` = 'COUPON_RECEIPT';
UPDATE `sbc-message`.`push_send_node` SET `node_context` = '您有{当天到账优惠券张数}张优惠券今日到期，赶紧花掉他们吧~戳此查看>' WHERE `node_code` = 'COUPON_EXPIRED';

UPDATE `sbc-message`.`sms_template` SET `template_content` = '您有${number}张优惠券到账，赶紧花掉他们吧~' WHERE `business_type` = 'COUPON_RECEIPT';
UPDATE `sbc-message`.`sms_template` SET `template_content` = '您有${number}张优惠券今日到期，赶紧花掉他们吧~' WHERE `business_type` = 'COUPON_EXPIRED';


UPDATE `sbc-marketing`.`marketing_plugin_config` SET `coexist` = 'COUPON,REDUCTION,DISCOUNT,RETURN' WHERE `id` = 2