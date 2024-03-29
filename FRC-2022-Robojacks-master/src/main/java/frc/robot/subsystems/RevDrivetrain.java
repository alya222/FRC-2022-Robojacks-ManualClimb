/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;


public class RevDrivetrain extends SubsystemBase {

  private CANSparkMax LFrontWheel = new CANSparkMax(kLeftFrontPort, MotorType.kBrushless);
  private CANSparkMax RFrontWheel = new CANSparkMax(kRightFrontPort, MotorType.kBrushless);

  private CANSparkMax LRearWheel = new CANSparkMax(kLeftRearPort, MotorType.kBrushless);
  private CANSparkMax RRearWheel = new CANSparkMax(kRightRearPort, MotorType.kBrushless);

  private DifferentialDrive roboDrive = new DifferentialDrive(LFrontWheel, RFrontWheel);

  public RevDrivetrain() {

    LFrontWheel.restoreFactoryDefaults();
    LFrontWheel.setIdleMode(IdleMode.kBrake);
    LFrontWheel.burnFlash();

    RFrontWheel.restoreFactoryDefaults();
    RFrontWheel.setIdleMode(IdleMode.kBrake);
    RFrontWheel.setInverted(true);
    RFrontWheel.burnFlash();

    LRearWheel.restoreFactoryDefaults();
    LRearWheel.setIdleMode(IdleMode.kBrake);
    LRearWheel.follow(LFrontWheel);
    LRearWheel.burnFlash();
    

    RRearWheel.restoreFactoryDefaults();
    RRearWheel.setIdleMode(IdleMode.kBrake);
    RRearWheel.follow(RFrontWheel);
    RRearWheel.burnFlash();
    
    
  }

  public double deadband(double JoystickValue, double DeadbandCutOff) {
    double deadbandreturn;

    if (Math.abs(JoystickValue) < DeadbandCutOff) {
      deadbandreturn = 0;
    }
    else {
      deadbandreturn = JoystickValue;
    }
    
    return deadbandreturn;
  }

  public void stopDriveMotors() {
    LFrontWheel.set(0);
    RFrontWheel.set(0);
  }

  public DifferentialDrive getDifferentialDrive() {
    return roboDrive;
  }

  @Override
  public void periodic() {
    roboDrive.feed();
  }

  public void my_DriveTank(double leftSpeed, double rightSpeed){
    roboDrive.tankDrive(leftSpeed, rightSpeed,true);
  }
  // right side moves back when lift motor down is pressed
}
