package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.IO.GameController;
import frc.robot.options.DrivetrainOption;

public class Robot extends TimedRobot {
  private GameController driverController = new GameController(0);
  private final WPI_TalonSRX backRight = new WPI_TalonSRX(MotorControllerPort.BACK_RIGHT);
  private final WPI_TalonSRX frontRight = new WPI_TalonSRX(MotorControllerPort.FRONT_RIGHT);
  private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(MotorControllerPort.FRONT_LEFT);
  private final WPI_TalonSRX backLeft = new WPI_TalonSRX(MotorControllerPort.BACK_LEFT);

  private DifferentialDrive robotDrive = new DifferentialDrive(backLeft::set, backRight::set);

  private final DrivetrainOption driveTrainOption = new DrivetrainOption();

  final static double TURBO_SPEED = 1;
  final static double REGULAR_SPEED = 0.75;
  final static double TURTLE_SPEED = 0.5;
  double speedMultiplier = REGULAR_SPEED;

  NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
  double limelightX = 0.0;
  double limelightZ = 0.0;
  final static double deadband = 0.05;

  @Override
  public void robotInit() {
    frontLeft.setInverted(true);
    backLeft.setInverted(true);

    frontRight.setNeutralMode(NeutralMode.Brake);
    frontLeft.setNeutralMode(NeutralMode.Brake);
    backRight.setNeutralMode(NeutralMode.Brake);
    backLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.set(TalonSRXControlMode.Follower, MotorControllerPort.BACK_RIGHT);
    frontLeft.set(TalonSRXControlMode.Follower, MotorControllerPort.BACK_LEFT);
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
  }

  @Override
  public void teleopPeriodic() {
    if (driverController.getRightBumper()) {
      speedMultiplier = TURTLE_SPEED;
    } else if (driverController.getLeftBumper()) {
      speedMultiplier = TURBO_SPEED;
    } else {
      speedMultiplier = REGULAR_SPEED;
    }

    switch (driveTrainOption.getSelected()) {
      case "tankDrive":
        robotDrive.tankDrive(
            driverController.getLeftY() * speedMultiplier,
            driverController.getRightY() * speedMultiplier);
        break;
      case "arcadeDrive":
        robotDrive.arcadeDrive(
            driverController.getLeftY() * speedMultiplier,
            -driverController.getLeftX() * speedMultiplier);
        break;
      case "curvatureDrive":
        robotDrive.curvatureDrive(
            driverController.getLeftY() * speedMultiplier,
            -driverController.getRightX() * speedMultiplier,
            true);
    }
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    // If the apriltag is outside the deadband zone rotate
    if (!(limelightX < deadband * 100 && limelightX > -deadband * 100)) {
      if (limelightX < 0) {
        backRight.set(TalonSRXControlMode.PercentOutput, -driverController.getRightY());
        backLeft.set(TalonSRXControlMode.PercentOutput, driverController.getRightY());
      } else {
        backRight.set(TalonSRXControlMode.PercentOutput, driverController.getRightY());
        backLeft.set(TalonSRXControlMode.PercentOutput, -driverController.getRightY());
      }
    } else {
      backRight.set(TalonSRXControlMode.PercentOutput, 0.0);
      backLeft.set(TalonSRXControlMode.PercentOutput, 0.0);
    }
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
