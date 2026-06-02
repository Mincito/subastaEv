package cl.duoc.subastaEv.mapper;

import cl.duoc.subastaEv.dto.CreateSubastaRequest;
import cl.duoc.subastaEv.dto.UpdateSubastaRequest;
import cl.duoc.subastaEv.model.Subasta;

public class SubastaMapper {

    public static Subasta toModel(CreateSubastaRequest request) {
        return new Subasta(
                0,
                request.idLote(),
                request.fechaInicio(),
                null,
                request.precioInicial(),
                null
        );
    }

    public static Subasta toModel(int id, UpdateSubastaRequest request) {
        return new Subasta(
                id, // ID del path parameter, dato que viene en la url,, ej: /api/v1/subastas/"5"
                request.idLote(),
                request.fechaInicio(),
                request.fechaCierre(),
                request.precioInicial(),
                request.estado()
        );
    }

    
}
