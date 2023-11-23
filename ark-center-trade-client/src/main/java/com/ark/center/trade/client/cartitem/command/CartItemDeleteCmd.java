package com.ark.center.trade.client.cartitem.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 添加商品进购物车模型
 * </p>
 *
 * @author EOP
 * @since 2022-08-23
 */
@Data
@Schema(name = "CartItemDeleteCmd", description = "删除购物车商品")
public class CartItemDeleteCmd implements Serializable {

    @Schema(name = "购物车商品子项ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购物车商品子项ID不能为空")
    private List<Long> cartItemIds;

}