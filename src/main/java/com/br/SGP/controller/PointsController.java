package com.br.SGP.controller;

import com.br.SGP.config.ApiVersion;
import com.br.SGP.dto.PointRequestDTO;
import com.br.SGP.dto.TimeDifferenceDTO;
import com.br.SGP.model.MatchDaySummary;
import com.br.SGP.model.Point;
import com.br.SGP.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiVersion.V1 + "/points")
public class PointsController {

    @Autowired
    private PointService pointService;

    // Registro de um novo ponto
    @PostMapping
    public ResponseEntity<Void> registerPoint(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody PointRequestDTO pointRequestDTO) {
        pointService.registerPoint(userDetails.getUsername(), pointRequestDTO);
        return ResponseEntity.noContent().build();
    }

    // Obter pontos do dia
    @GetMapping("/today")
    public List<Point> getPointsOfToday(@AuthenticationPrincipal UserDetails userDetails) {
        return pointService.getPointsOfToday(userDetails.getUsername());
    }

    // Resumo diario do usuario (nome, data, status da jornada, horas trabalhadas, horas restantes e horas extras)
    @GetMapping("/summary")
    public MatchDaySummary getDailySummary(@AuthenticationPrincipal UserDetails userDetails) {
        return pointService.getMatchDaySummary(userDetails.getUsername());
    }

    // Total de horas e minutos trabalhados no dia
    @GetMapping("/summary/total-hours")
    public TimeDifferenceDTO getTotalHours(@AuthenticationPrincipal UserDetails userDetails) {
        return pointService.calculateTotalHours(userDetails.getUsername());
    }

    // Tempo restante para o fim da jornada
    @GetMapping("/summary/time-remaining")
    public TimeDifferenceDTO getTimeRemaining(@AuthenticationPrincipal UserDetails userDetails) {
        return pointService.calculateTimeRemaining(userDetails.getUsername());
    }

    // Tempo extra trabalhado no dia
    @GetMapping("/summary/overtime")
    public TimeDifferenceDTO getExceededHours(@AuthenticationPrincipal UserDetails userDetails) {
        return pointService.calculateExceededHours(userDetails.getUsername());
    }

    // Status se a jornada foi completada ou não
    @GetMapping("/summary/is-journey-completed")
    public boolean isJourneyCompleted(@AuthenticationPrincipal UserDetails userDetails) {
        return pointService.fullJourney(userDetails.getUsername());
    }
}
