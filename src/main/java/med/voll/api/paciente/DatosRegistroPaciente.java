package med.voll.api.paciente;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.direccion.DatosDireccion;

public record DatosRegistroPaciente(

        @NotBlank String nombre,

        @NotBlank @Email String email,

        @NotBlank @Pattern(regexp = "\\d{10}") String telefono,

        @NotNull @Valid DatosDireccion direccion) {

}
