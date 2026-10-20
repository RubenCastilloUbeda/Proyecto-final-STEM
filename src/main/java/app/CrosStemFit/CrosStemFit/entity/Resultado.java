package app.CrosStemFit.CrosStemFit.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** * Clase que representa la entidad Resultado en la base de datos.
 * Esta clase se utiliza para almacenar los resultados obtenidos por los equipos en los eventos de una competición.
 * Contiene información sobre la puntuación obtenida, y las relaciones con el evento y el equipo al que corresponde ese resultado.
 */
@Entity
@Table(name="RESULTADOS")
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultado;

    @Column (nullable=false)
    private int puntuacion;

    /**
     * Relación con la entidad Evento
     * Un resultado pertenece a un único evento, y un evento puede tener múltiples resultados asociados.
     */
    @ManyToOne
    @JoinColumn(name="id_evento")
    private Evento evento;

    /**
     * Relación con la entidad Equipo
     * Un resultado pertenece a un único equipo, y un equipo puede tener múltiples resultados asociados en diferentes eventos.
     */
    @ManyToOne
    @JoinColumn(name="id_equipo")
    private Equipo equipo;

    public Resultado(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Resultado() {
    }

    public Long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Long idResultado) {
        this.idResultado = idResultado;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    public Evento getEvento() {
        return evento;
    }
    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
    
    @Override
    public String toString() {
        String mensaje="Puntuacion " + puntuacion;
        return mensaje;
    }

}
