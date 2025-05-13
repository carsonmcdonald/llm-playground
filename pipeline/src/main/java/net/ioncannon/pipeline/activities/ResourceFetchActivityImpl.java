package net.ioncannon.pipeline.activities;

import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@ActivityImpl(workers = "rag-pipeline-worker")
public class ResourceFetchActivityImpl implements ResourceFetchActivity {
    @Override
    public String startFetch(String resourceUrl) {
        try {
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
            InputStream inputStream = URI.create(resourceUrl).toURL().openStream();
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            // TODO actually handle the errors this can produce
            throw new RuntimeException(e);
        }
    }
}
