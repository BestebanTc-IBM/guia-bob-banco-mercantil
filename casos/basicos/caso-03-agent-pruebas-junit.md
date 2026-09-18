---
title: "Caso 3 — Agent Mode: Pruebas JUnit/Mockito"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › Caso 3</div>

# Caso 3 — Agent Mode: Generación de Pruebas JUnit/Mockito

> **Modo:** 🔴 Agent &nbsp;|&nbsp; **Tiempo:** 10 min &nbsp;|&nbsp; **Nivel:** Básico

---

## Contexto del Caso

El módulo de validación de cuentas tiene **0% de cobertura de pruebas unitarias**. Auditoría interna próxima.

**Objetivo:** Generar pruebas JUnit 5 + Mockito para `CuentaValidatorService.java`, cubriendo casos borde bancarios críticos.

---

## Código de Partida

Descarga [`CuentaValidatorService.java`](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil/blob/main/codigo/caso-03/CuentaValidatorService.java) del repositorio.

---

## Paso a Paso con Bob

### Paso 1 — Modo 🔴 Agent + archivo adjunto

### Paso 2 — Listar casos primero

```
Tengo adjunto CuentaValidatorService.java.
Antes de generar las pruebas, lista todos los casos de prueba que deberían
cubrirse para el método puedeDebitar(), incluyendo:
- Casos normales (happy path)
- Casos borde bancarios: saldo insuficiente, cuenta bloqueada, límite diario exacto,
  monto exactamente igual al umbral de sobregiro, monto nulo, monto en cero.
No escribas código todavía, solo lista los casos.
```

### Paso 3 — Generar las pruebas

```
Ahora genera el archivo CuentaValidatorServiceTest.java con JUnit 5 y Mockito.
Incluye:
1. Todos los casos que listaste para puedeDebitar().
2. Al menos 3 casos para esNumeroCuentaValido().
3. Al menos 4 casos para determinarTipoOperacion().
4. Usa @DisplayName() con descripciones en español legibles por negocio.
5. Usa @ParameterizedTest donde aplique.
6. Paquete: com.bancomercantil.core.cuentas
```

---

## Ejemplo de Prueba Bien Generada

```java
@Test
@DisplayName("Debe rechazar débito cuando el monto supera el límite de sobregiro")
void debeRechazarDebito_cuandoExcedeLimiteSobregiro() {
    BigDecimal saldo = new BigDecimal("-4999.00");
    BigDecimal monto = new BigDecimal("1.01"); // resultado: -5000.01

    assertFalse(validator.puedeDebitar(saldo, monto, EstadoCuenta.ACTIVA, BigDecimal.ZERO));
}
```

---

## 💡 Lo Que Aprendiste

1. **El patrón listar → generar** produce pruebas más completas.
2. **@DisplayName en español** hace que las pruebas sean documentación del negocio.
3. **Los casos borde bancarios son del dominio**: Bob necesita que se los describas.

---

## Navegación

| ← Anterior | Siguiente → |
|---|---|
| [Caso 2 — Bug BigDecimal](./caso-02-agent-bug-bigdecimal.md) | [Caso 4 — Microservicio REST](../avanzados/caso-04-plan-agent-microservicio-rest.md) |
