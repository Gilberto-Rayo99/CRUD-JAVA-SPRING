package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicosRepository;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    private MedicosRepository medicosRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(
            @RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicosRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getDocumento().toString(), medico.getEmail(), medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));

        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaMedico);

    }

    @GetMapping
    public ResponseEntity <Page<DatosListadoMedico>> listarMedicos(@PageableDefault(size = 2) Pageable pageable) {
        // return medicosRepository.findAll(pageable).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicosRepository.findByActivoTrue(pageable).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(
            @RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {

        Medico medico = medicosRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getDocumento().toString(), medico.getEmail(), medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

     @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id) {
        Medico medico = medicosRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getDocumento().toString(), medico.getEmail(), medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);

    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicosRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();

    }
    // DELETE EN BASE DE DATOS
    /*
     * public void eliminarMedico(@PathVariable Long id) {
     * Medico medico = medicosRepository.getReferenceById(id);
     * medicosRepository.delete(medico);
     * 
     * }
     */

}
