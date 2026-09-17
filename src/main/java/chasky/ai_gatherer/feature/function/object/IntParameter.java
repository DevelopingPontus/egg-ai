package chasky.ai_gatherer.feature.function.object;

import java.util.UUID;

import chasky.ai_gatherer.feature.function.entity.ParameterObject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class IntParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int integer;

    @ManyToOne
    private ParameterObject parameter;
}
