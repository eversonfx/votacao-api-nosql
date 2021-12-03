package com.assembleia.votacao.repositories;

import com.assembleia.votacao.domain.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PautaRepository extends MongoRepository<Pauta, Long> {
    Pauta getById(Long pautaId);
}
