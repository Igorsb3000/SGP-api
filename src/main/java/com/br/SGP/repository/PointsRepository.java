package com.br.SGP.repository;

import com.br.SGP.base.BaseRepository;
import com.br.SGP.model.Credentials;
import com.br.SGP.model.Point;
import org.springframework.data.jdbc.repository.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PointsRepository extends BaseRepository<Point> {
    //@Query("select p from Point p where p.usuario.id=:usuarioId and p.dateHour between :start and :end")
    //List<Point> findPointByUsuarioAndDAndDateHourBetween(String id, LocalDateTime start, LocalDateTime end);
    @Query("SELECT * FROM point p WHERE p.id_user =:usuarioId AND p.date_hour BETWEEN start AND end")
    List<Point> findPointByUsuarioIdAndDateHourBetween(String usuarioId, LocalDateTime start, LocalDateTime end);

}
