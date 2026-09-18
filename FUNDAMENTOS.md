---
title: Fundamentos de IBM Bob
layout: default
---

<div class="breadcrumb">📍 <a href="/guia-bob-banco-mercantil/">Inicio</a> › Fundamentos</div>

# Fundamentos de IBM Bob

> Lee esto primero. Son 15 minutos que evitarán el 80% de la confusión al usar Bob por primera vez.

---

## ¿Qué es IBM Bob?

IBM Bob es un **agente de IA para desarrolladores** integrado directamente en el entorno de trabajo. Bob:

- **Razona sobre el proyecto completo**: no solo el archivo abierto, sino todas las clases, dependencias e historial de la sesión.
- **Conversa en lenguaje natural**: se le describe el problema y responde con análisis, propuestas o cambios concretos.
- **Actúa de forma autónoma**: puede leer, crear, editar y navegar múltiples archivos en un solo flujo de trabajo.
- **Mantiene memoria de sesión**: recuerda el contexto de lo que se discutió durante la misma sesión de trabajo.
- **Tiene tres modos distintos**: Ask (análisis sin cambios), Plan (diseño previo) y Agent (ejecución directa).

---

## Los Tres Modos de Bob

### 🔵 Modo Ask — *"Explícame sin tocar nada"*

Úsalo cuando quieres **entender** algo: una clase que heredaste, un algoritmo de cálculo de comisiones, un proceso batch que nadie documentó.

Bob lee los archivos y responde en lenguaje natural. **No modifica ningún archivo.**

**Cuándo usarlo en el banco:**
- Entender lógica de cálculo de intereses o comisiones heredada
- Auditar un flujo de validación de cuentas
- Preguntar por qué un proceso falla sin saber dónde empezar

---

### 🟡 Modo Plan — *"Diseñemos antes de escribir"*

Úsalo cuando quieres que Bob **proponga una arquitectura o plan de implementación** antes de generar código.

Bob producirá una lista de pasos, componentes sugeridos, clases a crear y posibles riesgos. Puedes ajustar el plan antes de ejecutarlo.

**Cuándo usarlo en el banco:**
- Diseñar un nuevo microservicio de consulta de movimientos
- Planificar la migración de un servicio SOAP a REST
- Proponer la estructura de un módulo antes de que el equipo lo implemente

---

### 🔴 Modo Agent — *"Hazlo tú, yo reviso"*

El modo más poderoso. Bob puede **leer, crear, editar y eliminar archivos** de tu proyecto. Ejecuta acciones de forma autónoma, paso a paso, y te pide confirmación cuando lo necesita.

**Cuándo usarlo en el banco:**
- Corregir un bug en un cálculo monetario
- Crear pruebas unitarias para una clase existente
- Implementar el plan que diseñaste en Modo Plan

> ⚠️ **Consejo Senior:** Empieza siempre en Ask o Plan para entender el contexto. Cambia a Agent solo cuando ya sabes qué quieres que Bob haga. Siempre revisa el diff antes de aceptar cambios en archivos críticos.

---

## La Interfaz en 60 Segundos

### Mapa de la ventana de Bob

```
┌──────────────────────────────────────────────────────────────────┐
│  IBM BOB          ⚙️  ≡  +  ...  ⬜  ✕               61k/270k  │
│                   │   │  │                                  ↑    │
│                   │   │  └─ Nuevo chat          Contador tokens  │
│                   │   └─── Historial de chats anteriores         │
│                   └─────── Configuración del agente              │
├──────────────────────────────────────────────────────────────────┤
│  ▓▓▓▓  Tarea activa (barra azul)  ─────────────────────  1/4 ▓▓ │
│        Muestra el paso que Bob está ejecutando en Agent Mode     │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  [ área de conversación / respuestas de Bob ]                    │
│                                                                  │
│                                                                  │
├──────────────────────────────────────────────────────────────────┤
│  📋 N archivos modificados    [Deshacer todos]  [Mostrar todos]  │
│     Aparece cuando Bob hizo cambios en Modo Agent                │
├──────────────────────────────────────────────────────────────────┤
│  ┌────────────────────────────────────────────────────────────┐  │
│  │  Escribe tu mensaje...                                     │  │
│  └────────────────────────────────────────────────────────────┘  │
│   +   Agent ▾   🔒 Permisos ▾                        ✨   ▶     │
│   │      │           │                               │           │
│   │      │           │                               └─ Mejorar  │
│   │      │           │                                  prompt   │
│   │      │           └─ Controla qué puede hacer Bob             │
│   │      └─────────────── Selector de modo (Ask / Plan / Agent)  │
│   └────────────────────── Adjuntar archivos al contexto          │
└──────────────────────────────────────────────────────────────────┘
```

