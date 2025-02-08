package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private final XboxController driverXboxController = new XboxController(GameControllerPort.DRIVER.getValue());
  private final TalonSRX backRight = new TalonSRX(MotorControllerPort.BACK_RIGHT);
  private final TalonSRX frontRight = new TalonSRX(MotorControllerPort.FRONT_RIGHT.getValue());
  private final TalonSRX frontLeft = new TalonSRX(MotorControllerPort.FRONT_LEFT.getValue());
  private final TalonSRX backLeft = new TalonSRX(MotorControllerPort.BACK_LEFT.getValue());

  final static double TURBO_SPEED = 1;
  final static double REGULAR_SPEED = 0.75;
  final static double TURTLE_SPEED = 0.5;
  double speedMultiplier = REGULAR_SPEED;

  NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
  double limelightX = 0.0;
  double limelightZ = 0.0;
  final static double deadband = 0.05;

  public Robot() {
    frontLeft.setInverted(true);
    backLeft.setInverted(true);
  }

  @Override
  public void robotPeriodic() {
    limelightX = limelightTable.getEntry("tx").getDouble(0.0);
    limelightZ = limelightTable.getEntry("ta").getDouble(0.0);
    SmartDashboard.putNumber("LimeLight X", limelightX);
    SmartDashboard.putNumber("LimeLight Z", limelightZ);
  }

  @Override
  public void autonomousInit() {
  }


  @Override
  public void autonomousPeriodic() {
    double speed = limelightX / 35;
    if (speed > 1) {
      speed = 1;
    } else if (speed < -1) {
      speed = -1;
    } else if (speed < deadband && speed > -deadband) {
      speed = 0;
    }
    SmartDashboard.putNumber("Speed", speed);
    backRight.set(TalonSRXControlMode.PercentOutput, -speed);
    backLeft.set(TalonSRXControlMode.PercentOutput, speed);
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
      driverXboxController.getLeftY() * speedMultiplier
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
    if(!(limelightX < deadband * 100 && limelightX > -deadband * 100)) { //If the apriltag is outside the deadband zone rotate
      if(limelightX < 0) { //value is negative, rotate right
        backRight.set(TalonSRXControlMode.PercentOutput, -driverXboxController.getRightY());
        backLeft.set(TalonSRXControlMode.PercentOutput, driverXboxController.getRightY());

      } else { //value is positive, rotate left
        backRight.set(TalonSRXControlMode.PercentOutput, driverXboxController.getRightY());
        backLeft.set(TalonSRXControlMode.PercentOutput, -driverXboxController.getRightY());
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
