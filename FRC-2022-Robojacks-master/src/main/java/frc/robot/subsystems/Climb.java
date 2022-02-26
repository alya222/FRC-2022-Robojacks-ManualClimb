// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import information on CANSparkMAx motor controller
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import information from other files
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Climb extends SubsystemBase {
  /** Creates a new ClimAuto subsystem */
 
  // add a piston for moving right arm
  private Solenoid rightClimbPiston = new Solenoid(compressorModule, rightArmMoverPort);

  private Solenoid leftClimbPiston = new Solenoid(compressorModule, leftArmMoverPort);
  
  // add a motor for lift
  private CANSparkMax liftMotor = new CANSparkMax(liftPort, MotorType.kBrushless);

  public Climb () {

  }

  // add a method that moves lift up
  public void move(double speed) {
    liftMotor.set(speed);
  }

  // method makes piston extend (makes the arm reach)
  public void reaching(boolean pistonReach) {
    rightClimbPiston.set(pistonReach);
    leftClimbPiston.set(pistonReach);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
