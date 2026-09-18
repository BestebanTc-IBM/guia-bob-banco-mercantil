---
title: "Caso 3 — Agent Mode: Generación de Pruebas JUnit/Mockito"
---

# Caso 3 — Agent Mode: Generación de Pruebas JUnit/Mockito

> **Modo:** 🔴 Agent | **Tiempo:** 10 min | **Nivel:** Básico — generación de código de pruebas

---

## Contexto del Caso

El módulo de validación de cuentas tiene **0% de cobertura de pruebas unitarias**. Auditoría interna próxima.

**Objetivo:** Generar pruebas JUnit 5 y Mockito para `CuentaValidatorService.java`, cubriendo casos borde bancarios críticos.

---

## Código de Partida

Abre [`/codigo/caso-03/CuentaValidatorService.java`](/codigo/caso-03/CuentaValidatorService.java).

---

## Paso a Paso con Bob

### Paso 1 — Modo Agent + archivo adjunto

Selecciona **🔴 Agent** y adjunta `CuentaValidatorService.java`.

### Paso 2 — Prompt de análisis de casos (primero)

```
Tengo adjunto CuentaValidatorService.java.
Antes de generar las pruebas, lista todos los casos de prueba que deberían
cubrirse para el método puedeDebitar(), incluyendo:
- Casos normales (happy path)
- Casos borde bancarios: saldo insuficiente, cuenta bloqueada, límite diario exacto,
  monto exactamente igual al umbral de sobregiro, monto nulo, monto en cero.
No escribas código todavía, solo lista los casos.
```

### Paso 3 — Prompt de generación

```
Ahora genera el archivo CuentaValidatorServiceTest.java con JUnit 5 y Mockito.
Incluye:
1. Todos los casos que listaste para puedeDebitar().
2. Al menos 3 casos para esNumeroCuentaValido(): un número válido, uno con letras,
   uno con longitud incorrecta.
3. Al menos 4 casos para determinarTipoOperacion().
4. Usa @DisplayName() con descripciones en español legibles por negocio.
5. Usa @ParameterizedTest donde aplique.
6. Paquete: com.bancomercantil.core.cuentas
```

---

## Ejemplo de Prueba Bien Generada

```java
@Test
@DisplayName("Debe rechazar débito cuando el monto resultante supera el límite de sobregiro")
void debeRechazarDebito_cuandoSaldoResultanteExcedeLimiteSobregiro() {
    BigDecimal saldo = new BigDecimal("-4999.00");
    BigDecimal monto = new BigDecimal("1.01"); // resultado: -5000.01

    assertFalse(validator.puedeDebitar(saldo, monto, EstadoCuenta.ACTIVA, BigDecimal.ZERO));
}
```

---

## 💡 Lo Que Aprendiste

1. **El patrón listar → generar**: Pedir la lista primero produce pruebas más completas.
2. **@DisplayName en español**: Las pruebas son documentación viva para el equipo bancario.
3. **Los casos borde bancarios son del dominio**: Bob necesita que se los describas explícitamente.

---

## ▶️ Siguiente Caso

👉 **[Caso 4 — Plan → Agent: Microservicio REST Bancario](../avanzados/caso-04-plan-agent-microservicio-rest.md)**
