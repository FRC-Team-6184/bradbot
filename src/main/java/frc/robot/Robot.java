package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.CANifier.LEDChannel;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.IO.GameController;
import frc.robot.IO.LEDControl;
import frc.robot.Limelight.Limelight;
import frc.robot.Limelight.LimelightHelpers;
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

  Limelight limelight = new Limelight();
  final static double leftrightDeadband = 0.05;
  final static double distanceDeadband = 0.1;

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

    LEDControl.init();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("LimeLight X", LimelightHelpers.getTX());
    SmartDashboard.putNumber("LimeLight Z", LimelightHelpers.getTA());

    LEDControl.update();

  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    double speed = LimelightHelpers.getTX() / 35;
    double limelightDistance = LimelightHelpers.getTA() - 1.25; //goal is 1.25
    if (speed > 1) {
      speed = 1;

      SmartDashboard.putNumber("Speed", speed);
      robotDrive.tankDrive(-speed, speed);
  
      LEDControl.setSpeedPattern(-speed, speed);
    } else if (speed < -1) {
      speed = -1;

      SmartDashboard.putNumber("Speed", speed);
      robotDrive.tankDrive(-speed, speed);
  
      LEDControl.setSpeedPattern(-speed, speed);
    } else if (speed < leftrightDeadband && speed > -leftrightDeadband) {
      speed = 0;

      if((limelightDistance < distanceDeadband && limelightDistance > -distanceDeadband) || limelightDistance <= -1.2) {
        //do nothing
      robotDrive.tankDrive(0, 0);

      } else if (limelightDistance < 0) {
        // go forward
        robotDrive.tankDrive(-0.4, -0.4);
      LEDControl.setSpeedPattern(0.4, 0.4);

      } else if(limelightDistance > 0) {
        //go backwards
        robotDrive.tankDrive(0.4, 0.4);
      LEDControl.setSpeedPattern(-0.4, -0.4);

      }
      
    }

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
          break;
      default:
        robotDrive.stopMotor();
    }

    LEDControl.setSpeedPattern(backLeft.get(), backRight.get());
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    LEDControl.setRainbow();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    // If the apriltag is outside the deadband zone rotate
    if (!(LimelightHelpers.getTX() < leftrightDeadband * 100 && LimelightHelpers.getTX() > -leftrightDeadband * 100)) {
      if (LimelightHelpers.getTX() < 0) {
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
