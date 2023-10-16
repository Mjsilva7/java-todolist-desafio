package com.kamauro.todoList.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tarefas")
public class TarefaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String titulo;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime dataInicio;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime dataFim;
    private String prioridade;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    public void setTitulo(String titulo) throws Exception {
        if (titulo.length() > 50) {
            throw new Exception("O campo título deve conter no maximo 50 caracteres.");
        }
        this.titulo = titulo;
    }


    
}
