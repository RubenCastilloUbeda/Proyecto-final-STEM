package app.CrosStemFit.CrosStemFit.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa la entidad Evento en la base de datos.
 * Esta clase se utiliza para almacenar la información de los eventos que forman parte de una competición.
 * Contiene información sobre el nombre del evento, su duración, la competición a la que pertenece, y los resultados asociados a ese evento.
 */
@Entity
@Table(name="EVENTOS")
public class Evento {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id_evento;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private int duracion;

    /**
     * Relación con la entidad Competicion
     * Un evento pertenece a una única competición y una competición puede tener múltiples eventos.
     */
    @ManyToOne
    @JoinColumn(name="id_competicion")
    private Competicion competicion;

    /**
     * Relación con la entidad Resultado
     * Un evento puede tener múltiples resultados asociados, pero cada resultado pertenece a un único evento.
     * La eliminación de un evento también eliminará sus resultados asociados debido a la configuración de CascadeType.ALL.
     */
    @OneToMany(mappedBy="evento", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Resultado> resultados;

    public Evento(int duracion, String nombre) {
        this.duracion = duracion;
        this.nombre = nombre;
        this.resultados = new ArrayList<>();
    }

    public Evento() {
        this.resultados = new ArrayList<>();
    }

    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }
    
    public List<Resultado> getResultados() {
        return resultados;
    }
    
    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
    
    @Override
    public String toString() {
        String mensaje="Evento -> " + nombre + ". Ducarión: " + duracion + " minutos.";
        return mensaje;
    }
    
}
