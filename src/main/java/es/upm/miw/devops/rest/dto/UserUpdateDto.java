package es.upm.miw.devops.rest.dto;

public record UserUpdateDto(String firstName,
                            String familyName,
                            String email,
                            String identity,
                            String address,
                            String city,
                            String province,
                            String postalCode) {
}