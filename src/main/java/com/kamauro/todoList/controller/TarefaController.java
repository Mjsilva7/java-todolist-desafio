package com.kamauro.todoList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamauro.todoList.model.TarefaModel;
import com.kamauro.todoList.service.TarefaService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

     @Autowired
    private TarefaService service;
 
    @GetMapping("")
    public ResponseEntity<List<TarefaModel>> listar(HttpServletRequest request) {
        var idUsuario = request.getAttribute("idUsuario");
        var tarefa = this.service.listarTarefas(((Long) idUsuario));
        return ResponseEntity.status(HttpStatus.OK).body(tarefa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(HttpServletRequest request, @PathVariable Long id) {
        try {
            var idUsuario = request.getAttribute("idUsuario");
            var tarefa = this.service.getTarefaById(id, (Long) idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(tarefa);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarTarefa(@RequestBody TarefaModel tarefaModel, HttpServletRequest request) {
        try {
            var idUsuario = request.getAttribute("idUsuario");
            this.service.salvarTarefa(tarefaModel, (Long) idUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody TarefaModel tarefaModel, @PathVariable Long id, HttpServletRequest request) {
        try {
            var idUsuario = request.getAttribute("idUsuario");
            var tarefa = this.service.atualizarTarefa(tarefaModel, id, (Long) idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(tarefa);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            var idUsuario = request.getAttribute("idUsuario");
            this.service.destruirTarefa(id, (Long) idUsuario);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    
}
