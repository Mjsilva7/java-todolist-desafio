package com.kamauro.todoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamauro.todoList.model.TarefaModel;
import java.util.List;
import java.util.Optional;


public interface TarefaRepository extends JpaRepository<TarefaModel, Long>{

    List<TarefaModel> findByIdUsuario(Long idUsuario);
    Optional<TarefaModel> findById(Long id);
    
}
