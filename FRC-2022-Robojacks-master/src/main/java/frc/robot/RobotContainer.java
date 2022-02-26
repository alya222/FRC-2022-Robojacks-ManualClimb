/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

// import Xbox Controller and related buttons and axes
import edu.wpi.first.wpilibj.XboxController;
import static edu.wpi.first.wpilibj.XboxController.Axis.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

// import commands
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Shooter;

// import RevDrivetrain subsystem
import frc.robot.subsystems.RevDrivetrain;

// import constants
import static frc.robot.Constants.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */

public class RobotContainer {

  // drive controller
  private XboxController xbox = new XboxController(kXboxPort);

  // drive subsystem
  private final RevDrivetrain rDrive = new RevDrivetrain();

  // climbAuto subsystem
  private final Climb climb = new Climb();

  private final Shooter shooter = new Shooter();

  
  private SequentialCommandGroup shootThenGo = new SequentialCommandGroup(
    
    // shoot the cargo into the goal
    new RunCommand (() -> shooter.runShootMotor(shooterSpeed/2))
    .withTimeout(shooterWaitTime),
    
    new InstantCommand(()-> shooter.setShootPiston(true)),
    
    new RunCommand(() -> shooter.runShootMotor(shooterSpeed))
    .withTimeout(shooterWaitTime*2),
    
    new InstantCommand(()-> shooter.setShootPiston(false)),
    
    new RunCommand (() -> shooter.runShootMotor(0)).withTimeout(0.0005)

    // drive backwards at 50% speed for 5 seconds
    .andThen(new RunCommand(() -> rDrive.getDifferentialDrive()
    .tankDrive(autoDriveSpeed, autoDriveSpeed),rDrive).withTimeout(5))

  );

  private SequentialCommandGroup shoot = new SequentialCommandGroup(
    new RunCommand (() -> shooter.runShootMotor(shooterSpeed/2))
    .withTimeout(shooterWaitTime),
    
    new InstantCommand(()-> shooter.setShootPiston(true)),
    
    new RunCommand(() -> shooter.runShootMotor(shooterSpeed))
    .withTimeout(shooterWaitTime*2),
    
    new InstantCommand(()-> shooter.setShootPiston(false)),
    
    new RunCommand (() -> shooter.runShootMotor(0)).withTimeout(0.0005)
  );

  // drives the robot using joysticks
  private Command manualDrive = new RunCommand(
    
    () -> rDrive.getDifferentialDrive().
    tankDrive(rDrive.deadband(xbox.getRawAxis(kLeftY.value), percentDeadband), 
    rDrive.deadband(xbox.getRawAxis(kRightY.value), percentDeadband),
    false
    ),
    rDrive
    );

  // move the lift up and down with right and left triggers, respectively
  private Command moveArm = new RunCommand(
  
    () -> climb.move(xbox.getRawAxis(kRightTrigger.value) 
      - xbox.getRawAxis(kLeftTrigger.value)), climb);

  public RobotContainer() {

    // configure the button bindings
    configureButtonBindings();

    // default to running moveArm and manualDrive
    rDrive.setDefaultCommand(manualDrive);
    climb.setDefaultCommand(moveArm);

  }

  private void configureButtonBindings() {
    
    // moves arm in when left bumper is pressed
    new JoystickButton(xbox, kLeftBumper.value)
    .whenPressed(new InstantCommand (() -> climb.reaching(false)));

    // moves arm out when right bumper is pressed
    new JoystickButton(xbox, kRightBumper.value)
    .whenPressed(new InstantCommand (() -> climb.reaching(true)));

    // shoots when Y is pressed
    //new JoystickButton(xbox, kY.value)
    //.whenPressed(shoot);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

   public Command getAutonomousCommand() {
     return shootThenGo;
   }
}
