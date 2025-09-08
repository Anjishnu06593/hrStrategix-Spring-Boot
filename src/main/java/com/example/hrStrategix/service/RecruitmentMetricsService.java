package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CreateRecruitmentMetricsRequest;
import com.example.hrStrategix.dto.RecruitmentMetricsResponse;
import com.example.hrStrategix.entity.RecruitmentChannelMetric;
import com.example.hrStrategix.entity.RecruitmentSource;
import com.example.hrStrategix.repository.RecruitmentChannelMetricRepository;
import com.example.hrStrategix.repository.RecruitmentSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentMetricsService {
    private final RecruitmentChannelMetricRepository metricsRepository;
    private final RecruitmentSourceRepository recruitmentSourceRepository;

    public List<RecruitmentMetricsResponse> getAllMetrics() {
        return metricsRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RecruitmentMetricsResponse getMetricsById(Long id) {
        RecruitmentChannelMetric metric = metricsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with id: " + id));
        return mapToResponse(metric);
    }

    public List<RecruitmentMetricsResponse> getMetricsByChannel(Long channelId) {
        return metricsRepository.findByChannelId(channelId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RecruitmentMetricsResponse> getMetricsByDateRange(LocalDate startDate, LocalDate endDate) {
        return metricsRepository.findByMetricDateBetween(startDate, endDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecruitmentMetricsResponse createMetrics(CreateRecruitmentMetricsRequest request) {
        RecruitmentSource channel = recruitmentSourceRepository.findById(request.getRecruitmentChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Recruitment channel not found"));

        RecruitmentChannelMetric metric = RecruitmentChannelMetric.builder()
                .recruitmentChannel(channel)
                .metricDate(request.getMetricDate())
                .costSpent(request.getCostSpent())
                .hiresCount(request.getHiresCount())
                .avgTimeToHireDays(request.getAvgTimeToHireDays())
                .metadata(request.getMetadata())
                .createdAt(OffsetDateTime.now())
                .build();

        RecruitmentChannelMetric savedMetric = metricsRepository.save(metric);
        return mapToResponse(savedMetric);
    }

    @Transactional
    public void deleteMetrics(Long id) {
        if (!metricsRepository.existsById(id)) {
            throw new IllegalArgumentException("Metrics not found with id: " + id);
        }
        metricsRepository.deleteById(id);
    }

    private RecruitmentMetricsResponse mapToResponse(RecruitmentChannelMetric metric) {
        return RecruitmentMetricsResponse.builder()
                .id(metric.getId())
                .channelName(metric.getRecruitmentChannel().getName())
                .metricDate(metric.getMetricDate())
                .costSpent(metric.getCostSpent())
                .hiresCount(metric.getHiresCount())
                .avgTimeToHireDays(metric.getAvgTimeToHireDays())
                .metadata(metric.getMetadata())
                .build();
    }
}