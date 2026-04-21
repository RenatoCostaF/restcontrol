package com.restocontrol.restcontrol_api.infra.exceptions;

public class UsersNotFoundByNameException extends RuntimeException {
    private final String searchedName;

    public UsersNotFoundByNameException(String searchedName) {
        super("No users found with the given name");
        this.searchedName = searchedName;
    }

    public String getSearchedName() {
      return searchedName;
    }
}
