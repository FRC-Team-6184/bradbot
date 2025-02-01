package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private final XboxController driverXboxController = new XboxController(0);
  private final TalonSRX backRight = new TalonSRX(4);
  private final TalonSRX frontRight = new TalonSRX(3);
  private final TalonSRX frontLeft = new TalonSRX(2);
  private final TalonSRX backLeft = new TalonSRX(1);

  final static double TURBO_SPEED = 1;
  final static double REGULAR_SPEED = 0.75;
  final static double TURTLE_SPEED = 0.5;
  double speedMultiplier = REGULAR_SPEED;

  NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
  double limelightX = 0.0;

  public Robot() {
  }

  @Override
  public void robotPeriodic() {
    limelightX = limelightTable.getEntry("tx").getDouble(0.0);
    SmartDashboard.putNumber("LimeLight X", limelightX);

  }

  @Override
  public void autonomousInit() {
  }

  double deadband = 5.0;


  @Override
  public void autonomousPeriodic() {
    if(!(limelightX < deadband && limelightX > -deadband)) { //If the apriltag is outside the deadband zone rotate
      if(limelightX < 0) { //value is negative, rotate right
        backRight.set(TalonSRXControlMode.PercentOutput, -0.4);
        backLeft.set(TalonSRXControlMode.PercentOutput, -0.4);

      } else { //value is positive, rotate left
        backRight.set(TalonSRXControlMode.PercentOutput, 0.4);
        backLeft.set(TalonSRXControlMode.PercentOutput, 0.4);
      }
    } else {
      backRight.set(TalonSRXControlMode.PercentOutput, 0.0);
        backLeft.set(TalonSRXControlMode.PercentOutput, 0.0);
    }

  }

  @Override
  public void teleopInit() {
    frontRight.setNeutralMode(NeutralMode.Brake);
    frontLeft.setNeutralMode(NeutralMode.Brake);
    backRight.setNeutralMode(NeutralMode.Brake);
    backLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.set(TalonSRXControlMode.Follower, 4);
    frontLeft.set(TalonSRXControlMode.Follower, 1);
  }

  @Override
  public void teleopPeriodic() {
    if (driverXboxController.getRightBumper()) {
      speedMultiplier = TURTLE_SPEED;
    } else if (driverXboxController.getLeftBumper()) {
      speedMultiplier = TURBO_SPEED;
    } else {
      speedMultiplier = REGULAR_SPEED;
    }

    backRight.set(
      TalonSRXControlMode.PercentOutput,
      driverXboxController.getRightY() * speedMultiplier
    );
    backLeft.set(
      TalonSRXControlMode.PercentOutput,
      driverXboxController.getRightY() * speedMultiplier
    );
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {
    if(!(limelightX < deadband && limelightX > -deadband)) { //If the apriltag is outside the deadband zone rotate
      if(limelightX < 0) { //value is negative, rotate right
        backRight.set(TalonSRXControlMode.PercentOutput, -driverXboxController.getRightY());
        backLeft.set(TalonSRXControlMode.PercentOutput, -driverXboxController.getRightY());

      } else { //value is positive, rotate left
        backRight.set(TalonSRXControlMode.PercentOutput, driverXboxController.getRightY());
        backLeft.set(TalonSRXControlMode.PercentOutput, driverXboxController.getRightY());
      }
    } else {
      backRight.set(TalonSRXControlMode.PercentOutput, 0.0);
        backLeft.set(TalonSRXControlMode.PercentOutput, 0.0);
    }
  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
