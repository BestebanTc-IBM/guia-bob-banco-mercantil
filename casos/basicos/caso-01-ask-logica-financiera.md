---
title: "Caso 1 — Ask Mode: Lógica Financiera"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › <a href="/guia-bob-banco-mercantil/FUNDAMENTOS">Fundamentos</a> › Caso 1</div>

# Caso 1 — Ask Mode: Comprensión de Lógica Financiera

> **Modo:** 🔵 Ask &nbsp;|&nbsp; **Tiempo:** 10 min &nbsp;|&nbsp; **Nivel:** Básico — sin modificar código

---

## 📥 ¿Tienes el repositorio abierto en IBM Bob?

Para este caso necesitas el archivo `codigo/caso-01/ComisionService.java`. Si aún no tienes el repositorio, descárgalo ahora:

**Opción A — Con Git:**
```bash
git clone https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil.git
```

**Opción B — Sin Git:**
Ve a [github.com/BestebanTc-IBM/guia-bob-banco-mercantil](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil) → botón verde **`< > Code`** → **`Download ZIP`** → extrae y abre la carpeta en IBM Bob.

Una vez abierta la carpeta en IBM Bob, Bob tendrá acceso automático a todos los archivos. No necesitas adjuntar nada manualmente.

---

## Contexto del Caso

Eres nuevo en el equipo de **Core Bancario**. Te asignan revisar el módulo de cálculo de comisiones interbancarias, que nadie en el equipo ha tocado en 3 años. El archivo tiene 200 líneas, está sin comentarios y tiene varios métodos encadenados.

**Objetivo:** Usar Bob en modo Ask para entender completamente la lógica antes de cualquier cambio.

---

## Código de Partida

**Si ya tienes el repositorio abierto en IBM Bob:** el archivo está en `codigo/caso-01/ComisionService.java` — ábrelo directamente desde el explorador de archivos de IBM Bob.

**Si aún no tienes el repositorio:** descarga [`ComisionService.java`](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil/blob/main/codigo/caso-01/ComisionService.java) individualmente desde GitHub, o vuelve al [inicio](../../index.md) para clonar el repositorio completo antes de continuar.

Este es el código con el que trabajarás:

```java
// ComisionService.java
package com.bancomercantil.core.comisiones;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComisionService {

    private static final BigDecimal TASA_INTERBANCARIA_BASE = new BigDecimal("0.0035");
    private static final BigDecimal TASA_PREMIUM            = new BigDecimal("0.0020");
    private static final BigDecimal UMBRAL_MONTO_ALTO       = new BigDecimal("50000.00");
    private static final BigDecimal COMISION_MINIMA         = new BigDecimal("2.50");
    private static final BigDecimal COMISION_MAXIMA         = new BigDecimal("500.00");

    public BigDecimal calcularComision(BigDecimal monto, String tipoCuenta, boolean esClientePremium) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }

        BigDecimal tasa = resolverTasa(monto, tipoCuenta, esClientePremium);
        BigDecimal comision = monto.multiply(tasa).setScale(2, RoundingMode.HALF_UP);

        return aplicarLimites(comision);
    }

    private BigDecimal resolverTasa(BigDecimal monto, String tipoCuenta, boolean esClientePremium) {
        if (esClientePremium) {
            return TASA_PREMIUM;
        }
        if ("CORRIENTE".equals(tipoCuenta) && monto.compareTo(UMBRAL_MONTO_ALTO) > 0) {
            return TASA_INTERBANCARIA_BASE.multiply(new BigDecimal("0.80"))
                                         .setScale(4, RoundingMode.HALF_UP);
        }
        return TASA_INTERBANCARIA_BASE;
    }

    private BigDecimal aplicarLimites(BigDecimal comision) {
        if (comision.compareTo(COMISION_MINIMA) < 0) return COMISION_MINIMA;
        if (comision.compareTo(COMISION_MAXIMA) > 0) return COMISION_MAXIMA;
        return comision;
    }

    public boolean validarCuenta(String numeroCuenta, String tipoCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) return false;
        if (!numeroCuenta.matches("\\d{10,20}")) return false;

        return switch (tipoCuenta) {
            case "CORRIENTE", "AHORRO", "PLAZO_FIJO" -> true;
            default -> false;
        };
    }
}
```

