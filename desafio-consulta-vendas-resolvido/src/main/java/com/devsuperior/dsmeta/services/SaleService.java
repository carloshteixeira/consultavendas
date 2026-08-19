package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    @Transactional(readOnly = true)
    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<SaleReportDTO> getReport(
            String minDate, String maxDate, String sellerName, Pageable pageable) {

        LocalDate[] dates = getMinAndMaxDates(minDate, maxDate);
        LocalDate startDate = dates[0];
        LocalDate endDate = dates[1];

        return repository.salesReport(startDate, endDate, sellerName, pageable)
                .map(SaleReportDTO::new);
    }

    @Transactional(readOnly = true)
    public List<SaleSummaryDTO> getSummary(String minDate, String maxDate) {

        LocalDate[] dates = getMinAndMaxDates(minDate, maxDate);
        LocalDate startDate = dates[0];
        LocalDate endDate = dates[1];

        return repository.salesSummary(startDate, endDate)
                .stream()
                .map(SaleSummaryDTO::new)
                .toList();
    }

    private LocalDate[] getMinAndMaxDates(String minDate, String maxDate) {

        LocalDate startDate;
        LocalDate endDate;

        if (!maxDate.isEmpty()) {
            endDate = LocalDate.parse(maxDate);
        } else {
            endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }

        if (!minDate.isEmpty()) {
            startDate = LocalDate.parse(minDate);
        } else {
            startDate = endDate.minusYears(1L);
        }

        return new LocalDate[]{startDate, endDate};
    }
}
