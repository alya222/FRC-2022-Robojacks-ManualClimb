// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class Climber_Run extends CommandBase {
  private final Climb m_climb;
  private final XboxController m_xbox;
  /** Creates a new Climber_Run. */
  public Climber_Run(XboxController xbox ,Climb subsystem) {
    m_climb = subsystem;
    m_xbox = xbox;
    addRequirements(m_climb);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.move(m_xbox.getRawAxis(XboxController.Axis.kRightTrigger.value)-
       m_xbox.getRawAxis(XboxController.Axis.kLeftTrigger.value));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
