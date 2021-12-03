package com.assembleia.votacao;

import com.assembleia.votacao.domain.Associado;
import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.domain.Sessao;
import com.assembleia.votacao.repositories.AssociadoRepository;
import com.assembleia.votacao.repositories.AssociadoVotoRepository;
import com.assembleia.votacao.repositories.PautaRepository;
import com.assembleia.votacao.repositories.SessaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class VotacaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VotacaoApplication.class, args);
    }
}

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DataLoader implements ApplicationRunner {

    private PautaRepository pautaRepository;
    private SessaoRepository sessaoRepository;
    private AssociadoRepository associadoRepository;
    private AssociadoVotoRepository associdadoVotoRepository;

    public void run(ApplicationArguments args) {
        //Inserir Associados iniciais no banco
        Associado associado = new Associado();
        associado.setId(1);
        associado.setCpf("005.048.960-75");
        associado.setNome("João");
        associado.setSobrenome("dos Santos");
        associadoRepository.save(associado);

        Associado associado2 = new Associado();
        associado2.setId(2);
        associado2.setCpf("434.796.670-90");
        associado2.setNome("Guilherme");
        associado2.setSobrenome("Matos");
        associadoRepository.save(associado2);

        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Centro Comunitário");
        pauta.setDescricao("Criação de um centro comunitário na cidade");
        pauta = pautaRepository.save(pauta);

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setDatetimeCriacao(LocalDateTime.now());
        sessao.setTempoDuracao(LocalTime.of(00, 55, 00));
        sessaoRepository.save(sessao);
    }
}
