import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends Actor
{
    int dimension = 8;
    String orientation;
    long startTime = System.currentTimeMillis();
    long elapsedTime = 0;
    boolean fade = false;

    public Bullet(int rotation)
    {
        setRotation(rotation);
    }

    public void scale(int x, int y){
        GreenfootImage image = getImage();
        image.scale(x, y);
        setImage(image);
    }

    // public double timeElapsed(){
        // elapsedTime = System.currentTimeMillis()- startTime;
        // return elapsedTime;
    // }

    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
    }

    public void bounce(){
        orientation = "";
        if(elapsedTime<10000){
            for(int x = -1; x <= 1 ; x++){
                //above
                if(getOneObjectAtOffset(x,-dimension,Line.class)!=null){
                    orientation = "Horizontal";
                }   
                //below 
                else if(getOneObjectAtOffset(x,dimension,Line.class)!=null){
                    orientation = "Horizontal";
                }   

                //right
                else if(getOneObjectAtOffset(dimension, x, Line.class)!=null){
                    orientation = "Vertical";
                }   
                //left
                else if(getOneObjectAtOffset(-dimension, x, Line.class)!=null){
                    orientation = "Vertical";
                }   

                //top right
                else if(getOneObjectAtOffset(dimension,-dimension,Line.class)!=null){
                    setRotation(135);
                }   
                //bottom right
                else if(getOneObjectAtOffset(dimension,dimension,Line.class)!=null){
                    setRotation(225);
                }   

                //top left
                else if(getOneObjectAtOffset(-dimension, -dimension, Line.class)!=null){
                    setRotation(45);
                }   
                //bottom left
                else if(getOneObjectAtOffset(-dimension, dimension, Line.class)!=null){
                    setRotation(315);
                } 
            }

            //rotation
            if (orientation.equals("Horizontal")) {
                setRotation(360 - getRotation());
            } 
            else if(orientation.equals("Vertical")) {
                setRotation(180 - getRotation());
            }
        }
    }

    public void fade(){
         if(elapsedTime > 10000){
             getWorld().removeObject(this);
         }
     }
}
