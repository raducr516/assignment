package vessel.management.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vessel.management.data.ship;
import vessel.management.repo.shipRepository;

import java.util.*;
import java.util.stream.Collectors;

import vessel.management.data.owner;
import vessel.management.repo.categoryRepository;
import vessel.management.data.category;
import vessel.management.repo.ownerRepository;

@Service
public class shipServicies {
    private final categoryRepository categoryRepository;
    private final shipRepository shipRepository;
    private final ownerRepository ownerRepository;

    public shipServicies(categoryRepository categoryRepository, shipRepository shipRepository, ownerRepository ownerRepository) {
        this.categoryRepository = categoryRepository;
        this.shipRepository = shipRepository;
        this.ownerRepository = ownerRepository;
    }

    public category createShip(vessel.management.data.category shipcategory) {
        return categoryRepository.save(shipcategory);
    }


    public ship addShipWithShipId(ship shipInput) {
        Long shipId = shipInput.getShipId();
        if (shipId == null) throw new IllegalArgumentException("shipId is required");

        category cat = categoryRepository.findById(shipId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (cat.getOwners() != null && !cat.getOwners().isEmpty()) {
            owner ownerEntity = cat.getOwners().stream().findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("No owner associated with this category"));
            shipInput.setOwner(ownerEntity);
        }

        shipInput.setCategory(cat);
        return shipRepository.save(shipInput);
    }

    public List<Map<String, Object>> getAllShips() {
        return shipRepository.findAll().stream().map(ship -> {
            Map<String, Object> result = new HashMap<>();
            result.put("id", ship.getId());
            result.put("shipName", ship.getShipName());
            result.put("imoNumber", ship.getImoNumber());

            if (ship.getCategory() != null && ship.getCategory().getOwners() != null) {
                result.put("ownerIds", ship.getCategory().getOwners().stream()
                        .map(owner::getOwnerId)
                        .collect(Collectors.toList()));
            } else {
                result.put("ownerIds", Collections.emptyList());
            }
            return result;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> getShipById(Long id) {
        ship ship = shipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ship not found"));

        Map<String, Object> result = new HashMap<>();
        result.put("id", ship.getId());
        result.put("shipName", ship.getShipName());
        result.put("imoNumber", ship.getImoNumber());

        if (ship.getCategory() != null && ship.getCategory().getOwners() != null) {
            result.put("ownerIds", ship.getCategory().getOwners().stream()
                    .map(owner::getOwnerId)
                    .collect(Collectors.toList()));
        } else {
            result.put("ownerIds", Collections.emptyList());
        }

        result.put("category", ship.getCategory());
        return result;
    }

    public ship updateShip(long id, ship shipInput) {
        ship existingShip = shipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ship not found"));

        if (shipInput.getShipName() != null) {
            existingShip.setShipName(shipInput.getShipName());
        }

        if (shipInput.getImoNumber() != null) {
            existingShip.setImoNumber(shipInput.getImoNumber());
        }

        if (shipInput.getShipId() != null) {
            category cat = categoryRepository.findById(shipInput.getShipId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existingShip.setCategory(cat);
        }

        if (shipInput.getOwner() != null && shipInput.getOwner().getOwnerId() != null) {
            owner own = ownerRepository.findById(shipInput.getOwner().getOwnerId())
                    .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            existingShip.setOwner(own);
        }

        return shipRepository.save(existingShip);
    }


    public void deleteShip(long id) {
        ship existingShip = shipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ship not found"));

        if (existingShip.getOwner() != null) {
            existingShip.getOwner().getCategories().remove(existingShip.getCategory());
            existingShip.setOwner(null);
        }

        if (existingShip.getCategory() != null) {
            existingShip.getCategory().getOwners().forEach(o ->
                    o.getCategories().remove(existingShip.getCategory())
            );
            existingShip.setCategory(null);
        }

        shipRepository.save(existingShip);
        shipRepository.delete(existingShip);
    }

    public void deleteShipCategory(long id) {
        Optional<category> shipCategory = categoryRepository.findById(id);
        if (!shipCategory.isPresent()) {
            throw new EntityNotFoundException("Ship Category not found");
        }

        category existingCategory = shipCategory.get();
        List<ship> shipsWithCategory = shipRepository.findByCategory(existingCategory);
        for (ship s : shipsWithCategory) {
            s.setCategory(null);
            shipRepository.save(s);
        }

        for (owner o : new HashSet<>(existingCategory.getOwners())) {
            o.getCategories().remove(existingCategory);
            ownerRepository.save(o);
        }
        existingCategory.getOwners().clear();
        categoryRepository.save(existingCategory);

        categoryRepository.delete(existingCategory);
    }

    public category updateShipCategory(long id, category categoryInput) {
        category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (categoryInput.getShipType() != null) {
            existingCategory.setShipType(categoryInput.getShipType());
        }

        if (categoryInput.getShipTonnage() != 0) {
            existingCategory.setShipTonnage(categoryInput.getShipTonnage());
        }

        return categoryRepository.save(existingCategory);
    }

    public List<Map<String, Object>> getShipCategories() {
        return categoryRepository.findAll().stream().map(category -> {
            Map<String, Object> result = new HashMap<>();
            result.put("shipId", category.getShipId());
            result.put("shipType", category.getShipType());
            result.put("shipTonnage", category.getShipTonnage());
            return result;
        }).collect(Collectors.toList());
    }

}