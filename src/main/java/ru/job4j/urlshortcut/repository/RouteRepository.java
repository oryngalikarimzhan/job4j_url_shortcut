package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Route;
import java.util.Optional;

public interface RouteRepository extends CrudRepository<Route, Integer> {
    Optional<Route> findByShortcutUrl(String shortcutUrl);
}
