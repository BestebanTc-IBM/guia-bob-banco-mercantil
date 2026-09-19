// ResultadoConciliacion.java — resultado del proceso de conciliación para una referencia
package com.bancomercantil.batch.conciliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Encapsula el resultado de conciliar una referencia entre el core bancario
 * y el sistema externo.
 *
 * Estados posibles:
 *   CONCILIADA            — referencia encontrada en ambos sistemas con el mismo monto
 *   DIFERENCIA            — referencia encontrada en ambos sistemas pero con monto distinto
 *   NO_ENCONTRADA_EXTERNO — referencia presente en core pero ausente en el externo
 *   NO_ENCONTRADA_CORE    — referencia presente en externo pero ausente en el core
 */
public class ResultadoConciliacion {

    private String     referencia;
    private String     estado;           // ver estados posibles arriba
    private BigDecimal montoDiferencia;  // ZERO si CONCILIADA; monto de la diferencia si DIFERENCIA
    private LocalDate  fechaProcesamiento;
    private String     observaciones;

    public ResultadoConciliacion() {}

    public ResultadoConciliacion(String referencia, String estado,
                                 BigDecimal montoDiferencia, LocalDate fechaProcesamiento) {
        this.referencia         = referencia;
        this.estado             = estado;
        this.montoDiferencia    = montoDiferencia;
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public String     getReferencia()         { return referencia; }
    public String     getEstado()             { return estado; }
    public BigDecimal getMontoDiferencia()    { return montoDiferencia; }
    public LocalDate  getFechaProcesamiento() { return fechaProcesamiento; }
    public String     getObservaciones()      { return observaciones; }

    public void setReferencia(String referencia)                    { this.referencia = referencia; }
    public void setEstado(String estado)                            { this.estado = estado; }
    public void setMontoDiferencia(BigDecimal montoDiferencia)      { this.montoDiferencia = montoDiferencia; }
    public void setFechaProcesamiento(LocalDate fechaProcesamiento) { this.fechaProcesamiento = fechaProcesamiento; }
    public void setObservaciones(String observaciones)              { this.observaciones = observaciones; }

    /** Devuelve true si la conciliación detectó algún problema que requiere revisión humana. */
    public boolean requiereRevision() {
        return "DIFERENCIA".equals(estado)
            || "NO_ENCONTRADA_EXTERNO".equals(estado)
            || "NO_ENCONTRADA_CORE".equals(estado);
    }

    @Override
    public String toString() {
        return "ResultadoConciliacion{ref='" + referencia + "', estado='" + estado +
               "', diferencia=" + montoDiferencia + "}";
    }
}
