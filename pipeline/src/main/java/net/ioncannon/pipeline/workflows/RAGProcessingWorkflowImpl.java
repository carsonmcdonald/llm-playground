package net.ioncannon.pipeline.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import net.ioncannon.pipeline.activities.LLMEmbeddingActivity;
import net.ioncannon.pipeline.activities.ResourceFetchActivity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.Duration;

@WorkflowImpl(workers = "rag-pipeline-worker")
public class RAGProcessingWorkflowImpl implements RAGProcessingWorkflow {

    private static final Log log = LogFactory.getLog(RAGProcessingWorkflowImpl.class);
    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(10))
            .build();

    private final ResourceFetchActivity resourceFetchActivity = Workflow.newActivityStub(ResourceFetchActivity.class, options);
    private final LLMEmbeddingActivity llmEmbeddingActivity = Workflow.newActivityStub(LLMEmbeddingActivity.class, options);

    private String status = "INIT";

    @Override
    public void processResource(String resourceUrl) {
        status = "STARTED";

        String fetchedFileLocation = resourceFetchActivity.startFetch(resourceUrl);

        status = "FETCHED";

        llmEmbeddingActivity.extractAndStore(fetchedFileLocation);

        status = "DONE";
    }

    @Override
    public String getStatus() {
        return status;
    }
}
