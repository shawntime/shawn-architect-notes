package com.shawntime.api.product;

import com.shawntime.api.product.model.ProductOut;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author mashaohua
 * @date 2022/4/8 18:52
 */
@Component
public class ProductServiceFallBackFactory implements FallbackFactory {

    @Override
    public Object create(Throwable cause) {
        return (IProductService) productId -> {
            ProductOut productOut = new ProductOut();
            productOut.setProductId(productId);
            productOut.setProductName("降级处理");
            return productOut;
        };
    }
}
