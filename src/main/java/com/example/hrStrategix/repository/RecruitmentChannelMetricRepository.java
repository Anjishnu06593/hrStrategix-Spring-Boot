package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.RecruitmentChannelMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecruitmentChannelMetricRepository extends JpaRepository<RecruitmentChannelMetric, Long> {
    @Query("SELECT rcm FROM RecruitmentChannelMetric rcm WHERE rcm.recruitmentChannel.id = :channelId")
    List<RecruitmentChannelMetric> findByChannelId(@Param("channelId") Long channelId);
    
    @Query("SELECT rcm FROM RecruitmentChannelMetric rcm WHERE rcm.metricDate BETWEEN :startDate AND :endDate")
    List<RecruitmentChannelMetric> findByMetricDateBetween(@Param("startDate") LocalDate startDate, 
                                                          @Param("endDate") LocalDate endDate);
}