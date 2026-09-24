package chasky.ai_gatherer.feature.function;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import chasky.ai_gatherer.feature.function.entity.FunctionEntity;

public interface FunctionRepo extends JpaRepository<FunctionEntity, UUID> {

    List<FunctionEntity> findAllByParent(FunctionEntity parent);

    @Query(value = """
            WITH RECURSIVE subtree(id, reasoning, parent_id, name) AS (
                SELECT id, reasoning, parent_id, name
                FROM function_entity WHERE id = :rootId
                UNION ALL
                SELECT n.id, n.reasoning, n.parent_id, n.name
                FROM function_entity n
                JOIN subtree s ON n.parent_id = s.id
            )
            SELECT * FROM subtree
            """, nativeQuery = true)
    List<FunctionEntity> fetchSubtree(@Param("rootId") UUID rootId);

    @Query(value = """
            WITH RECURSIVE path(id, reasoning, parent_id, name) AS (
                SELECT id, reasoning, parent_id, name
                FROM function_entity WHERE id = :leafId
                UNION ALL
                SELECT n.id, n.reasoning, n.parent_id, n.name
                FROM function_entity n
                JOIN path p ON p.parent_id = n.id
            )
            SELECT * FROM path WHERE parent_id IS NULL
            """, nativeQuery = true)
    FunctionEntity fetchRoot(@Param("leafId") UUID leafId);

}
