package com.ejemplo.perfiles_db.controller;

import com.ejemplo.perfiles_db.model.Usuario;
import com.ejemplo.perfiles_db.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Usuarios",
        description = "CRUDs para usuarios"
)


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    // Inyectamos el perfil activo
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Inserta un nuevo usuario en la base de datos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente")
    })
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){

        Usuario guardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping
    @Operation(
            summary = "Listar usuarios",
            description = "Devuelve todos los usuarios registrados en la base de datos."
    )
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Busca un usuario por su ID. Devuelve error si no existe."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isEmpty()){
            return ResponseEntity.status(404).body("no existe ese usuario");
        }
        return ResponseEntity.ok(usuarioOpt.get());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Borrar usuario",
            description = "Elimina un usuario existente por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario borrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> borrarPorId(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(404).body("no existe ese usuario");
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("usuario con el id " + id + " borrado");
    }

    @GetMapping("/perfil")
    @Operation(
            summary = "Obtener perfil Maven activo",
            description = "Devuelve el perfil activo (dev, prod o default)."
    )
    public String getPerfilActivo(){
        return "Perfil activo: " + activeProfile;
    }

}
