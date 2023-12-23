package med.voll.api.paciente;

public record DatosListadoPacientes(Long id, String nombre, String email, String telefono) {

    public DatosListadoPacientes(Paciente paciente) {
        this(paciente.getId(),paciente.getNombre(), paciente.getEmail(), paciente.getTelefono());
    }

    
}
