package uet.soa.pastebin.infrastructure.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.soa.pastebin.application.dto.PasteTimeSeriesResponse;
import uet.soa.pastebin.application.usecase.AnalyticsUseCase;
import uet.soa.pastebin.infrastructure.service.UserPasteService;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AnalyticsController {
    private final AnalyticsUseCase analyticsUseCase;
    private final UserPasteService userPasteService;

    @GetMapping("/hourly/{pasteUrl}")
    public ResponseEntity<PasteTimeSeriesResponse> getHourlyStatistics(@PathVariable String pasteUrl) {
        PasteTimeSeriesResponse response = analyticsUseCase.getHourlyStatistics(pasteUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/weekly/{pasteUrl}")
    public ResponseEntity<PasteTimeSeriesResponse> getWeeklyStatistics(@PathVariable String pasteUrl) {
        PasteTimeSeriesResponse response = analyticsUseCase.getWeeklyStatistics(pasteUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly/{pasteUrl}")
    public ResponseEntity<PasteTimeSeriesResponse> getMonthlyStatistics(@PathVariable String pasteUrl) {
        PasteTimeSeriesResponse response = analyticsUseCase.getMonthlyStatistics(pasteUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/total-view")
    public ResponseEntity<Long> getTotalViewsByUserId(@PathVariable String userId) {
        Long totalViews = userPasteService.getTotalViewsByUserId(userId);
        return ResponseEntity.ok(totalViews);
    }

    @GetMapping("/{userId}/total-paste")
    public ResponseEntity<Long> getTotalPasteByUserId(@PathVariable String userId) {
        Long totalViews = userPasteService.getTotalPasteByUserId(userId);
        return ResponseEntity.ok(totalViews);
    }

}