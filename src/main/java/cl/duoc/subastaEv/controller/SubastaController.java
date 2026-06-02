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

@RestController
@RequestMapping("/api/v1/subastas")
public class SubastaController {

    private final SubastaService subastaService;

    public SubastaController(SubastaService subastaService) {
        this.subastaService = subastaService;
    }

    @GetMapping
    public ResponseEntity<List<Subasta>> getAllSubastas() {
        List<Subasta> subastas = subastaService.getAllSubastas();
        return ResponseEntity.ok(subastas);
    }

    @PostMapping
    public ResponseEntity<Subasta> guardarSubasta(@Valid @RequestBody CreateSubastaRequest request) {
        Subasta subasta =SubastaMapper.toModel(request);
        Subasta subastaGuardada = subastaService.guardarSubasta(subasta);

        return ResponseEntity.status(HttpStatus.CREATED).body(subastaGuardada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subasta> getById(@PathVariable Integer id) {
        Subasta subasta = subastaService.getById(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subasta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subasta> updateSubasta(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateSubastaRequest request) {

        Subasta subastaExistente = subastaService.getById(id);

        if (subastaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Subasta subasta = SubastaMapper.toModel(id, request);
        Subasta subastaActualizada = subastaService.updateSubasta(subasta);

        return ResponseEntity.ok(subastaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubasta(@PathVariable int id) {
        Subasta subasta = subastaService.getById(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        subastaService.deleteSubasta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> totalSubastas() {
        int total = subastaService.totalSubastas();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Subasta>> obtenerPorEstado(@PathVariable String estado) {
        List<Subasta> subastas = subastaService.obtenerPorEstado(estado);
        return ResponseEntity.ok(subastas);
    }

    @GetMapping("/lote/{idLote}")
    public ResponseEntity<List<Subasta>> obtenerPorLote(@PathVariable int idLote) {
        List<Subasta> subastas = subastaService.obtenerPorLote(idLote);
        return ResponseEntity.ok(subastas);
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Subasta> cerrarSubasta(@PathVariable int id) {
        Subasta subasta = subastaService.cerrarSubasta(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subasta);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Subasta> cancelarSubasta(@PathVariable int id) {
        Subasta subasta = subastaService.cancelarSubasta(id);

        if (subasta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subasta);
    }
}