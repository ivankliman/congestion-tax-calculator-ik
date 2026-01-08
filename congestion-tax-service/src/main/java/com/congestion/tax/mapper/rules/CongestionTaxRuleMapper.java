package com.congestion.tax.mapper.rules;

import com.congestion.tax.entity.rules.CongestionTaxRuleEntity;
import com.congestion.tax.mapper.AbstractMapper;
import com.congestion.tax.mapper.CityMapper;
import com.congestion.tax.model.rules.CongestionTaxRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  uses = {CityMapper.class})
public interface CongestionTaxRuleMapper extends AbstractMapper<CongestionTaxRule, CongestionTaxRuleEntity> {
}
