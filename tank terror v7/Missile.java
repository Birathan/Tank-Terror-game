import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Missile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Missile extends Bullet
{
    String right;
    String left;

    public Missile(int rotation, String r, String l){
        super(rotation);
        right = r;
        left = l;
        scale(30,20);
    }

    /**
     * Act - do whatever the Missile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.isKeyDown(right)){setRotation(getRotation()+5);}
        if(Greenfoot.isKeyDown(left)){setRotation(getRotation()-5);}
        fade();
        move(4);
        bounce();
    }    
}
