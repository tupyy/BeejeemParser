package com.beejeem.core.ssh;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class UserInformation {

    private String username;
    private String hostname;
    private String password;


    public UserInformation(String hostname, String username, String password) {
        checkNotNull(hostname, "Hostname cannot be null.");
        checkNotNull(username, "User cannot be null.");
        checkNotNull(password, "Password cannot be null.");

        checkArgument(hostname.isEmpty(), "Hostname cannot be empty.");
        checkArgument(username.isEmpty(), "Username cannot be empty");
        checkArgument(password.isEmpty(), "Password cannot be empty");

        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostname() {
        return hostname;
    }

}
