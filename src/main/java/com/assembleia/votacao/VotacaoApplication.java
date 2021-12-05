package com.assembleia.votacao;

import com.assembleia.votacao.domain.Associado;
import com.assembleia.votacao.domain.AssociadoVoto;
import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.domain.Sessao;
import com.assembleia.votacao.repositories.AssociadoRepository;
import com.assembleia.votacao.repositories.AssociadoVotoRepository;
import com.assembleia.votacao.repositories.PautaRepository;
import com.assembleia.votacao.repositories.SessaoRepository;
import com.assembleia.votacao.services.SequenceGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DataLoader implements ApplicationRunner {

    private PautaRepository pautaRepository;
    private SessaoRepository sessaoRepository;
    private AssociadoRepository associadoRepository;
    private AssociadoVotoRepository associdadoVotoRepository;
    private SequenceGeneratorService sequenceGenerator;

    public void run(ApplicationArguments args) {
        if ((associadoRepository.findAll().size() == 0) && (pautaRepository.findAll().size() == 0) && (sessaoRepository.findAll().size() == 0) && (associdadoVotoRepository.findAll().size() == 0)) {
            //Inserir Associados iniciais no banco
            log.info("Populando base de dados para desenvolvimento");

            Associado associado = new Associado();
            associado.setId(sequenceGenerator.generateSequence(Associado.SEQUENCE_NAME));
            associado.setCpf("005.048.960-75");
            associado.setNome("João");
            associado.setSobrenome("dos Santos");
            associadoRepository.save(associado);

            Associado associado2 = new Associado();
            associado2.setId(sequenceGenerator.generateSequence(Associado.SEQUENCE_NAME));
            associado2.setCpf("434.796.670-90");
            associado2.setNome("Guilherme");
            associado2.setSobrenome("Matos");
            associadoRepository.save(associado2);

            Pauta pauta = new Pauta();
            pauta.setId(sequenceGenerator.generateSequence(Pauta.SEQUENCE_NAME));
            pauta.setTitulo("Centro Comunitário");
            pauta.setDescricao("Criação de um centro comunitário na cidade");
            pauta = pautaRepository.save(pauta);

            Sessao sessao = new Sessao();
            sessao.setId(sequenceGenerator.generateSequence(Sessao.SEQUENCE_NAME));
            sessao.setPauta(pauta);
            sessao.setDatetimeCriacao(LocalDateTime.now());
            sessao.setTempoDuracao(LocalTime.of(00, 58, 02));
            sessaoRepository.save(sessao);

            AssociadoVoto assoVoto = new AssociadoVoto();
            assoVoto.setId(sequenceGenerator.generateSequence(AssociadoVoto.SEQUENCE_NAME));
            assoVoto.setAssociadoId(associado.getId());
            assoVoto.setSessaoId(sessao.getId());
            assoVoto.setVoto(String.valueOf(1));
            assoVoto.setDateTimeVoto(LocalDateTime.now());
            associdadoVotoRepository.save(assoVoto);

            AssociadoVoto assoVoto2 = new AssociadoVoto();
            assoVoto2.setId(sequenceGenerator.generateSequence(AssociadoVoto.SEQUENCE_NAME));
            assoVoto2.setAssociadoId(associado2.getId());
            assoVoto2.setSessaoId(sessao.getId());
            assoVoto2.setVoto(String.valueOf(2));
            assoVoto2.setDateTimeVoto(LocalDateTime.now());
            associdadoVotoRepository.save(assoVoto2);
        }
    }
}
