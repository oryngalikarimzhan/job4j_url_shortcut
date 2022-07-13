package ru.job4j.urlshortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.controller.tools.SimpleGenerator;
import ru.job4j.urlshortcut.domain.Person;
import ru.job4j.urlshortcut.domain.Route;
import ru.job4j.urlshortcut.domain.Website;
import ru.job4j.urlshortcut.controller.dto.request.ConvertRequestDto;
import ru.job4j.urlshortcut.controller.dto.request.RegistrationRequestDto;
import ru.job4j.urlshortcut.controller.dto.response.ConvertResponseDto;
import ru.job4j.urlshortcut.controller.dto.response.RegistrationResponseDto;
import ru.job4j.urlshortcut.controller.dto.response.StatisticResponseDto;
import ru.job4j.urlshortcut.repository.WebsiteRepository;
import ru.job4j.urlshortcut.service.MainService;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WebsitesController {
    private final MainService service;
    private final BCryptPasswordEncoder encoder;

    public WebsitesController(MainService service, WebsiteRepository websites, BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody RegistrationRequestDto site) {
        RegistrationResponseDto regResponseDto = null;
        String hostname = site.getSite().replace("www", "");
        if (!service.isExist(hostname)) {
            String username = SimpleGenerator.generate();
            String password = SimpleGenerator.generate();
            Person person = Person.build(username, encoder.encode(password));
            Website website = Website.build(hostname);
            website.setPerson(person);
            service.saveWebsite(website);
            regResponseDto = RegistrationResponseDto.build(true, username, password);
        } else {
            regResponseDto = RegistrationResponseDto.build(false, "", "");
        }
        return new ResponseEntity<RegistrationResponseDto>(
                regResponseDto,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/convert")
    public ResponseEntity<ConvertResponseDto> convert(@RequestBody ConvertRequestDto requestDto)
            throws MalformedURLException {
        ConvertResponseDto responseDto = null;
        URL url = new URL(requestDto.getUrl());
        Website website = service.getWebsite(url.getHost().replace("www", ""));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (website != null && website.getPerson().getUsername().equals(username)) {
            String path = url.getPath();
            if (path.length() == 0) {
                throw new IllegalArgumentException("url that you provided does not have path component");
            }
            String shortcutUrl = SimpleGenerator.generate();
            Route route = Route.build(url.toString(), shortcutUrl, website);;
            service.saveRoute(route);
            responseDto = ConvertResponseDto.build(shortcutUrl);
        } else {
            responseDto = ConvertResponseDto.build("Hostname does not exist or registered by another user");
        }
        return new ResponseEntity<ConvertResponseDto>(
                responseDto,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/redirect/{shortcutUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortcutUrl) throws MalformedURLException {
        Route route = service.getRoute(shortcutUrl);
        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY)
                .location(URI.create(route.getUrl()))
                .build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticResponseDto>> getStatistic() {
        List<StatisticResponseDto> statistic = new ArrayList<>();
        service.getStatistic()
                .forEach(
                        route -> statistic.add(
                                StatisticResponseDto.build(
                                        route.getUrl(),
                                        route.getRequestCounter()
                                )
                        )
                );
        return new ResponseEntity<>(statistic, HttpStatus.OK);
    }
}
