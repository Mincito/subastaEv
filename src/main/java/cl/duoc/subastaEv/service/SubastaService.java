package cl.duoc.subastaEv.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.subastaEv.model.Subasta;
import cl.duoc.subastaEv.repository.SubastaRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import cl.duoc.subastaEv.dto.LoteResponse;

@Service
public class SubastaService {
    @Autowired
    private SubastaRepository subastaRepository;
    
    @Autowired
    private WebClient loteWebClient;

    public List <Subasta> getAllSubastas(){
        return subastaRepository.findAll();

    }

    public List<Subasta> obtenerPorEstado(String estado) {
        return subastaRepository.selectPorEstado(estado);
    }

    public List<Subasta> obtenerPorLote(int idLote) {
        return subastaRepository.selectPorLote(idLote);
    }
    public Subasta getById(Integer id){
        return subastaRepository.findById(id).orElse(null);
    }
    public Subasta guardarSubasta(Subasta subasta) {

        try {
            LoteResponse lote = loteWebClient.get()
                    .uri("/{id}", subasta.getIdLote())
                    .retrieve()
                    .bodyToMono(LoteResponse.class)
                    .block();

            if (lote == null) {
                return null;
            }

            if (!lote.estado().equalsIgnoreCase("DISPONIBLE")) {
                return null;
            }

        } catch (WebClientResponseException.NotFound e) {
            return null;
        }

        subasta.setEstado("ABIERTA");

        if (subasta.getFechaInicio() == null) {
            subasta.setFechaInicio(LocalDateTime.now());
        }

        return subastaRepository.save(subasta);
    }

    public Subasta updateSubasta(Subasta subasta) {
        return subastaRepository.save(subasta);
    }

    public String deleteSubasta(int id) {
    subastaRepository.deleteById(id);
        return "Subasta eliminada del sistema!";
    }
    
    public Subasta cerrarSubasta(int id){
        Subasta subasta = subastaRepository.findById(id).orElse(null);
        if (subasta == null){
            return null;
        }
        subasta.setEstado("CERRADA");
        subasta.setFechaCierre(LocalDateTime.now());

        return subastaRepository.save(subasta);        
    }

    public Subasta cancelarSubasta(int id){
        Subasta subasta = subastaRepository.findById(id).orElse(null);
        if (subasta == null){
            return null;
        }
        subasta.setEstado("CANCELADA");
        subasta.setFechaCierre(LocalDateTime.now());
        return subastaRepository.save(subasta);
    }

    public int totalSubastas() {
        return subastaRepository.totalSubastas();
    }
    

    

    
}
