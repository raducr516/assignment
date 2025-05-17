package vessel.management.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category_table")
public class category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ship_id", nullable = false)
    private Long shipId;

    @Column(name = "ship_type", nullable = false)
    private String shipType;

    @Column(name = "ship_tonnage", nullable = false)
    private int shipTonnage;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<owner> owners = new HashSet<>();

    public category() {}
    public category(String shipType, int shipTonnage) {
        this.shipType = shipType;
        this.shipTonnage = shipTonnage;
    }

    public Long getShipId() {
        return shipId;
    }
    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }
    public String getShipType() {
        return shipType;
    }
    public void setShipType(String shipType) {
        this.shipType = shipType;
    }
    public int getShipTonnage() {
        return shipTonnage;
    }
    public void setShipTonnage(int shipTonnage) {
        this.shipTonnage = shipTonnage;
    }
    public Set<owner> getOwners() {
        return owners;
    }
    public void setOwners(Set<owner> owners) {
        this.owners = owners;
    }
}