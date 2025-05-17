package vessel.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vessel.management.data.category;
import vessel.management.data.owner;
import vessel.management.data.ship;
import vessel.management.repo.categoryRepository;
import vessel.management.repo.ownerRepository;
import vessel.management.repo.shipRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentApplicationTests {

    @Autowired
    private  MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private categoryRepository categoryRepository;
    @Autowired
    private shipRepository shipRepository;
    @Autowired
    private ownerRepository ownerRepository;
    private category testCategory;



    @BeforeEach
    @Transactional
    void setup() {
        for (owner o : ownerRepository.findAll()) {
            o.getCategories().clear();
            ownerRepository.save(o);
        }

        shipRepository.deleteAll();

        ownerRepository.deleteAll();
        categoryRepository.deleteAll();

        testCategory = new category("Bulk Carrier", 50000);
        testCategory = categoryRepository.save(testCategory);
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void testAddShipCategory() throws Exception {
        category newCategory = new category("Container", 30000);
        mockMvc.perform(post("/addShipCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipType").value("Container"))
                .andExpect(jsonPath("$.shipTonnage").value(30000));
    }

    @Test
    @Transactional
    void testAddAndGetShip() throws Exception {
        ship newShip = new ship();
        newShip.setShipName("TestShip");
        newShip.setImoNumber("IMO1234567");
        newShip.setShipId(testCategory.getShipId());
        newShip.setCategory(testCategory);

        String response = mockMvc.perform(post("/addShip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newShip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipName").value("TestShip"))
                .andReturn().getResponse().getContentAsString();

        ship createdShip = objectMapper.readValue(response, ship.class);

        mockMvc.perform(get("/getShip/" + createdShip.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipName").value("TestShip"));
    }

    @Test
    @Transactional
    void testUpdateShip() throws Exception {
        ship ship = new ship();
        ship.setShipName("OldName");
        ship.setImoNumber("IMO0000001");
        ship.setShipId(testCategory.getShipId());
        ship.setCategory(testCategory);
        ship = shipRepository.save(ship);

        ship.setShipName("UpdatedName");
        mockMvc.perform(put("/updateShip/" + ship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ship)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipName").value("UpdatedName"));
    }

    @Test
    @Transactional
    void testDeleteShip() throws Exception {
        ship ship = new ship();
        ship.setShipName("ToDelete");
        ship.setImoNumber("IMO0000002");
        ship.setShipId(testCategory.getShipId());
        ship.setCategory(testCategory);
        ship = shipRepository.save(ship);

        mockMvc.perform(delete("/deleteShip/" + ship.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testGetAllShips() throws Exception {
        mockMvc.perform(get("/getAllShips"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/getShipCategory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    void testUpdateShipCategory() throws Exception {
        testCategory.setShipType("UpdatedType");

        mockMvc.perform(put("/updateShipCategory/" + testCategory.getShipId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipType").value("UpdatedType"));
    }

    @Test
    @Transactional
    void testDeleteShipCategory() throws Exception {
        mockMvc.perform(delete("/deleteShipCategory/" + testCategory.getShipId()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testAddOwner() throws Exception {
        owner newOwner = new owner("TestOwner");

        mockMvc.perform(post("/addOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerName").value("TestOwner"));
    }

    @Test
    @Transactional
    void testAddOwnerToShipAndRemoveOwnerFromShip() throws Exception {
        owner newOwner = new owner("TestOwner");
        newOwner = ownerRepository.save(newOwner);

        mockMvc.perform(post("/addOwnerToShip/" + newOwner.getOwnerId() + "/" + testCategory.getShipId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId").value(newOwner.getOwnerId()));

        mockMvc.perform(delete("/removeOwnerFromShip/" + newOwner.getOwnerId() + "/" + testCategory.getShipId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId").value(newOwner.getOwnerId()));
    }

    @Test
    @Transactional
    void testDeleteOwner() throws Exception {
        owner newOwner = new owner("TestOwner");
        newOwner = ownerRepository.save(newOwner);

        mockMvc.perform(delete("/deleteOwner/" + newOwner.getOwnerId()))
                .andExpect(status().isOk());
    }
}