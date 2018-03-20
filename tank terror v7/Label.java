import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class Score here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Label extends Actor
{
    int variable;
    String labell;
    public Label(String label, int var){
        variable = var;
        labell = label;
    }

    public void act() 
    {
        setImage(new GreenfootImage(labell + variable, 24, Color.BLACK, null));   
    }    
}