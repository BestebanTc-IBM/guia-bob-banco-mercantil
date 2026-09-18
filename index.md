---
title: Guía IBM Bob — Banco Mercantil
layout: default
---

# Guía Práctica de IBM Bob — Banco Mercantil

> **Objetivo:** Llevar a cualquier desarrollador —desde el más senior hasta el que nunca usó un agente de IA— a ser productivo con IBM Bob en una sola sesión de trabajo.

---

## 📥 Obtén el Código Antes de Empezar

Todos los casos de uso tienen archivos Java listos para abrir en IBM Bob. Elige la opción que mejor se adapte a ti:

---

### Opción A — Díselo a Bob (la más fácil, sin saber de Git)

Cambia a **🔴 Agent Mode**, copia el siguiente prompt tal como está y envíalo. Bob se encargará de todo:

```
Actúa como ingeniero DevOps. Necesito que clones un repositorio de GitHub
en mi computadora para usarlo como proyecto de práctica.

PASOS QUE DEBES EJECUTAR:

1. Detecta el sistema operativo actual.

2. Determina la carpeta de destino según el SO:
   - Windows: usa C:\Users\<usuario_actual>\Documents\proyectos-bob\
     Si la carpeta no existe, créala.
   - macOS / Linux: usa ~/Documents/proyectos-bob/
     Si la carpeta no existe, créala con mkdir -p.

3. Verifica si Git está instalado ejecutando: git --version
   - Si Git NO está instalado, detente y dime solamente: "No tienes Git instalado."
   - Si Git SÍ está instalado, continúa al paso 4.

4. Verifica si la carpeta guia-bob-banco-mercantil/ ya existe dentro
   del destino para no clonar dos veces.
   - Si ya existe: infórmame y no hagas nada más.
   - Si no existe: ejecuta el clone.

5. Clona el repositorio con este comando exacto:
   git clone https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil.git

   Ejecuta el clone dentro de la carpeta de destino del paso 2.

6. Verifica que el clone fue exitoso comprobando que exista el archivo
   guia-bob-banco-mercantil/index.md en la carpeta de destino.

7. Reporta:
   - Ruta absoluta completa donde quedó el repositorio.
   - Confirmación de que el archivo index.md existe.
   - Instrucción exacta para abrir esa carpeta en IBM Bob
     (Archivo → Abrir carpeta → [ruta completa]).
```

> ⚠️ **Importante:** El prompt está en **Agent Mode** porque necesita ejecutar comandos en tu sistema. Cuando Bob te muestre un comando antes de ejecutarlo, **léelo, verifica que se ve razonable y luego acepta.** Nunca aceptes sin revisar.

---

### Opción A.1 — Con Git manualmente (si ya sabes usarlo)

```bash
git clone https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil.git
```

Abre IBM Bob → **Archivo → Abrir carpeta** → selecciona la carpeta `guia-bob-banco-mercantil/`

---

### Opción B — Sin Git: descarga el ZIP

1. Ve a **[github.com/BestebanTc-IBM/guia-bob-banco-mercantil](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil)**
2. Haz clic en el botón verde **`< > Code`** → **`Download ZIP`**
3. Extrae el ZIP en la carpeta donde guardas tus proyectos
4. Abre IBM Bob → **Archivo → Abrir carpeta** → selecciona la carpeta extraída

> 💡 Una vez abierta la carpeta, Bob tendrá acceso a todos los archivos del repositorio automáticamente. No necesitas adjuntar nada manualmente para los casos de uso.

---

## Ruta de Aprendizaje Recomendada

| Tiempo | Módulo | Descripción |
|--------|--------|-------------|
| 15 min | [Fundamentos](./FUNDAMENTOS.md) | Modos, interfaz y primeros prompts |
| 10 min | [Caso 1 — Ask Mode](./casos/basicos/caso-01-ask-logica-financiera.md) | Entender lógica financiera sin tocar código |
| 10 min | [Caso 2 — Agent Mode](./casos/basicos/caso-02-agent-bug-bigdecimal.md) | Detectar y corregir bug de precisión monetaria |
| 10 min | [Caso 3 — Agent Mode](./casos/basicos/caso-03-agent-pruebas-junit.md) | Generar pruebas JUnit/Mockito bancarias |
| 15 min | [Caso 4 — Plan → Agent](./casos/avanzados/caso-04-plan-agent-microservicio-rest.md) | Diseñar e implementar microservicio REST |
| 15 min | [Caso 5 — Diagnóstico](./casos/avanzados/caso-05-diagnostico-conciliacion.md) | Optimizar proceso batch de conciliación |
| 20 min | [Caso 6 — Premium Java](./casos/premium/caso-06-premium-java-upgrade.md) | Migración Java 8/11 → 17/21 asistida |
| 25 min | [Caso Extra — Dashboard Web 🚀](./casos/extra/caso-07-web-dashboard.md) | Dashboard HTML operativo bancario (opcional) |

**Tiempo total estimado: ~95 min · con caso extra: ~120 min**

---

## Perfil del Lector

- **El desarrollador que nunca usó un agente de IA**: aprenderá a delegar tareas repetitivas y explorar código sin riesgo.
- **El que ya tiene experiencia con herramientas de IA**: descubrirá las capacidades de razonamiento multiarchivo y los modos Ask, Plan y Agent.
- **El desarrollador senior Java/COBOL**: verá casos reales del core bancario y migración de legado.

---

## Requisitos Previos

- Acceso a IBM Bob
- JDK 11+ instalado localmente
- Maven 3.8+ o Gradle 7+
- Sin dependencias externas: todos los ejemplos son autocontenidos

---

## Versión para PDF

Abre [GUIA-CONSOLIDADA](./GUIA-CONSOLIDADA.md) para la versión imprimible con todo el contenido unificado.

---

> **Por dónde empezar:** Lee primero [Fundamentos](./FUNDAMENTOS.md) — 15 minutos que resolverán el 80% de las dudas iniciales.

*Guía preparada para la PoC de IBM Bob · Banco Mercantil · 2026*
