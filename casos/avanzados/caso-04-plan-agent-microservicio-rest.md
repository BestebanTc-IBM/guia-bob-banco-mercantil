---
title: "Caso 4 — Plan → Agent: Microservicio REST"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › Caso 4 — Avanzado</div>

# Caso 4 — Plan → Agent: Diseño e Implementación de Microservicio REST

> **Modos:** 🟡 Plan → 🔴 Agent &nbsp;|&nbsp; **Tiempo:** 15 min &nbsp;|&nbsp; **Nivel:** Avanzado

---

## Contexto del Caso

Canales Digitales necesita un microservicio REST para consulta de movimientos con **bloqueo preventivo** de transacciones sospechosas. El tech lead quiere el diseño aprobado antes de escribir una línea de código.

**Objetivo:** Plan Mode para arquitectura validable → Agent Mode para implementar.

---

## Parte 1 — Modo 🟡 Plan: Diseñar Antes de Codificar

### Prompt de diseño arquitectónico

```
Actúa como arquitecto de software Java senior especializado en banca.
Necesito diseñar un microservicio REST:

DOMINIO: Consulta de movimientos de cuentas bancarias con bloqueo preventivo.

REQUERIMIENTOS FUNCIONALES:
- GET /api/v1/cuentas/{numeroCuenta}/movimientos
  → Filtros opcionales: fechaDesde, fechaHasta, tipoMovimiento (DEBITO/CREDITO)
  → Paginación: page, size (máximo 50 por página)
- POST /api/v1/cuentas/{numeroCuenta}/bloqueo-preventivo
  → Solo roles AUDITOR y SUPERVISOR
- DELETE /api/v1/cuentas/{numeroCuenta}/bloqueo-preventivo

REQUERIMIENTOS TÉCNICOS:
- Java 11, Spring Boot 2.7, sin base de datos real
- Autenticación: header X-User-Role (simplificación para piloto)

Produce: lista de clases, estructura de paquetes, esquema de DTOs,
riesgos técnicos. No escribas código todavía.
```

> **Este es el momento de llevar el plan al comité de arquitectura.**

---

## Parte 2 — Modo 🔴 Agent: Implementar el Plan

```
Basándote en el plan que acabamos de diseñar, genera los 5 archivos:
1. MovimientoDTO.java
2. BloqueoRequest.java — con Bean Validation
3. MovimientoService.java — interfaz
4. MovimientoServiceImpl.java — en memoria con 5 movimientos de ejemplo
5. MovimientoController.java — con los 3 endpoints y validación de X-User-Role

Paquete base: com.bancomercantil.movimientos
```

### Verificación

```
Revisa el controlador generado y confirma:
1. ¿El DELETE también valida X-User-Role?
2. ¿obtenerMovimientos filtra correctamente cuando fecha y tipo son null?
3. ¿Las respuestas de error (403, 404) son consistentes con REST estándar?
Si hay algo incorrecto, corrígelo.
```

---

## 💡 Lo Que Aprendiste

1. **Plan Mode produce arquitectura presentable al comité** antes de una línea de código.
2. **El flujo Plan → Agent es el más poderoso**: Plan define el "qué y cómo", Agent ejecuta.
3. **Bob mantiene el contexto entre modos**: No necesitas repetir los requerimientos.

---

## Navegación

| ← Anterior | Siguiente → |
|---|---|
| [Caso 3 — Pruebas JUnit](../basicos/caso-03-agent-pruebas-junit.md) | [Caso 5 — Conciliación](./caso-05-diagnostico-conciliacion.md) |
