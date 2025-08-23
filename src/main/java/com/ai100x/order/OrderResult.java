package com.ai100x.order;

import java.util.List;

public class OrderResult {
    private int originalAmount;
    private int discount;
    private int totalAmount;
    private List<OrderItem> items;
    
    public OrderResult(int totalAmount, List<OrderItem> items) {
        this.originalAmount = totalAmount;
        this.discount = 0;
        this.totalAmount = totalAmount;
        this.items = items;
    }
    
    public OrderResult(int originalAmount, int discount, int totalAmount, List<OrderItem> items) {
        this.originalAmount = originalAmount;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.items = items;
    }
    
    public int getOriginalAmount() {
        return originalAmount;
    }
    
    public int getDiscount() {
        return discount;
    }
    
    public int getTotalAmount() {
        return totalAmount;
    }
    
    public List<OrderItem> getItems() {
        return items;
    }
}
