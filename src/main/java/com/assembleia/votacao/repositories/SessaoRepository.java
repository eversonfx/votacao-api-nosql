package com.assembleia.votacao.repositories;

import com.assembleia.votacao.domain.Sessao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends MongoRepository<Sessao, Long> {
}
