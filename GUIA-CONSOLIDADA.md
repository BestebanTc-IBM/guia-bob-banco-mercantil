---
title: "Guía Consolidada — IBM Bob para Banco Mercantil"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › Guía Consolidada (versión PDF)</div>

# Guía Práctica de IBM Bob — Banco Mercantil
## Documento Consolidado · Versión para PDF

> **Audiencia:** Desarrolladores Java del Core Bancario
> **Prerequisito:** Acceso a IBM Bob
> **Tiempo total estimado:** 90 minutos

---

# Parte 1 — Fundamentos de IBM Bob

## ¿Qué es IBM Bob?

IBM Bob es un **agente de IA para desarrolladores** integrado directamente en el entorno de trabajo:

- **Razona sobre el proyecto completo**: todas las clases, dependencias e historial de la sesión.
- **Conversa en lenguaje natural**: describe el problema y Bob responde con análisis, propuestas o cambios.
- **Actúa de forma autónoma**: lee, crea, edita y navega múltiples archivos en un solo flujo.
- **Mantiene memoria de sesión**: recuerda el contexto de lo discutido durante la misma sesión.
- **Tres modos distintos**: Ask (análisis sin cambios), Plan (diseño previo), Agent (ejecución directa).

---

## Los Tres Modos de Bob

### 🔵 Modo Ask — "Explícame sin tocar nada"

Úsalo cuando quieres **entender** algo: lógica heredada, algoritmos de cálculo, procesos sin documentar. **Bob no modifica ningún archivo.**

### 🟡 Modo Plan — "Diseñemos antes de escribir"

Úsalo para generar una **propuesta de arquitectura o plan de implementación** antes de generar código.

### 🔴 Modo Agent — "Hazlo tú, yo reviso"

El modo más poderoso. Bob puede **leer, crear, editar y eliminar archivos** del proyecto de forma autónoma.

> ⚠️ **Consejo Senior:** Empieza en Ask o Plan. Siempre revisa el diff antes de aceptar cambios en archivos críticos.

## Estructura de un buen prompt bancario

```
[ROL]         Actúa como desarrollador Java senior bancario
[CONTEXTO]    Tengo adjunto [archivo]. Este código hace [X].
[TAREA]       Necesito que [acción concreta].
[RESTRICCIÓN] No cambies [límites importantes].
[FORMATO]     Devuelve [formato de respuesta deseado].
```

| Quiero... | Modo |
|---|---|
| Entender código que no escribí | Ask |
| Revisar si una lógica es correcta | Ask |
| Planificar un nuevo servicio | Plan |
| Corregir un bug específico | Agent |
| Generar pruebas para una clase | Agent |
| Implementar lo que planeé | Plan → Agent |

---

# Parte 2 — Casos Básicos

## Caso 1 · Ask Mode: Comprensión de Lógica Financiera
**Modo:** 🔵 Ask | **Tiempo:** 10 min

**Prompt 1:** `Tengo adjunto ComisionService.java. Explícame: qué hace este servicio en términos bancarios, cuáles son los flujos principales, qué reglas de negocio están implementadas y qué casos borde podrían no estar cubiertos.`

**Prompt 2:** `Con ejemplos concretos, calcúlame la comisión para: $75,000 cuenta CORRIENTE estándar, $75,000 cliente PREMIUM, $100 cualquier cliente. Muestra el cálculo paso a paso.`

**Prompt 3:** `Como auditor de software bancario, ¿qué entradas podrían causar comportamiento inesperado? Considera: nulos, tipos de cuenta no listados, umbral exacto de $50,000.`

---

## Caso 2 · Agent Mode: Corrección de Bug BigDecimal
**Modo:** 🔴 Agent | **Tiempo:** 10 min

**Diagnóstico primero:**
```
Identifica todos los lugares donde se usa double para cálculos monetarios
en TransferenciaService.java. No modifiques nada todavía.
```

**Corrección:**
```
Refactoriza: reemplaza double por BigDecimal, usa constructores de String,
RoundingMode.HALF_UP escala 2, compareTo() en tieneFondosSuficientes().
```

---

## Caso 3 · Agent Mode: Pruebas JUnit/Mockito
**Modo:** 🔴 Agent | **Tiempo:** 10 min

**Listar primero:**
```
Lista todos los casos de prueba para puedeDebitar() incluyendo:
cuenta bloqueada, saldo insuficiente, límite diario exacto,
sobregiro exactamente igual al límite. No escribas código todavía.
```

**Generar:**
```
Genera CuentaValidatorServiceTest.java con JUnit 5 y Mockito.
Usa @DisplayName en español. Usa @ParameterizedTest donde aplique.
```

---

# Parte 3 — Casos Avanzados

## Caso 4 · Plan → Agent: Microservicio REST
**Modos:** 🟡 Plan → 🔴 Agent | **Tiempo:** 15 min

**Plan Mode:**
```
Actúa como arquitecto Java senior bancario.
Diseña microservicio REST:
- GET /api/v1/cuentas/{id}/movimientos (filtros, paginación max 50)
- POST /bloqueo-preventivo (solo AUDITOR/SUPERVISOR)
- DELETE /bloqueo-preventivo
Stack: Java 11, Spring Boot 2.7, en memoria, header X-User-Role.
Produce lista de clases, paquetes, DTOs, riesgos. No escribas código.
```

**Agent Mode:**
```
Genera los 5 archivos: MovimientoDTO, BloqueoRequest, MovimientoService,
MovimientoServiceImpl (en memoria), MovimientoController.
Paquete base: com.bancomercantil.movimientos
```

---

## Caso 5 · Ask → Agent: Optimización de Conciliación
**Modos:** 🔵 Ask → 🔴 Agent | **Tiempo:** 15 min

**Diagnóstico:**
```
Analiza ConciliacionBatchService.java con criterio de rendimiento Java.
Para 500,000 registros: identifica problemas técnicamente, estima impacto,
indica cuál tiene mayor impacto. No modifiques nada.
```

**Refactorización:**
```
Refactoriza: HashMap para búsqueda O(1), Set para evitar segunda pasada,
StringBuilder en generarReporteTexto(). Agrega comentarios.
```

**Validación obligatoria:**
```
Verifica que el resultado es idéntico para los 4 escenarios:
same monto, diferente monto, solo en core, solo en externo.
```

---

# Parte 4 — Premium Java Package

## Caso 6 · Modernización Java 11 → 21
**Tiempo:** 20 min

**Reporte:**
```
Analiza PagoService.java y pom.xml.
Objetivo: migrar Java 11/Spring Boot 2.7 → Java 21/Spring Boot 3.2.
Produce tabla: Problema | Impacto | Solución.
```

**Migración:**
```
Aplica: javax.* → jakarta.*, java.util.Date → LocalDateTime,
PagoResponse clase → Java Record.
```

**pom.xml:** `Spring Boot 2.7→3.2, Java 11→21, javax.validation→jakarta.validation-api.`

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

*Guía preparada para la PoC de IBM Bob · Banco Mercantil · 2026*
