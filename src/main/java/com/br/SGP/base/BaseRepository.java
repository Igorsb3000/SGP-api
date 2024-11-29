package com.br.SGP.base;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository <Model extends BaseModel> extends JpaRepository<Model, String> {
    @Query(value = "select task from #{#entityName} task where task.id=:id and task.deletedAt is null")
    Optional<Model> findById(String id);

    @Query(value = "select task from #{#entityName} task where task.deletedAt is null order by task.id")
    Page<Model> findAll(Pageable pageable);

    @Modifying
    @Query(value = "update #{#entityName} task set task.deletedAt = CURRENT_TIMESTAMP where task.id=:id and task.deletedAt is null")
    void deleteById(String id);
}

