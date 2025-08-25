package com.garagna.pharma.inventory.service;

import com.garagna.pharma.inventory.model.Medication;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryManager {
    private List<Medication> inventory;
    private boolean initialized = false;

    @PostConstruct
    public void initialize() {
        System.out.println("📦 InventoryManager: Initializing pharmaceutical inventory system...");

        inventory = new ArrayList<>();

        // Load initial sample data (later we'll load from JSON)
        loadSampleInventory();

        initialized = true;
        System.out.println("✅ InventoryManager: Inventory system initialized with " + inventory.size() + " medications");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("🔄 InventoryManager: Performing cleanup and saving inventory state...");

        if (inventory != null) {
            System.out.println("📊 Final inventory count: " + inventory.size() + " medications");
            // Later we'll save to JSON here
        }

        initialized = false;
        System.out.println("✅ InventoryManager: Cleanup completed successfully");
    }

    private void loadSampleInventory() {
        // Sample pharmaceutical data
        inventory.add(new Medication("Ibuprofen 400mg", "Ibuprofen", 500,
                LocalDate.now().plusMonths(6), "IBU-2024-001"));
        inventory.add(new Medication("Paracetamol 500mg", "Paracetamol", 750,
                LocalDate.now().plusMonths(8), "PAR-2024-002"));
        inventory.add(new Medication("Amoxicillin 250mg", "Amoxicillin", 200,
                LocalDate.now().plusMonths(4), "AMX-2024-003"));

        System.out.println("📋 Sample inventory loaded: " + inventory.size() + " medications");
    }

    public List<Medication> getAllMedications() {
        if (!initialized) {
            throw new IllegalStateException("InventoryManager not initialized");
        }
        return new ArrayList<>(inventory); // Return defensive copy
    }

    public int getTotalMedications() {
        return inventory != null ? inventory.size() : 0;
    }

    public int getTotalUnits() {
        if (inventory == null) return 0;
        return inventory.stream().mapToInt(Medication::getQuantity).sum();
    }

    public boolean isInitialized() {
        return initialized;
    }
}