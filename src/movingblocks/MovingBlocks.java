/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;

import static movingblocks.Grid.testGrid;
import static movingblocks.Block.testBlock;

/**
 *
 * @author Isabella
 */
public class MovingBlocks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        // tests the whether one block is able to move
        Grid game = new Grid();
        game.bigBang(230, 345, .1);
        // Testing the ChangeBlockArray
        System.out.println("\nTesting for my Block class: \n");
        testBlock();
        System.out.println("\nTesting for my Grid class: \n");
        testGrid();


    }

}
