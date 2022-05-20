package ru.job4j.urlshortcut.controller.dto.response;

public class ConvertResponseDto {
    private String url;

    public static ConvertResponseDto build(String url) {
        ConvertResponseDto convertResponseDto = new ConvertResponseDto();
        convertResponseDto.url = url;
        return convertResponseDto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
