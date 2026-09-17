package chasky.ai_gatherer.feature.function.entity;

import java.util.List;
import java.util.UUID;

import chasky.ai_gatherer.feature.function.object.BooleanParameter;
import chasky.ai_gatherer.feature.function.object.IntParameter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity 
public class ParameterObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany 
    private List<IntParameter> ints;

    @OneToMany 
    private List<BooleanParameter> bools;

    @ManyToOne 
    private FunctionEntity function;
}
