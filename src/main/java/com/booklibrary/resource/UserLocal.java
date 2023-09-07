package com.booklibrary.resource;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class UserLocal implements Principal {

    private String id;
    private List<String> permissions;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getName() {
        return id;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
