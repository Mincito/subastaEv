package cl.duoc.subastaEv.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdateSubastaRequest(

    @Positive(message = "El ID del lote debe ser mayor a 0")
    int idLote,

    LocalDateTime fechaInicio,

    LocalDateTime fechaCierre,

    @Positive(message = "El precio inicial debe ser mayor a 0")
    int precioInicial,

    @NotBlank(message = "El estado no puede estar vacío")
    String estado

) {
}