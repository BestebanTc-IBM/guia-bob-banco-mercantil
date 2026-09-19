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

> ⚠️ **Consejo Senior:** Empieza en Ask o Plan para entender el contexto. Cambia a Agent solo cuando sabes exactamente qué quieres. Siempre revisa el diff antes de aceptar cambios en archivos críticos.

## Buenas Prácticas de Prompts

### Estructura de un buen prompt bancario

```
[ROL]         Actúa como desarrollador Java senior bancario
[CONTEXTO]    Tengo adjunto [archivo]. Este código hace [X].
[TAREA]       Necesito que [acción concreta].
[RESTRICCIÓN] No cambies [límites importantes].
[FORMATO]     Devuelve [formato de respuesta deseado].
```

### Tabla rápida de decisión de modo

| Quiero... | Modo |
|---|---|
| Entender código que no escribí | Ask |
| Revisar si una lógica es correcta | Ask |
| Planificar un nuevo servicio | Plan |
| Proponer arquitectura para revisión | Plan |
| Corregir un bug específico | Agent |
| Generar pruebas para una clase | Agent |
| Implementar lo que planeé | Plan → Agent |

---

# Parte 2 — Casos de Uso Básicos

---

## Caso 1 · Ask Mode: Comprensión de Lógica Financiera

**Modo:** 🔵 Ask | **Tiempo:** 10 min | **Nivel:** Básico

### Contexto

Eres nuevo en el equipo de Core Bancario. Te asignan revisar el módulo de cálculo de comisiones interbancarias sin documentación, sin comentarios, 200 líneas.

### Código de Partida

```java
// ComisionService.java
public class ComisionService {
    private static final BigDecimal TASA_INTERBANCARIA_BASE = new BigDecimal("0.0035");
    private static final BigDecimal TASA_PREMIUM            = new BigDecimal("0.0020");
    private static final BigDecimal UMBRAL_MONTO_ALTO       = new BigDecimal("50000.00");
    private static final BigDecimal COMISION_MINIMA         = new BigDecimal("2.50");
    private static final BigDecimal COMISION_MAXIMA         = new BigDecimal("500.00");

    public BigDecimal calcularComision(BigDecimal monto, String tipoCuenta, boolean esClientePremium) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        BigDecimal tasa = resolverTasa(monto, tipoCuenta, esClientePremium);
        BigDecimal comision = monto.multiply(tasa).setScale(2, RoundingMode.HALF_UP);
        return aplicarLimites(comision);
    }
    // ... ver archivo completo en codigo/caso-01/
}
```

### Prompts para usar con Bob

**Prompt 1 — Visión general:**
```
Tengo adjunto ComisionService.java.
Explícame: qué hace este servicio en términos bancarios, cuáles son los flujos
principales, qué reglas de negocio están implementadas y qué casos borde
podrían no estar cubiertos.
```

**Prompt 2 — Ejemplos numéricos:**
```
Con ejemplos concretos, calcúlame la comisión para:
- $75,000 cuenta CORRIENTE cliente estándar
- $75,000 cliente PREMIUM
- $100 cualquier cliente
Muestra el cálculo paso a paso.
```

**Prompt 3 — Auditoría:**
```
Como auditor de software bancario, ¿qué entradas podrían causar comportamiento
unexpected? Considera: nulos, tipos de cuenta no listados, umbral exacto de $50,000.
```

### Lo Que Aprendiste
- Ask Mode es seguro: no modifica archivos.
- Bob entiende dominio bancario: no necesitas explicarle conceptos del sector.
- Los prompts en cadena son más poderosos que uno solo largo.

---

## Caso 2 · Agent Mode: Corrección de Bug de Precisión Monetaria

**Modo:** 🔴 Agent | **Tiempo:** 10 min | **Nivel:** Básico

### Contexto

Reporte de QA: diferencias de centavos en el balance de clientes. El `TransferenciaService.java` usa `double` para cálculos monetarios.

### Por Qué `double` es un Bug Bancario

```java
// Parece correcto pero NO lo es:
double saldo = 100.10;
System.out.println(saldo - 0.20);
// → 99.89999999999999  ← ¡Error de centavos!

// Correcto:
BigDecimal saldo = new BigDecimal("100.10");
System.out.println(saldo.subtract(new BigDecimal("0.20")));
// → 99.90  ✓
```

### Prompt de Diagnóstico (primero)
```
Identifica todos los lugares donde se usa double para cálculos monetarios
en TransferenciaService.java. Explica brevemente por qué cada uno
representa un riesgo bancario. No modifiques ningún archivo todavía.
```

### Prompt de Corrección y Verificación (después de confirmar)
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

> 🛡️ **Human in the Loop (Comandos en Terminal):** Bob solicitará autorización antes de ejecutar comandos CLI (`javac`, `java`). Revisa siempre la instrucción antes de aceptarla; eres el guardián de la seguridad en tu estación de trabajo.

### Checklist de Revisión del Diff
- [ ] ¿Todos los `double` fueron reemplazados?
- [ ] ¿Los constructores usan String, no literales?
- [ ] ¿`tieneFondosSuficientes` usa `compareTo()`?
- [ ] ¿Pasan todas las pruebas del runner ejecutable?

### Lo Que Aprendiste
- El patrón **diagnóstico → corrección y verificación** evita cambios inesperados y asegura la solución.
- El diff de Agent Mode es tu red de seguridad.
- Pedir pruebas autónomas con `main()` permite verificación inmediata sin lidiar con configuración de librerías.

---

## Caso 3 · Agent Mode: Generación de Pruebas JUnit/Mockito

**Modo:** 🔴 Agent | **Tiempo:** 10 min | **Nivel:** Básico

### Contexto

El módulo de validación de cuentas tiene 0% de cobertura de pruebas. Auditoría interna próxima.

### Prompt de Análisis de Casos (primero)
```
Antes de generar pruebas, lista todos los casos de prueba para puedeDebitar()
de CuentaValidatorService.java, incluyendo casos borde bancarios:
cuenta bloqueada, saldo insuficiente, límite diario exacto, monto nulo,
sobregiro exactamente igual al límite.
No escribas código todavía.
```

### Prompt de Generación
```
Genera CuentaValidatorServiceTest.java con JUnit 5 y Mockito.
Incluye todos los casos listados. Usa @DisplayName en español legible por negocio.
Usa @ParameterizedTest para validaciones de número de cuenta.
Paquete: com.bancomercantil.core.cuentas
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