---

## Paso a Paso con Bob

### Paso 1 — Selecciona el modo correcto

En el panel de Bob, selecciona el modo **🔵 Ask**.

> Por qué Ask y no Agent: Solo queremos entender, no modificar. Ask es más seguro y suficiente para esta tarea.

---

### Paso 2 — Adjunta el archivo

Arrastra `ComisionService.java` al chat de Bob, o escribe `@ComisionService.java` en el prompt.

---

### Paso 3 — Primer prompt: Visión general

Copia y pega este prompt tal cual:

```
Tengo adjunto el archivo ComisionService.java.
Explícame de forma clara y estructurada:
1. Qué hace este servicio en términos de negocio bancario.
2. Cuáles son los flujos principales del método calcularComision().
3. Qué reglas de negocio están implementadas (tarifas, límites, condiciones).
4. Qué casos borde podrían no estar cubiertos.
No modifiques ningún archivo.
```

**¿Qué esperar?**
Bob debería responder con algo similar a:

> *"Este servicio calcula la comisión interbancaria para una transacción. Aplica tres reglas: (1) clientes premium pagan tasa reducida (0.20%), (2) cuentas corrientes con montos superiores a $50,000 reciben descuento del 20% sobre la tasa base, (3) la comisión siempre queda entre $2.50 y $500.00 independientemente del cálculo..."*

---

### Paso 4 — Segundo prompt: Diagrama de flujo

```
Genera un diagrama de flujo del método calcularComision() usando un bloque
mermaid con sintaxis flowchart TD.

Reglas para que el diagrama no falle en el parser de Mermaid:
- No uses || ni && dentro de los nodos; escríbelos como texto: "es null o menor que cero", "y".
- No uses < > = en etiquetas de nodos; escríbelos como palabras: "mayor que", "menor que".
- No uses comas en números (50000 no 50,000).
- Nodos rectangulares entre [ ], decisiones entre { }.
- Etiquetas de aristas solo con Sí / No o texto simple.
```

> ⚠️ **Nota para el lector:** No le pidas a Bob que calcule comisiones numéricas paso a paso. Los LLMs pueden dar resultados incorrectos con total confianza en aritmética decimal. Para verificar valores concretos, ejecuta el código directamente en Java o en una consola Python.

---

### Paso 5 — Tercer prompt: Auditoría de casos borde

```
Actúa como auditor de software bancario.

Analiza solo el flujo lógico del código — sin hacer cálculos numéricos.

Para cada escenario indica qué rama se ejecuta y si hay riesgo:
  1. monto = null
  2. monto = 0.00 o negativo
  3. tipoCuenta = null o "EMPRESARIAL" o "corriente" (minúsculas)
  4. monto = 50000.00 exactamente vs 50000.01 en cuenta CORRIENTE
  5. esClientePremium = true con tipoCuenta = null

Devuelve una tabla: Escenario | Rama ejecutada | Riesgo (Alto/Medio/Bajo)
```

---

## ✅ Resultado Esperado

Al terminar los tres prompts deberás poder responder sin mirar el código:

- [ ] ¿Cuál es la tasa para un cliente premium?
- [ ] ¿Qué pasa con una cuenta de tipo "EMPRESARIAL"?
- [ ] ¿Qué comisión paga alguien que transfiere exactamente $50,000?
- [ ] ¿Qué pasa si el monto calculado es $1.00?

---

## 💡 Lo Que Aprendiste

1. **Ask Mode no modifica archivos**: Es completamente seguro para exploración.
2. **Bob entiende dominio bancario**: No necesitas explicarle qué es una tasa interbancaria.
3. **Los prompts en cadena son más poderosos**: Cada respuesta enriquece el contexto del siguiente prompt.
4. **Bob como colega de revisión**: Puedes usarlo para auditar código antes de tocarlo.

---

## ▶️ Siguiente Caso

👉 **[Caso 2 — Agent Mode: Corrección de Bug de Precisión](./caso-02-agent-bug-bigdecimal.md)**
