package com.garagna.pharma.inventory.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.garagna.pharma.inventory.model.Medication;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryManager {
    private List<Medication> inventory;
    private boolean initialized = false;
    private final ObjectMapper objectMapper;
    private final String DATA_DIR = "data";
    private final String INVENTORY_FILE = "inventory.json";

    public InventoryManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void initialize() {
        System.out.println("📦 InventoryManager: Initializing pharmaceutical inventory system...");

        inventory = new ArrayList<>();

        try {
            // Try to load from JSON file first
            if (loadInventoryFromJson()) {
                System.out.println("📁 Inventory loaded from JSON file successfully");
            } else {
                // If no file exists or loading fails, create sample data and save it
                System.out.println("📄 No existing inventory file found, creating initial data...");
                createSampleInventory();
                saveInventoryToJson();
            }
        } catch (Exception e) {
            System.err.println("❌ Error during inventory initialization: " + e.getMessage());
            System.out.println("📋 Falling back to sample data...");
            createSampleInventory();
        }

        initialized = true;
        System.out.println("✅ InventoryManager: Inventory system initialized with " + inventory.size() + " medications");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("🔄 InventoryManager: Performing cleanup and saving inventory state...");

        if (inventory != null) {
            System.out.println("📊 Final inventory count: " + inventory.size() + " medications");

            try {
                saveInventoryToJson();
                System.out.println("💾 Inventory state saved to JSON file successfully");
            } catch (Exception e) {
                System.err.println("❌ Error saving inventory: " + e.getMessage());
            }
        }

        initialized = false;
        System.out.println("✅ InventoryManager: Cleanup completed successfully");
    }

    private boolean loadInventoryFromJson() {
        try {
            // First, try to load from external file (for persistence)
            Path externalFile = Paths.get(DATA_DIR, INVENTORY_FILE);
            if (Files.exists(externalFile)) {
                List<Medication> loadedInventory = objectMapper.readValue(
                        externalFile.toFile(),
                        new TypeReference<List<Medication>>() {}
                );
                inventory.addAll(loadedInventory);
                System.out.println("📁 Loaded " + inventory.size() + " medications from external file: " + externalFile);
                return true;
            }

            // If external file doesn't exist, try to load from classpath resources
            Resource resource = new ClassPathResource("data/" + INVENTORY_FILE);
            if (resource.exists()) {
                try (InputStream inputStream = resource.getInputStream()) {
                    List<Medication> loadedInventory = objectMapper.readValue(
                            inputStream,
                            new TypeReference<List<Medication>>() {}
                    );
                    inventory.addAll(loadedInventory);
                    System.out.println("📁 Loaded " + inventory.size() + " medications from classpath resources");
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            System.err.println("❌ Error loading inventory from JSON: " + e.getMessage());
            return false;
        }
    }

    private void saveInventoryToJson() throws IOException {
        // Create data directory if it doesn't exist
        Path dataDir = Paths.get(DATA_DIR);
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
            System.out.println("📁 Created data directory: " + dataDir.toAbsolutePath());
        }

        // Save to external file for persistence
        Path inventoryFile = dataDir.resolve(INVENTORY_FILE);
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(inventoryFile.toFile(), inventory);

        System.out.println("💾 Inventory saved to: " + inventoryFile.toAbsolutePath());
    }


    private void createSampleInventory() {
        // Realistic Aristo Pharma products based on their actual portfolio
        inventory.add(new Medication("Alpha Lipon Aristo 600mg Filmtabletten", "Thioctsäure (Alpha-Lipoic Acid)", 300,
                LocalDate.now().plusMonths(9), "ALA-2024-001"));
        inventory.add(new Medication("Ibuprofen Aristo 400mg Filmtabletten", "Ibuprofen", 1500,
                LocalDate.now().plusMonths(3), "IBU-2024-007"));
        inventory.add(new Medication("Paracetamol Aristo 500mg Tabletten", "Paracetamol", 2000,
                LocalDate.now().plusMonths(7), "PAR-2024-015"));
        inventory.add(new Medication("Amoxicillin Aristo 750mg Filmtabletten", "Amoxicillin", 800,
                LocalDate.now().plusMonths(1), "AMO-2024-023"));
        inventory.add(new Medication("Omeprazol Aristo 20mg Kapseln", "Omeprazol", 1200,
                LocalDate.now().plusMonths(11), "OMP-2024-041"));
        inventory.add(new Medication("Metformin Aristo 850mg Filmtabletten", "Metformin HCl", 950,
                LocalDate.now().plusMonths(8), "MET-2024-033"));
        inventory.add(new Medication("Bisoprolol Aristo 5mg Filmtabletten", "Bisoprolol Fumarat", 600,
                LocalDate.now().plusMonths(4), "BIS-2024-019"));
        inventory.add(new Medication("Simvastatin Aristo 20mg Filmtabletten", "Simvastatin", 750,
                LocalDate.now().plusMonths(6), "SIM-2024-027"));

        System.out.println("📋 Sample inventory created with realistic Aristo Pharma products: " + inventory.size() + " medications");
    }

    // Existing methods remain the same
    public List<Medication> getAllMedications() {
        if (!initialized) {
            throw new IllegalStateException("InventoryManager not initialized");
        }
        return new ArrayList<>(inventory);
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

    // New methods for inventory operations
    public void addMedication(Medication medication) {
        if (!initialized) {
            throw new IllegalStateException("InventoryManager not initialized");
        }
        inventory.add(medication);
        System.out.println("➕ Added medication: " + medication.getName());
    }

    public boolean removeMedication(String batchNumber) {
        if (!initialized) {
            throw new IllegalStateException("InventoryManager not initialized");
        }
        boolean removed = inventory.removeIf(med -> med.getBatchNumber().equals(batchNumber));
        if (removed) {
            System.out.println("➖ Removed medication with batch: " + batchNumber);
        }
        return removed;
    }
}