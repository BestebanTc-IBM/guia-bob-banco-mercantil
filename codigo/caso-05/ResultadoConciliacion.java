// ResultadoConciliacion.java — resultado del proceso de conciliación para una referencia
package com.bancomercantil.batch.conciliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Encapsula el resultado de conciliar una referencia entre el core bancario
 * y el sistema externo.
 *
 * Estados posibles:
 *   CONCILIADA              — encontrada en ambos con el mismo monto
 *   DIFERENCIA              — encontrada en ambos pero con monto distinto
 *   NO_ENCONTRADA_EXTERNO   — presente en core, ausente en externo
 *   NO_ENCONTRADA_CORE      — presente en externo, ausente en core
 *
 * Archivo de soporte para el Caso 5 de la guía de IBM Bob.
 */
public class ResultadoConciliacion {

    private String     referencia;
    private String     estado;
    private BigDecimal montoDiferencia;
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

    /** true si requiere revisión humana (cualquier estado distinto a CONCILIADA) */
    public boolean requiereRevision() {
        return !"CONCILIADA".equals(estado);
    }

    @Override
    public String toString() {
        return "ResultadoConciliacion{ref='" + referencia + "', estado='" + estado +
               "', diferencia=" + montoDiferencia + "}";
    }
}
