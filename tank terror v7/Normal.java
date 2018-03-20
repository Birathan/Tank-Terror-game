import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Normal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Normal extends Bullet
{
    public Normal(int rotation){
        super(rotation);
        scale(20,20);
    }
    
    /**
     * Act - do whatever the Normal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        elapsedTime = System.currentTimeMillis()- startTime;
        move(4);
        bounce();
        fade();
    }    
}
