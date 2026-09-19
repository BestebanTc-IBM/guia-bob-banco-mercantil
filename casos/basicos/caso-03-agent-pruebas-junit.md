---
title: "Caso 3 — Agent Mode: Pruebas y Documentación de Negocio"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › <a href="/guia-bob-banco-mercantil/FUNDAMENTOS">Fundamentos</a> › Caso 3</div>

# Caso 3 — Agent Mode: Pruebas JUnit y Documentación de Negocio

> **Modo:** 🔴 Agent &nbsp;|&nbsp; **Tiempo:** 10 min &nbsp;|&nbsp; **Nivel:** Básico

---

## Contexto del Caso

El módulo de validación de cuentas tiene **0% de cobertura de pruebas** y la próxima semana hay una auditoría interna. Pero hay un segundo problema: el coordinador de QA necesita evidencia de los criterios de aceptación validados, en un formato que él pueda leer y adjuntar en el ticket de Jira — **sin acceso al código fuente**.

**Objetivo:** Con un solo flujo de trabajo en Bob generar dos salidas distintas:
1. `CuentaValidatorServiceTest.java` — pruebas JUnit 5 con `@DisplayName` descriptivos
2. `CRITERIOS-VALIDACION-CUENTAS.md` — documentación de criterios de aceptación lista para QA y negocio

Al final verás cómo usar la **Vista Previa de Markdown** de IBM Bob para revisar la documentación sin salir del entorno de trabajo.

---

## Código de Partida

**Si ya tienes el repositorio abierto en IBM Bob:** el archivo está en `codigo/caso-03/CuentaValidatorService.java` — ábrelo desde el explorador de IBM Bob.

**Si aún no tienes el repositorio:** descarga [`CuentaValidatorService.java`](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil/blob/main/codigo/caso-03/CuentaValidatorService.java) individualmente, o vuelve al [inicio](../../index.md) para clonar el repositorio completo.

```java
// CuentaValidatorService.java
package com.bancomercantil.core.cuentas;

import java.math.BigDecimal;

public class CuentaValidatorService {

    private static final BigDecimal LIMITE_SOBREGIRO = new BigDecimal("-5000.00");
    private static final BigDecimal LIMITE_DIARIO    = new BigDecimal("100000.00");

    public enum EstadoCuenta { ACTIVA, BLOQUEADA, INACTIVA, PENDIENTE }

    public boolean puedeDebitar(BigDecimal saldoActual, BigDecimal montoDebito,
                                EstadoCuenta estado, BigDecimal montoAcumuladoHoy) {
        if (estado != EstadoCuenta.ACTIVA) return false;
        if (montoDebito == null || montoDebito.compareTo(BigDecimal.ZERO) <= 0) return false;
        BigDecimal saldoResultante = saldoActual.subtract(montoDebito);
        if (saldoResultante.compareTo(LIMITE_SOBREGIRO) < 0) return false;
        BigDecimal nuevoAcumulado = montoAcumuladoHoy.add(montoDebito);
        return nuevoAcumulado.compareTo(LIMITE_DIARIO) <= 0;
    }

    public boolean esNumeroCuentaValido(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) return false;
        if (!numeroCuenta.matches("\\d{10,20}")) return false;
        int suma = 0;
        for (int i = 0; i < numeroCuenta.length(); i++) {
            int digito = Character.getNumericValue(numeroCuenta.charAt(i));
            suma += (i % 2 == 0) ? digito * 2 : digito;
        }
        return suma % 10 == 0;
    }

    public String determinarTipoOperacion(BigDecimal saldoActual, BigDecimal monto,
                                          boolean esTransferenciaInternacional) {
        if (esTransferenciaInternacional) {
            return monto.compareTo(new BigDecimal("10000.00")) > 0 ? "SWIFT_ALTO_VALOR" : "SWIFT_NORMAL";
        }
        if (saldoActual.compareTo(BigDecimal.ZERO) < 0) return "DEBITO_SOBREGIRO";
        return "DEBITO_NORMAL";
    }
}
```

