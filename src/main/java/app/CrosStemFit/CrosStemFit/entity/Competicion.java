package app.CrosStemFit.CrosStemFit.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/* Clase que representa la entidad Competicion en la base de datos.
 * Esta clase se utiliza para almacenar la información de las competiciones, incluyendo su nombre, precio, fechas, localización, eventos asociados, equipos registrados y clasificaciones.
 */
@Entity
@Table(name="COMPETICIONES")
public class Competicion {

    /* Identificador único de la competición */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompeticion;

    @Column(nullable = false)
    private String nombre; 

    @Column(nullable = false)
    private double precio;

    @Column
    private int maxEquipos;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String localizacion;

    @Column(nullable = false)
    private String direccion;

    /**
     *  Relación con la entidad Evento 
     * Una competición puede tener múltiples eventos, pero un evento solo pertenece a una competición.
     */
    @OneToMany(mappedBy="competicion", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Evento> eventos;

    /**
     * Relación con la entidad Equipo
     * Una competición puede tener múltiples equipos registrados y un equipo puede estar registrado en múltiples competiciones.
     */
    @ManyToMany(mappedBy="registroCompeticiones")
    @JsonIgnore
    private List<Equipo> equiposRegistrados;

    /**
     * Relación con la entidad Clasificacion
     * Una competición puede tener múltiples clasificaciones y una clasificación pertenece a una única competición.
     */
    @OneToMany(mappedBy = "competicion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Clasificacion> clasificaciones;

    public Competicion(String direccion, LocalDate fechaInicio, LocalDate fechaFin, String localizacion, int maxEquipos, String nombre, double precio) {
        this.direccion = direccion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.localizacion = localizacion;
        this.maxEquipos = maxEquipos;
        this.nombre = nombre;
        this.precio = precio;
        this.eventos = new ArrayList<>();
        this.equiposRegistrados = new ArrayList<>();
        this.clasificaciones = new ArrayList<>();
    }

    public Competicion() {
        this.eventos = new ArrayList<>();
        this.equiposRegistrados = new ArrayList<>();
        this.clasificaciones = new ArrayList<>();
    }

    public Long getIdCompeticion() {
        return idCompeticion;
    }

    public void setIdCompeticion(Long idCompeticion) {
        this.idCompeticion = idCompeticion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getMaxEquipos() {
        return maxEquipos;
    }

    public void setMaxEquipos(int maxEquipos) {
        this.maxEquipos = maxEquipos;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Equipo> getEquiposRegistrados() {
        return equiposRegistrados;
    }
    
    public void setEquiposRegistrados(List<Equipo> equiposRegistrados) {
        this.equiposRegistrados = equiposRegistrados;
    }
    
    public List<Clasificacion> getClasificaciones() {
        return clasificaciones;
    }
    
    public void setClasificaciones(List<Clasificacion> clasificaciones) {
        this.clasificaciones = clasificaciones;
    }
    
    @Override
    public String toString() {
        String mensaje = nombre + " fecha del evento " + fechaInicio + " - " + fechaFin + ". Ubicado en " + direccion + ", " + localizacion + 
        ". Precio de la inscripcion de " + precio + "€. Numero maximo de equipos por categoria: " + maxEquipos;
        return mensaje;
    }

}
