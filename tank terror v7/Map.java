import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Map here.
 * 
 * @author Birathan Somasundaram 
 * @version 15/01/2016
 */
public class Map extends World
{
    //new Tank(color, up, down, left, right, shoot)
    Tank player1;
    Tank player2;

    //power bullets
    powerBullet[] pBullets1 = new powerBullet[1];
    powerBullet[] pBullets2 = new powerBullet[1];

    //score panel
    scorePanel scor = new scorePanel(); 

    //line pixel size
    int size = 120;

    //score
    Label scoreP1 = new Label("Score: ", 0);;
    Label scoreP2 = new Label("Score: ", 0);;
    
    long startTime = System.currentTimeMillis();
    long elapsedTime = 0;
    /**
     * Constructor for objects of class Map.
     * 
     */
    public Map()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1020, 840, 1); 
        setup();
        unEnclose();
        setPaintOrder(Label.class,scorePanel.class,Tank.class, Bullet.class);
    }

    public void act(){
        String key = Greenfoot.getKey();
        //check who died and give point to other tank
        win();
        //spawn powerups
        powerUpSpawn();
        //shooting power bullets

        //shootNormal();
        if (("n").equals(key)) {        
            if(pBullets1[0] == null){         
                pBullets1[0] = new powerBullet(player1.getRotation());
                addObject(pBullets1[0] , player1.getX()+player1.front[0], player1.getY()+player1.front[1]);
            }
        }
        if (("w").equals(key)) {        
            if(pBullets2[0] == null){         
                pBullets2[0] = new powerBullet(player2.getRotation());
                addObject(pBullets2[0] , player2.getX()+player2.front[0], player2.getY()+player2.front[1]);
            }
        }
        if (("m").equals(key)) {  
            if(player1.ammoType == "normal"){
                player1.shooting();
            }
        }
        if (("q").equals(key)) {   
            if(player2.ammoType == "normal"){
                player2.shooting();
            }
        }
    } 

    public void shootNormal(){
        //String key = Greenfoot.getKey();
    }

    public void unEnclose(){
        List<Line> list = getObjects(Line.class);
        int rand = Greenfoot.getRandomNumber(100);
        for(int x = 0; x<10; x++){
            while(list.get(rand)==null){
                rand = Greenfoot.getRandomNumber(50);
            }
            Line line = list.get(rand);
            list = getObjects(Line.class);
            if(list.contains(line)){
                if((line.getY()!=5)||(line.getY()!= 835) ){
                    if(line.orientation == "horizontal"){
                        if(line.verticals()>=2){
                            removeObject(line);
                            line = null;
                        }
                    }
                }
            }
        }
    }

    public void win(){
        if((player1.death == true)||(player2.death == true)){
            elapsedTime = System.currentTimeMillis()-startTime;
            if(elapsedTime > 4.0){
                //System.out.println ("death");
                if(player1.death == true){
                    scoreP2.variable++;
                }
                if(player2.death == true){
                    scoreP1.variable++;
                }
                clear();
                setup();
            }
        }

        if(player1.death == true){
            scoreP2.variable++;
            player2.move.stop();
            player1.move.stop();
            //clear map
            clear();
            setup();
        }
        if(player2.death == true){
            scoreP1.variable++;
            player2.move.stop();
            player1.move.stop();
            //clear map
            clear();
            setup();
        }
    }

    public void clear(){
        removeObjects(getObjects(Bullet.class));
        removeObjects(getObjects(Line.class));
        removeObjects(getObjects(Label.class));
        removeObjects(getObjects(scorePanel.class));
        removeObjects(getObjects(PowerUp.class));
        removeObject(player1);
        removeObject(player2);
    }

    public void powerUpSpawn(){
        int x = Greenfoot.getRandomNumber(6);
        int y = Greenfoot.getRandomNumber(6);
        int rand = Greenfoot.getRandomNumber(10000);
        if(rand == 43){
            addObject(new pMissile(), 60 + (x*120), 60+(y*120));
        }
        if(rand == 53){
            addObject(new pMachineGun(), 60 + (x*120), 60+(y*120));
        }
        //         if(rand == 63){
        //             addObject(new pLaser(), 60 + (x*120), 60+(y*120));
        //         }
        //         if(rand == 73){
        //             addObject(new pRay(), 60 + (x*120), 60+(y*120));
        //         }
    }

    public void setupSurround(){        
        for(int n=0 ; n < 840/120; n++){
            addObject(new Line(120, "vertical"), 5, n*120+60);  
            addObject(new Line(120, "vertical"), 840-5, n*120+60);  
        }
        for(int n=0 ; n < 840/120; n++){
            addObject(new Line(120, "horizontal"), n*120+60, 5);  
            addObject(new Line(120, "horizontal"), n*120+60, 840-5);
        }
    }

    public void setup(){
        player1 = new Tank("Red", "up", "down", "left", "right", "m");
        player2 = new Tank("Green", "e", "d", "s", "f", "q");
        player1.ammunition(5, "normal");
        player2.ammunition(5, "normal");
        setupSurround();

        //power bullet reset
        pBullets2 = new powerBullet[1];
        pBullets1 = new powerBullet[1];

        //score panel
        addObject(scor,930,420);

        //players
        addObject(player1, 400, 400);  
        addObject(player2, 200, 200);

        //score labels
        addObject(scoreP1,930,230);
        addObject(scoreP2, 930, 670);

        //random map
        String orientation;
        int randy = 0;
        int randx = 0;
        int rando = Greenfoot.getRandomNumber(2);
        for(int z = 0; z < 100; z++){
            randx += 1;
            rando = Greenfoot.getRandomNumber(2);
            if(randx == 840/size){
                randx = 0;
                randy++;
            }
            if(rando == 1){
                orientation = "horizontal";
                addObject(new Line(size, orientation), randx*size+60,randy*size);
            }
            else{
                orientation = "vertical";
                addObject(new Line(size, orientation), randx*size,randy*size+60);
            }           
        } 
    }
}

