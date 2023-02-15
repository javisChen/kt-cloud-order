package com.ark.center.trade.client.order.dto.info;

import com.ark.center.trade.client.order.dto.ReceiveDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author EOP
 * @since 2022-08-09
 */
@Data
@ApiModel(value = "OrderInfoDTO", description = "订单详情")
public class OrderInfoDTO implements Serializable {

    @ApiModelProperty(value = "订单基本信息", required = true)
    private OrderBaseDTO orderBase;

    @ApiModelProperty(value = "订单费用信息", required = true)
    private OrderChargeDTO orderCharge;

    @ApiModelProperty(value = "订单收货信息", required = true)
    private ReceiveDTO orderReceive;

    @ApiModelProperty(value = "订单商品信息", required = true)
    private List<OrderCommodityDTO> orderCommodities;


}