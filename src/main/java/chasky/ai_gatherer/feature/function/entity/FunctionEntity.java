package chasky.ai_gatherer.feature.function.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class FunctionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 1000)
    private String Reasoning;

    private String name;

    // The mappedBy parameter makes the database more data efficient.
    // Removes the need for junction table by telling JPA that relation is defined
    // on other entity as foreign key.
    // This halves foreign key storage in db compared to not using mappedBy.
    @Transient 
    private List<FunctionEntity> helperFunctions;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FunctionEntity parent;

}
