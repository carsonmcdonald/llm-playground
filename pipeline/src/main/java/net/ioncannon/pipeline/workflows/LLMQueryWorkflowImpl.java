package net.ioncannon.pipeline.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import net.ioncannon.pipeline.activities.LLMQueryActivity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.Duration;

@WorkflowImpl(workers = "llm-query-worker")
public class LLMQueryWorkflowImpl implements LLMQueryWorkflow {

    private static final Log log = LogFactory.getLog(LLMQueryWorkflowImpl.class);

    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(10))
            .build();

    private final LLMQueryActivity llmQueryActivity = Workflow.newActivityStub(LLMQueryActivity.class, options);

    private String queryResponse;

    @Override
    public void queryMessage(String message) {
        queryResponse = llmQueryActivity.startQuery(message);
    }

    @Override
    public String getQueryMessage() {
        return queryResponse;
    }
}
