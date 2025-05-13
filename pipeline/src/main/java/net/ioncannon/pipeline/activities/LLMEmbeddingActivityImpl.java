package net.ioncannon.pipeline.activities;

import io.temporal.spring.boot.ActivityImpl;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(workers = "rag-pipeline-worker")
public class LLMEmbeddingActivityImpl implements LLMEmbeddingActivity {
    private final VectorStore vectorStore;

    public LLMEmbeddingActivityImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void extractAndStore(String localFileLocation) {
        try {
            // TODO this assumes the input is a PDF, instead should pass in the type and do different processing with that
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(new FileSystemResource(localFileLocation));
            TextSplitter textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(pdfReader.get()));
        } catch (Exception e) {
            // TODO actually handle the exceptions
            throw new RuntimeException(e);
        }
    }
}
