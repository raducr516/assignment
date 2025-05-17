package vessel.management.data;

import jakarta.persistence.*;

@Entity
@Table(name = "ship_table")
public class ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private category category;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private owner owner;

    @Column(name = "ship_name", nullable = false)
    private String shipName;

    @Column(name = "imo_number", nullable = false, unique = true)
    private String imoNumber;

    @Transient
    private Long shipId;

    public ship() {}
    public ship(String shipName, String imoNumber) {
        this.shipName = shipName;
        this.imoNumber = imoNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public category getCategory() { return category; }
    public void setCategory(category category) { this.category = category; }
    public owner getOwner() { return owner; }
    public void setOwner(owner owner) { this.owner = owner; }
    public String getShipName() { return shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }
    public String getImoNumber() { return imoNumber; }
    public void setImoNumber(String imoNumber) { this.imoNumber = imoNumber; }
    public Long getShipId() { return shipId; }
    public void setShipId(Long shipId) { this.shipId = shipId; }
}