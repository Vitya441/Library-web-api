package com.example.library_service.service;

import com.example.library_service.dto.RecordDTO;
import com.example.library_service.entity.Record;
import com.example.library_service.exception.RecordNotFoundException;
import com.example.library_service.mapper.RecordMapper;
import com.example.library_service.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final RecordMapper recordMapper;


    public void registerNewBook(Long bookId) {
        Record record = new Record();
        record.setBookId(bookId);
        libraryRepository.save(record);
    }


    public List<RecordDTO> getAvailableBooks() {
        return recordMapper.toRecordDTOList(libraryRepository.findAllByReturnByIsNull());
    }


    public RecordDTO updateRecord(Long id, RecordDTO newRecord) {
        Record record = libraryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record with id = " + id + " not found"));
        record.setBorrowedAt(newRecord.getBorrowedAt());
        record.setReturnBy(newRecord.getReturnBy());
        return recordMapper.toRecordDto(libraryRepository.save(record));
    }
}
