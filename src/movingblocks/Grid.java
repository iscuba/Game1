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
import java.util.Random;
import static movingblocks.Block.makeBlockUnder;
import static movingblocks.Block.makeRandomBlock;

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
    
    public WorldImage back = new RectangleImage(new Posn(0, 40), 465, 665, new Black());
    public WorldImage frame = new FrameImage(new Posn(0, 0), 460, 660, new Blue());
    public WorldImage backFrame = new OverlayImages(back, frame);
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
        return new OverlayImages(backdrop, score);

    }

    public WorldImage lastImage() {
        WorldImage y = new TextImage(new Posn(230, 330), "Bye", 100, new Green());
        return new OverlayImages(this.makeImage(), y);
    }

    public Grid onKeyEvent(String ke) {
        switch (ke) {
            case "s":
                return changeBlockArray();
            default:
                return this;
        }
    }

    public boolean LooseHuh() {
        return movingBlocks.isEmpty();
    }

    public boolean WinHuh() {
        return deadBlocks.get(deadBlocks.size() - 1).y > 15;
    }

    public Grid onTick() {
        if (LooseHuh()) {
            return this;
        } else if (WinHuh()) {
            return this;

        } else {
            return this.moveOneWay();
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

    //makes a grid with 2 stached blocks at a random position. 
    public static Grid makeRandStackGrid() {
        Block randBlock = makeRandomBlock();
        ArrayList<Block> array1 = new ArrayList<>();
        array1.add(randBlock);
        ArrayList<Block> array2 = new ArrayList<>();
        Block block1 = makeBlockUnder(randBlock);
        array2.add(block1);
        Grid testGrid = new Grid(array2, array1);
        return testGrid;
    }

    public static void testGrid() {

        int runTimes = 10;
        for (int i = 0; i <= runTimes; i++) {
       
            Grid testerGrid = makeRandStackGrid();
            Grid changedGrid = testerGrid.onKeyEvent("s");
            System.out.println("the size of deadBlocks of the first Grid should be <= the size of the moved grid. This is: "
                    + (testerGrid.deadBlocks.size() <= changedGrid.deadBlocks.size()));
        // Tests onKey when the world is stopped by the used and the blocks are not stacked.            
            ArrayList<Block> arr = new ArrayList();
            Block ran = makeRandomBlock();
            arr.add(ran);
            ArrayList<Block> arr2 = new ArrayList();
            arr2.add(new Block(ran.x + 1, ran.y + 1));
            Grid gir = new Grid(arr, arr2);
            Grid gir2 = gir.onKeyEvent("s");
            System.out.println("Did we Loose the game? should be true: " + gir2.movingBlocks.isEmpty());

            //Tests on tick: makes sure the blocks are moving:
            System.out.println("Did the block move?:" + ((testerGrid.movingBlocks.get(0).x + 1) == testerGrid.moveOneWay().movingBlocks.get(0).x));

            // Makes sure that the Block Wraps the screen when x>=12
            ArrayList<Block> testArr = new ArrayList<>();
            ArrayList<Block> testArray = new ArrayList<>();
            Random rando = new Random();
            int randomY = rando.nextInt((12 - 1) + 1) + 1;
            Block wrapBlock = new Block(12, randomY);
            testArr.add(wrapBlock);
            testArray.add(makeRandomBlock());
            Grid wrapGrid = new Grid(testArr, testArray);
            Grid wrappedGrid = wrapGrid.onTick();
            Block movedBlock = new Block(0, wrapGrid.movingBlocks.get(0).y);
            System.out.println("Did the Block Wrap?:" + wrappedGrid.movingBlocks.get(0).equalBlock(movedBlock));

        }
    }

}
