package ru.job4j.urlshortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Route;
import ru.job4j.urlshortcut.domain.Website;
import ru.job4j.urlshortcut.repository.PersonRepository;
import ru.job4j.urlshortcut.repository.RouteRepository;
import ru.job4j.urlshortcut.repository.WebsiteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    private final PersonRepository users;
    private final RouteRepository routes;
    private final WebsiteRepository websites;

    public MainService(PersonRepository users, RouteRepository routes, WebsiteRepository websites) {
        this.users = users;
        this.routes = routes;
        this.websites = websites;
    }

    public boolean isExist(String hostname) {
        return websites.existsByHostName(hostname);
    }

    public void saveWebsite(Website website) {
        websites.save(website);
    }

    public void saveRoute(Route route) {
        routes.save(route);
    }

    public Website getWebsite(String hostname) {
        return websites.findByHostName(hostname)
                .orElseThrow(() -> new NullPointerException("not found"));
    }

    public Route getRoute(String shortcutUrl) {
        return routes.findByShortcutUrlAndIncrementCounter(shortcutUrl)
                .orElseThrow(() -> new NullPointerException("not found"));
    }

    public List<Route> getStatistic() {
        List<Route> allRoutes = new ArrayList<>();
        routes.findAll().forEach(allRoutes::add);
        return allRoutes;
    }
}
