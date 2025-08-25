package com.garagna.pharma.inventory.controller;

import com.garagna.pharma.inventory.model.Medication;
import com.garagna.pharma.inventory.service.InventoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InventoryController {

    private final InventoryManager inventoryManager;

    @Autowired
    public InventoryController(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        System.out.println("🎮 InventoryController: Controller initialized with InventoryManager dependency");
    }

    @GetMapping("/")
    public String showInventory(Model model) {
        System.out.println("🌐 InventoryController: Processing inventory request...");

        try {
            List<Medication> medications = inventoryManager.getAllMedications();
            int totalMedications = inventoryManager.getTotalMedications();
            int totalUnits = inventoryManager.getTotalUnits();

            model.addAttribute("medications", medications);
            model.addAttribute("totalMedications", totalMedications);
            model.addAttribute("totalUnits", totalUnits);
            model.addAttribute("systemStatus", "Online");

            System.out.println("✅ InventoryController: Data prepared for view - " + totalMedications + " medications");

            return "index";
        } catch (Exception e) {
            System.err.println("❌ InventoryController: Error processing request - " + e.getMessage());
            model.addAttribute("error", "Inventory system unavailable");
            model.addAttribute("systemStatus", "Error");
            return "index";
        }
    }
}