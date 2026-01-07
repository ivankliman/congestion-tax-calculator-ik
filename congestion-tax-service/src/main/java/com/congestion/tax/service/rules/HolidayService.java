package com.congestion.tax.service.rules;

import com.congestion.tax.exception.IdMissingException;
import com.congestion.tax.exception.NotFoundException;
import com.congestion.tax.mapper.rules.HolidayMapper;
import com.congestion.tax.model.rules.Holiday;
import com.congestion.tax.repository.rules.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;

    public Holiday createHoliday(Holiday holiday) {
        final var recordToSave = holidayMapper.toEntity(holiday);
        final var savedRecord = holidayRepository.save(recordToSave);

        return holidayMapper.toDto(savedRecord);
    }

    public Holiday updateHoliday(Holiday holiday) {
        //field validation -- potentially move to javax/jakarta validation
        if (Objects.isNull(holiday.id())) {
            throw new IdMissingException("Id is required for the edit request");
        }
        holidayRepository.findById(holiday.id())
                .orElseThrow(()->new NotFoundException("No record found with id " + holiday.id()));

        final var recordToSave = holidayMapper.toEntity(holiday);
        final var savedRecord = holidayRepository.save(recordToSave);

        return holidayMapper.toDto(savedRecord);
    }

    public Holiday getById(Long id) {
        final var requestedRecord = holidayRepository.findById(id)
                .orElseThrow(()->new NotFoundException("No record found with id " + id));

        return holidayMapper.toDto(requestedRecord);
    }

    public Page<Holiday> findHolidays(Pageable pageable) {
        return holidayRepository
                .findAll(pageable).map(holidayMapper::toDto);
    }

    public void deleteById(Long id) {
        holidayRepository.deleteById(id);
    }
}
