Spring Boot Bean Lifecycle Starter

A simple starter project to explore the Spring Bean lifecycle and use Thymeleaf templates for dynamic HTML rendering.
This project is meant as a learning base to understand how Spring manages beans and how to serve dynamic content in a clean, extendable way.

🎯 Project Purpose

Learn about Spring bean lifecycle (@PostConstruct, @PreDestroy)

See how Spring beans can be injected into controllers

Render bean fields dynamically in an HTML template using Thymeleaf

🚀 What It Demonstrates

✅ Spring Boot auto-configuration

✅ A custom bean (HelloBean) with lifecycle callbacks

✅ Dependency injection into a controller

✅ Serving a dynamic index.html page via Thymeleaf

✅ Clean project structure ready for extension

🛠 Technology Stack

Java 17+

Spring Boot 3.x

Spring Web

Thymeleaf (for HTML templates)

Maven

---

### Deutsche Zusammenfassung 

Diese Demoanwendung illustriert den Lebenszyklus von Spring Beans und deren Verwaltung innerhalb des Spring IoC-Containers. Sie dient als Referenz für das Verständnis der Kernkonzepte von Spring, insbesondere für erfahrene Entwickler, die ihre Kenntnisse auffrischen oder vertiefen möchten.

**Kernkonzepte des Bean-Lebenszyklus:**

1.  **Instanziierung:** Der Spring-Container erstellt die Bean-Instanz aus der Klassendefinition.
2.  **Abhängigkeitsinjektion:** Der Container injiziert alle Abhängigkeiten, die für die Bean erforderlich sind (z. B. andere Beans).
3.  **Initialisierung:**
    *   **`@PostConstruct`:** Eine mit dieser Annotation versehene Methode wird nach der Abhängigkeitsinjektion aufgerufen. Dies ist der ideale Ort für Initialisierungslogik, die auf die injizierten Abhängigkeiten angewiesen ist.
4.  **Betrieb:** Die Bean ist nun vollständig initialisiert und bereit, Anfragen zu bearbeiten.
5.  **Zerstörung:**
    *   **`@PreDestroy`:** Wenn der `ApplicationContext` heruntergefahren wird, wird eine mit dieser Annotation versehene Methode aufgerufen. Dies ermöglicht ein sauberes Herunterfahren und die Freigabe von Ressourcen.

In diesem Projekt wird der `InventoryManager` als `@Service` deklariert, was ihn zu einer von Spring verwalteten Bean macht. Die `@PostConstruct`-Annotation wird verwendet, um das Inventar beim Start der Anwendung zu initialisieren, und `@PreDestroy`, um den Zustand beim Herunterfahren zu speichern. Dieses Beispiel zeigt, wie der Bean-Lebenszyklus genutzt werden kann, um die Initialisierung und Zerstörung von Ressourcen effektiv zu verwalten.