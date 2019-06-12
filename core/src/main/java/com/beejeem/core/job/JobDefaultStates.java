package com.beejeem.core.job;

import java.util.UUID;

public class JobDefaultStates {

    public final static UUID STOP_STATE = UUID.randomUUID();
    public final static UUID ERROR_STATE = UUID.randomUUID();
    public final static UUID FINISH_STATE = UUID.randomUUID();
    public final static UUID READY_STATE = UUID.randomUUID();

    public JobDefaultStates() {}
}
