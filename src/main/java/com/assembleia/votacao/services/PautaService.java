package com.assembleia.votacao.services;

import com.assembleia.votacao.domain.Associado;
import com.assembleia.votacao.domain.AssociadoVoto;
import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.domain.Sessao;
import com.assembleia.votacao.dto.PautaResultadoDTO;
import com.assembleia.votacao.dto.PautaSessaoNewDTO;
import com.assembleia.votacao.dto.ResponseCheckCPFDTO;
import com.assembleia.votacao.dto.VotoDTO;
import com.assembleia.votacao.exceptions.DenyVoteException;
import com.assembleia.votacao.exceptions.ObjectNotFoundException;
import com.assembleia.votacao.repositories.AssociadoRepository;
import com.assembleia.votacao.repositories.AssociadoVotoRepository;
import com.assembleia.votacao.repositories.PautaRepository;
import com.assembleia.votacao.repositories.SessaoRepository;
import com.assembleia.votacao.utils.LoggingComponent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Log4j2
@Component
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PautaService {
    private PautaRepository pautaRepo;
    private AssociadoRepository associadoRepo;
    private AssociadoVotoRepository assoVotoRepo;
    private SessaoRepository sessaoRepo;
    private SequenceGeneratorService sequenceGenerator;
    private LoggingComponent loggingComponent;

    public PautaResultadoDTO find(Long id) throws ObjectNotFoundException {
        Pauta pauta;
        Sessao sessao;

        pauta = pautaRepo.getById(id);
        sessao = sessaoRepo.getByPauta(pauta);

        List<AssociadoVoto> votosAssociados;
        votosAssociados = assoVotoRepo.findBySessaoId(pauta.getId());
        long votosSim = votosAssociados.stream().filter(v -> v.getVoto().equals("1")).count();
        long votosNao = votosAssociados.size() - votosSim;

        return new PautaResultadoDTO(sessao.getPauta().getTitulo(), sessao.getDatetimeCriacao(), sessao.getTempoDuracao(), votosSim, votosNao, (votosSim + votosNao));
    }

    @Transactional
    public Pauta insert(PautaSessaoNewDTO pautaSessaoNewDTO) throws ObjectNotFoundException {
        Pauta pauta = toModel(pautaSessaoNewDTO);
        pauta.setId(sequenceGenerator.generateSequence(Pauta.SEQUENCE_NAME));
        pauta = pautaRepo.save(pauta);

        //Simplificando a criação da sessão logo após a pauta
        Sessao sessao = new Sessao();
        sessao.setId(sequenceGenerator.generateSequence(Sessao.SEQUENCE_NAME));
        sessao.setPauta(pauta);
        sessao.setDatetimeCriacao(LocalDateTime.now());
        if (pautaSessaoNewDTO.getTempoDuracao() != null)
            sessao.setTempoDuracao(LocalTime.parse(pautaSessaoNewDTO.getTempoDuracao()));
        sessaoRepo.save(sessao);
        return pauta;
    }

    public AssociadoVoto computarVoto(VotoDTO votoDTO) throws ObjectNotFoundException, DenyVoteException {
        if (!checkCPF(votoDTO.getAssociadoCPF()))
            throw new DenyVoteException("incapaz de votar");

        Associado associado;
        try {
            associado = associadoRepo.findByCpf(votoDTO.getAssociadoCPF());
            if (associado == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            throw new ObjectNotFoundException(votoDTO.getAssociadoCPF());
        }

        Sessao sessao;
        try {
            sessao = sessaoRepo.getByPauta(pautaRepo.getById(Long.valueOf(votoDTO.getPautaId())));
            if (sessao == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            throw new ObjectNotFoundException(String.valueOf(String.valueOf(votoDTO.getPautaId())));
        }

        try {
            List<AssociadoVoto> bySessaoIdAndAssociadoId = assoVotoRepo.findBySessaoIdAndAssociadoId(sessao.getId(), associado.getId());
            if (bySessaoIdAndAssociadoId.size() > 0)
                throw new DenyVoteException("Associado já votou nessa sessão");
        } catch (NullPointerException e) {
            throw new ObjectNotFoundException("Sem votos");
        }

        AssociadoVoto newAssociadoVoto = getAssociadoVoto(votoDTO, associado, sessao);

        //Verificação do tempo limite para computar votos
        var horario = sessao.getDatetimeCriacao();
        LocalDateTime localDateTimePlus = horario.plusHours(sessao.getTempoDuracao().getHour())
                .plusMinutes(sessao.getTempoDuracao().getMinute())
                .plusSeconds(sessao.getTempoDuracao().getSecond());

        if (localDateTimePlus.isAfter(LocalDateTime.now()))
            return assoVotoRepo.save(newAssociadoVoto);
        else throw new DenyVoteException("tempo excedido para votar nessa sessão");
    }

    private AssociadoVoto getAssociadoVoto(VotoDTO votoDTO, Associado associado, Sessao sessao) {
        AssociadoVoto newAssociadoVoto = new AssociadoVoto();
        newAssociadoVoto.setId(sequenceGenerator.generateSequence(AssociadoVoto.SEQUENCE_NAME));
        newAssociadoVoto.setSessaoId(sessao.getId());
        newAssociadoVoto.setAssociadoId(associado.getId());
        newAssociadoVoto.setVoto(String.valueOf(votoDTO.getVoto()));
        return newAssociadoVoto;
    }

    private Pauta toModel(PautaSessaoNewDTO pautaDTO) throws ObjectNotFoundException {
        Pauta pauta = new Pauta();
        pauta.setTitulo(pautaDTO.getTitulo());
        pauta.setDescricao(pautaDTO.getDescricao());
        return pauta;
    }

    private boolean checkCPF(String cpf) {
        log.info("Checando CPF: " + cpf + " em API externa");

        String url = "https://user-info.herokuapp.com/users/" + cpf.replace(".", "").replace("-", "").trim().substring(0, 11);
        RestTemplate restTemplate = new RestTemplate();
        ResponseCheckCPFDTO objResponse;
        for (int i = 0; i < 3; i++) {
            objResponse = restTemplate.getForObject(url, ResponseCheckCPFDTO.class);
            if (objResponse.getStatus().equals("ABLE_TO_VOTE"))
                return true;
        }
        return false;
    }
}
