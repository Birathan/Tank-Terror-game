import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{
    Button play = new Button ("play.png");
    Button instructions = new Button ("instructions.png");
    Button next = new Button ("next.png");
    GreenfootSound theme = new GreenfootSound("menuTheme.mp3");
    GreenfootSound fight= new GreenfootSound("fight.mp3");
    /**
     * Constructor for objects of class Menu.
     * 
     */
    public Menu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1260, 840, 1); 
        addObject(play ,330, 510);
        addObject(instructions ,590, 510);
    }

    public void act(){
        theme.setVolume(30);
        theme.play();
        if(Greenfoot.mouseClicked(play)){
            Greenfoot.setWorld(new Map());
            theme.stop();
            fight.setVolume(80);
            fight.play();
        }
        if(Greenfoot.mouseClicked(instructions)){
            removeObject(play);
            removeObject(instructions);
            setBackground("info.png");
            addObject(next, 460, 495);
        }
        if(Greenfoot.mouseClicked(next)){
            setBackground("info2.png");
            removeObject(next);
            addObject(play, 300, 490);
        }
    }
}
