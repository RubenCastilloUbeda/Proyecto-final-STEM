package app.CrosStemFit.CrosStemFit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/* Clase que representa la entidad Clasificacion en la base de datos.
 * Esta clase se utiliza para almacenar la clasificación de los equipos en una competición.
 * Contiene información sobre la posición del equipo, los puntos totales, y las relaciones con el equipo y la competición.
 */
@Entity
@Table(name="CLASIFICACION")
public class Clasificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClasificacion;

    @Column (nullable=false)
    private int posicion;

    @Column (nullable=false)
    private int puntosTotales;

    /**
     * Relación con la entidad Equipo
     * Una clasificación pertenece a un único equipo, y un equipo puede tener múltiples clasificaciones en diferentes competiciones.
     */
    @ManyToOne
    @JoinColumn(name="id_equipo")
    private Equipo equipo;

    /**
     * Relación con la entidad Competicion
     * Una clasificación pertenece a una única competición y una competición puede tener múltiples clasificaciones para diferentes equipos.
      */
    @ManyToOne
    @JoinColumn(name = "id_competicion")
    private Competicion competicion;

    public Clasificacion(int posicion, int puntosTotales) {
        this.posicion = posicion;
        this.puntosTotales = puntosTotales;
    }

    public Clasificacion() {
    }

    public Long getidClasificacion() {
        return idClasificacion;
    }

    public void setidClasificacion(Long idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }
    
    public Equipo getEquipo() {
        return equipo;
    }
    
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
    
    public Competicion getCompeticion() {
        return competicion;
    }
    
    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clasificacion{");
        sb.append("idClasificacion=").append(idClasificacion);
        sb.append(", posicion=").append(posicion);
        sb.append(", puntosTotales=").append(puntosTotales);
        sb.append('}');
        return sb.toString();
    }
}
