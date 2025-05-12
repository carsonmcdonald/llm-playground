package net.ioncannon.pipeline.workflows;

import io.temporal.spring.boot.WorkflowImpl;

@WorkflowImpl(workers = "rag-pipeline-worker")
public class RAGProcessingWorkflowImpl implements RAGProcessingWorkflow {
    @Override
    public void processDocument(String documentLocation) {
        // TODO create activity that will fetch the document from the given location
        // TODO create activity that will extract the embeddings from the document
    }

    @Override
    public String getDocumentLocation() {
        return "";
    }
}
