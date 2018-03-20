import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class powerBullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class powerBullet extends Actor
{
    public powerBullet(int rotation)
    {
        GreenfootImage image = getImage();
        image.scale(20, 20);
        setImage(image);
        setRotation(rotation);
    }   
    
    public void act(){
        move(4);
    }
}
