package com.example.library_service.service;

import com.example.library_service.dto.RecordRequestDTO;
import com.example.library_service.dto.RecordResponseDTO;
import com.example.library_service.entity.Record;
import com.example.library_service.exception.RecordNotFoundException;
import com.example.library_service.mapper.RecordMapper;
import com.example.library_service.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final RecordMapper recordMapper;

    @Transactional
    public void registerNewBook(Long bookId) {
        Record record = new Record();
        record.setBookId(bookId);
        libraryRepository.save(record);
    }

    public List<RecordResponseDTO> getAvailableBooks() {
        return recordMapper.toRecordDTOList(libraryRepository.findAllByBorrowedAtIsNullAndAndReturnByIsNull());
    }

    @Transactional
    public RecordResponseDTO updateRecord(Long id, RecordRequestDTO recordDTO) {
        Record record = libraryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Запись с id = " + id + " не найдена"));

        if ((recordDTO.getBorrowedAt() == null && recordDTO.getReturnBy() != null) ||
                (recordDTO.getBorrowedAt() != null && recordDTO.getReturnBy() == null)) {
            throw new IllegalArgumentException("Оба поля borrowedAt и returnBy должны быть заданы, либо равны null");
        }
        recordMapper.updateRecordFromDTO(recordDTO, record);
        Record updatedRecord = libraryRepository.save(record);
        return recordMapper.toRecordDto(updatedRecord);
    }
}



