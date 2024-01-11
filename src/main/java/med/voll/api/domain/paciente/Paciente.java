package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo=true;
    
    @Embedded
    private Direccion direccion;

    public void actualizarDatos(@Valid DatosActualizarPaciente datosActualizarPaciente) {

        if (datosActualizarPaciente.nombre() != null) {
            this.nombre = datosActualizarPaciente.nombre();
        }
        if (datosActualizarPaciente.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo=false;
    }

    

    

}
