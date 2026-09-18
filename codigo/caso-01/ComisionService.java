// ComisionService.java
package com.bancomercantil.core.comisiones;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Servicio de cálculo de comisiones interbancarias.
 * Código legado sin documentación - candidato a revisión.
 * Archivo de partida para el Caso 1 de la guía de IBM Bob.
 */
public class ComisionService {

    private static final BigDecimal TASA_INTERBANCARIA_BASE = new BigDecimal("0.0035");
    private static final BigDecimal TASA_PREMIUM            = new BigDecimal("0.0020");
    private static final BigDecimal UMBRAL_MONTO_ALTO       = new BigDecimal("50000.00");
    private static final BigDecimal COMISION_MINIMA         = new BigDecimal("2.50");
    private static final BigDecimal COMISION_MAXIMA         = new BigDecimal("500.00");

    public BigDecimal calcularComision(BigDecimal monto, String tipoCuenta, boolean esClientePremium) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        BigDecimal tasa = resolverTasa(monto, tipoCuenta, esClientePremium);
        BigDecimal comision = monto.multiply(tasa).setScale(2, RoundingMode.HALF_UP);
        return aplicarLimites(comision);
    }

    private BigDecimal resolverTasa(BigDecimal monto, String tipoCuenta, boolean esClientePremium) {
        if (esClientePremium) {
            return TASA_PREMIUM;
        }
        if ("CORRIENTE".equals(tipoCuenta) && monto.compareTo(UMBRAL_MONTO_ALTO) > 0) {
            return TASA_INTERBANCARIA_BASE.multiply(new BigDecimal("0.80"))
                                         .setScale(4, RoundingMode.HALF_UP);
        }
        return TASA_INTERBANCARIA_BASE;
    }

    private BigDecimal aplicarLimites(BigDecimal comision) {
        if (comision.compareTo(COMISION_MINIMA) < 0) return COMISION_MINIMA;
        if (comision.compareTo(COMISION_MAXIMA) > 0) return COMISION_MAXIMA;
        return comision;
    }

    public boolean validarCuenta(String numeroCuenta, String tipoCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) return false;
        if (!numeroCuenta.matches("\\d{10,20}")) return false;
        return switch (tipoCuenta) {
            case "CORRIENTE", "AHORRO", "PLAZO_FIJO" -> true;
            default -> false;
        };
    }
}
