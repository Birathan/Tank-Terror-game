import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MachineGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MachineGun extends Bullet
{

    public MachineGun(int rotation){
        super(rotation);
        scale(10,10);
    }

    /**
     * Act - do whatever the MachineGun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(4);
        dimension = 10;
        bounce();
        fade();
    }    
}
