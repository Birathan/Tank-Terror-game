import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class tank here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tank extends Actor
{
    String forward;
    String backward;
    String left;
    String right;
    String shoot;

    //moving sound
    GreenfootSound move = new GreenfootSound("tank.mp3");
    //death sound
    GreenfootSound deaths = new GreenfootSound("death.mp3");
    //shoot sound
    GreenfootSound shot = new GreenfootSound("shoot.mp3");
    //pickup power
    GreenfootSound pickup = new GreenfootSound("reload.mp3");

    //track keys
    String trackF;
    String trackB;
    String trackL;
    String trackR;
    boolean movement = true;

    //dimensions at 0 degrees
    int width = 50;
    int height = 30;

    //checking death
    boolean death = false;

    //number of bullets
    String ammoType;
    int ammoNum;
    Bullet[] bullets;
    int index = 0;

    //coordinates that are tracked for object collision
    int [] rearLeft = new int[2]; 
    int [] rearRight = new int[2]; 
    int [] frontLeft = new int[2]; 
    int [] frontRight = new int[2]; 
    int [] front = new int[2]; 
    int [] back = new int[2]; 

    //stopwatch
    long startTime = System.currentTimeMillis();
    long elapsedTime = 0;

    public Tank(String color, String f , String b, String l, String r, String s){
        setImage("tank"+ color +".png");
        forward = f;
        backward = b;
        left = l;
        right = r;
        shoot = s;

        //tracking variables
        trackF = forward;
        trackB = backward;
        trackL = left;
        trackR = right;
    }

    /**
     * Act - do whatever the tank wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        shot.setVolume(10);
        //check if tank is hit
        checkHit();
        //track movement coordinates
        LineCollision();    
        //moving tank and shooting
        tankControl();
        //check for powerups
        powerUps();
        if(ammoType != "normal"){
            powerFade();
        }
    }

    public void powerFade(){
        if(bullets[bullets.length-1]!=null && ammoType == "missile"){
            //if(bullets[bullets.length-1].stop.elapsedTime()>9.99999){
                movement = true;
                ammunition(5, "normal");
            //}
        }
    }

    public void remove(Bullet bullet){
        getWorld().removeObject(bullet);
        if(ammoType == "missile"){
            List list = getWorld().getObjects(Bullet.class);
            if(list.contains(bullets[index])){
                if(bullets[index].fade == true){
                    movement = true;
                    ammunition(5,"normal");
                }
            }
        }
    }

    public void tankControl(){
        move.setVolume(10);
        if(movement==true){
            if(Greenfoot.isKeyDown(right)){
                setRotation(getRotation()+4);
                move.play();
            }
            if(Greenfoot.isKeyDown(left)){
                setRotation(getRotation()-4);
                move.play();
            }
            if(Greenfoot.isKeyDown(forward)){
                move(3);
                move.play();
            }
            if(Greenfoot.isKeyDown(backward)){
                move(-3);
                move.play();
            }
            if(Greenfoot.isKeyDown(right)==false && Greenfoot.isKeyDown(left) == false && Greenfoot.isKeyDown(forward) == false && Greenfoot.isKeyDown(backward) == false){
                move.pause();
            }            
        }
        elapsedTime = System.currentTimeMillis()-startTime;
        if((Greenfoot.isKeyDown(shoot))&&(elapsedTime>0.1)){
            shootPower();
            startTime = System.currentTimeMillis();
        }
    }

    public void shootPower(){
        if(ammoType == "missile"){
            List list = getWorld().getObjects(Bullet.class);
            if(list.contains(bullets[index]) != true){
                movement = false;
                shot.stop();
                shot.play();
                bullets[index] = new Missile(getRotation(),right, left);
                getWorld().addObject(bullets[index], getX() + front[0], getY() + front[1]);
            }
        }
        if(ammoType == "machinegun"){
            List list = getWorld().getObjects(MachineGun.class);
            if(list.contains(bullets[index]) != true){
                bullets[index] = new MachineGun(getRotation());
                getWorld().addObject(bullets[index], getX() + front[0], getY() + front[1]);
                index++;
            }
            if(index == bullets.length - 1){
                ammunition(5, "normal");
            }
        }
    }

    public void shooting(){
        //if index reaches its max, it is reset to 0 so that it can go through the array again
        if(index == ammoNum){
            index = 0;
        }

        //check if the space for shooting is available
        List list = getWorld().getObjects(Bullet.class);
        if(list.contains(bullets[index]) != true){
            shot.stop();
            shot.play();
            bullets[index] = new Normal(getRotation());
            getWorld().addObject(bullets[index], getX() + front[0], getY()+front[1]);
            index++;
        }
    }

    public void ammunition(int ammoN, String ammoT){
        ammoNum = ammoN;
        ammoType = ammoT;
        if(ammoType == "normal"){
            bullets = new Normal[ammoNum];
            index = 0;
        }
        if(ammoType == "missile"){
            bullets = new Missile[ammoNum];
            index = 0;
        }
        if(ammoType == "ray"){
            bullets = new Normal[ammoNum];
            index = 0;
        }
        if(ammoType == "machinegun"){
            bullets = new MachineGun[ammoNum];
            index = 0;
        }
    }

    public void powerUps(){
        if(death!=true){        
            pMissile a =(pMissile)getOneIntersectingObject(pMissile.class);
            if(a != null){
                pickup.stop();
                pickup.play();
                getWorld().removeObject(a);
                ammunition(1,"missile");
            }
            pRay b =(pRay)getOneIntersectingObject(pRay.class);
            if(b != null){
                pickup.stop();
                pickup.play();
                getWorld().removeObject(b);
                ammunition(1,"ray");
            }
            pMachineGun c =(pMachineGun)getOneIntersectingObject(pMachineGun.class);
            if(c != null){
                pickup.stop();
                pickup.play();
                getWorld().removeObject(c);
                ammunition(20,"machinegun");
            }
        }
    }

    public void checkHit(){
        Bullet a =(Bullet)getOneIntersectingObject(Bullet.class);
        if(a != null){
            if(a.elapsedTime >= 200){
                death();
            }
        }
    }

    public void LineCollision(){
        if(death!=true){
            double angle = Math.toRadians(getRotation());
            double add;
            double ref;
            int length = (int)Math.sqrt(Math.pow(height/2, 2.0) + Math.pow(width/2, 2.0));
            //sine inverse ((height/2)/length)
            double addition = 31;
            int x;
            int y;
            double sum;

            //TRACKING COORDINATES

            //rearLeft
            ref = 180.0;
            add = Math.toRadians(ref + addition);
            sum = angle+add;
            x = (int)Math.round(Math.cos(sum)*length);
            y = (int)Math.round(Math.sin(sum)*length);
            rearLeft[0] =  x;
            rearLeft[1] = y;

            //rearRight
            ref = 180.0;
            add = Math.toRadians(ref - addition);
            sum = angle+add;
            x = (int)Math.round(Math.cos(sum)*length);
            y = (int)Math.round(Math.sin(sum)*length);
            rearRight[0] =  x;
            rearRight[1] = y;

            //frontleft
            ref = 360.0;
            add = Math.toRadians(ref - addition);
            sum = angle+add;
            x = (int)Math.round(Math.cos(sum)*length);
            y = (int)Math.round(Math.sin(sum)*length);
            frontLeft[0] =  x;
            frontLeft[1] = y;

            //frontRight
            add = Math.toRadians(addition);
            sum = angle+add;
            x = (int)Math.round(Math.cos(sum)*length);
            y = (int)Math.round(Math.sin(sum)*length);
            frontRight[0] =  x;
            frontRight[1] = y;

            //front
            x = (int)Math.round(Math.cos(angle)*25);
            y = (int)Math.round(Math.sin(angle)*25);
            front[0] = x;
            front[1] = y;

            //back
            x = (int)Math.round(Math.cos(angle+Math.toRadians(180))*25);
            y = (int)Math.round(Math.sin(angle+Math.toRadians(180))*25);
            back[0] = x;
            back[1] = y;

            if(getOneObjectAtOffset(rearLeft[0],rearLeft[1],Line.class)!=null||(getOneObjectAtOffset(rearRight[0],rearRight[1],Line.class)!=null)||(getOneObjectAtOffset(back[0],back[1],Line.class)!=null)){
                backward = " ";
            }
            else{
                backward = trackB;
            }

            if(getOneObjectAtOffset(frontLeft[0],frontLeft[1],Line.class)!=null||(getOneObjectAtOffset(frontRight[0],frontRight[1],Line.class)!=null)||(getOneObjectAtOffset(front[0],front[1],Line.class)!=null)){
                forward = " ";
            }
            else{
                forward = trackF;
            }

            if(getOneObjectAtOffset(frontLeft[0],frontLeft[1],Line.class)!=null||(getOneObjectAtOffset(rearRight[0],rearRight[1],Line.class)!=null)){
                left = " ";
            }
            else{
                left = trackL;
            }

            if(getOneObjectAtOffset(rearLeft[0],rearLeft[1],Line.class)!=null||(getOneObjectAtOffset(frontRight[0],frontRight[1],Line.class)!=null)){
                right = " ";
            }
            else{
                right = trackR;
            }
        }
    }

    public void death(){
        deaths.play();
        move.stop();
        removeTouching(Bullet.class);
        GreenfootImage image = getImage();
        while(height!=1){
            image = getImage();
            image.scale(width--, height--);
            setImage(image);
            Greenfoot.delay(2);
        }
        death = true;
        getWorld().removeObject(this);
    }
}
