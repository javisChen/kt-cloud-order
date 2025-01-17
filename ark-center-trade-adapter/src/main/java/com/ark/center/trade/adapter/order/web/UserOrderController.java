package com.ark.center.trade.adapter.order.web;

import com.ark.center.trade.application.order.OrderQueryService;
import com.ark.center.trade.client.order.dto.OrderDTO;
import com.ark.center.trade.client.order.query.OrderDetailsQuery;
import com.ark.center.trade.client.order.query.UserOrderPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户订单管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user/order")
public class UserOrderController extends BaseController {

    private final OrderQueryService orderQueryService;

    @Operation(summary = "查询订单列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<OrderDTO>> queryUserOrderPages(@RequestBody @Validated UserOrderPageQry qry) {
        return SingleResponse.ok(orderQueryService.queryUserOrderPages(qry));
    }

    @Operation(summary = "查询订单详情")
    @GetMapping("/details")
    public SingleResponse<OrderDTO> details(OrderDetailsQuery qry) {
        return SingleResponse.ok(orderQueryService.queryDetails(qry));
    }


}
