// PagoService.java — Java 11 / Spring Boot 2.7 / javax.* (código de partida para Caso 6)
package com.bancomercantil.pagos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * Servicio de procesamiento de pagos.
 * Versión legada con javax.* y java.util.Date.
 * Código de partida para el Caso 6 de la guía de IBM Bob.
 */
public class PagoService {

    @Transactional
    public PagoResponse procesarPago(@Valid @NotNull PagoRequest request) {
        if (request.getMonto() == null || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }
        Date fechaProcesamiento = new Date();
        Pago pago = new Pago();
        pago.setReferencia(generarReferencia());
        pago.setMonto(request.getMonto());
        pago.setFecha(fechaProcesamiento);
        pago.setEstado("PENDIENTE");
        Optional<Pago> pagoExistente = buscarPorReferencia(pago.getReferencia());
        if (pagoExistente.isPresent()) {
            throw new EntityNotFoundException("Referencia duplicada: " + pago.getReferencia());
        }
        return new PagoResponse(pago.getReferencia(), pago.getEstado(), fechaProcesamiento);
    }

    private String generarReferencia() { return "PAG-" + System.currentTimeMillis(); }
    private Optional<Pago> buscarPorReferencia(String referencia) { return Optional.empty(); }

    // Candidata a Java Record en el Caso 6
    public static class PagoResponse {
        private final String referencia;
        private final String estado;
        private final Date fecha;
        public PagoResponse(String referencia, String estado, Date fecha) {
            this.referencia = referencia; this.estado = estado; this.fecha = fecha;
        }
        public String getReferencia() { return referencia; }
        public String getEstado()     { return estado; }
        public Date   getFecha()      { return fecha; }
    }
}
