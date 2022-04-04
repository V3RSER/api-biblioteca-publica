package co.com.sofka.app.biblioteca.services.impl;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;
import co.com.sofka.app.biblioteca.models.Recurso;
import co.com.sofka.app.biblioteca.repositories.RecursoRepository;
import co.com.sofka.app.biblioteca.services.iRecursoService;
import co.com.sofka.app.biblioteca.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class RecursoService implements iRecursoService {
    @Autowired
    private RecursoRepository repository;

    @Override
    public RecursoDTO add(RecursoDTO recursoDTO) {
        return AppUtils.recursoModelToDto(
                this.repository.save(
                        AppUtils.recursoDtoToModel(recursoDTO)));
    }

    @Override
    public Optional<RecursoDTO> delete(String id) {
        return repository.findById(id).stream()
                .map(recurso1 -> {
                    repository.deleteById(id);
                    return AppUtils.recursoModelToDto(recurso1);
                })
                .findFirst();
    }

    @Override
    public Optional<RecursoDTO> update(String id, RecursoDTO recursoDTO) {
        return this.repository.findById(id).stream()
                .map(recurso -> {
                    recursoDTO.setId(id);
                    return AppUtils.recursoModelToDto(
                            this.repository.save(
                                    AppUtils.recursoDtoToModel(recursoDTO)));
                })
                .findFirst();
    }

    @Override
    public Optional<String> lend(String id) {
        AtomicBoolean prestado = new AtomicBoolean(false);
        return this.repository.findById(id)
                .stream()
                .map((Recurso recurso) -> {
                    if (recurso.getDisponible()) {
                        recurso.setDisponible(false);
                        recurso.setFechaPrestamo(LocalDateTime.now());
                        prestado.set(true);
                        return recurso;
                    }
                    return recurso;
                })
                .map((Recurso recurso) -> {
                    this.repository.save(recurso);
                    return prestado.get() ?
                            "El recurso ha sido prestado con éxito" :
                            "El recurso no se encuentra disponible";
                })
                .findFirst();
    }

    @Override
    public Optional<String> giveBack(String id) {
        AtomicBoolean devuelto = new AtomicBoolean(false);
        return this.repository.findById(id)
                .stream()
                .map((Recurso recurso) -> {
                    if (!recurso.getDisponible()) {
                        recurso.setDisponible(true);
                        devuelto.set(true);
                        return recurso;
                    }
                    return recurso;
                })
                .map((Recurso recurso) -> {
                    this.repository.save(recurso);
                    return devuelto.get() ?
                            "El recurso ha sido devuelto con éxito" :
                            "El recurso no se encuentra prestado";
                })
                .findFirst();
    }

    @Override
    public Optional<RecursoDTO> findById(String id) {
        return this.repository.findById(id)
                .stream()
                .map(AppUtils::recursoModelToDto)
                .findFirst();
    }

    @Override
    public List<RecursoDTO> findByTipo(String tipo) {
        return this.repository.findByTipoIgnoreCase(tipo.toLowerCase())
                .stream()
                .map(AppUtils::recursoModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecursoDTO> findByArea(String area) {
        return this.repository.findByAreaIgnoreCase(area.toLowerCase())
                .stream()
                .map(AppUtils::recursoModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecursoDTO> findByTipoAndArea(String tipo, String area) {
        return this.repository.findByTipoAndAreaAllIgnoreCase(tipo.toLowerCase(), area.toLowerCase())
                .stream()
                .map(AppUtils::recursoModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecursoDTO> findAll() {
        return this.repository.findAll()
                .stream()
                .map(AppUtils::recursoModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> available(String id) {
        return this.repository.findById(id)
                .map(recurso -> recurso.getDisponible() ?
                        "El recurso se encuentra disponible" :
                        ("El recurso no se encuentra disponible. Fecha del último préstamo: " +
                                DateTimeFormatter
                                        .ofPattern("yyyy/MMMM/dd HH:mm:ss")
                                        .format(recurso.getFechaPrestamo())));
    }
}
