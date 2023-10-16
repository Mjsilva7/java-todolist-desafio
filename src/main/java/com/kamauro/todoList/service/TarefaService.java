package com.kamauro.todoList.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamauro.todoList.model.TarefaModel;
import com.kamauro.todoList.repository.TarefaRepository;
import com.kamauro.todoList.utils.Utils;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repositorio;

    public List<TarefaModel> listarTarefas(Long idUsuario) {
        return this.repositorio.findByIdUsuario(idUsuario);
    }

    public TarefaModel getTarefaById(Long id, Long idUsuario) throws Exception {
        var tarefa = this.repositorio.findById(id).orElse(null);
        this.checarTarefaPorUsuario(tarefa, idUsuario);
        return tarefa;
    }

    public void salvarTarefa(TarefaModel TarefaModel, Long idUsuario) throws Exception {
        TarefaModel.setIdUsuario((Long) idUsuario);
        this.checarData(TarefaModel);
        this.repositorio.save(TarefaModel);
    }

    public TarefaModel atualizarTarefa(TarefaModel TarefaModel, Long id, Long idUsuario) throws Exception {
        var tarefa = this.repositorio.findById(id).orElse(null);
        this.checarTarefaPorUsuario(tarefa, idUsuario);
        Utils.copyNonNullProperties(TarefaModel, tarefa);
        this.checarData(tarefa);
        return this.repositorio.save(tarefa);
    }

    public void destruirTarefa(Long id, Long idUsuario) throws Exception {
        var tarefa = this.repositorio.findById(id).orElse(null);
        this.checarTarefaPorUsuario(tarefa, idUsuario);
        this.repositorio.deleteById(id);
    }

    private void checarTarefaPorUsuario(TarefaModel tarefa, Long idUsuario) throws Exception {
        if (tarefa == null) {
            throw new Exception("Tarefa não encontrada.");
        }
        if (!tarefa.getIdUsuario().equals(idUsuario)) {
            throw new Exception("Usuario não tem permisso para alterar esta tarefa.");
        }
    }

    private void checarData(TarefaModel TarefaModel) throws Exception {
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(TarefaModel.getDataInicio())) {
            throw new Exception("A data de início não pode ser menor que a data atual.");
        }
        if (currentDate.isAfter(TarefaModel.getDataFim())) {
            throw new Exception("A data de término não pode ser menor que a data atual.");
        }
        if (TarefaModel.getDataInicio().isAfter(TarefaModel.getDataFim())) {
            throw new Exception("A data de início não pode ser depois da data de termino.");
        }
    }
    
}
