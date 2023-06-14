package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import guru.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerService beerService;
    BeerServiceImpl beerServiceImpl;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    public void patchBeerTest() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testBeer.getId());
        assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo(beerMap.get("beerName"));
    }

    @Test
    public void deleteBeerTest() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(testBeer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    public void updateBeerTest() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class), any(Beer.class));
    }

    @Test
    public void createBeerTest() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        testBeer.setVersion(null);
        testBeer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));
        mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    @Test
    void listBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(beerServiceImpl.listBeers().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].beerName", is(beerServiceImpl.listBeers().get(0).getBeerName())))
                .andExpect(jsonPath("$[1].id", is(beerServiceImpl.listBeers().get(1).getId().toString())))
                .andExpect(jsonPath("$[1].beerName", is(beerServiceImpl.listBeers().get(1).getBeerName())))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getById() throws Exception {

        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getById(any(UUID.class))).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }
}