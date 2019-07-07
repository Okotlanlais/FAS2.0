package com.fas.fotomania.fotomania.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name="auth_users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message="Name must be complete")
    private String name;

    @NotEmpty(message = "Email must be complete")
    @Email(message="Email format not valid")
    private String email;

    @Length(min = 5, message = "Password must have at least 5 characters")
    @NotEmpty(message = "Password must be complete")
    private String password;

    private String status;

    private String company;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="auth_user_role", joinColumns = @JoinColumn(name="auth_user_id"),inverseJoinColumns = @JoinColumn(name="auth_role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Tool> tools;

    @OneToMany(mappedBy = "user")
    private Set<Specialty> specialties;

    @OneToMany(mappedBy = "user")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "user")
    private Set<Offer> offers;

    @OneToOne(mappedBy = "user")
    private Schedule schedule;

    @OneToMany(mappedBy = "client")
    private Set<Reservation> reservationsClient;

    @OneToMany(mappedBy = "company")
    private Set<Reservation> getReservationsCompany;

    @OneToOne(mappedBy = "user")
    private CompanyInfo companyInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Reservation> getReservationsClient() {
        return reservationsClient;
    }

    public void setReservationsClient(Set<Reservation> reservationsClient) {
        this.reservationsClient = reservationsClient;
    }

    public Set<Reservation> getGetReservationsCompany() {
        return getReservationsCompany;
    }

    public void setGetReservationsCompany(Set<Reservation> getReservationsCompany) {
        this.getReservationsCompany = getReservationsCompany;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }
}
