---
title: "Caso Extra 7 — Dashboard Web de Métricas Operativas"
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › Caso Extra 7 — 🚀 Dashboard Web</div>

# Caso Extra 7 — Dashboard Web de Métricas Operativas Bancarias

> **Modos:** 🟡 Plan → 🔴 Agent &nbsp;|&nbsp; **Tiempo:** 25 min &nbsp;|&nbsp; **Nivel:** Extra — impacto visual garantizado 🚀

---

## 📥 ¿Tienes el repositorio abierto en IBM Bob?

Para este caso necesitas el archivo `codigo/caso-07/MetricasDashboardController.java`. Si aún no tienes el repositorio, descárgalo ahora:

**Opción A — Con Git:**
```bash
git clone https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil.git
```

**Opción B — Sin Git:**
Ve a [github.com/BestebanTc-IBM/guia-bob-banco-mercantil](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil) → botón verde **`< > Code`** → **`Download ZIP`** → extrae y abre la carpeta en IBM Bob.

Una vez abierta la carpeta en IBM Bob, Bob tendrá acceso automático a todos los archivos. No necesitas adjuntar nada manualmente.

---

## ¿Por Qué Este Caso?

Los desarrolladores bancarios pasan el día mirando logs y tablas de base de datos. Este caso demuestra que Bob puede generar **interfaces visuales completas** — no solo código de backend — a partir de endpoints que ya existen en el sistema.

El resultado es un **dashboard operativo interno** que el área de Operaciones podría usar hoy para monitorear el estado del día: transacciones procesadas, estado de la conciliación, alertas activas y carga por hora.

> **▶ Antes de empezar:** Descarga [`dashboard-resultado-esperado.html`](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil/blob/main/codigo/caso-07/dashboard-resultado-esperado.html), ábrelo en Chrome y observa el objetivo. Eso es lo que vas a generar con Bob.

---

## Contexto del Caso

El área de **Operaciones Bancarias** solicita un dashboard HTML interno. No necesita backend complejo — los datos ya los expone `MetricasDashboardController.java`.

El dashboard debe mostrar:
- KPIs del día: transacciones totales, monto procesado, pendientes, rechazadas
- Estado de la conciliación nocturna con gráfico visual (donut SVG)
- Top 5 cuentas por volumen de transacciones
- Alertas operativas activas con niveles (Alto / Medio / Bajo)
- Gráfico de carga de transacciones por hora del día

**Restricciones:**
- HTML + CSS + JavaScript puro (sin React, Vue, Angular, jQuery, Bootstrap, Chart.js)
- Funciona abriendo el archivo directamente en el navegador (sin servidor)
- Sin librerías externas: los gráficos son SVG inline o barras CSS

---

## Archivos de Partida

Descarga del repositorio y ábrelos en IBM Bob:

