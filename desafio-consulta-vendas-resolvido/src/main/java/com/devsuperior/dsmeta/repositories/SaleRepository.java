package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT
            obj.id AS id,
            obj.date AS date,
            obj.amount AS amount,
            obj.seller.name AS sellerName
        FROM Sale obj
        WHERE obj.date BETWEEN :minDate AND :maxDate
          AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :sellerName, '%'))
        ORDER BY obj.date DESC, obj.id ASC
        """)
    Page<SaleReportProjection> salesReport(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate,
            @Param("sellerName") String sellerName,
            Pageable pageable);

    @Query("""
        SELECT
            obj.seller.name AS sellerName,
            SUM(obj.amount) AS total
        FROM Sale obj
        WHERE obj.date BETWEEN :minDate AND :maxDate
        GROUP BY obj.seller.name
        ORDER BY obj.seller.name
        """)
    List<SaleSummaryProjection> salesSummary(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate);
}