### Los 5 controles que usarás siempre

| Control | Ubicación | Para qué sirve |
|---|---|---|
| **`+`** (Adjuntar) | Barra inferior izquierda | Agregar archivos Java, logs, configs al contexto de Bob |
| **`Agent ▾`** | Barra inferior centro | Cambiar entre Ask, Plan y Agent antes de enviar |
| **`🔒 Permisos ▾`** | Barra inferior centro | Controlar qué archivos y carpetas puede tocar Bob |
| **`✨`** (Mejorar prompt) | Barra inferior derecha | Bob reformula tu prompt para hacerlo más claro y completo — **úsalo antes de enviar prompts importantes** |
| **`≡`** (Historial) | Barra superior | Ver y retomar conversaciones anteriores |

### ✨ La estrella: tu copiloto para escribir mejores prompts

El botón **✨** (icono de estrella/destellos, al lado derecho del campo de texto) es una de las funciones más útiles para quienes están empezando.

**¿Qué hace?**
Antes de enviar tu mensaje, Bob analiza lo que escribiste y lo reformula con más contexto, estructura y precisión — sin que tú tengas que conocer la técnica de prompting perfecta.

**Ejemplo práctico:**

Escribes:
```
"hay un bug en el calculo de comisiones"
```

Presionas ✨ y Bob lo transforma en algo como:
```
"Actúa como desarrollador Java senior. Tengo adjunto ComisionService.java.
El método calcularComisionInterbancaria() parece estar produciendo resultados
incorrectos. Analiza la lógica completa del método, identifica el error
y propón la corrección con el menor impacto en los demás métodos del servicio."
```

> 💡 **Recomendación:** Escribe tu idea en lenguaje natural, presiona ✨ para que Bob la estructure, revisa que el prompt mejorado refleje tu intención, y *entonces* envíalo.

### El contador de tokens

En la esquina superior derecha verás algo como `61.2k / 270.0k`. Esto indica cuánto contexto lleva la sesión actual vs. el límite disponible.

- **Por debajo del 50%**: sesión en buen estado, Bob tiene todo el contexto.
- **Por encima del 80%**: considera iniciar un nuevo chat para evitar que Bob pierda el hilo de conversaciones anteriores.
- **No es un costo directo para ti**: es la ventana de contexto del modelo, no una factura.

---

## Buenas Prácticas de Prompts

> **La calidad del output de Bob depende directamente de la calidad del input.** Un prompt pobre con código bancario crítico es el camino más rápido a resultados inútiles o peligrosos.

---

### 🎯 Anatomía de un prompt bancario de calidad

Cada componente cumple una función. Ninguno es decorativo.

```
[ROL]         Actúa como desarrollador Java senior especializado en core bancario
[CONTEXTO]    Tengo adjunto TransferenciaService.java. Este servicio procesa
              transferencias interbancarias en tiempo real usando la API de SWIFT.
              El método ejecutarTransferencia() es llamado desde 3 servicios distintos.
[TAREA]       Necesito que detectes todos los puntos donde se usan tipos primitivos
              (double, float) para representar montos monetarios y los refactorices
              a BigDecimal con escala 2 y RoundingMode.HALF_UP.
[RESTRICCIÓN] No cambies las firmas públicas de los métodos. No toques la capa de
              persistencia. El campo 'montoOriginal' debe mantenerse como está.
[FORMATO]     Muéstrame primero la lista de cambios que harás, luego el código
              corregido completo, y al final un resumen de riesgo de cada cambio.
```

