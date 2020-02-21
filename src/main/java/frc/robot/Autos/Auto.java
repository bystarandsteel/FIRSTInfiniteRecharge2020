package frc.robot.Autos;

import frc.robot.procedures.Procedure;

public class Auto {

    private Procedure[] procedures;
    private double[] params;

    public Auto(Procedure[] procedures, double[] params) {
        if (procedures.length != params.length) {
            throw new IllegalArgumentException();
        }

        this.procedures = procedures;
        this.params = params;
    }

    public Procedure[] getProcedures() {
        return procedures;
    }

    public double[] getParams() {
        return params;
    }
}