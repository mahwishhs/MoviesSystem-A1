package com.example.moviessystema1.domain;

public class Director {
    private int directorId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String nationality;

    public Director(int directorId, String firstName, String lastName, String dateOfBirth, String nationality) {
        this.directorId = directorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public int getDirectorId() {
        return directorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
