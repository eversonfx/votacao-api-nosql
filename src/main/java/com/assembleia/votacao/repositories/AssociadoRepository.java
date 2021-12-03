package com.assembleia.votacao.repositories;

import com.assembleia.votacao.domain.Associado;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssociadoRepository extends MongoRepository<Associado, Long> {
    Associado findByCpf(String cpf);
}
