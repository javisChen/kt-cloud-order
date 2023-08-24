package com.ark.center.trade.adapter.cart.web;

import com.ark.center.trade.application.cart.CartAppService;
import com.ark.center.trade.client.cartitem.command.CartItemAddCmd;
import com.ark.center.trade.client.client.command.CartItemCheckCmd;
import com.ark.center.trade.client.client.dto.CartItemDTO;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Tag(name = "购物车接口")
@Validated
@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {
    private final CartAppService cartAppService;
    @Operation(summary = "添加/编辑购物车商品")
    @PostMapping("/item/add")
    public ServerResponse create(@RequestBody @Validated CartItemAddCmd cmd) {
        cartAppService.addOrUpdateCartItem(cmd);
        return ServerResponse.ok();
    }

    @Operation(summary = "选中购物车项")
    @PostMapping("/item/checked")
    public ServerResponse checkCartItem(@RequestBody @Validated CartItemCheckCmd cmd) {
        cartAppService.checkCartItem(cmd);
        return ServerResponse.ok();
    }

    @Operation(summary = "获取用户的购物车信息")
    @GetMapping("/items")
    public MultiResponse<CartItemDTO> listBuyerItems() {
        return MultiResponse.ok(cartAppService.listBuyerCartItems());
    }

}
