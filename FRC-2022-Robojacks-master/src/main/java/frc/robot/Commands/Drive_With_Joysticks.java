// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RevDrivetrain;

public class Drive_With_Joysticks extends CommandBase {
  private final RevDrivetrain m_rDrive;
  private DoubleSupplier m_LeftSpeed;
  private DoubleSupplier m_RightSpeed;
  /** Creates a new Drive_With_Joysticks. */
  public Drive_With_Joysticks(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed, RevDrivetrain subsystem) {
    m_rDrive= subsystem;
    m_LeftSpeed = leftSpeed;
    m_RightSpeed = rightSpeed;
    addRequirements(m_rDrive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftSpeed = m_LeftSpeed.getAsDouble();
    double rightSpeed = m_RightSpeed.getAsDouble();
    m_rDrive.my_DriveTank(leftSpeed, rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_rDrive.my_DriveTank(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
