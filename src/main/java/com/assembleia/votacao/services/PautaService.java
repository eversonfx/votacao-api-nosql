package com.assembleia.votacao.services;

import com.assembleia.votacao.domain.*;
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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PautaService {
    private PautaRepository pautaRepo;
    private AssociadoRepository associadoRepo;
    private AssociadoVotoRepository assoVotoRepo;
    private SessaoRepository sessaoRepo;

    public PautaResultadoDTO find(Long id) throws ObjectNotFoundException {
        Pauta pauta;
        List<AssociadoVoto> votosAssociados;
        try {
            pauta = pautaRepo.getById(id);
            //votosAssociados = assoVotoRepo.findBySessaoId(pauta.getSessao().getId());
            votosAssociados = new ArrayList<AssociadoVoto>();
            long votosSim = votosAssociados.stream().filter(v -> v.getVoto().equals("1")).count();
            long votosNao = votosAssociados.size() - votosSim;
            return new PautaResultadoDTO(pauta.getTitulo(), pauta.getSessao().getDatetimeCriacao(), pauta.getSessao().getTempoDuracao(), votosSim, votosNao, votosAssociados.size());
        } catch (Exception e) {
            throw new ObjectNotFoundException(String.valueOf(id));
        }
    }

    @Transactional
    public Pauta insert(PautaSessaoNewDTO pautaSessaoNewDTO) throws ObjectNotFoundException {
        Pauta pauta = toModel(pautaSessaoNewDTO);
        pauta.setId(null);
        pauta = pautaRepo.save(pauta);

        //Simplificando a criação da sessão logo após a pauta
        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDatetimeCriacao(LocalDateTime.now());
        if (pautaSessaoNewDTO.getTempoDuracao() != null)
            sessao.setTempoDuracao(LocalTime.parse(pautaSessaoNewDTO.getTempoDuracao()));
        sessaoRepo.save(sessao);

        return pauta;
    }

    /*public AssociadoVoto computarVoto(VotoDTO votoDTO) throws ObjectNotFoundException, DenyVoteException {
        if (!checkCPF(votoDTO.getAssociadoCPF()))
            throw new DenyVoteException("incapaz de votar");

        Associado associado;
        try {
            associado = associadoRepo.findByCpf(votoDTO.getAssociadoCPF());
        } catch (Exception e) {
            throw new ObjectNotFoundException(String.valueOf(votoDTO.getAssociadoCPF()));
        }

        Sessao sessao;
        try {
            sessao = pautaRepo.getById(Long.valueOf(votoDTO.getPautaId())).getSessao();
        } catch (Exception e) {
            throw new ObjectNotFoundException(String.valueOf(String.valueOf(votoDTO.getPautaId())));
        }

        /*if (assoVotoRepo.findBySessaoIdAndAssociadoId(sessao.getId(), associado.getId()).size() > 0) {
            throw new DenyVoteException("Associado já votou nessa sessão");
        }

        //Construção da chave composta com os dados da sessão e do associado
        AssociadoVotoId assoVotoId = new AssociadoVotoId(sessao.getId(), associado.getId());

        AssociadoVoto newAssociadoVoto = getAssociadoVoto(votoDTO, associado, sessao, assoVotoId);

        //Verificação do tempo limite para computar votos
        var horario = sessao.getDatetimeCriacao();
        LocalDateTime localDateTimePlus = horario.plusHours(sessao.getTempoDuracao().getHour())
                .plusMinutes(sessao.getTempoDuracao().getMinute())
                .plusSeconds(sessao.getTempoDuracao().getSecond());

        if (localDateTimePlus.isAfter(LocalDateTime.now()))
            return assoVotoRepo.save(newAssociadoVoto);
        else throw new DenyVoteException("tempo excedido para votar nessa sessão");
    }

    private AssociadoVoto getAssociadoVoto(VotoDTO votoDTO, Associado associado, Sessao sessao, AssociadoVotoId assoId) {
        AssociadoVoto newAssociadoVoto = new AssociadoVoto();
        newAssociadoVoto.setId(assoId);
        newAssociadoVoto.setVoto(String.valueOf(votoDTO.getVoto()));
        return newAssociadoVoto;
    }*/

    private Pauta toModel(PautaSessaoNewDTO pautaDTO) throws ObjectNotFoundException {
        Pauta pauta = new Pauta();
        pauta.setTitulo(pautaDTO.getTitulo());
        pauta.setDescricao(pautaDTO.getDescricao());
        return pauta;
    }

    private boolean checkCPF(String cpf) {
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
