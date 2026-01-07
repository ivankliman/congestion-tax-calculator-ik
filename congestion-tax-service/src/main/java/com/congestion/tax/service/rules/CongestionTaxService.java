package com.congestion.tax.service.rules;

import com.congestion.tax.exception.IdMissingException;
import com.congestion.tax.exception.NotFoundException;
import com.congestion.tax.mapper.rules.CongestionTaxMapper;
import com.congestion.tax.model.rules.CongestionTax;
import com.congestion.tax.repository.rules.CongestionTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CongestionTaxService {
    private final CongestionTaxRepository congestionTaxRepository;
    private final CongestionTaxMapper congestionTaxMapper;

    public CongestionTax createCongestionTax(CongestionTax congestionTax) {
        final var recordToSave = congestionTaxMapper.toEntity(congestionTax);
        final var savedRecord = congestionTaxRepository.save(recordToSave);

        return congestionTaxMapper.toDto(savedRecord);
    }

    public CongestionTax updateCongestionTax(CongestionTax congestionTax) {
        //field validation -- potentially move to javax/jakarta validation
        if (Objects.isNull(congestionTax.id())) {
            throw new IdMissingException("Id is required for the edit request");
        }
        final var recordToUpdate = congestionTaxRepository.findById(congestionTax.id());
        if(recordToUpdate.isEmpty()){
            throw new NotFoundException("No record found with id " + congestionTax.id());
        }

        final var recordToSave = congestionTaxMapper.toEntity(congestionTax);
        final var savedRecord = congestionTaxRepository.save(recordToSave);

        return congestionTaxMapper.toDto(savedRecord);
    }

    public CongestionTax getById(Long id) {
        final var requestedRecord = congestionTaxRepository.findById(id);
        if(requestedRecord.isEmpty()){
            throw new NotFoundException("No record found with id " + id);
        }

        return congestionTaxMapper.toDto(requestedRecord.get());
    }

    public Page<CongestionTax> findCongestionTaxRecords(Pageable pageable) {
        return congestionTaxRepository
                .findAll(pageable).map(congestionTaxMapper::toDto);
    }

    public void deleteById(Long id) {
        congestionTaxRepository.deleteById(id);
    }
}
