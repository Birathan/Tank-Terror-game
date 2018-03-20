import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Line here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Line extends Actor
{
    public String orientation;
    GreenfootSound explode = new GreenfootSound("explode.mp3");

    public Line(int length, String orient){
        GreenfootImage image;
        int div = length/60;
        if(orient == "horizontal"){
            orientation = orient;
            setImage("Hline60.png");
            image = getImage();
            image.scale(image.getWidth()*div+10, image.getHeight());
        }
        else{
            orientation = "vertical";
            setImage("line60.png");
            image = getImage();
            image.scale(image.getWidth(), image.getHeight()*div+10);
        }      
    }

    public String getOrientation() {
        return orientation;
    }

    public int verticals(){
        List<Line> list = getIntersectingObjects(Line.class);
        int num = 0;
        for(int x = 0; x<list.size(); x++){
            if(list.get(x).orientation == ""){
                num++;
            }
        }
        return num;
    }

    /**
     * Act - do whatever the Line wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        explode.setVolume(20);
        powerBullet a =(powerBullet)getOneIntersectingObject(powerBullet.class);
        if(a != null){
            explode.play();
            removeTouching(powerBullet.class);
            getWorld().removeObject(this);
        }
    }    
}
