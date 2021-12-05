package com.assembleia.votacao.resources;

import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.repositories.AssociadoRepository;
import com.assembleia.votacao.repositories.PautaRepository;
import com.assembleia.votacao.repositories.SessaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PautaResourceTest {
    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        Pauta pauta = new Pauta(1L, "Centro Comunitário", "Criação de um centro comunitário na cidade");
        pautaRepository.save(pauta);
    }

    @Test
    public void deve_criar_pauta_sessao() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("titulo", "Nova Pauta");
        body.put("descricao", "Criação de nova pauta");
        body.put("tempoDuracao", "23:42:23");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/pauta")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void deve_retornar_status_json_e_status_ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/pauta/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void nao_deve_computar_voto_associado_inexistente() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("associadoCPF", "595.712.190-88");
        body.put("pautaId", "1");
        body.put("voto", "1");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/computarvoto")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }
}