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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/** * Clase que representa la entidad Usuario en la base de datos.
 * Esta clase se utiliza para almacenar la información de los usuarios que participan en las competiciones.
 * Contiene información personal del usuario, como su nombre, apellidos, edad, email y DNI, así como la relación con los equipos a los que pertenece.
 */
@Entity
@Table (name="USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column
    private String dni;

    @Column (nullable=false)
    private String nombre;

    @Column (nullable=false)
    private String apellidos;

    @Column (nullable=false)
    private int edad;

    @Column (nullable=false)
    private String email;

    /**
     * Relación con la entidad Equipo
     * Un usuario puede pertenecer a múltiples equipos, y un equipo puede tener múltiples usuarios como miembros.
     * La eliminación de un usuario no eliminará los equipos a los que pertenece debido a la configuración de CascadeType.PERSIST.
     */
    @ManyToMany(mappedBy="miembrosEquipo", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Equipo> equipos  = new ArrayList<>();

    public Usuario(String apellidos, String dni, int edad, String nombre, String email) {
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.nombre = nombre;
        this.email = email;
    }

    public Usuario() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        String mensaje = nombre + " " + apellidos + " con dni " + dni + " y edad " + edad + " años";
        return mensaje;
    }
}
