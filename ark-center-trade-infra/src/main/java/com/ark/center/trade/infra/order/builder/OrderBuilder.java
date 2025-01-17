package com.ark.center.trade.infra.order.builder;

import com.ark.center.trade.client.order.dto.*;
import com.ark.center.trade.infra.order.Order;
import com.ark.center.trade.infra.order.service.OrderService;
import com.ark.component.common.util.assemble.DataProcessor;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderBuilder {

    private final OrderService orderService;

    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        // 订单基本信息
        OrderBaseDTO orderBaseDTO = assembleOrderBase(order);
        orderDTO.setOrderBase(orderBaseDTO);

        // 订单费用信息
        OrderAmountDTO orderAmountDTO = assembleOrderAmount(order);
        orderDTO.setOrderAmount(orderAmountDTO);
        return orderDTO;
    }

    private OrderAmountDTO assembleOrderAmount(Order order) {
        OrderAmountDTO orderAmountDTO = new OrderAmountDTO();
        orderAmountDTO.setExpectAmount(order.getExpectAmount());
        orderAmountDTO.setActualAmount(order.getActualAmount());
        orderAmountDTO.setFreightAmount(order.getFreightAmount());
        return orderAmountDTO;
    }

    private OrderBaseDTO assembleOrderBase(Order order) {
        OrderBaseDTO orderBaseDTO = new OrderBaseDTO();
        orderBaseDTO.setCreateTime(order.getCreateTime());
        orderBaseDTO.setId(order.getId());
        orderBaseDTO.setTradeNo(order.getTradeNo());
        orderBaseDTO.setOrderType(order.getOrderType());
        orderBaseDTO.setOrderChannel(order.getOrderChannel());
        orderBaseDTO.setOrderStatus(order.getOrderStatus());
        orderBaseDTO.setPayStatus(order.getPayStatus());
        orderBaseDTO.setPayType(order.getPayType());
        orderBaseDTO.setPayTradeNo(order.getPayTradeNo());
        orderBaseDTO.setPayTime(order.getPayTime());
        orderBaseDTO.setDeliverTime(order.getDeliverTime());
        orderBaseDTO.setReceiveTime(order.getReceiveTime());
        orderBaseDTO.setBuyerRemark(order.getBuyerRemark());
        orderBaseDTO.setBuyerId(order.getBuyerId());
        orderBaseDTO.setBuyerName(order.getBuyerName());
        orderBaseDTO.setSellerId(order.getSellerId());
        orderBaseDTO.setLogisticsCompany(order.getLogisticsCompany());
        orderBaseDTO.setLogisticsCode(order.getLogisticsCode());
        return orderBaseDTO;
    }

    public OrderDTO build(Order record, OrderBuildProfiles profiles) {
        return Optional.ofNullable(build(Lists.newArrayList(record), profiles))
                .map(List::getFirst)
                .orElse(null);
    }

    public List<OrderDTO> build(List<Order> records, OrderBuildProfiles profiles) {
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        log.info("Building orders, profiles = {}", profiles);
        List<OrderDTO> orders = records.stream().map(this::toOrderDTO).toList();

        Function<OrderDTO, Long> orderIdFunc = orderDTO -> orderDTO.getOrderBase().getId();

        DataProcessor<OrderDTO> dataProcessor = DataProcessor.create(orders);

        if (profiles.getWithReceive()) {
            dataProcessor.keySelect(orderIdFunc)
                    .query(orderService::selectReceives)
                    .keyBy(OrderReceiveDTO::getOrderId)
                    .object()
                    .process(OrderDTO::setOrderReceive);
        }

        if (profiles.getWithOrderItems()) {
            dataProcessor.keySelect(orderIdFunc)
                    .query(orderService::selectOrderItems)
                    .keyBy(OrderItemDTO::getOrderId)
                    .collection()
                    .process(OrderDTO::setOrderItems);
        }

        return orders;
    }
}
