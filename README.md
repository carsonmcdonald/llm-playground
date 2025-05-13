# Testing different RAG techniques WIP

A starting point for doing RAG processing.

For the moment I'm using [Spring AI](https://spring.io/projects/spring-ai) and [Ollama](https://ollama.com/) for everything, but I plan on changing that as I work through the different techniques. This is a good [Spring AI intro video](https://www.youtube.com/watch?v=6Pgmr7xMjiY).

I'm using [Temporal](https://temporal.io/) for the workflow parts, if you want to replicate this you will need to get a Temporal server set up locally or use their cloud version. See their [Spring Boot Tutorial](https://learn.temporal.io/tutorials/java/build-an-email-drip-campaign/) for more information on the Spring Boot integration parts.

Info for [PGVector on Docker](https://github.com/pgvector/pgvector?tab=readme-ov-file#docker)

Here is how I'm running PGVector for the testing:
```shell
docker run -d -p 5432:5432 \
    -e POSTGRES_DB=rag \
    -e POSTGRES_USER=rag-user \
    -e POSTGRES_PASSWORD=password \
    pgvector/pgvector:pg17
```

Example interaction:
```shell
# Make a user chat request
curl -X PATCH -H "Content-Type: application/json" -d "tell me a joke" http://localhost:8080/chat
curl -H "Content-Type: application/json" http://localhost:8080/chat/1836441a-0c5c-405f-8780-9df5533e708b

# Make an admin process resource request
curl -X POST -H "Content-Type: application/json" -d "https://example.com/example.pdf" http://localhost:8080/chat
curl -H "Content-Type: application/json" http://localhost:8080/admin/resource/1836441a-0c5c-405f-8780-9df5533e708b/status
```
