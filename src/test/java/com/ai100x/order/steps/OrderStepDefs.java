package com.ai100x.order.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;
import com.ai100x.order.OrderService;
import com.ai100x.order.OrderItem;
import com.ai100x.order.OrderResult;
import com.ai100x.order.ThresholdDiscountPromotion;
import com.ai100x.order.BuyOneGetOnePromotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OrderStepDefs {
    
    private OrderService orderService;
    private List<OrderItem> orderItems;
    private OrderResult orderResult;
    
    @Given("^no promotions are applied$")
    public void noPromotions() {
        orderService = new OrderService();
    }
    
    @Given("^the threshold discount promotion is configured:$")
    public void thresholdDiscountPromotionConfigured(io.cucumber.datatable.DataTable table) {
        if (orderService == null) {
            orderService = new OrderService();
        }
        List<Map<String, String>> rows = table.asMaps();
        Map<String, String> row = rows.get(0);
        int threshold = Integer.parseInt(row.get("threshold"));
        int discount = Integer.parseInt(row.get("discount"));
        
        ThresholdDiscountPromotion promotion = new ThresholdDiscountPromotion(threshold, discount);
        orderService.setThresholdDiscountPromotion(promotion);
    }
    
    @Given("^the buy one get one promotion for cosmetics is active$")
    public void buyOneGetOnePromotionActive() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        BuyOneGetOnePromotion promotion = new BuyOneGetOnePromotion("cosmetics");
        orderService.setBuyOneGetOnePromotion(promotion);
    }
    
    @When("^a customer places an order with:$")
    public void placeOrder(io.cucumber.datatable.DataTable table) {
        orderItems = new ArrayList<>();
        List<Map<String, String>> rows = table.asMaps();
        
        for (Map<String, String> row : rows) {
            String productName = row.get("productName");
            int quantity = Integer.parseInt(row.get("quantity"));
            int unitPrice = Integer.parseInt(row.get("unitPrice"));
            
            OrderItem item;
            if (row.containsKey("category")) {
                String category = row.get("category");
                item = new OrderItem(productName, quantity, unitPrice, category);
            } else {
                item = new OrderItem(productName, quantity, unitPrice);
            }
            orderItems.add(item);
        }
        
        orderResult = orderService.calculateOrder(orderItems);
    }
    
    @Then("^the order summary should be:$")
    public void orderSummary(io.cucumber.datatable.DataTable expected) {
        List<Map<String, String>> rows = expected.asMaps();
        Map<String, String> expectedRow = rows.get(0);
        
        if (expectedRow.containsKey("totalAmount") && !expectedRow.containsKey("originalAmount")) {
            // 第一個和第三個情境：只有 totalAmount
            int expectedTotalAmount = Integer.parseInt(expectedRow.get("totalAmount"));
            Assertions.assertEquals(expectedTotalAmount, orderResult.getTotalAmount(), 
                "Order total amount should match expected value");
        } else {
            // 第二個情境：有 originalAmount, discount, totalAmount
            int expectedOriginalAmount = Integer.parseInt(expectedRow.get("originalAmount"));
            int expectedDiscount = Integer.parseInt(expectedRow.get("discount"));
            int expectedTotalAmount = Integer.parseInt(expectedRow.get("totalAmount"));
            
            Assertions.assertEquals(expectedOriginalAmount, orderResult.getOriginalAmount(), 
                "Order original amount should match expected value");
            Assertions.assertEquals(expectedDiscount, orderResult.getDiscount(), 
                "Order discount should match expected value");
            Assertions.assertEquals(expectedTotalAmount, orderResult.getTotalAmount(), 
                "Order total amount should match expected value");
        }
    }
    
    @And("^the customer should receive:$")
    public void customerReceive(io.cucumber.datatable.DataTable expected) {
        List<Map<String, String>> expectedRows = expected.asMaps();
        List<OrderItem> actualItems = orderResult.getItems();
        
        // 按商品名稱分組計算實際數量
        Map<String, Integer> actualQuantities = new HashMap<>();
        for (OrderItem item : actualItems) {
            String productName = item.getProductName();
            actualQuantities.put(productName, 
                actualQuantities.getOrDefault(productName, 0) + item.getQuantity());
        }
        
        Assertions.assertEquals(expectedRows.size(), actualQuantities.size(), 
            "Number of unique items should match");
        
        for (Map<String, String> expectedRow : expectedRows) {
            String expectedProductName = expectedRow.get("productName");
            int expectedQuantity = Integer.parseInt(expectedRow.get("quantity"));
            
            Assertions.assertTrue(actualQuantities.containsKey(expectedProductName),
                "Product " + expectedProductName + " should be present");
            Assertions.assertEquals(expectedQuantity, actualQuantities.get(expectedProductName),
                "Quantity for " + expectedProductName + " should match");
        }
    }
}
