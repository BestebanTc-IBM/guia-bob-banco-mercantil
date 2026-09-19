// TransaccionCore.java — modelo de transacción del sistema core bancario
package com.bancomercantil.batch.conciliacion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa una transacción registrada en el sistema Core bancario (origen interno).
 */
public class TransaccionCore {

    private String     referencia;
    private BigDecimal monto;
    private LocalDate  fecha;
    private String     cuentaOrigen;
    private String     cuentaDestino;
    private String     tipoTransaccion; // TRANSFERENCIA, PAGO, DEBITO, CREDITO

    public TransaccionCore() {}

    public TransaccionCore(String referencia, BigDecimal monto, LocalDate fecha,
                           String cuentaOrigen, String cuentaDestino, String tipoTransaccion) {
        this.referencia      = referencia;
        this.monto           = monto;
        this.fecha           = fecha;
        this.cuentaOrigen    = cuentaOrigen;
        this.cuentaDestino   = cuentaDestino;
        this.tipoTransaccion = tipoTransaccion;
    }

    public String     getReferencia()      { return referencia; }
    public BigDecimal getMonto()           { return monto; }
    public LocalDate  getFecha()           { return fecha; }
    public String     getCuentaOrigen()    { return cuentaOrigen; }
    public String     getCuentaDestino()   { return cuentaDestino; }
    public String     getTipoTransaccion() { return tipoTransaccion; }

    public void setReferencia(String referencia)           { this.referencia = referencia; }
    public void setMonto(BigDecimal monto)                 { this.monto = monto; }
    public void setFecha(LocalDate fecha)                  { this.fecha = fecha; }
    public void setCuentaOrigen(String cuentaOrigen)       { this.cuentaOrigen = cuentaOrigen; }
    public void setCuentaDestino(String cuentaDestino)     { this.cuentaDestino = cuentaDestino; }
    public void setTipoTransaccion(String tipoTransaccion) { this.tipoTransaccion = tipoTransaccion; }

    @Override
    public String toString() {
        return "TransaccionCore{ref='" + referencia + "', monto=" + monto +
               ", fecha=" + fecha + ", tipo=" + tipoTransaccion + "}";
    }
}
