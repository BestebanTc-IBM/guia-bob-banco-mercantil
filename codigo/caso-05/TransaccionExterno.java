// TransaccionExterno.java — modelo de transacción del sistema externo / corresponsal
package com.bancomercantil.batch.conciliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa una transacción reportada por el sistema externo (corresponsal bancario,
 * cámara de compensación SICAM, red interbancaria).
 */
public class TransaccionExterno {

    private String     referencia;
    private BigDecimal monto;
    private LocalDate  fecha;
    private String     codigoBanco;   // Código del banco corresponsal
    private String     estadoExterno; // PROCESADA, PENDIENTE, RECHAZADA
    private String     descripcion;

    public TransaccionExterno() {}

    public TransaccionExterno(String referencia, BigDecimal monto, LocalDate fecha,
                              String codigoBanco, String estadoExterno, String descripcion) {
        this.referencia    = referencia;
        this.monto         = monto;
        this.fecha         = fecha;
        this.codigoBanco   = codigoBanco;
        this.estadoExterno = estadoExterno;
        this.descripcion   = descripcion;
    }

    public String     getReferencia()    { return referencia; }
    public BigDecimal getMonto()         { return monto; }
    public LocalDate  getFecha()         { return fecha; }
    public String     getCodigoBanco()   { return codigoBanco; }
    public String     getEstadoExterno() { return estadoExterno; }
    public String     getDescripcion()   { return descripcion; }

    public void setReferencia(String referencia)       { this.referencia = referencia; }
    public void setMonto(BigDecimal monto)             { this.monto = monto; }
    public void setFecha(LocalDate fecha)              { this.fecha = fecha; }
    public void setCodigoBanco(String codigoBanco)     { this.codigoBanco = codigoBanco; }
    public void setEstadoExterno(String estadoExterno) { this.estadoExterno = estadoExterno; }
    public void setDescripcion(String descripcion)     { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "TransaccionExterno{ref='" + referencia + "', monto=" + monto +
               ", banco=" + codigoBanco + ", estado=" + estadoExterno + "}";
    }
}