**¿Por qué funciona cada parte?**

| Componente | Sin él, Bob... | Con él, Bob... |
|---|---|---|
| **ROL** | responde de forma genérica | aplica criterio y vocabulario bancario |
| **CONTEXTO** | asume lo que no sabe | entiende el impacto real del cambio |
| **TAREA** | interpreta libremente el objetivo | ejecuta exactamente lo que necesitas |
| **RESTRICCIÓN** | puede romper contratos de API o afectar otros servicios | respeta los límites críticos del sistema |
| **FORMATO** | devuelve lo que le parece conveniente | organiza la respuesta para tu flujo de revisión |

---

### 📈 Niveles de prompt: del básico al experto

La misma necesidad, tres niveles de precisión. Los tres son válidos — el nivel correcto depende de cuánto contexto ya tiene Bob en la sesión.

#### Nivel 1 — Básico (sirve para exploración inicial)
```
"Explícame qué hace el método calcularComision() en el archivo adjunto."
```
✔ Útil cuando recién llegaste a un código desconocido y solo quieres orientarte.

---

#### Nivel 2 — Intermedio (sirve para tareas concretas)
```
"Tengo adjunto ComisionService.java. El método calcularComisionInterbancaria()
parece no estar aplicando la exención para cuentas VIP (tipo PREMIUM).
Analiza la lógica y dime si el bug está en la condición del if o en
cómo se está cargando el tipo de cuenta desde el parámetro de entrada."
```
✔ Útil cuando ya sabes qué área tiene el problema pero no la causa exacta.

---

#### Nivel 3 — Experto (sirve para cambios en código crítico)
```
"Actúa como desarrollador Java senior con experiencia en sistemas de pagos ISO 20022.
Tengo adjuntos: ComisionService.java, CuentaRepository.java y TipoCuenta.java.

El servicio calcula comisiones interbancarias. El bug reportado es:
las cuentas con tipo PREMIUM no reciben la exención del 100% en transferencias
menores a $500 USD, a pesar de que la condición esTypeEnum.PREMIUM existe en el código.

Necesito que:
1. Traces el flujo completo desde calcularComisionInterbancaria() hasta
   la consulta del tipo de cuenta.
2. Identifiques el punto exacto donde la condición falla.
3. Propongas la corrección con el menor impacto posible en otros tipos de cuenta.

Restricciones:
- No modificar CuentaRepository.java (está en producción congelada).
- La corrección debe ser retrocompatible con cuentas STANDARD y CORPORATE.

Formato: Diagnóstico → Causa raíz → Código corregido → Prueba unitaria que
confirme el fix."
```
✔ Útil para bugs críticos, migraciones o cambios en código de alto impacto.

---

### ✅ Antes y después: prompts reales mejorados

| ❌ Prompt pobre | ✅ Prompt mejorado | Por qué mejora |
|---|---|---|
| `"Arregla el código"` | `"El método procesarPago() en PagoService.java lanza NullPointerException cuando cuenta es null. Agrega validación de entrada al inicio del método y lanza IllegalArgumentException con mensaje descriptivo."` | Tiene contexto, causa y resultado esperado |
| `"Escribe una API REST"` | `"Crea un endpoint GET /cuentas/{id}/movimientos en Spring Boot que reciba un rango de fechas como parámetros opcionales y devuelva una lista de MovimientoDTO. Sin dependencias externas, solo Spring Web y Java estándar."` | Especifica tecnología, contrato y restricciones |
| `"Mejora esto"` | `"Revisa ConciliacionService.java enfocándote solo en rendimiento. El proceso tarda 45 segundos para 10.000 registros. Identifica el cuello de botella más evidente sin cambiar la lógica de negocio."` | Define el criterio de mejora y el alcance |
| `"¿Está bien este código?"` | `"Revisa el manejo de excepciones en TransferenciaService.java. ¿Hay algún caso donde una excepción podría tragarse silenciosamente y dejar una transferencia en estado inconsistente?"` | Pregunta por un riesgo específico, no aprobación general |

---

### ⚠️ Errores frecuentes al usar Bob con código bancario

