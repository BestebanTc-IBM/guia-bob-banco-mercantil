// CuentaValidatorService.java
package com.bancomercantil.core.cuentas;

import java.math.BigDecimal;

/**
 * Servicio de validación de cuentas bancarias y operaciones.
 * Archivo de partida para el Caso 3 de la guía de IBM Bob.
 */
public class CuentaValidatorService {

    private static final BigDecimal LIMITE_SOBREGIRO = new BigDecimal("-5000.00");
    private static final BigDecimal LIMITE_DIARIO    = new BigDecimal("100000.00");

    public enum EstadoCuenta { ACTIVA, BLOQUEADA, INACTIVA, PENDIENTE }

    public boolean puedeDebitar(BigDecimal saldoActual, BigDecimal montoDebito,
                                EstadoCuenta estado, BigDecimal montoAcumuladoHoy) {
        if (estado != EstadoCuenta.ACTIVA) return false;
        if (montoDebito == null || montoDebito.compareTo(BigDecimal.ZERO) <= 0) return false;
        BigDecimal saldoResultante = saldoActual.subtract(montoDebito);
        if (saldoResultante.compareTo(LIMITE_SOBREGIRO) < 0) return false;
        BigDecimal nuevoAcumulado = montoAcumuladoHoy.add(montoDebito);
        return nuevoAcumulado.compareTo(LIMITE_DIARIO) <= 0;
    }

    public boolean esNumeroCuentaValido(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) return false;
        if (!numeroCuenta.matches("\\d{10,20}")) return false;
        int suma = 0;
        for (int i = 0; i < numeroCuenta.length(); i++) {
            int digito = Character.getNumericValue(numeroCuenta.charAt(i));
            suma += (i % 2 == 0) ? digito * 2 : digito;
        }
        return suma % 10 == 0;
    }

    public String determinarTipoOperacion(BigDecimal saldoActual, BigDecimal monto,
                                          boolean esTransferenciaInternacional) {
        if (esTransferenciaInternacional) {
            return monto.compareTo(new BigDecimal("10000.00")) > 0 ? "SWIFT_ALTO_VALOR" : "SWIFT_NORMAL";
        }
        if (saldoActual.compareTo(BigDecimal.ZERO) < 0) return "DEBITO_SOBREGIRO";
        return "DEBITO_NORMAL";
    }
}
