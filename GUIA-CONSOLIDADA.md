---
title: "Guía Consolidada — IBM Bob para Banco Mercantil"
---

# Guía Práctica de IBM Bob — Banco Mercantil
## Documento Consolidado · Versión para PDF

> **Audiencia:** Desarrolladores Java del Core Bancario
> **Prerequisito:** Acceso a IBM Bob instalado en VS Code o IntelliJ
> **Tiempo total estimado:** 90 minutos

---

# Parte 1 — Fundamentos

## Los Tres Modos

| Modo | Modifica archivos | Usa cuando... |
|---|---|---|
| 🔵 Ask | No | Quieres entender sin riesgo |
| 🟡 Plan | No | Quieres diseñar antes de codificar |
| 🔴 Agent | Sí | Quieres que Bob actúe |

## Estructura de un buen prompt

```
[ROL]         Actúa como desarrollador Java senior bancario
[CONTEXTO]    Tengo adjunto [archivo]. Este código hace [X].
[TAREA]       Necesito que [acción concreta].
[RESTRICCIÓN] No cambies [límites importantes].
[FORMATO]     Devuelve [formato de respuesta deseado].
```

---

# Parte 2 — Casos Básicos

## Caso 1 · Ask Mode: Comprensión de Lógica Financiera
**Modo:** 🔵 Ask | **Tiempo:** 10 min

**Prompt 1 — Visión general:**
```
Tengo adjunto ComisionService.java. Explícame:
1. Qué hace este servicio en términos bancarios.
2. Cuáles son los flujos principales de calcularComision().
3. Qué reglas de negocio están implementadas.
4. Qué casos borde podrían no estar cubiertos.
No modifiques ningún archivo.
```

**Prompt 2 — Ejemplos numéricos:**
```
Con ejemplos concretos, calcúlame la comisión para:
- $75,000 cuenta CORRIENTE cliente estándar
- $75,000 cliente PREMIUM
- $100 cualquier cliente
Muestra el cálculo paso a paso.
```

**Prompt 3 — Auditoría:**
```
Como auditor de software bancario, ¿qué entradas podrían causar
comportamiento inesperado? Considera: nulos, tipos de cuenta no
listados, umbral exacto de $50,000.
```

---

## Caso 2 · Agent Mode: Corrección de Bug BigDecimal
**Modo:** 🔴 Agent | **Tiempo:** 10 min

**Por qué double es un bug bancario:**
```java
double saldo = 100.10;
System.out.println(saldo - 0.20);
// → 99.89999999999999  ← ¡Error de centavos!

// Correcto:
BigDecimal saldo = new BigDecimal("100.10");
System.out.println(saldo.subtract(new BigDecimal("0.20")));
// → 99.90  ✓
```

**Prompt de diagnóstico (primero):**
```
Identifica todos los lugares donde se usa double para cálculos
monetarios en TransferenciaService.java. Explica brevemente por qué
cada uno representa un riesgo bancario.
No modifiques ningún archivo todavía.
```

**Prompt de corrección:**
```
Refactoriza TransferenciaService.java:
1. Reemplaza todos los double por BigDecimal.
2. Usa constructores de String: new BigDecimal("valor").
3. Usa RoundingMode.HALF_UP con escala de 2.
4. En tieneFondosSuficientes(), usa compareTo() en lugar de >=.
5. Mantén los mismos nombres de métodos.
```

---

## Caso 3 · Agent Mode: Pruebas JUnit/Mockito
**Modo:** 🔴 Agent | **Tiempo:** 10 min

**Prompt de análisis (primero):**
```
Antes de generar las pruebas, lista todos los casos de prueba para
puedeDebitar() de CuentaValidatorService.java, incluyendo:
cuenta bloqueada, saldo insuficiente, límite diario exacto,
monto nulo, sobregiro exactamente igual al límite.
No escribas código todavía.
```

**Prompt de generación:**
```
Genera CuentaValidatorServiceTest.java con JUnit 5 y Mockito.
Incluye todos los casos listados.
Usa @DisplayName en español legible por negocio.
Usa @ParameterizedTest donde aplique.
Paquete: com.bancomercantil.core.cuentas
```

---

# Parte 3 — Casos Avanzados

## Caso 4 · Plan → Agent: Microservicio REST
**Modos:** 🟡 Plan → 🔴 Agent | **Tiempo:** 15 min

**Plan Mode — diseño arquitectónico:**
```
Actúa como arquitecto Java senior bancario.
Diseña un microservicio REST:
- GET /api/v1/cuentas/{id}/movimientos (filtros fecha, tipo, paginación max 50)
- POST /api/v1/cuentas/{id}/bloqueo-preventivo (solo AUDITOR/SUPERVISOR)
- DELETE /api/v1/cuentas/{id}/bloqueo-preventivo
Stack: Java 11, Spring Boot 2.7, en memoria, header X-User-Role.
Produce: lista de clases, paquetes, DTOs, riesgos. No escribas código.
```

