package com.congestion.tax.service.rules;

import com.congestion.tax.exception.IdMissingException;
import com.congestion.tax.exception.NotFoundException;
import com.congestion.tax.mapper.rules.CongestionTaxRuleMapper;
import com.congestion.tax.model.rules.CongestionTaxRule;
import com.congestion.tax.repository.rules.CongestionTaxRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CongestionTaxRuleService {
    private final CongestionTaxRuleRepository congestionTaxRuleRepository;
    private final CongestionTaxRuleMapper congestionTaxRuleMapper;

    public CongestionTaxRule createCongestionTaxRule(CongestionTaxRule congestionTaxRule) {
        final var recordToSave = congestionTaxRuleMapper.toEntity(congestionTaxRule);
        final var savedRecord = congestionTaxRuleRepository.save(recordToSave);

        return congestionTaxRuleMapper.toDto(savedRecord);
    }

    public CongestionTaxRule updateCongestionTaxRule(CongestionTaxRule congestionTaxRule) {
        //field validation -- potentially move to javax/jakarta validation
        if (Objects.isNull(congestionTaxRule.id())) {
            throw new IdMissingException("Id is required for the edit request");
        }
        congestionTaxRuleRepository
                .findById(congestionTaxRule.id())
                .orElseThrow(()->new NotFoundException("No record found with id " + congestionTaxRule.id()));

        final var recordToSave = congestionTaxRuleMapper.toEntity(congestionTaxRule);
        final var savedRecord = congestionTaxRuleRepository.save(recordToSave);

        return congestionTaxRuleMapper.toDto(savedRecord);
    }

    public CongestionTaxRule getById(Long id) {
        final var requestedRecord = congestionTaxRuleRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException("No record found with id " + id));

        return congestionTaxRuleMapper.toDto(requestedRecord);
    }

    public Page<CongestionTaxRule> findCongestionTaxRules(Pageable pageable) {
        return congestionTaxRuleRepository
                .findAll(pageable).map(congestionTaxRuleMapper::toDto);
    }

    public void deleteById(Long id) {
        congestionTaxRuleRepository.deleteById(id);
    }
}
