package com.assembleia.votacao.resources;

import com.assembleia.votacao.domain.AssociadoVoto;
import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.dto.PautaResultadoDTO;
import com.assembleia.votacao.dto.PautaSessaoNewDTO;
import com.assembleia.votacao.dto.VotoDTO;
import com.assembleia.votacao.exceptions.DenyVoteException;
import com.assembleia.votacao.exceptions.ObjectNotFoundException;
import com.assembleia.votacao.services.PautaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/pauta")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PautaResource {
    private PautaService pautaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PautaResultadoDTO> find(@PathVariable Long id) throws ObjectNotFoundException {
        PautaResultadoDTO pautaResultadoDTO = pautaService.find(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return ResponseEntity.ok().headers(headers).body(pautaResultadoDTO);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PautaSessaoNewDTO pautaSessaoNewDTO) throws ObjectNotFoundException {
        Pauta pauta = pautaService.insert(pautaSessaoNewDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /*@RequestMapping(value = "/computarvoto", method = RequestMethod.POST)
    public ResponseEntity<Void> computar(@Valid @RequestBody VotoDTO votoDTO) throws DenyVoteException, ObjectNotFoundException {
        AssociadoVoto associadoVoto = pautaService.computarVoto(votoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(associadoVoto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }*/
}
