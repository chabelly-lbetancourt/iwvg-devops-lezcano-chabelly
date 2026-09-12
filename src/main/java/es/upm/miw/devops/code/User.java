package es.upm.miw.devops.code;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(name = "email")
    private String email;

    @Column(name = "identity")
    private String identity;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "active")
    private boolean active;

    @Transient
    private List<Fraction> fractions;

    public User() {
        this.active = true;
        this.fractions = new ArrayList<>();
    }

    public User(String id, String firstName, String familyName, List<Fraction> fractions) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.active = true;
        this.fractions = fractions;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public void addFraction(Fraction fraction) {
        this.fractions.add(fraction);
    }

    public boolean isBillable() {
        return isNotBlank(firstName)
                && isNotBlank(familyName)
                && isNotBlank(email)
                && isNotBlank(identity)
                && isNotBlank(address)
                && isNotBlank(city)
                && isNotBlank(province)
                && isNotBlank(postalCode);
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    public String fullName() {
        return this.firstName + " " + this.familyName;
    }

    public String initials() {
        return this.firstName.charAt(0) + ".";
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}