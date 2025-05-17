package vessel.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vessel.management.data.owner;
import vessel.management.data.category;
import vessel.management.data.ship;
import vessel.management.repo.ownerRepository;
import vessel.management.repo.categoryRepository;
import jakarta.persistence.EntityNotFoundException;
import vessel.management.repo.shipRepository;

import java.util.List;


@Service
public class ownerServicies {
    private final ownerRepository ownerRepo;
    private final categoryRepository categoryRepo;
    private final shipRepository shipRepo;

    @Autowired
    public ownerServicies(ownerRepository ownerRepo, categoryRepository categoryRepo, shipRepository shipRepo) {
        this.ownerRepo = ownerRepo;
        this.categoryRepo = categoryRepo;
        this.shipRepo = shipRepo;
    }

    public owner addOwner(owner owner) {
        return ownerRepo.save(owner);
    }

    public owner addCategoryToOwner(Long ownerId, Long categoryId) {
        owner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        owner.addCategory(category);
        return ownerRepo.save(owner);
    }

    public owner removeCategoryFromOwner(Long ownerId, Long categoryId) {
        owner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        owner.removeCategory(category);
        return ownerRepo.save(owner);
    }

    public owner deleteOwner(Long ownerId) {
        owner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        List<ship> ships = shipRepo.findAll();
        for (ship s : ships) {
            if (s.getOwner() != null && s.getOwner().getOwnerId().equals(ownerId)) {
                s.setOwner(null);
                shipRepo.save(s);
            }
        }
        ownerRepo.delete(owner);
        return null;
    }

}