package com.ai100x.order;

import java.util.List;
import java.util.Map;

public class OrderService {
    
    private ThresholdDiscountPromotion thresholdDiscountPromotion;
    private BuyOneGetOnePromotion buyOneGetOnePromotion;
    
    public OrderService() {
        this.thresholdDiscountPromotion = null;
        this.buyOneGetOnePromotion = null;
    }
    
    public void setThresholdDiscountPromotion(ThresholdDiscountPromotion promotion) {
        this.thresholdDiscountPromotion = promotion;
    }
    
    public void setBuyOneGetOnePromotion(BuyOneGetOnePromotion promotion) {
        this.buyOneGetOnePromotion = promotion;
    }
    
    public OrderResult calculateOrder(List<OrderItem> items) {
        // 先應用買一送一促銷
        List<OrderItem> itemsAfterBogo = items;
        if (buyOneGetOnePromotion != null) {
            itemsAfterBogo = buyOneGetOnePromotion.applyPromotion(items);
        }
        
        int originalAmount = 0;
        for (OrderItem item : itemsAfterBogo) {
            originalAmount += item.getQuantity() * item.getUnitPrice();
        }
        
        int discount = 0;
        if (thresholdDiscountPromotion != null && thresholdDiscountPromotion.isApplicable(originalAmount)) {
            discount = thresholdDiscountPromotion.getDiscount();
        }
        
        int totalAmount = originalAmount - discount;
        
        // 返回正確的計算結果
        return new OrderResult(originalAmount, discount, totalAmount, itemsAfterBogo);
    }
}
