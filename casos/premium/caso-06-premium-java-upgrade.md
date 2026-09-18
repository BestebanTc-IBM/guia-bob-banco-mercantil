---
title: "Caso 6 — Premium Java Package: Modernización Java 11 → 21"
---

# Caso 6 — Premium Java Package: Modernización Java 8/11 → 17/21

> **Capacidad:** IBM Bob Premium Package for Java | **Tiempo:** 20 min | **Nivel:** Especial

---

## Contexto del Caso

Infraestructura aprobó la migración del core bancario de **Java 11 a Java 21 LTS**. El mayor bloqueador: múltiples servicios usan `javax.*` (EE 8), renombrado a `jakarta.*` en Jakarta EE 9+. Spring Boot 3.x requiere `jakarta.*` obligatoriamente.

**Objetivo:** Usar el **Premium Java Package de IBM Bob** para guiar la migración de forma automatizada y segura.

---

## ¿Qué es el Premium Java Package de Bob?

| Capacidad | Descripción |
|---|---|
| **Análisis de migración de versión** | Escanea el proyecto e identifica APIs deprecadas o removidas |
| **Recetas de modernización** | Aplica transformaciones conocidas (javax → jakarta) |
| **Análisis de dependencias** | Detecta librerías incompatibles con la versión objetivo |
| **Explicación de características LTS** | Explica Records, Sealed Classes, Virtual Threads con contexto bancario |
| **pom.xml / build.gradle upgrade** | Actualiza versiones de Spring Boot, plugins y dependencias |

---

## Código de Partida

Abre [`/codigo/caso-06/PagoService.java`](/codigo/caso-06/PagoService.java) y [`/codigo/caso-06/pom.xml`](/codigo/caso-06/pom.xml).

---

## Paso a Paso con Bob Premium

### Paso 1 — Reporte de Migración (Ask Mode)

Adjunta `PagoService.java` y `pom.xml` con **🔵 Ask**:

```
Actúa como experto en modernización Java con IBM Bob Premium Java Package.
Tengo adjuntos PagoService.java y pom.xml.
Objetivo: migrar de Java 11 / Spring Boot 2.7 a Java 21 / Spring Boot 3.2.

Produce un reporte de migración con tabla: Problema | Impacto | Solución.
Incluye: imports javax.*, APIs de fecha legadas (java.util.Date),
clases candidatas a Java Records, versiones en pom.xml.
```

### Paso 2 — Migración Automática (Agent Mode)

```
Aplica la migración en PagoService.java:
1. javax.validation.* → jakarta.validation.*
2. javax.persistence.* → jakarta.persistence.*
3. javax.transaction.* → jakarta.transaction.*
4. java.util.Date → java.time.LocalDateTime en toda la clase.
5. Convierte la clase interna PagoResponse a un Java Record:
   public record PagoResponse(String referencia, String estado, LocalDateTime fecha) {}
```

### Paso 3 — Actualización del pom.xml

```
Actualiza pom.xml:
1. spring-boot-starter-parent: 2.7.x → 3.2.x
2. Java: 11 → 21 en maven.compiler properties
3. javax.validation → jakarta.validation-api
4. Actualiza spring-boot-maven-plugin si está presente
```

### Paso 4 — Verificación de Compatibilidad

```
¿El @Transactional de jakarta.transaction es equivalente al de javax.transaction
en Spring Boot 3? ¿O debería usar org.springframework.transaction.annotation.Transactional?
¿El Java Record PagoResponse es compatible con Bean Validation?
¿Hay incompatibilidad entre LocalDateTime y serialización JSON por defecto?
```

---

## Comparación Final: Java 11 vs Java 21

```java
// ANTES — Java 11 / Spring Boot 2.7
import javax.transaction.Transactional;
import java.util.Date;

public static class PagoResponse {
    private final String referencia;
    private final Date fecha;
    public PagoResponse(String r, Date f) { ... }
    public String getReferencia() { return referencia; }
    public Date getFecha() { return fecha; }
}

// DESPUÉS — Java 21 / Spring Boot 3.2
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

public record PagoResponse(String referencia, String estado, LocalDateTime fecha) {}
```

**El mismo resultado con menos código y sin riesgos de compatibilidad.**

---

## 🏁 ¡Guía Completada!

| Caso | Modo | Capacidad |
|---|---|---|
| 1 | Ask | Comprensión de lógica sin riesgo |
| 2 | Agent | Corrección quirúrgica de bugs |
| 3 | Agent | Generación de cobertura de pruebas |
| 4 | Plan → Agent | Arquitectura + implementación |
| 5 | Ask → Agent | Diagnóstico + optimización de rendimiento |
| 6 | Premium Java | Modernización de plataforma asistida |

👉 **[Volver al inicio](../../README.md)**  
📄 **[Ver guía consolidada para PDF](../../GUIA-CONSOLIDADA.md)**
