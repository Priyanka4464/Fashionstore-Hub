package com.fashionstore.model;

import java.sql.Timestamp;
import java.util.List; 

public class Order {

    private int orderId;
    private int userId;
    private String orderNumber;
    private double totalAmount;
    private double discount;
    private double finalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    
    // Delivery Address
    private String name;
    private String phone;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
    
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Order() {}

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    // Helper method to get full address
    public String getFullAddress() {
        return addressLine + ", " + city + ", " + state + " - " + pincode;
    }
     // add this at top of file

 // Add this field and getter/setter
 private List<OrderItem> orderItemsList;

 public List<OrderItem> getOrderItemsList() { return orderItemsList; }
 public void setOrderItemsList(List<OrderItem> list) { this.orderItemsList = list; }
}