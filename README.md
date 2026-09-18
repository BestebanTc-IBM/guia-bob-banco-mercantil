---
title: Guía IBM Bob — Banco Mercantil
layout: default
---

# Guía Práctica de IBM Bob — Banco Mercantil

> **Objetivo:** Llevar a cualquier desarrollador —desde el más senior hasta el que nunca usó un agente de IA— a ser productivo con IBM Bob en una sola sesión de trabajo.

---

## Ruta de Aprendizaje Recomendada

| Tiempo | Módulo | Descripción |
|--------|--------|-------------|
| 10 min | [Fundamentos](./FUNDAMENTOS.md) | Modos, interfaz y primeros prompts |
| 10 min | [Caso 1 — Ask Mode](./casos/basicos/caso-01-ask-logica-financiera.md) | Entender lógica financiera sin tocar código |
| 10 min | [Caso 2 — Agent Mode](./casos/basicos/caso-02-agent-bug-bigdecimal.md) | Detectar y corregir bug de precisión monetaria |
| 10 min | [Caso 3 — Agent Mode](./casos/basicos/caso-03-agent-pruebas-junit.md) | Generar pruebas JUnit/Mockito bancarias |
| 15 min | [Caso 4 — Plan → Agent](./casos/avanzados/caso-04-plan-agent-microservicio-rest.md) | Diseñar e implementar microservicio REST |
| 15 min | [Caso 5 — Diagnóstico](./casos/avanzados/caso-05-diagnostico-conciliacion.md) | Optimizar proceso batch de conciliación (4 archivos Java) |
| 20 min | [Caso 6 — Premium Java](./casos/premium/caso-06-premium-java-upgrade.md) | Migración Java 8/11 → 17/21 asistida |
| 25 min | [Caso Extra — Dashboard Web 🚀](./casos/extra/caso-07-web-dashboard.md) | Dashboard HTML operativo bancario (opcional) |

**Tiempo total estimado: ~90 min · con caso extra: ~115 min**

---

## Perfil del Lector

- **El que viene de un IDE clásico** (Eclipse, IntelliJ sin IA): aprenderá a delegar tareas repetitivas.
- **El que ya usó GitHub Copilot**: entenderá por qué Bob razona sobre el proyecto completo, no solo el archivo abierto.
- **El desarrollador senior Java/COBOL**: verá casos reales del core bancario y migración de legado.

---

## Requisitos Previos

- Acceso a IBM Bob (extensión instalada en VS Code o IntelliJ)
- JDK 11+ instalado localmente
- Maven 3.8+ o Gradle 7+
- Sin dependencias externas: todos los ejemplos son autocontenidos

---

> **Por dónde empezar:** Lee primero [Fundamentos](./FUNDAMENTOS.md) — 10 minutos que resolverán el 80% de las dudas iniciales.

*Guía preparada para la PoC de IBM Bob · Banco Mercantil · 2025*
