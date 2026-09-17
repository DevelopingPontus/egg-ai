package chasky.ai_gatherer.feature.function.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class FunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 1000)
    private String Reasoning;
    
    private String name;

    // The mappedBy parameter makes the database more data efficient.
    // Removes the need for junction table by telling JPA that relation is defined on other entity as foreign key.
    // This halves foreign key storage in db compared to not using mappedBy.    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<FunctionEntity> helperFunctions;

    @ManyToOne
    private FunctionEntity parent;

}
