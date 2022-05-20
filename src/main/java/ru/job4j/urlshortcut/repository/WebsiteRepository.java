package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Website;

import java.util.Optional;

public interface WebsiteRepository extends CrudRepository<Website, Integer> {
    Boolean existsByHostName(String hostname);
    Optional<Website> findByHostName(String hostname);
}
