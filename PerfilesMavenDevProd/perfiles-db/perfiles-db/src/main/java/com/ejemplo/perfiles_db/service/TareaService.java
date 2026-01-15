package com.ejemplo.perfiles_db.service;


import com.ejemplo.perfiles_db.model.Tarea;
import com.ejemplo.perfiles_db.model.Usuario;
import com.ejemplo.perfiles_db.repository.TareaRepository;
import com.ejemplo.perfiles_db.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private TareaRepository tareaRepository;
    private UsuarioRepository usuarioRepository;

    public TareaService(TareaRepository tareaRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Tarea> crearTareaParaUsuario(Tarea tarea, Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        tarea.setUsuario(usuarioOpt.get());
        Tarea guardada = tareaRepository.save(tarea);
        return Optional.of(guardada);
    }

    public List<Tarea> listarTodas() {
        return tareaRepository.findAll();
    }

    public List<Tarea> listarPorUsuario (Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }
}
