// TransferenciaService.java — VERSIÓN CON BUG (código de partida para Caso 2)
package com.bancomercantil.core.transferencias;

/**
 * ADVERTENCIA: Este archivo contiene el bug intencional de usar double para cálculos monetarios.
 * Es el código de partida para el Caso 2 de la guía de IBM Bob.
 */
public class TransferenciaService {

    public double calcularMontoFinal(double monto, double comision) {
        return monto + comision;
    }

    public double aplicarImpuesto(double monto, double tasaImpuesto) {
        return monto * (1 + tasaImpuesto);
    }

    public double calcularSaldoResultante(double saldoActual, double montoDebito, double montoCredito) {
        double resultado = saldoActual - montoDebito + montoCredito;
        // Redondeo manual con double, que NO garantiza exactitud bancaria
        return Math.round(resultado * 100.0) / 100.0;
    }

    public boolean tieneFondosSuficientes(double saldo, double montoSolicitado) {
        return saldo >= montoSolicitado;
    }

    public double calcularComisionConDescuento(double monto, double comisionBase, double descuento) {
        double comisionFinal = comisionBase - (comisionBase * descuento);
        return monto + comisionFinal;
    }
}
