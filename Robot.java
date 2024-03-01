// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;

import java.util.Timer;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// importing our command class



/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
 
  Victor frontLeft = new Victor(4);
  Victor frontRight = new Victor(1);
  Victor rearLeft = new Victor(3);
  Victor rearRight = new Victor(5);
  PWMSparkMax Backintake = new PWMSparkMax(2);
  PWMSparkMax Upperintake = new PWMSparkMax(6);
  PWMSparkMax ShooterU = new PWMSparkMax(7);
  PWMSparkMax Motor = new PWMSparkMax(0);

  DoubleSolenoid m_Pneumatics = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);


  Servo exampleServo1 = new Servo(8);

Timer timer1 = new Timer();

  private Command m_autonomousCommand;



  @Override



  public void robotInit() {

   

// assign motors to their ports
  CameraServer.startAutomaticCapture();

  }


  @Override
  public void robotPeriodic() {
 
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = null;

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }


    // schedule the autonomous command (example)
 
 
 

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
   
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
   

   /*controller 1: LB, RB, A, B, Y, X */
   /*controller 2: LB, RB, A, B, Y, X */

    XboxController controller1 = new XboxController(0);
    XboxController controller2 = new XboxController(1);


   
 
    //Driving controls (including strafe)
    if(controller1.getLeftBumper()){

      frontLeft.set(1);
      rearLeft.set(-1);
      rearLeft.setInverted(true);
      frontRight.set(1);
      frontRight.setInverted(true);
      rearRight.set(-1);
     
       
      } else if(controller1.getRightBumper()){
       
        frontLeft.set(-1);
        rearLeft.set(1);
        frontLeft.setInverted(true);
        frontRight.set(-1);
        rearRight.setInverted(true);
        rearRight.set(1);
      } else {
      frontLeft.set(-controller1.getLeftY());
      rearLeft.set(-controller1.getLeftY());
      frontRight.set(controller1.getRightY());
      rearRight.set(controller1.getRightY());
      }
      //-----------------------------------------------------

        //Backintake (intaking) Upperintake (intaking)
      if(controller2.getBButton()){
        Backintake.set(.9);
        Upperintake.set(.7);
      } else if(controller2.getAButton()){
        Backintake.set(0);
        Upperintake.set(0);
      }
      //----------------------------------------------------

       //Controls both shooters//
      if(controller1.getYButton()){
        ShooterU.set(-.98);
      }
      if(controller1.getXButton()){
        ShooterU.set(0);
      }
      //----------------------------------------------------
     // Flicker Program//
      if (controller2.getRightBumper()){
      exampleServo1.setPosition(.5);
      } else {
      exampleServo1.setPosition(1);
      }

      //--------------------------------------------
      /*controls the thing on the tip of the shooter */
      if(controller2.getYButtonPressed()){
        Motor.set(-.58);
      }else if(controller2.getYButtonReleased()){
        Motor.set(0);
      }
        if(controller2.getXButtonPressed()){
        Motor.set(.85);
      }else if(controller2.getXButtonReleased()){
        Motor.set(0);
      }
      //---------------------------------------------
      /*pneumatics*/
      if(controller1.getBButton()){
        m_Pneumatics.set(Value.kForward);
      }
      else if(controller1.getAButton()){
        m_Pneumatics.set(Value.kReverse);
      } else {
        m_Pneumatics.set(Value.kOff);
      }
       
  }
   
 

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}