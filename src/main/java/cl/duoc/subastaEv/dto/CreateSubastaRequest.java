package cl.duoc.subastaEv.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Positive;

public record CreateSubastaRequest(

    @Positive(message = "El ID del lote debe ser mayor a 0")
    int idLote,

    LocalDateTime fechaInicio,

    @Positive(message = "El precio inicial debe ser mayor a 0")
    int precioInicial

) {
}