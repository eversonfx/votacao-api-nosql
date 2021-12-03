package com.assembleia.votacao.resources;

import com.assembleia.votacao.dto.PautaSessaoNewDTO;
import com.assembleia.votacao.exceptions.ObjectNotFoundException;
import com.assembleia.votacao.services.PautaService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(PautaResource.class)
class PautaResourceTest {
    @Autowired
    private PautaResource pautaResource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @Before
    public void setUp() throws ObjectNotFoundException {
        mockMvc = standaloneSetup(pautaResource).build();
        PautaSessaoNewDTO pautaSessaoNew = new PautaSessaoNewDTO("Pauta 67867", "Criação de uma ponte sobre o rio", "00:12:23");
        pautaService.insert(pautaSessaoNew);
    }

    @Test
    void getPautaResultado() throws Exception {
        mockMvc.perform(
                get("/api/pauta/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}