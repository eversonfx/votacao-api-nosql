package com.assembleia.votacao.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
public class AssociadoVoto {

    @Id
    private Long id;

    String voto;

    LocalDateTime dateTimeVoto = LocalDateTime.now();
}