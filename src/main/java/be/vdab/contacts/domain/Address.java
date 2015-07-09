package be.vdab.contacts.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Hyuberuto on 09/07/15.
 */


public class Address implements Serializable{

    @NotBlank
    @Size(min = 1, max = 15)
    private String city;

    public Address(String city) {
        this.city = city;
    }

    public Address() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
