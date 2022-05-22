package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Route;
import java.util.Optional;

public interface RouteRepository extends CrudRepository<Route, Integer> {
    @Query(value = "select * from  select_route_and_increment_counter_func(?1)", nativeQuery = true)
    Optional<Route> findByShortcutUrlAndIncrementCounter(String shortcutUrl);
}
