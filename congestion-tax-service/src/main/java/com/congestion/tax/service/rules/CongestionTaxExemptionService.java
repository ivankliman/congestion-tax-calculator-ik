package com.congestion.tax.service.rules;

import com.congestion.tax.exception.IdMissingException;
import com.congestion.tax.exception.NotFoundException;
import com.congestion.tax.mapper.rules.CongestionTaxExemptionMapper;
import com.congestion.tax.model.rules.CongestionTaxExemption;
import com.congestion.tax.repository.rules.CongestionTaxExemptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CongestionTaxExemptionService {
    private final CongestionTaxExemptionRepository congestionTaxExemptionRepository;
    private final CongestionTaxExemptionMapper congestionTaxExemptionMapper;

    public CongestionTaxExemption createCongestionTaxExemption(CongestionTaxExemption congestionTaxExemption) {
        final var recordToSave = congestionTaxExemptionMapper.toEntity(congestionTaxExemption);
        final var savedRecord = congestionTaxExemptionRepository.save(recordToSave);

        return congestionTaxExemptionMapper.toDto(savedRecord);
    }

    public CongestionTaxExemption updateCongestionTaxExemption(CongestionTaxExemption congestionTaxExemption) {
        //field validation -- potentially move to javax/jakarta validation
        if (Objects.isNull(congestionTaxExemption.id())) {
            throw new IdMissingException("Id is required for the edit request");
        }
        final var recordToUpdate = congestionTaxExemptionRepository.findById(congestionTaxExemption.id());
        if(recordToUpdate.isEmpty()){
            throw new NotFoundException("No record found with id " + congestionTaxExemption.id());
        }

        final var recordToSave = congestionTaxExemptionMapper.toEntity(congestionTaxExemption);
        final var savedRecord = congestionTaxExemptionRepository.save(recordToSave);

        return congestionTaxExemptionMapper.toDto(savedRecord);
    }

    public CongestionTaxExemption getById(Long id) {
        final var requestedRecord = congestionTaxExemptionRepository.findById(id);
        if(requestedRecord.isEmpty()){
            throw new NotFoundException("No record found with id " + id);
        }

        return congestionTaxExemptionMapper.toDto(requestedRecord.get());
    }

    public Page<CongestionTaxExemption> findCongestionTaxExemptionRecords(Pageable pageable) {
        return congestionTaxExemptionRepository
                .findAll(pageable).map(congestionTaxExemptionMapper::toDto);
    }

    public void deleteById(Long id) {
        congestionTaxExemptionRepository.deleteById(id);
    }
}
