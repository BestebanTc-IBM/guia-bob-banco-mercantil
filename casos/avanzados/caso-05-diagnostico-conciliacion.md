---
title: "Caso 5 — Diagnóstico y Optimización de Conciliación"
---

# Caso 5 — Diagnóstico y Optimización de Proceso de Conciliación

> **Modos:** 🔵 Ask → 🔴 Agent | **Tiempo:** 15 min | **Nivel:** Avanzado

---

## Contexto del Caso

El proceso nocturno de **conciliación** tarda más de 40 minutos para 500,000 registros. Afecta el cierre contable.

**Objetivo:** Diagnosticar los cuellos de botella y guiar una refactorización que mejore el rendimiento.

---

## El Problema Central: Complejidad O(n²)

```java
// LENTO: por cada transacción del core, recorre TODA la lista externa
for (TransaccionCore tc : transaccionesCore) {
    for (TransaccionExterno te : transaccionesExterno) {   // ← O(n²)
        if (tc.getReferencia().equals(te.getReferencia())) {
            // ...
        }
    }
}
```

Abre el código completo: [`/codigo/caso-05/ConciliacionBatchService.java`](/codigo/caso-05/ConciliacionBatchService.java).

---

## Parte 1 — Ask Mode: Diagnóstico

Arrastra el archivo con **🔵 Ask** activado.

```
Tengo adjunto ConciliacionBatchService.java.
Este proceso debe manejar 500,000 registros y tarda más de 40 minutos.
Analiza el código con criterio de rendimiento Java y dime:
1. ¿Cuántos problemas de rendimiento identificas? Nómbralos técnicamente.
2. Para cada problema, explica el impacto estimado con 500,000 registros.
3. ¿Cuál tiene mayor impacto? ¿Por qué?
4. ¿Qué estructuras de datos resolverían cada problema?
No modifiques el archivo.
```

---

## Parte 2 — Agent Mode: Refactorización

Cambia a **🔴 Agent**:

```
Basándote en el diagnóstico, refactoriza ConciliacionBatchService.java:

1. PROBLEMA O(n²): Convierte transaccionesExterno en HashMap<String, TransaccionExterno>
   indexado por referencia ANTES del loop. Reduce búsqueda a O(1).
2. SEGUNDA PASADA: Usa un Set de referencias procesadas para evitar el segundo loop O(n²).
3. STRING BUILDER: Reemplaza concatenación de String por StringBuilder.
Agrega un comentario breve en cada cambio explicando el motivo.
```

### Validación obligatoria antes de liberar en producción

```
Verifica que el algoritmo refactorizado produce los mismos resultados para:
- Una transacción en core y externo con el mismo monto.
- Una transacción en core y externo con montos diferentes.
- Una transacción en core que no existe en externo.
- Una transacción en externo que no existe en core.
Si hay diferencias en el comportamiento, corrígelas.
```

---

## 💡 Lo Que Aprendiste

1. **Ask Mode para diagnóstico evita refactorizaciones en la dirección incorrecta.**
2. **Bob conoce complejidad algorítmica** y razona sobre rendimiento, no solo sintaxis.
3. **El prompt de validación es obligatorio** antes de cambios en procesos del cierre contable.

---

## ▶️ Siguiente Caso

👉 **[Caso 6 — Premium Java Package: Modernización Java 8/11 → 17/21](../premium/caso-06-premium-java-upgrade.md)**