---

## Paso a Paso con Bob

### Paso 1 — Selecciona 🔴 Agent y adjunta el archivo

Selecciona **Agent Mode** y arrastra `CuentaValidatorService.java` al chat.

> ⚠️ En Agent Mode Bob puede crear y editar archivos. Revisa siempre el diff antes de aceptar.

---

### Paso 2 — Elige tu estilo de prompt

El prompt detallado siempre produce un resultado más preciso porque Bob tiene instrucciones exactas sobre qué cubrir. El corto es útil para explorar rápido, pero puede omitir casos borde que no están explícitos en el código.

| | Prompt corto | Prompt detallado |
|---|---|---|
| **Cuándo usarlo** | Exploración rápida o primera iteración | Código crítico, auditorías, entrega a QA |
| **Cobertura** | Bob infiere los casos — puede haber omisiones | Tú defines los casos — cobertura garantizada |
| **Velocidad** | Más rápido de escribir | Más tiempo de redacción, menos iteraciones |
| **Calidad del output** | Variable según el contexto inferido | Consistente y predecible |

---

#### Opción 1 — Prompt corto (Bob infiere el dominio)

```
Tengo adjunto CuentaValidatorService.java. Es un servicio de validación bancaria.

Genera dos archivos y guárdalos en codigo/caso-03/:

1. CuentaValidatorServiceTest.java — pruebas JUnit 5 completas, @DisplayName en español
   legible para QA, @ParameterizedTest para validaciones de número de cuenta,
   comentarios "// CRITERIO DE NEGOCIO:" en los casos críticos.

2. CRITERIOS-VALIDACION-CUENTAS.md — documento para el coordinador de QA con tabla
   de criterios, casos borde en lenguaje de negocio (sin código) y reglas implícitas.
```

---

#### Opción 2 — Prompt detallado (tú defines los casos)

```
Actúa como desarrollador Java senior y analista de calidad bancario.
Tengo adjunto CuentaValidatorService.java.

Genera DOS archivos en paralelo y guárdalos en codigo/caso-03/:

--- ARCHIVO 1: CuentaValidatorServiceTest.java ---
Pruebas JUnit 5 para los tres métodos del servicio.
- Paquete: com.bancomercantil.core.cuentas
- @DisplayName en español, formato: "Debe [resultado] cuando [condición]"
- @ParameterizedTest + @ValueSource para esNumeroCuentaValido()
- Casos obligatorios para puedeDebitar():
    • Cuenta BLOQUEADA → false
    • Cuenta INACTIVA → false
    • Saldo suficiente, cuenta ACTIVA → true
    • Monto que deja saldo exactamente en -5000.00 → true
    • Monto que deja saldo en -5000.01 → false
    • Acumulado diario que supera 100000.00 → false
    • montoDebito nulo → false
    • montoDebito en cero → false
- Comentario inline // CRITERIO DE NEGOCIO: en cada test que lo amerite

--- ARCHIVO 2: CRITERIOS-VALIDACION-CUENTAS.md ---
Documento de criterios de aceptación para el coordinador de QA.
Secciones:
- Resumen ejecutivo
- Tabla: ID | Método | Condición | Resultado Esperado | Estado
- Casos Borde Críticos en lenguaje de negocio (sin código Java)
- Reglas de Negocio Implícitas (límites, umbrales, estados válidos)
- Versión y Cobertura (fecha, N tests generados)
```

---

### Paso 3 — Revisa el diff de los dos archivos

Bob mostrará dos diffs separados. Verifica:

**Para `CuentaValidatorServiceTest.java`:**
- [ ] ¿Hay al menos 12 métodos de prueba?
- [ ] ¿Los `@DisplayName` están en español y son legibles sin ver el código?
- [ ] ¿Existe un `@ParameterizedTest` para `esNumeroCuentaValido()`?
- [ ] ¿El caso del límite exacto (-5000.00) retorna `true`?

