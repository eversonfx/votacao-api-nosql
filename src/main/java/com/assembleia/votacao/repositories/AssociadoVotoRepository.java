package com.assembleia.votacao.repositories;

import com.assembleia.votacao.domain.AssociadoVoto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssociadoVotoRepository extends MongoRepository<AssociadoVoto, Long> {

}
