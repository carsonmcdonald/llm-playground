package net.ioncannon.api.controllers;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import net.ioncannon.api.workflows.LLMQueryWorkflow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserActionController {

    private static final Log log = LogFactory.getLog(UserActionController.class);

    private final WorkflowClient workflowClient;

    public UserActionController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PatchMapping("/chat")
    public String addToConversation(@RequestBody String message) {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setWorkflowId(UUID.randomUUID().toString())
                .setTaskQueue("llm-query-task-queue")
                .build();

        LLMQueryWorkflow llmQueryWorkflow = workflowClient.newWorkflowStub(LLMQueryWorkflow.class, workflowOptions);
        WorkflowClient.start(llmQueryWorkflow::queryMessage, message);

        return workflowOptions.getWorkflowId();
    }

    @GetMapping("/chat/{workflowId}")
    public String fetchConversationResponse(@PathVariable String workflowId) {
        LLMQueryWorkflow llmQueryWorkflow = workflowClient.newWorkflowStub(LLMQueryWorkflow.class, workflowId);
        return llmQueryWorkflow.getQueryMessage();
    }

}
