package com.beejeem.core.ssh;

public interface SshSession {
    /**
     * Connect to host
     * @param userInformation provides hostname, username and password
     */
    public void connect(UserInformation userInformation);

    /**
     * Disconnect from host
     */
    public void disconnect();

    /**
     * Evaluate whether the session is connected to host
     * @return true if the the session is open; otherwise false
     */
    public boolean isConnected();

    /**
     * Evaluate whether the user has been authenticated
     * @return
     */
    public boolean isAuthenticated();
    
}
