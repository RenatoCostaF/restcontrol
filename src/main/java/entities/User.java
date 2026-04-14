package entities;

import java.util.Date;

public class User {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public class Person {
        private Long id;
        private String name;
        private String email;
        private String login;
        private String password;
        private Date lastModifiedDate;
        private String address;
        private String type;
    }
}
