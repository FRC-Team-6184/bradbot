package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.XboxController;

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

  public Robot() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
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
      -driverXboxController.getLeftY() * speedMultiplier
    );
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