**Para `CRITERIOS-VALIDACION-CUENTAS.md`:**
- [ ] ¿Tiene tabla con columnas ID / Método / Condición / Resultado / Estado?
- [ ] ¿Los casos borde están descritos en lenguaje de negocio, sin código Java?
- [ ] ¿Tiene la sección de Reglas de Negocio Implícitas?

---

### Paso 4 — Abre la Vista Previa de Markdown en IBM Bob

Este es el diferencial de este caso: Bob renderiza Markdown directamente en el entorno.

1. En el explorador de archivos de IBM Bob, navega a `codigo/caso-03/`
2. Haz clic derecho sobre `CRITERIOS-VALIDACION-CUENTAS.md`
3. Selecciona **"Abrir vista previa"** (o **"Open Preview"**)

Verás el documento renderizado con tablas, encabezados y formato — exactamente como lo recibirá el coordinador de QA al abrirlo en GitHub o en cualquier visor Markdown.

> 💡 **La Vista Previa de Markdown es útil para:** documentación técnica, READMEs de microservicios, criterios de aceptación, runbooks y cualquier archivo `.md` que generes con Bob. No necesitas abrir un navegador ni herramienta externa.

---

### Paso 5 — Prompt de refinamiento (opcional)

Si el documento de QA necesita más detalle en algún criterio:

```
En CRITERIOS-VALIDACION-CUENTAS.md, expande la sección "Casos Borde Críticos"
con una descripción de negocio para el caso del límite de sobregiro exacto:
¿por qué -5000.00 es válido pero -5000.01 no lo es? Usa lenguaje bancario,
sin mencionar tipos de datos ni código Java.
```

---

## ✅ Resultado Esperado

**`CuentaValidatorServiceTest.java`:**
- [ ] Al menos 12 tests con `@DisplayName` en español
- [ ] Un `@ParameterizedTest` para validación de número de cuenta
- [ ] El caso del límite exacto de sobregiro cubierto correctamente
- [ ] Comentarios `// CRITERIO DE NEGOCIO:` en los casos críticos

**`CRITERIOS-VALIDACION-CUENTAS.md` (visto en Vista Previa):**
- [ ] Tabla de criterios renderizada con alineación correcta
- [ ] Sección de reglas de negocio implícitas completa
- [ ] Legible para alguien que no sabe Java

---

## Ejemplo de Prueba Bien Generada

```java
@Test
@DisplayName("Debe permitir débito cuando el saldo resultante iguala exactamente el límite de sobregiro")
void debePermitirDebito_cuandoSaldoResultanteEsExactamenteLimiteSobregiro() {
    // CRITERIO DE NEGOCIO: -5000.00 es el límite, no se supera, debe autorizarse
    BigDecimal saldo     = new BigDecimal("-4999.00");
    BigDecimal monto     = new BigDecimal("1.00"); // resultado: exactamente -5000.00
    BigDecimal acumulado = BigDecimal.ZERO;

    assertTrue(validator.puedeDebitar(saldo, monto, EstadoCuenta.ACTIVA, acumulado));
}
```

---

## 💡 Lo Que Aprendiste

1. **Un prompt puede generar múltiples archivos**: Bob no está limitado a un output por conversación.
2. **`@DisplayName` como contrato de negocio**: Las pruebas bien nombradas son documentación ejecutable que el equipo de QA puede leer sin conocer Java.
3. **Vista Previa de Markdown**: IBM Bob renderiza `.md` directamente — no necesitas salir del entorno para revisar documentación.
4. **El mismo trabajo sirve a dos audiencias**: El test es para el desarrollador; el `.md` es para QA, negocio y auditoría.

---

## ▶️ Siguiente Caso

Has completado los tres casos básicos. Ahora es momento de los avanzados.

👉 **[Caso 4 — Plan → Agent: Microservicio REST Bancario](../avanzados/caso-04-plan-agent-microservicio-rest.md)**
