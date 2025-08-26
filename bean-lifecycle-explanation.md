
# Understanding the Spring Bean Lifecycle in the Pharmaceutical Inventory System

This document explains the lifecycle of Spring beans, using the Pharmaceutical Inventory Management System as a practical example.

## The Spring Bean Lifecycle: A General Overview

In Spring, the IoC (Inversion of Control) container is responsible for managing the lifecycle of objects, which are called "beans." This lifecycle consists of several distinct phases:

1.  **Instantiation:** The Spring container creates an instance of the bean from its class definition. At this point, the bean is just a raw object with its fields uninitialized.

2.  **Populating Properties (Dependency Injection):** The container injects all the dependencies (i.e., other beans) that the bean requires. This is typically done through constructor injection, setter injection, or field injection.

3.  **Initialization:** This phase allows the bean to perform any necessary setup before it's ready to be used. Spring provides several ways to hook into this phase, with the most common being:
    *   **`@PostConstruct`:** A method annotated with `@PostConstruct` is executed after the bean's properties have been set. This is the recommended way to perform custom initialization logic.

4.  **In Use:** The bean is now fully initialized and ready to be used by the application to handle requests or perform its designated tasks.

5.  **Destruction:** When the application is shutting down, the container manages the destruction of the beans. This phase allows for a graceful cleanup and release of resources.
    *   **`@PreDestroy`:** A method annotated with `@PreDestroy` is called just before the bean is removed from the container. This is the ideal place to perform cleanup tasks, such as closing database connections or, in our case, saving in-memory data to a file.

## The Bean Lifecycle in Action: The `InventoryManager`

The `InventoryManager` class in this project is a perfect example of a Spring-managed bean that leverages the lifecycle callbacks.

### 1. Instantiation and Dependency Injection

*   The `InventoryManager` is annotated with `@Service`, which tells Spring to create a single instance of this class and manage it as a bean.
*   The `InventoryController` receives the `InventoryManager` instance through constructor injection. The Spring container automatically supplies the `InventoryManager` bean when it creates the `InventoryController`.

### 2. Initialization with `@PostConstruct`

*   The `initialize()` method in `InventoryManager` is annotated with `@PostConstruct`.
*   **What it does:** After the `InventoryManager` is created, this method is automatically called. It loads the initial inventory data from the `data/inventory.json` file. If the file doesn't exist, it populates the inventory with sample data.
*   **Why it's important:** This ensures that the `InventoryManager` has a valid state and is ready to serve inventory data as soon as the application starts. It separates the core logic of the bean from its initialization process.

```java
@PostConstruct
public void initialize() {
    System.out.println("📦 InventoryManager: Initializing pharmaceutical inventory system...");
    // ... logic to load or create inventory data ...
    System.out.println("✅ InventoryManager: Inventory system initialized...");
}
```

### 3. In Use

*   Once initialized, the `InventoryManager` bean is available in the application.
*   The `InventoryController` uses the `inventoryManager` instance to get the list of medications, calculate statistics, and pass this data to the `index.html` view.

### 4. Destruction with `@PreDestroy`

*   The `cleanup()` method in `InventoryManager` is annotated with `@PreDestroy`.
*   **What it does:** When the application is shut down (e.g., by stopping the server), the Spring container calls this method before destroying the `InventoryManager` bean. It saves the current state of the inventory back to the `inventory.json` file.
*   **Why it's important:** This ensures that any changes made to the inventory during the application's runtime are not lost. It provides a mechanism for graceful shutdown and data persistence.

```java
@PreDestroy
public void cleanup() {
    System.out.println("🔄 InventoryManager: Performing cleanup and saving inventory state...");
    // ... logic to save inventory data to JSON ...
    System.out.println("✅ InventoryManager: Cleanup completed successfully");
}
```

## Summary

The Spring bean lifecycle provides a powerful mechanism for managing the creation, initialization, and destruction of objects in a structured and predictable way. By using annotations like `@PostConstruct` and `@PreDestroy`, we can hook into these lifecycle events to perform essential tasks, as demonstrated by the `InventoryManager` in this project. This leads to cleaner, more organized, and more maintainable code.
