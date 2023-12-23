package med.voll.api.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.DatosActualizarPaciente;
import med.voll.api.paciente.DatosListadoPacientes;
import med.voll.api.paciente.DatosRegistroPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacientesRepository;


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
   public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente) {

    PacientesRepository.save(new Paciente(datosRegistroPaciente));

}

    @GetMapping
    public Page<DatosListadoPacientes> listarPacientes(@PageableDefault(size=2) Pageable pageable) {
        //return PacientesRepository.findAll(pageable).map(DatosListadoPacientes::new);
        return PacientesRepository.findByActivoTrue(pageable).map(DatosListadoPacientes::new);
    }

    @PutMapping
    @Transactional
    public void actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){

        Paciente paciente= PacientesRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarPaciente(@PathVariable Long id){
        Paciente paciente= PacientesRepository.getReferenceById(id);
        paciente.desactivarPaciente();

    }

    //DELETE EN BASE DE DATOS
    /* public void eliminarPaciente(@PathVariable Long id) {
        Paciente paciente = pacientesRepository.getReferenceById(id);
        pacientesRepository.delete(paciente);

    } */

    
}
