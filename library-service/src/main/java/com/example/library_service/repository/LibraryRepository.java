package com.example.library_service.repository;

import com.example.library_service.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByReturnByIsNull();
}