**Agent Mode — implementación (después de validar el plan):**
```
Basándote en el plan, genera los 5 archivos:
1. MovimientoDTO.java
2. BloqueoRequest.java — con Bean Validation
3. MovimientoService.java — interfaz
4. MovimientoServiceImpl.java — implementación en memoria con datos de ejemplo
5. MovimientoController.java — con los 3 endpoints y validación de X-User-Role
Paquete base: com.bancomercantil.movimientos
```

---

## Caso 5 · Ask → Agent: Optimización de Conciliación
**Modos:** 🔵 Ask → 🔴 Agent | **Tiempo:** 15 min

**Prompt de diagnóstico (Ask Mode):**
```
Analiza ConciliacionBatchService.java con criterio de rendimiento Java.
Para 500,000 registros: identifica problemas técnicamente, estima impacto
de cada uno, indica cuál tiene mayor impacto. No modifiques nada.
```

**Prompt de refactorización (Agent Mode):**
```
Refactoriza ConciliacionBatchService.java:
1. Convierte transaccionesExterno en HashMap indexado por referencia
   antes del loop. Reduce búsqueda a O(1).
2. Elimina la segunda pasada O(n²) usando un Set de referencias procesadas.
3. Reemplaza concatenación de String por StringBuilder.
Agrega comentario breve en cada cambio explicando el motivo.
```

**Validación obligatoria antes de liberar:**
```
Verifica que el algoritmo refactorizado produce los mismos resultados para:
- Transacción en core y externo, mismo monto.
- Transacción en core y externo, montos distintos.
- Transacción en core sin par en externo.
- Transacción en externo sin par en core.
```

---

# Parte 4 — Premium Java Package

## Caso 6 · Modernización Java 11 → 21
**Tiempo:** 20 min

**Capacidades del Premium Java Package:**
| Capacidad | Descripción |
|---|---|
| Análisis de migración | Identifica APIs deprecadas o removidas entre versiones |
| Recetas de modernización | Aplica transformaciones (javax → jakarta) |
| Análisis de dependencias | Detecta librerías incompatibles |
| Explicación LTS | Explica Records, Sealed Classes, Virtual Threads |
| Upgrade pom.xml | Actualiza versiones de Spring Boot y dependencias |

**Prompt 1 — Reporte de migración:**
```
Actúa como experto en modernización Java con IBM Bob Premium Java Package.
Tengo adjuntos PagoService.java y pom.xml.
Objetivo: migrar Java 11/Spring Boot 2.7 → Java 21/Spring Boot 3.2.
Produce tabla: Problema | Impacto | Solución.
Incluye: javax.*, java.util.Date, candidatos a Java Records, pom.xml.
```

**Prompt 2 — Migración automática:**
```
Aplica la migración en PagoService.java:
1. javax.* → jakarta.*
2. java.util.Date → java.time.LocalDateTime
3. Clase interna PagoResponse → Java Record:
   public record PagoResponse(String referencia, String estado, LocalDateTime fecha) {}
```

**Prompt 3 — Actualización pom.xml:**
```
Actualiza pom.xml:
- spring-boot-starter-parent: 2.7.x → 3.2.x
- Java: 11 → 21 en maven.compiler properties
- javax.validation → jakarta.validation-api
```

**Antes / Después:**
```java
// ANTES — Java 11 / Spring Boot 2.7
import javax.transaction.Transactional;
import java.util.Date;

public static class PagoResponse {
    private final String referencia;
    private final Date fecha;
    // constructor + getters...
}

// DESPUÉS — Java 21 / Spring Boot 3.2
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

public record PagoResponse(String referencia, String estado, LocalDateTime fecha) {}
```

---

# Apéndice — Referencia Rápida

## Checklist del Desarrollador Bob

**Antes de enviar un prompt:**
- [ ] ¿Está el modo correcto seleccionado?
- [ ] ¿Adjunté los archivos relevantes?
- [ ] ¿Mi prompt tiene ROL, CONTEXTO, TAREA y RESTRICCIONES?

**Antes de aceptar cambios de Agent Mode:**
- [ ] ¿Revisé el diff completo?
- [ ] ¿Los cambios están dentro del alcance que pedí?
- [ ] ¿Para código bancario crítico, pedí un prompt de validación lógica?

## Frases Útiles

| Situación | Frase |
|---|---|
| Limitar alcance | "No modifiques ningún archivo todavía" |
| Confirmación previa | "Primero lista los cambios que harás" |
| Contexto bancario | "Actúa como desarrollador Java senior del core bancario" |
| Validación | "Verifica que el resultado es correcto para estos casos: ..." |

---

*Guía preparada para la PoC de IBM Bob · Banco Mercantil · 2025*
