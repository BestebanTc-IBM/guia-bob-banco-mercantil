// ConciliacionBatchService.java — código de partida para el Caso 5
package com.bancomercantil.batch.conciliacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de conciliación nocturna de transacciones.
 * Compara los registros del sistema Core bancario contra los reportados
 * por corresponsales bancarios y la red interbancaria (SICAM).
 */
public class ConciliacionBatchService {

    public List<ResultadoConciliacion> conciliarTransacciones(
            List<TransaccionCore> transaccionesCore,
            List<TransaccionExterno> transaccionesExterno) {

        List<ResultadoConciliacion> resultados = new ArrayList<>();

        for (TransaccionCore tc : transaccionesCore) {
            boolean encontrada = false;
            for (TransaccionExterno te : transaccionesExterno) {
                if (tc.getReferencia().equals(te.getReferencia())) {
                    ResultadoConciliacion resultado = new ResultadoConciliacion();
                    resultado.setReferencia(tc.getReferencia());
                    resultado.setMontoDiferencia(
                        tc.getMonto().subtract(te.getMonto()).abs()
                    );
                    resultado.setEstado(
                        resultado.getMontoDiferencia().compareTo(BigDecimal.ZERO) == 0
                            ? "CONCILIADA" : "DIFERENCIA"
                    );
                    resultados.add(resultado);
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                ResultadoConciliacion resultado = new ResultadoConciliacion();
                resultado.setReferencia(tc.getReferencia());
                resultado.setEstado("NO_ENCONTRADA_EXTERNO");
                resultado.setMontoDiferencia(tc.getMonto());
                resultados.add(resultado);
            }
        }

        for (TransaccionExterno te : transaccionesExterno) {
            boolean encontradaEnCore = false;
            for (TransaccionCore tc : transaccionesCore) {
                if (te.getReferencia().equals(tc.getReferencia())) {
                    encontradaEnCore = true;
                    break;
                }
            }
            if (!encontradaEnCore) {
                ResultadoConciliacion resultado = new ResultadoConciliacion();
                resultado.setReferencia(te.getReferencia());
                resultado.setEstado("NO_ENCONTRADA_CORE");
                resultado.setMontoDiferencia(te.getMonto());
                resultados.add(resultado);
            }
        }

        return resultados;
    }

    public String generarReporteTexto(List<ResultadoConciliacion> resultados) {
        String reporte = "";
        reporte += "=== REPORTE DE CONCILIACIÓN ===\n";
        reporte += "Total registros: " + resultados.size() + "\n";
        for (ResultadoConciliacion r : resultados) {
            reporte += r.getReferencia() + " | " + r.getEstado() +
                       " | " + r.getMontoDiferencia() + "\n";
        }
        return reporte;
    }
}
