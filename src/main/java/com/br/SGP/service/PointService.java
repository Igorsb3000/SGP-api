package com.br.SGP.service;

import com.br.SGP.base.BaseService;
import com.br.SGP.dto.PointRequestDTO;
import com.br.SGP.dto.TimeDifferenceDTO;
import com.br.SGP.model.*;
import com.br.SGP.repository.CredentialsRepository;
import com.br.SGP.repository.PointsRepository;
import com.br.SGP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import com.br.SGP.utils.TimeUtils;

@Service
public class PointService extends BaseService<Point, PointsRepository> {
    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UserRepository userRepository;


    // Busca as credenciais e dados do usuario atraves do username
    private Optional<User> searchUserByUsername(String username){
        Optional<User> user = Optional.empty();
        Optional<Credentials> credentials;

        credentials = credentialsRepository.findCredentialsByUsername(username);

        if(credentials.isPresent()){
            user = userRepository.findById(credentials.get().getId());
        }
        return user;
    }

    // Retorna o tempo total trabalhado por um usuario atraves da lista de pontos do dia
    private Duration getTotalWorked(List<Point> pointList) {
        Duration totalWorked = Duration.ZERO;

        for (int i = 0; i < pointList.size(); i += 2) {
            if (i + 1 < pointList.size()) { // Par de entrada/saida completo
                LocalDateTime start = pointList.get(i).getDateHour();
                LocalDateTime end = pointList.get(i + 1).getDateHour();
                totalWorked = totalWorked.plus(Duration.between(start, end));
            } else { // Ultima entrada sem saida
                LocalDateTime start = pointList.get(i).getDateHour();
                LocalDateTime now = LocalDateTime.now(); // Horario atual
                totalWorked = totalWorked.plus(Duration.between(start, now));
            }
        }
        return totalWorked;
    }

    // Salva um novo registro de ponto
    public void registerPoint(String username, PointRequestDTO pointRequestDTO){
        Point point = new Point();
        Optional<User> user;
        List<Point> listPoints;

        user = this.searchUserByUsername(username);

        if(user.isPresent()){
            listPoints = getUserPointsOfToday(user.get().getId());
            // Logica para saber se é ponto de ENTRADA ou SAIDA
            if(listPoints.isEmpty()){
                point.setPointType(PointType.ENTRADA);
            } else {
                // Par
                if(listPoints.size() % 2 == 0){
                    point.setPointType(PointType.ENTRADA);
                } else {
                    point.setPointType(PointType.SAIDA);
                }
            }
            point.setUsuario(user.get());
            point.setDateHour(pointRequestDTO.getDateHour());
            pointsRepository.save(point);
        }
    }


    // Retorna todos os pontos do dia de um usuario dado um username
    public List<Point> getPointsOfToday(String username){
        Optional<User> user;

        user = this.searchUserByUsername(username);
        return user.map(value -> getUserPointsOfToday(value.getId())).orElse(null);
    }

