// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  private XboxController gadgetThing;
  private CANSparkMax feeder;
//creating talonfx motors
private WPI_TalonFX leftMain;
private WPI_TalonFX rightMain;
//creating left folloiwng motors
private WPI_TalonFX leftfollower1;
private WPI_TalonFX leftfollower2;
//creating the right following motors
private WPI_TalonFX rightfollower1;
private WPI_TalonFX rightfollower2;
//private Solenoid shooterLeft0;
//private Solenoid shooterLeft1;
//private Solenoid shooterRight2;
//private Solenoid shooterRight5;
private final Timer a_timer = new Timer();
 
private Solenoid shifter0;
private Solenoid shifter3;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
     
    shifter0= new Solenoid(10, PneumaticsModuleType.CTREPCM, 0);
    shifter3= new Solenoid(10, PneumaticsModuleType.CTREPCM, 3);
 
    feeder= new CANSparkMax(20, MotorType.kBrushless);
 
    leftMain = new WPI_TalonFX(1);
    rightMain= new WPI_TalonFX(5);
 
    leftfollower1= new WPI_TalonFX(2);
    leftfollower2= new WPI_TalonFX(3);
 
    rightfollower1= new WPI_TalonFX(6);
    rightfollower2= new WPI_TalonFX(4);
 
    rightMain.setInverted(true);
    rightfollower1.setInverted(true);
    rightfollower2.setInverted(true);
   
 
    leftfollower1.follow(leftMain);
    leftfollower2.follow(leftMain);
 
    rightfollower1.follow(rightMain);
    rightfollower2.follow(rightMain);
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    //m_rightMotor.setInverted(true);
 
    m_myRobot = new DifferentialDrive(leftMain, rightMain);
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
    gadgetThing = new XboxController(2);

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {

    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    
    System.out.println("Auto selected: " + m_autoSelected);
    String autoSelected = SmartDashboard.getString("Auto selector", "Default");
    a_timer.reset();
    a_timer.start();
    }
  

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
    if (a_timer.get() < 5.0) {
      m_myRobot.tankDrive(0.5, 0.5);
    }
    else {
      m_myRobot.stopMotor();
    }
//     switch (m_autoSelected) {
//       case kCustomAuto:
//         // Put custom auto code here
//         m_myRobot.tankDrive(0.2, 0.2);

        
// // Customcode here 

//         break;
//       case kDefaultAuto:
//       default:
//         // Put default auto code here
//         break;
//     }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    shifter0.set(false);
    shifter3.set(true);  

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    System.out.println("test");
    m_myRobot.tankDrive(Math.pow(m_leftStick.getY(),2), Math.pow(m_rightStick.getY(),2));
 
    //   if (GadgetThing.getRawButton(1))
    // {
    // feeder.set(0.5);
    // }
    // else
    // {
    //   feeder.set(0.0);
    // }
   
    if (gadgetThing.getRawButton(3))
    {
    feeder.set(-0.05);
    }
    else if (gadgetThing.getRawButton(1))
    {
      feeder.set(0.05);
    }
    else
    {
      feeder.set(0.0);
    }
    // if (GadgetThing.getAButton())
    // {
    //   System.out.println("button  A pressed");
    // feeder.set(0.5);
    // }
    // else
    // {
    //   feeder.set(0.0);
    // }
    if (m_leftStick.getRawButtonPressed(4)) {
    
      System.out.println("button  4 pressed");
      shifter0.set(!shifter0.get());
      shifter3.set(!shifter3.get());
    }  
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
