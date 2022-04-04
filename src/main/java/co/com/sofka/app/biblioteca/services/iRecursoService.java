package co.com.sofka.app.biblioteca.services;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;

import java.util.List;

public interface iRecursoService {
    // POST
    RecursoDTO add(RecursoDTO recursoDTO);

    // DELETE
    RecursoDTO delete(String id);

    // PUT
    RecursoDTO update(String id, RecursoDTO recursoDTO);

    String lend(String id);

    String giveBack(String id);

    // GET
    RecursoDTO findById(String id);

    List<RecursoDTO> findByTipo(String tipo);

    List<RecursoDTO> findByArea(String area);

    List<RecursoDTO> findByTipoAndArea(String tipo, String area);

    List<RecursoDTO> findAll();

    String available(String id);
}
