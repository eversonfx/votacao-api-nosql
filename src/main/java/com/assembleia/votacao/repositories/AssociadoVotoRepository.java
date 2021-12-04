package com.assembleia.votacao.repositories;

import com.assembleia.votacao.domain.AssociadoVoto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssociadoVotoRepository extends MongoRepository<AssociadoVoto, Long> {
    List<AssociadoVoto> findBySessaoId(Long sessaoId);

    List<AssociadoVoto> findBySessaoIdAndAssociadoId(Long sessaoId, Long associadoId);
}
