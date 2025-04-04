package frc.robot.Limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
  private final NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
  final static double deadband = 0.05;

  public enum Value {
    TX("tx"),
    TA("ta");

    private final String value;

    private Value(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  public double getPosition(Value value) {
    return limelightTable.getEntry(value.toString().toLowerCase()).getDouble(0.0);
  }
}
