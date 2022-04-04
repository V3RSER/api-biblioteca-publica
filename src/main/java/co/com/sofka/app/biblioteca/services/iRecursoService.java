package co.com.sofka.app.biblioteca.services;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;

import java.util.List;
import java.util.Optional;

public interface iRecursoService {
    // POST
    RecursoDTO add(RecursoDTO recursoDTO);

    // DELETE
    Optional<RecursoDTO> delete(String id);

    // PUT
    Optional<RecursoDTO> update(String id, RecursoDTO recursoDTO);

    Optional<String> lend(String id);

    Optional<String> giveBack(String id);

    // GET
    Optional<RecursoDTO> findById(String id);

    List<RecursoDTO> findByTipo(String tipo);

    List<RecursoDTO> findByArea(String area);

    List<RecursoDTO> findByTipoAndArea(String tipo, String area);

    List<RecursoDTO> findAll();

    Optional<String> available(String id);
}
