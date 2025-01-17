package com.ark.center.trade.adapter.order.consumer;

import com.ark.center.trade.application.order.OrderCommandHandler;
import com.ark.center.trade.client.pay.common.PayConst;
import com.ark.center.trade.client.pay.mq.PayOrderChangedEventDTO;
import com.ark.component.mq.MQType;
import com.ark.component.mq.core.annotations.MQMessageListener;
import com.ark.component.mq.core.processor.SimpleMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付单生成成功后通知
 */
@MQMessageListener(
        mq = MQType.ROCKET,
        consumerGroup = PayConst.CG_TAG_PAY_ORDER_CREATED,
        topic = PayConst.TOPIC_PAY,
        tags = PayConst.TAG_PAY_ORDER_CREATED
)
@Component
@Slf4j
@RequiredArgsConstructor
public class PayOrderCreatedEventListener extends SimpleMessageHandler<PayOrderChangedEventDTO> {

    private final OrderCommandHandler orderCommandHandler;

    @Override
    protected void handleMessage(String msgId, String sendId, PayOrderChangedEventDTO eventDTO, Object o) {
        log.info("Received [PayOrderCreated] message -> msgId = {}, sendId = {}, eventDTO = {}", msgId, sendId, eventDTO);
        String bizTradeNo = eventDTO.getBizTradeNo();
        try {
            orderCommandHandler.onPayOrderCreated(eventDTO);
            log.info("Order {} has been updated successfully", bizTradeNo);
        } catch (Exception e) {
            log.error("Failed to update order {}", bizTradeNo, e);
            throw e;
        }
    }
}
