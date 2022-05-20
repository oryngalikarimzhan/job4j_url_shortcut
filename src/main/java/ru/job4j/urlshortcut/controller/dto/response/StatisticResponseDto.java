package ru.job4j.urlshortcut.controller.dto.response;

public class StatisticResponseDto {
    private String url;
    private int total;

    public static StatisticResponseDto build(String url, int total) {
        StatisticResponseDto responseDto = new StatisticResponseDto();
        responseDto.url = url;
        responseDto.total = total;
        return responseDto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
