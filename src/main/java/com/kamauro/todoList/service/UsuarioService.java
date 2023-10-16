package com.kamauro.todoList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.kamauro.todoList.model.UsuarioModel;
import com.kamauro.todoList.repository.UsuarioRepository;
import com.kamauro.todoList.utils.Utils;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorio;

    public UsuarioService(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<UsuarioModel> todosUsuarios() {
        return repositorio.findAll();
    }

    public UsuarioModel getusuarioById(Long id) throws Exception {
        return this.getusuarioById(id);
    }
    
    public void salvarUsuario(UsuarioModel usuarioModel) throws Exception {
        this.checarUsername(usuarioModel.getUsername());
        var senhaHash = this.hashSenha(usuarioModel.getSenha().toCharArray());
        usuarioModel.setSenha(senhaHash);
        this.repositorio.save(usuarioModel);
    }

    public UsuarioModel atualizarUsuario(UsuarioModel usuarioModel, Long id) throws Exception {
        var usuario = this.getusuarioById(id);
        Utils.copyNonNullProperties(usuarioModel, usuario);
        var senhaHash = this.hashSenha(usuario.getSenha().toCharArray());
        usuario.setSenha(senhaHash);
        return repositorio.save(usuario);
    } 

    public void destruirUsuario(Long id) throws Exception {
        this.getusuarioById(id);
        this.repositorio.deleteById(id);
    }

    private UsuarioModel getUsuarioModelById(Long id) throws Exception {
        var usuario = repositorio.findById(id).orElse(null);
        if(usuario == null) {
            throw new Exception("Usuário não cadastrado.");

        }
        return usuario;
    }

    private UsuarioModel checarUsername(String username) throws Exception {
        var usuario = repositorio.findByUsername(username);
        if(usuario != null) {
            throw new Exception("Usuário ja cadastrado.");
        }
        return usuario;
    }

    private String hashSenha(char[] senha) {
        return BCrypt.withDefaults().hashToString(12, senha);
    }

    
}
