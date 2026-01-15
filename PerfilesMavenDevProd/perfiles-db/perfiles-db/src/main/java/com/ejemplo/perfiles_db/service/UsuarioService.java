package com.ejemplo.perfiles_db.service;

import com.ejemplo.perfiles_db.model.Usuario;
import com.ejemplo.perfiles_db.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public  boolean borrarUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            return  false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }
}
