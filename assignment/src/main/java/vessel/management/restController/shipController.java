package vessel.management.restController;


import org.springframework.web.bind.annotation.*;
import vessel.management.data.category;
import vessel.management.data.ship;
import vessel.management.services.shipServicies;
import java.util.*;


@RestController
public class shipController {
    private final shipServicies shipServicies;


    public shipController(shipServicies shipServicies) {
        this.shipServicies = shipServicies;
    }

    @PostMapping("/addShipCategory")
    public category addShipCategory(@RequestBody category shipCategory) {
        return shipServicies.createShip(shipCategory);
    }

    @PostMapping("/addShip")
    public ship addShip(@RequestBody ship shipInput) {
        return shipServicies.addShipWithShipId(shipInput);
    }

    @GetMapping("/getAllShips")
    public List<Map<String, Object>> getAllShips() {
        return shipServicies.getAllShips();
    }

    @GetMapping("/getShip/{id}")
    public Map<String, Object> getShipById(@PathVariable Long id) {
        return shipServicies.getShipById(id);
    }

    @GetMapping("/getShipCategory")
    public List<Map<String, Object>> getShipCategory() {
        return shipServicies.getShipCategories();
    }

    @PutMapping("/updateShip/{id}")
    public ship updateShip(@PathVariable long id,@RequestBody ship shipInput) {
        return shipServicies.updateShip(id, shipInput);
    }
    @PutMapping("/updateShipCategory/{id}")
    public category updateShipCategory(@PathVariable long id,@RequestBody category categoryInput) {
        return shipServicies.updateShipCategory(id, categoryInput);
    }

    @DeleteMapping("/deleteShip/{id}")
    public void deleteShip(@PathVariable long id) {
        shipServicies.deleteShip(id);
    }

    @DeleteMapping("/deleteShipCategory/{id}")
    public void deleteShipCategory(@PathVariable long id) {
        shipServicies.deleteShipCategory(id);
    }
}