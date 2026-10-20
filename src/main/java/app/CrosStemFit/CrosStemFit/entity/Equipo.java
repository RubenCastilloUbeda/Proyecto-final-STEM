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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/* Clase que representa la entidad Equipo en la base de datos.
 * Esta clase se utiliza para almacenar la información de los equipos, incluyendo su nombre, número máximo de miembros, miembros del equipo, competiciones registradas, clasificaciones y resultados.
 */
@Entity
@Table(name="EQUIPOS")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable=false)
    private String nombre;

    @Column (nullable=false)
    private Integer maxMiembros;

    /**
     * Relación con la entidad Usuario 
     * Un equipo puede tener múltiples miembros, y un usuario puede pertenecer a múltiples equipos.
     */
    @ManyToMany
    @JoinTable(
        name="USUARIOS_PERTENECEN_EQUIPOS",
        joinColumns=@JoinColumn(name="id_equipo"),
        inverseJoinColumns=@JoinColumn(name="id_usuario")
    )
    @JsonIgnore
    private List<Usuario> miembrosEquipo;

    /** 
     * Relación con la entidad Competicion 
     * Un equipo puede estar registrado en múltiples competiciones, y una competición puede tener múltiples equipos registrados.
     */
    @ManyToMany
    @JoinTable(
        name="EQUIPOS_COMPETICION",
        joinColumns=@JoinColumn(name="id_equipo"),
        inverseJoinColumns=@JoinColumn(name="id_competicion")
    )
    @JsonIgnore
    private List<Competicion> registroCompeticiones;

    /**
     * Relación con la entidad Clasificacion
     * Un equipo puede tener múltiples clasificaciones en diferentes competiciones.
     */
    @OneToMany(mappedBy="equipo", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Clasificacion> registroClasificaciones;

    /**
     * Relación con la entidad Resultado
     * Un equipo puede tener múltiples resultados en diferentes eventos.
     */
    @OneToMany(mappedBy="equipo", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Resultado> resultados; 

    public Equipo(String nombre, Integer numero_miembros) {
        this.nombre = nombre;
        this.maxMiembros = numero_miembros;
        this.registroCompeticiones = new ArrayList<>();
        this.registroClasificaciones = new ArrayList<>();
        this.resultados = new ArrayList<>();
        this.miembrosEquipo = new ArrayList<>();
    }

    public Equipo() {
        this.registroCompeticiones = new ArrayList<>();
        this.registroClasificaciones = new ArrayList<>();
        this.resultados = new ArrayList<>();
        this.miembrosEquipo = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMaxMiembros() {
        return maxMiembros;
    }

    public void setMaxMiembros(Integer maxMiembros) {
        this.maxMiembros = maxMiembros;
    }

    
    public List<Usuario> getMiembrosEquipo() {
        return miembrosEquipo;
    }
    
    public void setMiembrosEquipo(List<Usuario> miembrosEquipo) {
        this.miembrosEquipo = miembrosEquipo;
    }

    public List<Competicion> getRegistroCompeticiones() {
        return registroCompeticiones;
    }

    public void setRegistroCompeticiones(List<Competicion> registroCompeticiones) {
        this.registroCompeticiones = registroCompeticiones;
    }

    public List<Clasificacion> getRegistroClasificaciones() {
        return registroClasificaciones;
    }

    public void setRegistroClasificaciones(List<Clasificacion> registroClasificaciones) {
        this.registroClasificaciones = registroClasificaciones;
    }
    
    public List<Resultado> getResultados() {
        return resultados;
    }
    
    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
    
    @Override
    public String toString() {
        String mensaje = "Nombre de equipo -> " + nombre + ". Numero de miembros " + maxMiembros;
        return mensaje;
    }
}
