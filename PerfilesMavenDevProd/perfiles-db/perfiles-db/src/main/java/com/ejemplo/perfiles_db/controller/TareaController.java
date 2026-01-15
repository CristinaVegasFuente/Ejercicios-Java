package com.ejemplo.perfiles_db.controller;


import com.ejemplo.perfiles_db.model.Tarea;
import com.ejemplo.perfiles_db.model.Usuario;
import com.ejemplo.perfiles_db.repository.TareaRepository;
import com.ejemplo.perfiles_db.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaController(TareaRepository tareaRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearTarea (@RequestBody Tarea tarea){

        if (tarea.getUsuario() == null || tarea.getUsuario().getId() == null){
            return ResponseEntity.badRequest().body("indica a un usuario valido");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(tarea.getUsuario().getId());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("el usuario con ese id " + tarea.getUsuario().getId() + " no existe");
        }

        tarea.setUsuario(usuarioOpt.get());
        Tarea guardada = tareaRepository.save(tarea);

        return ResponseEntity.ok(guardada);
    }

    @GetMapping
    public List<Tarea>obtenerTodas(){
        return tareaRepository.findAll();
    }

    @GetMapping("usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long usuarioId){
        List<Tarea> tareas = tareaRepository.findByUsuarioId(usuarioId);

        if (tareas.isEmpty()) {
            return ResponseEntity.ok("el usuario no tiene tareas");
        }

        return ResponseEntity.ok(tareas);
    }
}