**1. Adjuntar demasiados archivos sin orientar a Bob**
```
❌ [Adjunta 15 archivos] "¿Qué está mal aquí?"
✅ [Adjunta 2 archivos relevantes] "El error ocurre en PagoService.java línea 47.
   Adjunto también CuentaDTO.java porque es el objeto que se pasa al método."
```

**2. Pedir cambios globales sin restricciones**
```
❌ "Refactoriza todo el servicio para usar patrones modernos."
✅ "Refactoriza únicamente el método validarCuenta() para reducir su complejidad
   ciclomática. No toques los demás métodos del servicio."
```

**3. No especificar el formato cuando el output va a revisión**
```
❌ "Genera las pruebas unitarias para este servicio."
✅ "Genera pruebas JUnit 5 con Mockito para validarSaldo(). Necesito:
   - Un test por cada caso (saldo suficiente, insuficiente, cuenta bloqueada)
   - Nombres de método en español: deberiaX_cuandoY
   - Sin llamadas reales a base de datos, todo mockeado."
```

**4. Pedir a Bob que adivine el contexto de negocio**
```
❌ "¿Es correcto este cálculo?"
✅ "Según la regulación SUDEBAN vigente, las transferencias entre bancos distintos
   deben aplicar una comisión del 0.5% con tope de 50 BsD.
   ¿El método calcularComision() en el archivo adjunto implementa esta regla
   correctamente para todos los casos?"
```

---

### 🔁 El ciclo de trabajo recomendado

Para tareas en código crítico bancario, este flujo reduce el riesgo:

```
1. Ask Mode   → "Explícame cómo funciona X antes de que toque algo"
               ↓
2. Ask Mode   → "¿Qué riesgos hay si cambio Y?"
               ↓
3. Plan Mode  → "Proponme los pasos para implementar Z"
               ↓
4. (Revisar y ajustar el plan con tu equipo)
               ↓
5. Agent Mode → "Ejecuta el paso 1 del plan que acordamos"
               ↓
6. Ask Mode   → "Revisa el cambio que acabas de hacer. ¿Hay algo que hayas
                 pasado por alto dado que este método es llamado desde [X]?"
```

> 💡 **No tienes que empezar siempre desde el paso 1.** Si ya conoces el código, puedes ir directamente a Plan o Agent. El punto clave es: **no uses Agent Mode en código crítico sin haber entendido el contexto primero.**

---

## Conceptos que confunden al principio

**"¿Bob tiene acceso a producción?"**
No. Bob trabaja exclusivamente con los archivos que tú le compartes en la sesión. Nunca accede a bases de datos, servidores ni sistemas externos.

**"¿Guarda mi código en la nube?"**
Depende de la configuración del tenant de tu organización. En entornos empresariales IBM, el código se procesa con las mismas políticas de datos que cualquier otro servicio IBM en tu contrato.

**"Si Bob modifica un archivo, ¿puedo deshacer?"**
Sí. Los cambios de Bob en Modo Agent se muestran como diferencias (diff) antes de aplicarse, y VS Code / IntelliJ mantienen el historial de deshacer normal.

**"¿Funciona con COBOL?"**
Sí. Bob tiene capacidades para COBOL, JCL y otros lenguajes de mainframe. Los casos de uso de esta guía usan Java por ser el más común en el equipo, pero los principios aplican igual.

---

## Tabla de Decisión de Modo

| Quiero... | Modo |
|---|---|
| Entender código que no escribí yo | Ask |
| Revisar si una lógica financiera es correcta | Ask |
| Planificar un nuevo servicio antes de codificar | Plan |
| Proponer arquitectura para revisión del equipo | Plan |
| Corregir un bug específico | Agent |
| Generar pruebas para una clase existente | Agent |
| Implementar lo que planeé | Plan → Agent |

---

## ▶️ Siguiente Paso

Cuando termines de leer esta sección, ve directamente al primer caso de uso:

👉 **[Caso 1 — Ask Mode: Lógica Financiera](./casos/basicos/caso-01-ask-logica-financiera.md)**

---

*Tiempo estimado de este módulo: 15 minutos*