1. **[`MetricasDashboardController.java`](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil/blob/main/codigo/caso-07/MetricasDashboardController.java)** — los 4 endpoints REST con las métricas
2. **[`dashboard-resultado-esperado.html`](https://github.com/BestebanTc-IBM/guia-bob-banco-mercantil/blob/main/codigo/caso-07/dashboard-resultado-esperado.html)** — ábrelo en Chrome para ver el objetivo

---

## Paso a Paso con Bob

### Paso 1 — 🟡 Plan: Define la estructura visual

Adjunta `MetricasDashboardController.java` y escribe:

```
Actúa como desarrollador frontend senior con expertise en dashboards internos bancarios.
Tengo adjunto MetricasDashboardController.java con 4 endpoints que exponen métricas.

Diseña la estructura de un dashboard HTML interno para Operaciones Bancarias.

RESTRICCIONES:
- HTML + CSS + JS puro (sin frameworks ni librerías externas)
- Gráficos con SVG inline o barras CSS
- Funciona abriendo el .html directo en el navegador
- Datos hardcodeados (mock)

Produce:
1. Wireframe textual del layout de secciones.
2. Lista de componentes visuales.
3. Paleta de colores corporativa bancaria (azul oscuro, profesional).
4. Plan de implementación en 3 pasos.
No escribas código todavía.
```

### Paso 2 — Verifica el plan

Antes de codificar, confirma que el plan incluye:
- [ ] Header fijo con nombre del banco y reloj en tiempo real
- [ ] Al menos 4 KPI cards con animación de contador al cargar
- [ ] Gráfico de conciliación (donut SVG)
- [ ] Tabla top cuentas con barras proporcionales
- [ ] Panel de alertas con colores por nivel
- [ ] Gráfico de barras de volumen por hora

### Paso 3 — 🔴 Agent: Genera el dashboard

```
Basándote en el plan, genera un archivo completo llamado dashboard-operativo.html.
Debe ser un HTML self-contained (todo el CSS y JS inline).

COMPONENTES REQUERIDOS:

1. HEADER: fijo, azul oscuro (#0f3460)
   - "Banco Mercantil · Dashboard Operativo"
   - Reloj digital actualizado cada segundo, fecha en español

2. KPI CARDS (fila de 5):
   - Transacciones hoy: 12,847 (verde)
   - Monto procesado: Bs. 487.5M
   - Pendientes: 234 (amarillo)
   - Rechazadas: 17 (rojo)
   - % Conciliación: 98.78%
   - Contadores animados de 0 al valor final al cargar (1.2 seg, easing cúbico)

3. FILA DE 2 PANELES:
   Izquierdo: "Estado de Conciliación Nocturna"
     - Donut SVG: Conciliadas (98.78%), Con diferencia (0.85%), No encontradas (0.37%)
     - Leyenda con colores y cantidades absolutas
   Derecho: "Top 5 Cuentas por Volumen"
     - Tabla: cuenta enmascarada, nombre, txns, monto con barras CSS proporcionales

4. SEGUNDA FILA DE 2 PANELES:
   Izquierdo: "Alertas Operativas"
     - ALTO (rojo): diferencia en conciliación
     - MEDIO (amarillo): retraso en batch
     - BAJO (verde): latencia API
   Derecho: "Volumen por Hora"
     - 6 barras horizontales 06:00–11:00, hora pico bancaria = 09:00

5. FOOTER: "Dashboard Operativo · Banco Mercantil · Datos actualizados cada 5 min"

ESTILO:
- Fondo #f1f2f6, cards blancas, sombra suave
- border-left de color en cada KPI card
- Responsive: 1 columna en mobile, 2 columnas en desktop
- Tipografía: -apple-system, Segoe UI, sans-serif
```

### Paso 4 — Prueba en el navegador

1. Guarda el archivo como `dashboard-operativo.html`
2. Ábrelo en Chrome haciendo doble clic
3. Verifica que el reloj se actualiza cada segundo
4. Verifica que los KPIs se animan al cargar
5. Redimensiona la ventana para probar responsive

### Paso 5 — Refinamiento iterativo (prompts extra)

```
Agrega un badge "EN VIVO" que parpadee suavemente en el header
usando solo CSS animation keyframes (sin JavaScript).
```

```
El panel de alertas se ve plano. Añade un borde izquierdo de 4px
del color del nivel a cada alerta y mejora el espaciado interno.
```

```
Conecta el dashboard a datos reales: reemplaza los datos hardcodeados
con fetch() a los endpoints de MetricasDashboardController.
Si el servidor no está disponible, usa los datos mock como fallback.
Refresca cada 5 minutos.
```

---

## ✅ Resultado Esperado

- [ ] Abre directamente en Chrome sin errores en consola
- [ ] Reloj actualizándose en tiempo real
- [ ] KPI counters animan de 0 al valor final
- [ ] Donut SVG con proporciones visualmente correctas
- [ ] Barras de top cuentas proporcionales
- [ ] Alertas con colores correctos por nivel
- [ ] Responsive en mobile

---

## 💡 Por Qué Este Caso es Diferente

| Aspecto | Casos 1–6 | Caso Extra 7 |
|---|---|---|
| Lenguaje | Java | HTML + CSS + JavaScript |
| Resultado | Clase compilable | Página que abre en el navegador |
| Audiencia | Desarrolladores | Operadores y supervisores bancarios |
| Iteración | Compilar y testear | Guardar y refrescar el navegador |

**Lo que demuestra:** Bob no es solo un asistente Java. El mismo flujo Plan → Agent funciona para backend, frontend, scripts, configuraciones y documentación.

---

## Navegación

| ← Anterior | |
|---|---|
| [Caso 6 — Premium Java](../premium/caso-06-premium-java-upgrade.md) | [🏦 Volver al Inicio](../../README.md) |
