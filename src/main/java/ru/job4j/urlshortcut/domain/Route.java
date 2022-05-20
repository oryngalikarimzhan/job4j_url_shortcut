package ru.job4j.urlshortcut.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "routes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"url", "shortcut_url"}),
        @UniqueConstraint(columnNames = "shortcut_url"),
})
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "url")
    private String url;
    @Column(name = "shortcut_url")
    private String shortcutUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "website_id")
    private Website website;
    @Column(name = "request_counter")
    private int requestCounter;

    public static Route build(String url, String shortcutUrl, Website website) {
        Route route = new Route();
        route.url = url;
        route.shortcutUrl = shortcutUrl;
        route.website = website;
        return route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortcutUrl() {
        return shortcutUrl;
    }

    public void setShortcutUrl(String shortcutUrl) {
        this.shortcutUrl = shortcutUrl;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public int getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(int requestCounter) {
        this.requestCounter = requestCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route routeUrl = (Route) o;
        return id == routeUrl.id
                && Objects.equals(url, routeUrl.url)
                && Objects.equals(shortcutUrl, routeUrl.shortcutUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, shortcutUrl);
    }
}
