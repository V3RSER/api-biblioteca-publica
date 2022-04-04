package co.com.sofka.app.biblioteca.repositories;

import co.com.sofka.app.biblioteca.models.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends MongoRepository<Recurso, String> {

    List<Recurso> findByTipoIgnoreCase(String area);

    List<Recurso> findByAreaIgnoreCase(String area);

    List<Recurso> findByTipoAndAreaAllIgnoreCase(String tipo, String area);
}
