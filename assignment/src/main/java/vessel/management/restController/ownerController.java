package vessel.management.restController;

import org.springframework.web.bind.annotation.*;
import vessel.management.data.owner;
import vessel.management.services.ownerServicies;


@RestController
public class ownerController {
    private final ownerServicies ownerServices;

    public ownerController(ownerServicies ownerServices) {
        this.ownerServices = ownerServices;
    }

    @PostMapping("/addOwner")
    public owner addOwner(@RequestBody owner newOwner) {
        return ownerServices.addOwner(newOwner);
    }

    @PostMapping("/addOwnerToShip/{ownerId}/{categoryId}")
    public owner addCategoryToOwner(@PathVariable Long ownerId, @PathVariable Long categoryId) {
        return ownerServices.addCategoryToOwner(ownerId, categoryId);
    }

    @DeleteMapping("/removeOwnerFromShip/{ownerId}/{categoryId}")
    public owner removeCategoryFromOwner(@PathVariable Long ownerId, @PathVariable Long categoryId) {
        return ownerServices.removeCategoryFromOwner(ownerId, categoryId);
    }

    @DeleteMapping("/deleteOwner/{ownerId}")
    public owner deleteOwner(@PathVariable Long ownerId) {
        return ownerServices.deleteOwner(ownerId);
    }
}