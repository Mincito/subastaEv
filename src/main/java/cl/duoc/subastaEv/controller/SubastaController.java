package cl.duoc.subastaEv.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import cl.duoc.subastaEv.dto.CreateSubastaRequest;
import cl.duoc.subastaEv.dto.UpdateSubastaRequest;
import cl.duoc.subastaEv.mapper.SubastaMapper;
import cl.duoc.subastaEv.model.Subasta;
import cl.duoc.subastaEv.service.SubastaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Subastas",
    description = "Operaciones relacionadas con la gestión de subastas"
)
@RestController
@RequestMapping("/api/v1/subastas")
public class SubastaController {

    private final SubastaService subastaService;

    public SubastaController(SubastaService subastaService) {
        this.subastaService = subastaService;
    }

    @Operation(
        summary = "Listar subastas",
        description = "Obtiene todas las subastas registradas en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de subastas obtenida correctamente"
    )
    @GetMapping
    public ResponseEntity<List<Subasta>> getAllSubastas() {
        List<Subasta> subastas = subastaService.getAllSubastas();
        return ResponseEntity.ok(subastas);
    }

    @Operation(
        summary = "Crear subasta",
        description = "Crea una nueva subasta asociada a un lote existente"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Subasta creada correctamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o lote no disponible"
        )
    })
    @PostMapping
    public ResponseEntity<Subasta> guardarSubasta(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos necesarios para crear una subasta",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = CreateSubastaRequest.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de subasta",
                        value = """
                        {
                          "idLote": 1,
                          "fechaInicio": "2026-07-03T12:00:00",
                          "precioInicial": 2500
                        }
                        """
                    )
                )
            )
            @Valid @RequestBody CreateSubastaRequest request) {

        Subasta subasta = SubastaMapper.toModel(request);
        Subasta subastaGuardada = subastaService.guardarSubasta(subasta);

        if (subastaGuardada == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(subastaGuardada);
    }

    @Operation(
        summary = "Buscar subasta por ID",
        description = "Obtiene una subasta según su identificador"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Subasta encontrada"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Subasta no encontrada"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Subasta> getById(@PathVariable Integer id) {
        Subasta subasta = subastaService.getById(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subasta);
    }

    @Operation(
        summary = "Actualizar subasta",
        description = "Modifica los datos de una subasta existente"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Subasta actualizada correctamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Los datos enviados no son válidos"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Subasta no encontrada"
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Subasta> updateSubasta(
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados de la subasta",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = UpdateSubastaRequest.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de actualización",
                        value = """
                        {
                          "idLote": 1,
                          "fechaInicio": "2026-07-03T12:00:00",
                          "fechaCierre": "2026-07-03T20:00:00",
                          "precioInicial": 3000,
                          "estado": "ABIERTA"
                        }
                        """
                    )
                )
            )
            @Valid @RequestBody UpdateSubastaRequest request) {

        Subasta subastaExistente = subastaService.getById(id);

        if (subastaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Subasta subasta = SubastaMapper.toModel(id, request);
        Subasta subastaActualizada = subastaService.updateSubasta(subasta);

        return ResponseEntity.ok(subastaActualizada);
    }

    @Operation(
        summary = "Eliminar subasta",
        description = "Elimina una subasta según su identificador"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Subasta eliminada correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Subasta no encontrada"
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubasta(@PathVariable int id) {
        Subasta subasta = subastaService.getById(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        subastaService.deleteSubasta(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener total de subastas",
        description = "Devuelve la cantidad total de subastas registradas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Total de subastas obtenido correctamente"
    )
    @GetMapping("/total")
    public ResponseEntity<Integer> totalSubastas() {
        int total = subastaService.totalSubastas();
        return ResponseEntity.ok(total);
    }

    @Operation(
        summary = "Buscar subastas por estado",
        description = "Obtiene las subastas que tengan el estado indicado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Subastas obtenidas correctamente"
    )
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Subasta>> obtenerPorEstado(@PathVariable String estado) {
        List<Subasta> subastas = subastaService.obtenerPorEstado(estado);
        return ResponseEntity.ok(subastas);
    }

    @Operation(
        summary = "Buscar subastas por lote",
        description = "Obtiene las subastas asociadas a un lote"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Subastas obtenidas correctamente"
    )
    @GetMapping("/lote/{idLote}")
    public ResponseEntity<List<Subasta>> obtenerPorLote(@PathVariable int idLote) {
        List<Subasta> subastas = subastaService.obtenerPorLote(idLote);
        return ResponseEntity.ok(subastas);
    }

    @Operation(
        summary = "Cerrar subasta",
        description = "Cambia el estado de una subasta a CERRADA"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Subasta cerrada correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Subasta no encontrada"
        )
    })
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Subasta> cerrarSubasta(@PathVariable int id) {
        Subasta subasta = subastaService.cerrarSubasta(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subasta);
    }

    @Operation(
        summary = "Cancelar subasta",
        description = "Cambia el estado de una subasta a CANCELADA"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Subasta cancelada correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Subasta no encontrada"
        )
    })
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Subasta> cancelarSubasta(@PathVariable int id) {
        Subasta subasta = subastaService.cancelarSubasta(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subasta);
    }
}