package ru.job4j.urlshortcut.controller.dto.response;

public class RegistrationResponseDto {
    private boolean registration;
    private String username;
    private String password;

    public static RegistrationResponseDto build(boolean registration, String login, String password) {
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto();
        registrationResponseDto.registration = registration;
        registrationResponseDto.username = login;
        registrationResponseDto.password = password;
        return registrationResponseDto;
    }

    public boolean isRegistration() {
        return registration;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
