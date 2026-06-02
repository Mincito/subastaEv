package cl.duoc.subastaEv.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tabla_subastas")
public class Subasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "idLote", nullable = false)
    private int idLote;

    @Column(name = "fechaInicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fechaCierre")
    private LocalDateTime fechaCierre;

    @Column(name = "precioInicial", nullable = false)
    private int precioInicial;

    @Column(name = "estado", nullable = false, length = 40) //si ta abierta, cerrada o se canceló
    private String estado;

    public Subasta() {
    }

    public Subasta(int id, int idLote, LocalDateTime fechaInicio, LocalDateTime fechaCierre, int precioInicial,
            String estado) {
        this.id = id;
        this.idLote = idLote;
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.precioInicial = precioInicial;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public int getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(int precioInicial) {
        this.precioInicial = precioInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
