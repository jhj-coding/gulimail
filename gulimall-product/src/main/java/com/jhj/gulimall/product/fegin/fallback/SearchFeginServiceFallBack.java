package com.jhj.gulimall.product.fegin.fallback;

import com.jhj.common.to.es.SkuEsModel;
import com.jhj.common.utils.R;
import com.jhj.gulimall.product.fegin.SearchFeginService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchFeginServiceFallBack implements SearchFeginService {

    @Override
    public R productStatusUp(List<SkuEsModel> skuEsModels) {
        return null;
    }
}
