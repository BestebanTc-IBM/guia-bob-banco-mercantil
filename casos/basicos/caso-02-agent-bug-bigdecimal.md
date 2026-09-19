---
title: "Caso 2 — Agent Mode: Bug de Precisión Monetaria"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › Caso 2</div>

# Caso 2 — Agent Mode: Corrección de Bug de Precisión Monetaria

> **Modo:** 🔴 Agent &nbsp;|&nbsp; **Tiempo:** 10 min &nbsp;|&nbsp; **Nivel:** Básico

---

## Contexto del Caso

El equipo recibió un reporte: **algunas transferencias muestran diferencias de centavos en el balance del cliente**. Tras revisar los logs, sospechan que el servicio `TransferenciaService.java` usa `double` para los cálculos monetarios, lo cual en Java genera errores de punto flotante. Un bug clásico pero con impacto real en el core bancario.

**Objetivo:** Usar Bob en modo Agent para localizar y corregir todos los cálculos con `double` y migrarlos a `BigDecimal` de forma segura.

---

## El Problema: ¿Por qué `double` es un Bug en Bancos?

```java
// Esto parece correcto pero NO lo es:
double saldo = 100.10;
double debito = 0.20;
System.out.println(saldo - debito);
// Resultado: 99.89999999999999  ← ¡Error de centavos!

// Correcto con BigDecimal:
BigDecimal saldo = new BigDecimal("100.10");
BigDecimal debito = new BigDecimal("0.20");
System.out.println(saldo.subtract(debito));
// Resultado: 99.90  ✓
```

---

## Código de Partida

Abre [`../../codigo/caso-02/TransferenciaService.java`](../../codigo/caso-02/TransferenciaService.java).

```java
// TransferenciaService.java  — VERSIÓN CON BUG
package com.bancomercantil.core.transferencias;

public class TransferenciaService {

    public double calcularMontoFinal(double monto, double comision) {
        return monto + comision;
    }

    public double aplicarImpuesto(double monto, double tasaImpuesto) {
        return monto * (1 + tasaImpuesto);
    }

    public double calcularSaldoResultante(double saldoActual, double montoDebito, double montoCredito) {
        double resultado = saldoActual - montoDebito + montoCredito;
        // Redondeo manual con doble, que NO garantiza exactitud
        return Math.round(resultado * 100.0) / 100.0;
    }

    public boolean tieneFondosSuficientes(double saldo, double montoSolicitado) {
        return saldo >= montoSolicitado;
    }

    public double calcularComisionConDescuento(double monto, double comisionBase, double descuento) {
        double comisionFinal = comisionBase - (comisionBase * descuento);
        return monto + comisionFinal;
    }
}
```

---

## Paso a Paso con Bob

### Paso 1 — Cambia al modo Agent

En el panel de Bob, selecciona el modo **🔴 Agent**.

> ⚠️ En este modo Bob SÍ puede editar archivos. Revisa siempre el diff antes de aceptar.

---

### Paso 2 — Adjunta el archivo

Arrastra `TransferenciaService.java` al chat.

---

### Paso 3 — Prompt de diagnóstico primero (buena práctica)

Antes de pedir cambios, confirma el problema:

```
Tengo adjunto TransferenciaService.java.
Identifica todos los lugares donde se usa el tipo primitivo double
para cálculos monetarios. Explica brevemente por qué cada uno
representa un riesgo en un sistema bancario.
No modifiques ningún archivo todavía.
```

Espera la respuesta. Bob debería identificar los 5 métodos con `double`.

---

### Paso 4 — Prompt de corrección y verificación

Una vez confirmado el diagnóstico, da la instrucción de modificación y validación en un solo paso:

```
Realiza la refactorización y validación de TransferenciaService.java:

1. Migración a BigDecimal:
   - Reemplaza todos los tipos 'double' por 'BigDecimal'.
   - Usa siempre constructores de String (ej. new BigDecimal("100.10")), nunca literales double.
   - Aplica .setScale(2, RoundingMode.HALF_UP) a los cálculos monetarios.
   - En tieneFondosSuficientes(), usa compareTo() en lugar de >=.
   - Conserva los nombres de métodos, firmas lógicas y reglas de negocio intactas.
   - Agrega los imports necesarios de java.math.BigDecimal y java.math.RoundingMode.

2. Pruebas y Verificación:
   - Crea un archivo TransferenciaServiceTest.java con un método main() autónomo (sin dependencias externas de JUnit) que valide todos los métodos y casos borde (como el desfase de centavos en 100.10 - 0.20).
   - Compila y ejecuta las pruebas mostrando el reporte en consola.
```

> 🛡️ **Seguridad y Control — "Human in the Loop" (Humano en el Medio):**
> 
> En este paso, Bob te solicitará autorización para **ejecutar comandos de terminal** (compilación con `javac` y ejecución con `java`).
> 
> - **Revisión obligatoria:** Antes de presionar *Aceptar*, inspecciona detenidamente el comando sugerido en pantalla.
> - **Entorno local:** Estos comandos se ejecutan directamente sobre tu estación de trabajo (a menos que las políticas de seguridad de tu máquina o permisos de usuario restrinjan la consola).
> - **Criterio técnico:** Si el comando es seguro y coherente con la tarea, apruébalo; de lo contrario, recházalo o pide un ajuste en el chat. Recuerda: **la IA propone, pero tú como desarrollador mantienes el control total de la ejecución.**

---

### Paso 5 — Revisa el diff antes de aceptar

Bob mostrará los cambios propuestos. Verifica:

- [ ] ¿Todos los `double` fueron reemplazados?
- [ ] ¿Los constructores usan String, no literales?
- [ ] ¿El método `tieneFondosSuficientes` usa `compareTo()`?
- [ ] ¿Los imports están agregados?

Si algo no está bien, dile a Bob exactamente qué corregir antes de aceptar.

---

### Paso 6 — Verifica el resultado

```java
// Así debería quedar tieneFondosSuficientes:
public boolean tieneFondosSuficientes(BigDecimal saldo, BigDecimal montoSolicitado) {
    return saldo.compareTo(montoSolicitado) >= 0;
}
```

---

## ✅ Resultado Esperado

El archivo `TransferenciaService.java` refactorizado debería:

- [ ] No contener ninguna ocurrencia de `double` en parámetros o variables de negocio
- [ ] Usar `BigDecimal` con escala explícita en todos los cálculos
- [ ] Compilar sin errores
- [ ] Mantener la misma firma lógica de cada método
- [ ] Pasar el 100% de las pruebas unitarias generadas en `TransferenciaServiceTest.java`

---

## 💡 Lo Que Aprendiste

1. **Agent Mode edita y ejecuta**: A diferencia de Ask, aquí Bob puede modificar código y ejecutar verificaciones.
2. **El patrón diagnóstico → corrección**: Primero confirmar, luego actuar. Esto aplica a cualquier cambio crítico.
3. **El diff es tu red de seguridad**: Revisa siempre los cambios propuestos antes de aceptarlos.
4. **Verificación autónoma en el prompt**: Solicitar tests con `main()` permite validar los cambios inmediatamente sin fricción de dependencias o frameworks externos.

---

## Navegación

| ← Anterior | Siguiente → |
|---|---|
| [Caso 1 — Lógica Financiera](./caso-01-ask-logica-financiera.md) | [Caso 3 — Pruebas JUnit](./caso-03-agent-pruebas-junit.md) |
