package com.ai100x.order;

import java.util.List;
import java.util.ArrayList;

public class BuyOneGetOnePromotion {
    private String category;
    
    public BuyOneGetOnePromotion(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }
    
    public List<OrderItem> applyPromotion(List<OrderItem> items) {
        List<OrderItem> result = new ArrayList<>();
        
        for (OrderItem item : items) {
            result.add(item);
            
            // 如果是化妝品類別，買一送一
            if ("cosmetics".equals(item.getCategory())) {
                // 為每個化妝品添加一個免費的相同商品
                OrderItem freeItem = new OrderItem(
                    item.getProductName(), 
                    1, 
                    0, // 免費商品價格為 0
                    item.getCategory()
                );
                result.add(freeItem);
            }
        }
        
        return result;
    }
}
