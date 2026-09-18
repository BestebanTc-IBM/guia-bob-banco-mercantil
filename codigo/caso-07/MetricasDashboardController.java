// MetricasDashboardController.java — endpoint REST que alimenta el dashboard interno
package com.bancomercantil.dashboard;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST que expone los datos de métricas operativas para el
 * dashboard interno de monitoreo del área de Operaciones Bancarias.
 *
 * PUNTO DE PARTIDA para el Caso Extra 7.
 * Actualmente devuelve datos hardcodeados (mock).
 * El ejercicio consiste en pedirle a Bob que:
 *   1. Diseñe e implemente los DTOs de respuesta
 *   2. Genere el HTML/CSS/JS del dashboard que consume estos endpoints
 *   3. Añada lógica de alertas visuales por umbral
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = "*")
public class MetricasDashboardController {

    @GetMapping("/resumen-dia")
    public Map<String, Object> getResumenDia() {
        return Map.of(
            "fecha",                    "2025-09-18",
            "totalTransacciones",       12847,
            "montoTotalProcesado",      new BigDecimal("487523900.50"),
            "transaccionesPendientes",  234,
            "transaccionesRechazadas",  17,
            "alertasActivas",           3
        );
    }

    @GetMapping("/conciliacion")
    public Map<String, Object> getEstadoConciliacion() {
        return Map.of(
            "fecha",                      "2025-09-18",
            "totalRegistros",             48392,
            "conciliadas",                47801,
            "conDiferencia",              412,
            "noEncontradasExterno",       98,
            "noEncontradasCore",          81,
            "porcentajeConciliacion",     98.78,
            "tiempoProcesamientoMinutos", 42
        );
    }

    @GetMapping("/top-cuentas")
    public List<Map<String, Object>> getTopCuentas() {
        return List.of(
            Map.of("cuenta", "****4821", "nombre", "Empresa A", "transacciones", 847,  "monto", new BigDecimal("12500000.00")),
            Map.of("cuenta", "****9034", "nombre", "Empresa B", "transacciones", 612,  "monto", new BigDecimal("8700000.00")),
            Map.of("cuenta", "****2271", "nombre", "Empresa C", "transacciones", 589,  "monto", new BigDecimal("6200000.00")),
            Map.of("cuenta", "****7743", "nombre", "Empresa D", "transacciones", 401,  "monto", new BigDecimal("4100000.00")),
            Map.of("cuenta", "****5519", "nombre", "Persona E", "transacciones", 378,  "monto", new BigDecimal("980000.00"))
        );
    }

    @GetMapping("/alertas")
    public List<Map<String, Object>> getAlertas() {
        return List.of(
            Map.of("id", 1, "nivel", "ALTO",  "mensaje", "Conciliación con diferencia > $10,000 en 3 referencias", "desde", "08:23"),
            Map.of("id", 2, "nivel", "MEDIO", "mensaje", "Proceso batch de débitos automáticos con 15 min de retraso", "desde", "09:47"),
            Map.of("id", 3, "nivel", "BAJO",  "mensaje", "API Connect: latencia elevada en servicio de saldos (p95 > 800ms)", "desde", "10:12")
        );
    }
}
