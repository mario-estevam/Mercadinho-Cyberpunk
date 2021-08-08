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
    String nome;
    @Size(min = 3, max = 10, message = Mensagem.ERRO_TAMANHO_STRING)
    String tipo;
    String fabricado;
    String fornecedor;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    LocalDate vencimento = LocalDate.now();
    String imagemUri;
    Date deleted;
}