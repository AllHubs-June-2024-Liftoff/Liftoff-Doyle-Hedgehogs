package org.launchcode.demo.models.dto;

public class UserEmailDTO {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

//data transfer object encapsulates data and sends from one subsystem of an application to another
//helpful when data is being transferred from user interface via form
