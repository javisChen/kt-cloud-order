package com.kt.cloud.order.acl.sku;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;

import com.kt.cloud.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.component.exception.BizException;
import com.kt.component.microservice.rpc.util.RpcUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuServiceFacade {

    private final SkuServiceApi skuServiceApi;

    public SkuServiceFacade(SkuServiceApi skuServiceApi) {
        this.skuServiceApi = skuServiceApi;
    }

    public List<SkuRespDTO> getSkuInfoList(List<Long> skuIds) {
        SkuInfoGetReqDTO skuInfoGetReqDTO = new SkuInfoGetReqDTO();
        skuInfoGetReqDTO.setSkuIds(skuIds);
        return RpcUtils.checkAndGetData(skuServiceApi.getSkuInfoList(skuInfoGetReqDTO));
    }

    public SkuRespDTO getSkuInfoList(Long skuId) {
        List<SkuRespDTO> skuInfoList = getSkuInfoList(Lists.newArrayList(skuId));
        if (CollUtil.isNotEmpty(skuInfoList)) {
            return skuInfoList.get(0);
        }
        throw new BizException("SKU已失效或已下架");
    }
}