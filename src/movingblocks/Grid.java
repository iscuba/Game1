/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;

import java.util.ArrayList;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

/**
 *
 * @author Isabella
 */
public class Grid extends World {

    int width = 12;
    int height = 15;

    public ArrayList<Block> movingBlocks;
    public ArrayList<Block> deadBlocks;

    public Grid() {
        ArrayList<Block> firstDead = new ArrayList<>();
        ArrayList<Block> firstBorn = new ArrayList<>();
        Block db1 = new Block(5, 1);
        Block db2 = new Block(6, 1);
        Block db3 = new Block(7, 1);
        Block db4 = new Block(8, 1);

        firstDead.add(db1);
        firstDead.add(db2);
        firstDead.add(db3);
        firstDead.add(db4);

        Block mb1 = new Block(5, 2);
        Block mb2 = new Block(6, 2);
        Block mb3 = new Block(7, 2);
        Block mb4 = new Block(8, 2);

        firstBorn.add(mb1);
        firstBorn.add(mb2);
        firstBorn.add(mb3);
        firstBorn.add(mb4);

        this.movingBlocks = firstBorn;
        this.deadBlocks = firstDead;
    }

    private Grid(ArrayList<Block> moving, ArrayList<Block> dead) {
        super();
        this.movingBlocks = moving;
        this.deadBlocks = dead;
    } 

    public Grid changeBlockArray() {
        ArrayList<Block> tempMove = new ArrayList<>();
        ArrayList<Block> tempDead = new ArrayList<>();
        for (int i = 0; i < deadBlocks.size(); i++) {
            tempDead.add(deadBlocks.get(i));
        }
        for (int i = 0; i < movingBlocks.size(); i++) {
            for (int j = 0; j < deadBlocks.size(); j++) {
                if (deadBlocks.get(j).isOnTop(movingBlocks.get(i))) {
                    int blX = movingBlocks.get(i).x;
                    int blY = movingBlocks.get(i).y + 1;
                    tempMove.add(new Block(blX, blY));
                    tempDead.add(movingBlocks.get(i));
                } else {
                }
            }
        }
        return new Grid(tempMove, tempDead);
    }

    public boolean LooseHuh() {
        return movingBlocks.isEmpty();
    }

    public boolean WinHuh() {
        return deadBlocks.get(deadBlocks.size() - 1).y == 16;
    }

   
    public boolean offLeftGridHuh() {
        int num = movingBlocks.size() - 1;
        return (movingBlocks.get(num).x <= 1);
    }

    public boolean offRightGridHuh() {
        int num = movingBlocks.size() - 1;
        return (movingBlocks.get(num).x >= width - 1);
    }

    public World onTick() {
        if (LooseHuh()) {
            return this.endOfWorld("You loose");
        } else if (WinHuh()) {
            return this.endOfWorld("WINNER!");

        } else {
            return this.moveOneWay();
        }
    }
    
    public int countDeadBlocks(){
        return deadBlocks.size();
    }
    
    public WorldImage back = new RectangleImage(new Posn(0,0),500,810, new Black());
    public WorldImage frame = new FrameImage(new Posn(0, 0), 460, 660, new Blue());
    public WorldImage backFrame = new OverlayImages(back,frame);
    public WorldImage backdrop = new OverlayImages(back, frame);
    

    public WorldImage makeImage() {
        for (int i = 0; i < movingBlocks.size(); i++) {
            WorldImage b = movingBlocks.get(i).makeBlock();
            backdrop = new OverlayImages(backdrop, b);
        }
        for (int j = 0; j < deadBlocks.size(); j++) {
            WorldImage pic = deadBlocks.get(j).makeBlock();
            backdrop = new OverlayImages(backdrop, pic);
        }
        WorldImage score = new TextImage(new Posn(230, 600), "Score: " + deadBlocks.size(), 13, -1, new Red());
        return new OverlayImages( backdrop, score);

    }
    
    public WorldImage lastImage(){
        WorldImage y =  new TextImage(new Posn (230,330),"Bye", 100, new Green());
        return new OverlayImages(this.makeImage(),y);
    }

    public World onKeyEvent(String ke) {
        switch (ke) {
            case "x":
                return this.endOfWorld("Goodbye");
            case "s":
                return changeBlockArray();
            default:
                return this;
        }
    }

