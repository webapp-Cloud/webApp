package com.rio_rishabhNEU.quizApp;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulationConfig {
    private static boolean simulateDbDisconnection = true;

    public static boolean isSimulateDbDisconnection() {
        return simulateDbDisconnection;
    }

    public static void setSimulateDbDisconnection(boolean simulate) {
        simulateDbDisconnection = simulate;
    }
}