package chasky.ai_gatherer.feature.node.dto;

import java.util.List;

import chasky.ai_gatherer.feature.node.NodeId;

public record NodeResponse(
                String subject,
                String topic,
                List<NodeId> prerequisits,
                // List<NodeId> isPrerequisitTo,
                Boolean queryWasReasonable) {

}
