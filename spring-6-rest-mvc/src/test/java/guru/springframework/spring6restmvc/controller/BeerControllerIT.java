package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.WebApplicationContextServletContextAwareProcessor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void listBeersTest() {
        List<BeerDTO> beerDTOS = beerController.listBeers();
        assertThat(beerDTOS.size()).isEqualTo(2413);
    }

    @Transactional
    @Rollback
    @Test
    void emptyListTest() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = beerController.listBeers();
        assertThat(beerDTOS.size()).isEqualTo(0);
    }

    @Test
    void getByIdTest() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void beerIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void saveBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("TestBeer")
                .build();

        ResponseEntity response = beerController.handlePost(beerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();

    }


    @Test
    void updateNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beertoBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity response = beerController.updateById(beer.getId(), beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);

    }

    @Test
    void patchBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beertoBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "PATCHED";
        beerDTO.setBeerName(beerName);

        ResponseEntity response = beerController.patchById(beer.getId(), beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Beer patchedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(patchedBeer.getBeerName()).isEqualTo(beerName);

    }

    @Test
    void patchBeerBadNameTest() throws Exception {
        Beer testBeer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "123456789012345678901234567890123456789012345678901");

       MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Transactional
    @Rollback
    @Test
    void deleteByIdFoundTest() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity response = beerController.deleteById(beer.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void deleteByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });

    }
}