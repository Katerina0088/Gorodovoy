package com.zags.gorodovoy.repository;
import com.zags.gorodovoy.models.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Query("SELECT u FROM Document u WHERE CONCAT(u.id, u.user, u.creationDate, u.documentName, u.task) LIKE %?1%")
    public Iterable<Document> search(String keyword);
}