    // Retorna todos os pontos do dia de um usuario dado um id de usuario
    private List<Point> getUserPointsOfToday(String usuarioId) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay(); // Ex: 2024-11-29T00:00
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1); // Ex: 2024-11-29T23:59:59
        return pointsRepository.findPointByUsuarioIdAndDateHourBetween(usuarioId, startOfDay, endOfDay);
    }

    // Retorna o total de tempo trabalhado do dia de um usuario dado o username
    public TimeDifferenceDTO calculateTotalHours(String username){
        Optional<User> user;
        List<Point> pointListToday;
        TimeDifferenceDTO timeDifferenceDTO = new TimeDifferenceDTO(0, 0);
        Duration totalWorked;
        long hours, minutes;

        user = this.searchUserByUsername(username);
        if(user.isPresent()){
            pointListToday = getUserPointsOfToday(user.get().getId());

            if(!pointListToday.isEmpty()){
                totalWorked = getTotalWorked(pointListToday);

                hours = totalWorked.toHours();
                minutes = totalWorked.toMinutes() % 60;

                timeDifferenceDTO.setHours(hours);
                timeDifferenceDTO.setMinutes(minutes);
            }
        }
        return timeDifferenceDTO;
    }

    // Retorna true caso o usuario tenha completado sua jornada de trabalho e false caso contrario
    public boolean fullJourney(String username){
        Optional<User> user;
        TimeDifferenceDTO timeDifferenceDTO;
        WorkingHours workingHoursUser = null;

        timeDifferenceDTO = this.calculateTotalHours(username);

        user = this.searchUserByUsername(username);
        if(user.isPresent()){
            workingHoursUser = user.get().getWorkingHours();
        }

        // true = Jornada completa
        // false = Jornada incompleta
        if(workingHoursUser != null){
            return timeDifferenceDTO.getHours() >= workingHoursUser.getHours();
        }
        return false;
    }

    // Retorna o tempo restante para o usuario cumprir o seu expediente
    public TimeDifferenceDTO calculateTimeRemaining(String username) {
        Duration totalWorked = Duration.ZERO;
        Optional<User> user;
        List<Point> pointList;
        TimeDifferenceDTO timeDifferenceDTO = new TimeDifferenceDTO();
        long hoursJourney = 0;

        user = this.searchUserByUsername(username);

        if (user.isPresent()) {
            pointList = this.getUserPointsOfToday(user.get().getId());
            hoursJourney = user.get().getWorkingHours().getHours();

            if (!pointList.isEmpty()) {
                totalWorked = getTotalWorked(pointList);
            }
        }
        Duration standardJourney = Duration.ofHours(hoursJourney);
        Duration remainder = standardJourney.minus(totalWorked);

        long hours = remainder.isNegative() ? 0 : remainder.toHours();
        long minutes = remainder.isNegative() ? 0 : remainder.toMinutes() % 60;

        timeDifferenceDTO.setHours(hours);
        timeDifferenceDTO.setMinutes(minutes);

        return timeDifferenceDTO;
    }

    // Retorna o tempo de trabalho extra de um usuario
    public TimeDifferenceDTO calculateExceededHours(String username){
        Duration totalWorked = Duration.ZERO;
        Optional<User> user;
        List<Point> pointList;
        TimeDifferenceDTO timeDifferenceDTO = new TimeDifferenceDTO();
        long hoursJourney = 0;

        user = this.searchUserByUsername(username);

        if (user.isPresent()) {
            pointList = this.getUserPointsOfToday(user.get().getId());
            hoursJourney = user.get().getWorkingHours().getHours();

            if (!pointList.isEmpty()) {
                totalWorked = getTotalWorked(pointList);
            }
        }
        Duration standardJourney = Duration.ofHours(hoursJourney);
        Duration exceeded = totalWorked.minus(standardJourney);

        long hours = exceeded.isNegative() ? 0 : exceeded.toHours();
        long minutes = exceeded.isNegative() ? 0 : exceeded.toMinutes() % 60;

        timeDifferenceDTO.setHours(hours);
        timeDifferenceDTO.setMinutes(minutes);

        return timeDifferenceDTO;
    }

    // Retorna um resumo de jornada do dia atual, contem:
    // - Nome do usuario
    // - Data do dia
    // - Informa se o usuario cumpriu sua jornada de trabalho
    // - Informa o tempo total trabalhado no dia
    // - Informa o tempo restante para o fim da jornada de trabalho
    // - Informa o tempo de trabalho extra
    public MatchDaySummary getMatchDaySummary(String username) {
        Optional<User> user = this.searchUserByUsername(username);

        if (user.isPresent()) {
            String name = user.get().getName();
            LocalDate today = LocalDate.now();

            // Formatar para dd-MM-aaaa
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = today.format(formatter);

            // Calcula as informacoes do resumo do dia
            Duration totalWorked = this.getTotalWorked(this.getUserPointsOfToday(user.get().getId()));
            Duration hoursRemaining = Duration.ofHours(user.get().getWorkingHours().getHours()).minus(totalWorked);
            Duration overtime = totalWorked.minus(Duration.ofHours(user.get().getWorkingHours().getHours()));

            // Retorna o objeto MatchDaySummary
            return new MatchDaySummary(
                    name,
                    formattedDate,
                    this.fullJourney(username),
                    TimeUtils.convertDurationToTime(totalWorked),
                    TimeUtils.convertDurationToTime(hoursRemaining.isNegative() ? Duration.ZERO : hoursRemaining),
                    TimeUtils.convertDurationToTime(overtime.isNegative() ? Duration.ZERO : overtime)
            );
        }
        throw new IllegalArgumentException("Usuário não encontrado");
    }



}