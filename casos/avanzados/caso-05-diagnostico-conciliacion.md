# Caso 5 — Reporte de Auditoría Incompleto en el Proceso de Conciliación

> **Modos:** 🔵 Ask → 🔴 Agent  
> **Tiempo estimado:** 15 minutos  
> **Nivel:** Avanzado — razonamiento sobre múltiples archivos con dependencias cruzadas

---

## Contexto del Caso

El equipo de **Auditoría Interna** del banco acaba de observar que los reportes nocturnos de conciliación **no cumplen con los requisitos mínimos del regulador (SUDEBAN)**. El informe generado por el sistema solo muestra referencia, estado y monto, pero la norma exige que cada línea incluya: la fecha de procesamiento, el origen de la discrepancia y una marca explícita de si el registro requiere revisión humana.

El desarrollador senior te pasa la clase `ConciliacionBatchService.java` y dice: *"el problema está en `generarReporteTexto`, arréglalo"*.

**Lo que no sabe el desarrollador**: el problema no está solo en ese método.

---

## Código de Partida

Abre la carpeta [`../../codigo/caso-05/`](../../codigo/caso-05/) completa. Contiene 4 archivos:

| Archivo | Rol |
|---|---|
| [`ConciliacionBatchService.java`](../../codigo/caso-05/ConciliacionBatchService.java) | Orquesta el proceso y genera el reporte |
| [`TransaccionCore.java`](../../codigo/caso-05/TransaccionCore.java) | Modelo de transacción del sistema Core bancario |
| [`TransaccionExterno.java`](../../codigo/caso-05/TransaccionExterno.java) | Modelo de transacción del corresponsal / red interbancaria |
| [`ResultadoConciliacion.java`](../../codigo/caso-05/ResultadoConciliacion.java) | Encapsula el resultado de conciliar una referencia |

> 💡 **Antes de continuar:** Asegúrate de que Bob tiene la carpeta `caso-05/` abierta como contexto de trabajo, no solo el archivo del servicio.

---

## Parte 1 — Ask Mode: Diagnóstico Real del Problema

### Paso 1 — Selecciona Ask Mode

Cambia a **🔵 Ask** en la barra inferior de Bob.

---

### Paso 2 — Prompt de diagnóstico (foco aparente: un método)

```
Tengo abiertos los archivos de la carpeta caso-05.
El equipo de auditoría dice que generarReporteTexto() en ConciliacionBatchService
no produce información suficiente para cumplir con la norma regulatoria del banco.
La norma exige que cada línea del reporte incluya:
  - La fecha en que se procesó la conciliación
  - Si el registro requiere revisión humana (sí/no)
  - El estado reportado por el sistema externo cuando hay discrepancia

Sin modificar nada todavía, analiza si generarReporteTexto() puede producir
esa información con los datos que recibe, y si no puede, explica exactamente
por qué no puede hacerlo.
```

**¿Qué tiene que hacer Bob aquí?**  
Para responder correctamente, Bob **debe** abrir los otros archivos porque:

- Necesita ver `ResultadoConciliacion` para saber qué campos existen. Encontrará `fechaProcesamiento` y `requiereRevision()` — que el reporte nunca usa.
- Necesita ver `TransaccionExterno` para saber que el `estadoExterno` del corresponsal existe en el modelo original pero nunca llega al resultado.
- Necesita ver `ConciliacionBatchService.conciliarTransacciones()` para confirmar que `fechaProcesamiento` nunca se setea allí, aunque el campo sí existe en `ResultadoConciliacion`.

**Respuesta esperada de Bob:**  
Bob debería señalar que el problema **no es solo el método `generarReporteTexto()`**, sino que hay una cadena de 3 omisiones:

1. `ConciliacionBatchService.conciliarTransacciones()` nunca asigna `fechaProcesamiento` ni `observaciones` a los resultados.
2. El `estadoExterno` de `TransaccionExterno` nunca se transfiere al `ResultadoConciliacion` (no hay campo para eso, ni se setea en ninguna parte).
3. `generarReporteTexto()` tampoco llama a `requiereRevision()` aunque el método ya existe en `ResultadoConciliacion`.

---

### Paso 3 — Prompt de alcance del cambio

```
Entonces para que el reporte cumpla la norma, ¿cuántos archivos hay que modificar?
Lista cada archivo, qué cambio específico necesita, y en qué orden deberían hacerse
los cambios para no romper la compilación entre pasos.
```

**Respuesta esperada de Bob:**  
Bob debería proponer este orden:

1. **`ResultadoConciliacion.java`** — Agregar campo `String estadoExterno` para poder transportar el estado del corresponsal, con su getter y setter.
2. **`ConciliacionBatchService.conciliarTransacciones()`** — Asignar `fechaProcesamiento` (`LocalDate.now()`) y el `estadoExterno` de `TransaccionExterno` al crear cada `ResultadoConciliacion`.
3. **`ConciliacionBatchService.generarReporteTexto()`** — Ampliar el reporte para usar `fechaProcesamiento`, `requiereRevision()` y el nuevo `estadoExterno`.

