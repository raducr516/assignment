package vessel.management.data;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "owner_table")
public class owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "owner_name")
    private String ownerName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "owner_ships",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "ship_id") // should match ship_id in category
    )
    private Set<category> categories = new HashSet<>();

    public owner() {}
    public owner(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public Set<category> getCategories() { return categories; }
    public void setCategories(Set<category> categories) { this.categories = categories; }
    public void addCategory(category category) {
        this.categories.add(category);
        category.getOwners().add(this);
    }
    public void removeCategory(category category) {
        this.categories.remove(category);
        category.getOwners().remove(this);
    }
}