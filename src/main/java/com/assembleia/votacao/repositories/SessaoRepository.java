package com.assembleia.votacao.repositories;

import com.assembleia.votacao.domain.AssociadoVoto;
import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.domain.Sessao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoRepository extends MongoRepository<Sessao, Long> {
    Sessao getByPauta(Pauta pauta);
}
