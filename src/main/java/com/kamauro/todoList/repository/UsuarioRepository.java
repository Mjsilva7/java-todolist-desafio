package com.kamauro.todoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamauro.todoList.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    UsuarioModel findByUsername(String username);    
}
