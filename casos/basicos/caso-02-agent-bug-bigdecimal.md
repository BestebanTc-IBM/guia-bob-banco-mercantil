---
title: "Caso 2 — Agent Mode: Corrección de Bug de Precisión Monetaria"
---

# Caso 2 — Agent Mode: Corrección de Bug de Precisión Monetaria

> **Modo:** 🔴 Agent | **Tiempo:** 10 min | **Nivel:** Básico — modificación quirúrgica guiada

---

## Contexto del Caso

Reporte de QA: **diferencias de centavos en el balance del cliente**. El `TransferenciaService.java` usa `double` para cálculos monetarios.

**Objetivo:** Localizar y corregir todos los cálculos con `double` y migrarlos a `BigDecimal`.

---

## El Problema: ¿Por qué `double` es un Bug en Bancos?

```java
// Parece correcto pero NO lo es:
double saldo = 100.10;
System.out.println(saldo - 0.20);
// Resultado: 99.89999999999999  ← ¡Error de centavos!

// Correcto con BigDecimal:
BigDecimal saldo = new BigDecimal("100.10");
System.out.println(saldo.subtract(new BigDecimal("0.20")));
// Resultado: 99.90  ✓
```

---

## Código de Partida

Abre [`/codigo/caso-02/TransferenciaService.java`](/codigo/caso-02/TransferenciaService.java).

---

## Paso a Paso con Bob

### Paso 1 — Modo Agent

Selecciona **🔴 Agent**.
> ⚠️ En este modo Bob SÍ puede editar archivos. Revisa siempre el diff antes de aceptar.

### Paso 2 — Prompt de diagnóstico (buena práctica: primero confirmar)

```
Tengo adjunto TransferenciaService.java.
Identifica todos los lugares donde se usa el tipo primitivo double
para cálculos monetarios. Explica brevemente por qué cada uno
representa un riesgo en un sistema bancario.
No modifiques ningún archivo todavía.
```

### Paso 3 — Prompt de corrección

```
Ahora realiza la refactorización:
1. Reemplaza todos los parámetros y variables de tipo double por BigDecimal.
2. Usa BigDecimal con constructores de String (new BigDecimal("valor")),
   nunca con literales double (new BigDecimal(0.1) tiene el mismo problema).
3. Usa RoundingMode.HALF_UP con escala de 2 para todos los setScale().
4. En tieneFondosSuficientes(), usa compareTo() en lugar de >= .
5. Mantén los mismos nombres de métodos y la misma lógica de negocio.
6. Agrega los imports necesarios.
```

### Paso 4 — Revisa el diff antes de aceptar

- [ ] ¿Todos los `double` fueron reemplazados?
- [ ] ¿Los constructores usan String, no literales?
- [ ] ¿`tieneFondosSuficientes` usa `compareTo()`?
- [ ] ¿Los imports están agregados?

---

## 💡 Lo Que Aprendiste

1. **El patrón diagnóstico → corrección**: Primero confirmar, luego actuar.
2. **El diff es tu red de seguridad**: Nunca aceptes cambios sin leerlo completo.
3. **Prompts con restricciones explícitas**: "usa constructores de String" evita reproducir el error.

---

## ▶️ Siguiente Caso

👉 **[Caso 3 — Agent Mode: Generación de Pruebas JUnit](./caso-03-agent-pruebas-junit.md)**
