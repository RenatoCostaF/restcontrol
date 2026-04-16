package br.com.fiap.tech_challege.tech_challenge.entities;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    private String name;
    private String email;
    private String password;
    /*private Date lastModifiedDate;*/
    private String address;
    private String type;

}