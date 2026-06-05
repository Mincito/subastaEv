package cl.duoc.subastaEv.dto;

public record LoteResponse(
    Long id,
    Long capturaId,
    double precioBase,
    String estado
) {
}