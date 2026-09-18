---
title: Fundamentos de IBM Bob
---

# Fundamentos de IBM Bob

> Lee esto primero. Son 10 minutos que evitarán el 80% de la confusión al usar Bob por primera vez.

---

## ¿Qué es IBM Bob y en qué se diferencia del autocompletado?

| Característica | Autocompletado clásico (Copilot básico) | IBM Bob |
|---|---|---|
| **Contexto** | El archivo que tienes abierto | Todo el proyecto, archivos relacionados, historial de la sesión |
| **Interacción** | Sugerencias de línea / bloque | Conversación de ida y vuelta con razonamiento |
| **Acción** | Solo escribe código | Lee, escribe, navega y edita múltiples archivos |
| **Modos** | Uno solo | Ask, Plan y Agent (con comportamientos distintos) |
| **Memoria de sesión** | No | Sí, recuerda lo que hablaron antes en la misma sesión |

---

## Los Tres Modos de Bob

### 🔵 Modo Ask — *"Explícame sin tocar nada"*

Úsalo cuando quieres **entender** algo: una clase que heredaste, un algoritmo de cálculo de comisiones, un proceso batch que nadie documentó.

Bob lee los archivos que le señalas y responde en lenguaje natural. **No modifica ningún archivo.**

**Cuándo usarlo en el banco:**
- Entender lógica de cálculo de intereses o comisiones heredada
- Auditar un flujo de validación de cuentas
- Preguntar por qué un proceso falla sin saber dónde empezar

---

### 🟡 Modo Plan — *"Diseñemos antes de escribir"*

Úsalo cuando tienes un requerimiento nuevo y quieres que Bob **proponga una arquitectura o plan de implementación** antes de generar código.

**Cuándo usarlo en el banco:**
- Diseñar un nuevo microservicio de consulta de movimientos
- Planificar la migración de un servicio SOAP a REST
- Proponer la estructura de un módulo antes de que el equipo lo implemente

---

### 🔴 Modo Agent — *"Hazlo tú, yo reviso"*

Es el modo más poderoso. Bob puede **leer, crear, editar y eliminar archivos** de tu proyecto.

> ⚠️ **Consejo Senior:** Empieza siempre en Ask o Plan para entender el contexto. Cambia a Agent solo cuando ya sabes qué quieres que Bob haga. Esto evita cambios inesperados en archivos críticos.

---

## Buenas Prácticas de Prompts

### ✅ Prompts que funcionan bien

```
"Tengo adjunto el archivo ComisionService.java.
Explícame paso a paso qué hace el método calcularComisionInterbancaria()
y qué casos borde no están cubiertos."
```

### ❌ Prompts que dan resultados pobres

```
"Arregla el código"         ← Sin contexto, sin objetivo claro
"Escribe una API"           ← Sin especificaciones
"Mejora esto"               ← Bob no sabe qué considera mejora para ti
```

### 🎯 Estructura de un buen prompt bancario

```
[ROL]        Actúa como desarrollador Java senior bancario
[CONTEXTO]   Tenemos adjunto [archivo]. Este código hace [X].
[TAREA]      Necesito que [acción concreta].
[RESTRICCIÓN] No cambies [límites importantes].
[FORMATO]    Devuelve [formato de respuesta deseado].
```

---

## Resumen de Atajos Mentales

| Quiero... | Modo |
|---|---|
| Entender código que no escribí yo | Ask |
| Revisar si una lógica financiera es correcta | Ask |
| Planificar un nuevo servicio antes de codificar | Plan |
| Proponer arquitectura para revisión del equipo | Plan |
| Corregir un bug específico | Agent |
| Generar pruebas para una clase existente | Agent |
| Implementar lo que planeé | Plan → Agent |

---

## ▶️ Siguiente Paso

👉 **[Caso 1 — Ask Mode: Lógica Financiera](./casos/basicos/caso-01-ask-logica-financiera.md)**

*Tiempo estimado de este módulo: 10 minutos*