> 🔍 **Observa:** Bob acaba de identificar que hay que tocar 2 archivos distintos para resolver lo que parecía un problema de un solo método. Eso es análisis de dependencias cruzadas.

---

## Parte 2 — Agent Mode: Corrección en Cadena

### Paso 4 — Cambia a Agent Mode

Cambia a **🔴 Agent** en la barra inferior.

> ⚠️ **Importante:** Bob va a proponer cambios en **múltiples archivos** en esta misma operación. Revisa cada archivo modificado en el panel de cambios antes de aceptar.

---

### Paso 5 — Prompt de corrección multiarchivo

```
Aplica los cambios necesarios en todos los archivos de la carpeta caso-05 para que
el reporte de conciliación cumpla la norma regulatoria. Los cambios deben:

1. En ResultadoConciliacion.java: agregar el campo estadoExterno (String) con
   su getter y setter, representando el estado reportado por el sistema corresponsal.

2. En ConciliacionBatchService.conciliarTransacciones():
   - Asignar fechaProcesamiento = LocalDate.now() en cada ResultadoConciliacion creado.
   - Copiar el estadoExterno de TransaccionExterno al ResultadoConciliacion cuando
     la referencia se encuentra en el externo.
   - En los casos NO_ENCONTRADA_EXTERNO, dejar estadoExterno como "SIN_REGISTRO_EXTERNO".
   - En los casos NO_ENCONTRADA_CORE, copiar el estadoExterno del registro externo.

3. En ConciliacionBatchService.generarReporteTexto():
   - Agregar fechaProcesamiento a cada línea del reporte.
   - Agregar una columna REQUIERE_REVISION con el resultado de requiereRevision().
   - Agregar estadoExterno a cada línea.
   - Usar StringBuilder en lugar de concatenación de String.

No cambies TransaccionCore.java ni TransaccionExterno.java.
Mantén los mismos nombres de métodos públicos en todos los archivos.
```

**¿Qué hace Bob en este paso?**  
Bob va a abrir y modificar `ResultadoConciliacion.java` y `ConciliacionBatchService.java` en la misma operación. En el panel de archivos modificados verás **2 archivos** tocados simultáneamente — eso es lo diferente respecto a los casos anteriores donde siempre era 1.

---

### Paso 6 — Verificación cruzada

```
Revisa los cambios aplicados y responde:
¿En el caso de una transacción NO_ENCONTRADA_CORE, el estadoExterno que se
copia al resultado corresponde al campo estadoExterno de TransaccionExterno
o al campo descripcion? ¿Cuál es el correcto según el significado del campo?
```

> 🔍 **Por qué este prompt**: Obliga a Bob a releer `TransaccionExterno` y confirmar que usó el campo correcto (`estadoExterno`: PROCESADA/PENDIENTE/RECHAZADA) y no el campo `descripcion` (texto libre). Es una validación de semántica, no de sintaxis.

---

## ✅ Resultado Esperado

Al finalizar el caso, los cambios deben cumplir:

- [ ] `ResultadoConciliacion.java` tiene el campo `estadoExterno` con getter y setter
- [ ] `conciliarTransacciones()` asigna `fechaProcesamiento` en **todos** los casos (CONCILIADA, DIFERENCIA, NO\_ENCONTRADA\_EXTERNO, NO\_ENCONTRADA\_CORE)
- [ ] `conciliarTransacciones()` copia `estadoExterno` desde `TransaccionExterno` cuando el registro externo existe
- [ ] `generarReporteTexto()` imprime: referencia | fecha | estado | estadoExterno | diferencia | requiereRevision
- [ ] `generarReporteTexto()` usa `StringBuilder`
- [ ] `TransaccionCore.java` y `TransaccionExterno.java` **no fueron modificados**
- [ ] El panel de archivos modificados de Bob muestra exactamente 2 archivos tocados

---

## 💡 Lo Que Aprendiste

1. **Un síntoma en un método puede tener la raíz en otro archivo**: El desarrollador señaló `generarReporteTexto()`, pero el problema real estaba en `conciliarTransacciones()` y en un campo faltante en el modelo.
2. **Bob razona sobre dependencias entre archivos, no solo sobre el archivo abierto**: Al tener la carpeta como contexto, Bob puede trazar la cadena completa: `TransaccionExterno` → `conciliarTransacciones()` → `ResultadoConciliacion` → `generarReporteTexto()`.
3. **El panel de archivos modificados es tu red de seguridad**: Cuando Bob toca más de un archivo, ese panel te dice exactamente qué cambió y dónde — revísalo siempre antes de aceptar.
4. **Ask Mode para diagnóstico evita el error clásico**: Si hubieras ido directo a Agent Mode con el mensaje del desarrollador (*"arregla generarReporteTexto"*), habrías obtenido un reporte que llama a campos que están vacíos. El diagnóstico primero descubrió que faltaba información más arriba en la cadena.

---

## ▶️ Siguiente Caso

Has completado los casos avanzados. Ahora el módulo especial.

👉 **[Caso 6 — Premium Java Package: Modernización Java 8/11 → 17/21](../premium/caso-06-premium-java-upgrade.md)**
