package chasky.ai_gatherer.feature.function;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import chasky.ai_gatherer.feature.function.entity.FunctionEntity;

public interface FunctionRepo extends JpaRepository<FunctionEntity, UUID> {


}
