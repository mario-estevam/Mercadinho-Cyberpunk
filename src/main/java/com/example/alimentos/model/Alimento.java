package com.example.alimentos.model;


import com.example.alimentos.message.Mensagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = Mensagem.NAME_BLANK)
    @Size(min = 3, max = 20, message = Mensagem.ERRO_TAMANHO_NOME)
    String nome;
    @NotBlank(message = Mensagem.NAME_BLANK)
    @Size(min = 3, max = 10, message = Mensagem.ERRO_TAMANHO_TIPO)
    String tipo;
    @NotBlank(message = Mensagem.NAME_BLANK)
    String peso;
    @NotBlank(message = Mensagem.NAME_BLANK_PRECO)
    String preco;
    String imagemUri;
    Date delete = null;
}