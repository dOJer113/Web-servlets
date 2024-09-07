package ru.roznov.servlets_2.objects.requests;

import ru.roznov.servlets_2.objects.products.ProductEnum;

public class HandlingRequest extends AbstractRequest {
    private int productCount;
    private ProductEnum productEnum;

    public HandlingRequest(int driverId, RequestType requestType, int productCount, ProductEnum productEnum) {
        super(driverId, requestType);
        this.productCount = productCount;
        this.productEnum = productEnum;
    }

    public int getProductCount() {
        return this.productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public ProductEnum getProductEnum() {
        return this.productEnum;
    }

    public void setProductEnum(ProductEnum productEnum) {
        this.productEnum = productEnum;
    }



}
