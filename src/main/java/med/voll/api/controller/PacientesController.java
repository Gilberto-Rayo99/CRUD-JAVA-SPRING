package med.voll.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosActualizarPaciente;
import med.voll.api.domain.paciente.DatosListadoPacientes;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.DatosRespuestaPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacientesRepository;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    @Autowired
    private PacientesRepository PacientesRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder) {

        Paciente paciente=PacientesRepository.save(new Paciente(datosRegistroPaciente));
        
        DatosRespuestaPaciente datosRespuestaPaciente=new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
        paciente.getTelefono(),
        new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getNumero(),
                paciente.getDireccion().getComplemento()));

                URI uri= uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
                return ResponseEntity.created(uri).body(datosRespuestaPaciente);

    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPacientes>> listarPacientes(@PageableDefault(size = 2) Pageable pageable) {
        // return PacientesRepository.findAll(pageable).map(DatosListadoPacientes::new);
        return ResponseEntity.ok(PacientesRepository.findByActivoTrue(pageable).map(DatosListadoPacientes::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(
            @RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {

        Paciente paciente = PacientesRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornarDatosPaciente(@PathVariable Long id) {
        Paciente paciente = PacientesRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosPaciente);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> eliminarPaciente(@PathVariable Long id) {
        Paciente paciente = PacientesRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();

    }

    // DELETE EN BASE DE DATOS
    /*
     * public void eliminarPaciente(@PathVariable Long id) {
     * Paciente paciente = pacientesRepository.getReferenceById(id);
     * pacientesRepository.delete(paciente);
     * 
     * }
     */

}
