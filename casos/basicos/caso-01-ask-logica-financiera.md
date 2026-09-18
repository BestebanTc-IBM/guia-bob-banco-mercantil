---
title: "Caso 1 — Ask Mode: Comprensión de Lógica Financiera"
---

# Caso 1 — Ask Mode: Comprensión de Lógica Financiera

> **Modo:** 🔵 Ask | **Tiempo:** 10 min | **Nivel:** Básico — sin modificar código

---

## Contexto del Caso

Eres nuevo en el equipo de **Core Bancario**. Te asignan revisar el módulo de cálculo de comisiones interbancarias, que nadie ha tocado en 3 años. Sin comentarios, 200 líneas, métodos encadenados.

**Objetivo:** Usar Bob en modo Ask para entender completamente la lógica antes de cualquier cambio.

---

## Código de Partida

Abre [`/codigo/caso-01/ComisionService.java`](/codigo/caso-01/ComisionService.java) del repositorio.

```java
// ComisionService.java
public class ComisionService {
    private static final BigDecimal TASA_INTERBANCARIA_BASE = new BigDecimal("0.0035");
    private static final BigDecimal TASA_PREMIUM            = new BigDecimal("0.0020");
    private static final BigDecimal UMBRAL_MONTO_ALTO       = new BigDecimal("50000.00");
    private static final BigDecimal COMISION_MINIMA         = new BigDecimal("2.50");
    private static final BigDecimal COMISION_MAXIMA         = new BigDecimal("500.00");

    public BigDecimal calcularComision(BigDecimal monto, String tipoCuenta, boolean esClientePremium) {
        // ... ver archivo completo
    }
}
```

---

## Paso a Paso con Bob

### Paso 1 — Modo Ask
Selecciona **🔵 Ask** en el panel de Bob.
> No modifica archivos. Es completamente seguro para exploración.

### Paso 2 — Adjunta el archivo
Arrastra `ComisionService.java` al chat o escribe `@ComisionService.java`.

### Paso 3 — Primer prompt: Visión general

```
Tengo adjunto el archivo ComisionService.java.
Explícame de forma clara y estructurada:
1. Qué hace este servicio en términos de negocio bancario.
2. Cuáles son los flujos principales del método calcularComision().
3. Qué reglas de negocio están implementadas (tarifas, límites, condiciones).
4. Qué casos borde podrían no estar cubiertos.
No modifiques ningún archivo.
```

### Paso 4 — Ejemplos numéricos

```
Ahora explícame con un ejemplo numérico concreto:
- Una transferencia de $75,000 de una cuenta CORRIENTE de cliente estándar.
- Una transferencia de $75,000 de un cliente PREMIUM.
- Una transferencia de $100 de cualquier cliente.
Muestra el cálculo paso a paso para cada caso.
```

### Paso 5 — Auditoría de casos borde

```
Actuando como un auditor de software bancario:
¿Qué escenarios de entrada podrían causar un comportamiento inesperado
en este código? Considera: valores nulos, tipos de cuenta no listados,
montos negativos, montos exactamente iguales al umbral de $50,000.
```

---

## ✅ Resultado Esperado

Al terminar deberás poder responder sin mirar el código:
- [ ] ¿Cuál es la tasa para un cliente premium?
- [ ] ¿Qué pasa con una cuenta de tipo "EMPRESARIAL"?
- [ ] ¿Qué comisión paga alguien que transfiere exactamente $50,000?
- [ ] ¿Qué pasa si el monto calculado es $1.00?

---

## 💡 Lo Que Aprendiste

1. **Ask Mode no modifica archivos**: Seguro para exploración en código de producción.
2. **Bob entiende dominio bancario**: No necesitas explicarle qué es una tasa interbancaria.
3. **Prompts en cadena son más poderosos**: Cada respuesta enriquece el siguiente prompt.

---

## ▶️ Siguiente Caso

👉 **[Caso 2 — Agent Mode: Corrección de Bug de Precisión](./caso-02-agent-bug-bigdecimal.md)**
