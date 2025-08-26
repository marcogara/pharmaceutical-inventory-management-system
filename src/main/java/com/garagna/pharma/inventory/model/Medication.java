package com.garagna.pharma.inventory.model;

import java.time.LocalDate;

public class Medication {
    private String name;
    private String activeIngredient;
    private int quantity;
    private LocalDate expiryDate;
    private String batchNumber;

    public Medication() {}

    public Medication(String name, String activeIngredient, int quantity, LocalDate expiryDate, String batchNumber) {
        this.name = name;
        this.activeIngredient = activeIngredient;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.batchNumber = batchNumber;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getActiveIngredient() { return activeIngredient; }
    public void setActiveIngredient(String activeIngredient) { this.activeIngredient = activeIngredient; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    @Override
    public String toString() {
        return "Medication{" +
                "name='" + name + '\'' +
                ", activeIngredient='" + activeIngredient + '\'' +
                ", quantity=" + quantity +
                ", expiryDate=" + expiryDate +
                ", batchNumber='" + batchNumber + '\'' +
                '}';
    }
}