    public Grid moveOneWay() {
        for (int i = 0; i < movingBlocks.size(); i++) {
            int x = movingBlocks.get(i).x;
            int ex = x + 1;
            int why = movingBlocks.get(i).y;
            if (ex >= width) {
                movingBlocks.set(i, new Block(0, why));
            } else {
                movingBlocks.set(i, new Block(ex, why));
            }

        }
        return new Grid(movingBlocks, deadBlocks);
    }
    
    
    public ArrayList<Block> addDeadBlock(Block block) {
        ArrayList<Block> temp = new ArrayList();
        for (int i = 0; i < deadBlocks.size(); i++) {
            temp.add(deadBlocks.get(i));
        }
        temp.add(block);
        return temp;
    }

    public ArrayList<Block> addLiveBlock(Block block) {
        ArrayList<Block> temp = new ArrayList();
        for (int i = 0; i < movingBlocks.size(); i++) {
            temp.add(i, movingBlocks.get(i));
        }
        temp.add(block);
        return temp;

    }

    public ArrayList<Block> removeLiveBlock(Block block) {
        ArrayList<Block> temp = new ArrayList();
        for (int i = 0; i < movingBlocks.size(); i++) {
            if (!movingBlocks.get(i).equalBlock(block)) {
                temp.add(i, movingBlocks.get(i));
            } else {
            }
        }
        return temp;


    }

    public static void testGrid() {

        Block block1 = new Block(5, 5);
        ArrayList<Block> array1 = new ArrayList<>(1);
        ArrayList<Block> array2 = new ArrayList();
        Grid testGrid1 = new Grid(array1, array2);
        Grid testGrid2 = new Grid(testGrid1.addLiveBlock(block1), array2);
        System.out.println("the first X Value is 5 : " + testGrid2.movingBlocks.get(0).x
                + " The Moved x value should be 6 : " + testGrid2.moveOneWay().movingBlocks.get(0).x);

        System.out.println("the first X Value is 6 : " + testGrid2.movingBlocks.get(0).x
                + " The Moved x value should be 7 : " + testGrid2.moveOneWay().movingBlocks.get(0).x);
        
        Block block = new Block(10,10);
        ArrayList<Block> array3 = new ArrayList<>(1);
        ArrayList<Block> array4 = new ArrayList();
        Grid testGrid3 = new Grid(array3, array4);
        Grid testGrid4 = new Grid(testGrid3.addLiveBlock(block), array2);
        System.out.println("the first X Value is 10 : " + testGrid4.movingBlocks.get(0).x
                + " The Moved x value should be 11: " + testGrid4.moveOneWay().movingBlocks.get(0).x);

        Block block2 = new Block(5, 5);
        Block block3 = new Block(5, 4);
        ArrayList<Block> arrayA = new ArrayList<>();
        ArrayList<Block> arrayB = new ArrayList();

        Grid testGridA = new Grid(arrayA, arrayB);
        testGridA.movingBlocks = arrayA;
        testGridA.deadBlocks = arrayB;

        Grid testGridB = new Grid(testGridA.addLiveBlock(block2), testGridA.addDeadBlock(block3));
        Grid afterChange = testGridB.changeBlockArray();

        System.out.println("this should return true: " + block3.isOnTop(block2));
        System.out.println("this should return false: " + block2.isOnTop(block3));

        System.out.println("testGridB's movingBlocks should have 1 thing and has: " + testGridB.movingBlocks.size());
        System.out.println("testGridB's DeadBlocks should have 1 thing and has: " + testGridB.deadBlocks.size());
        System.out.println(" I expect movingBlocks after change to have 1 block in it and it has: " + afterChange.movingBlocks.size());
        System.out.println("The first grid has 2 blocks: Live block: (5,5) deadblock: (5,4) ("
                + testGridB.movingBlocks.get(0).x + ", " + testGridB.movingBlocks.get(0).y + ") and ("
                + testGridB.deadBlocks.get(0).x + "," + testGridB.deadBlocks.get(0).y
                + ") after call to changeBlock Array: should have 3 blocks total (5,5) (5,4) (5,6): ("
                + afterChange.movingBlocks.get(0).x + ", " + afterChange.movingBlocks.get(0).y + ")"
                + "(" + afterChange.deadBlocks.get(0).x + "," + afterChange.deadBlocks.get(0).y + ") ("
                + afterChange.deadBlocks.get(1).x + ", " + afterChange.deadBlocks.get(1).y + ")");
    }
}
