package cl.duoc.subastaEv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.subastaEv.model.Subasta;

@Repository
public interface SubastaRepository extends JpaRepository<Subasta, Integer> {

    @Query(value = "SELECT * FROM tabla_subastas WHERE estado = :estado", nativeQuery = true)
    List<Subasta> selectPorEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM tabla_subastas WHERE idLote = :idLote", nativeQuery = true)
    List<Subasta> selectPorLote(@Param("idLote") int idLote);

    default int totalSubastas(){
        return (int) this.count();
    }

    
}
