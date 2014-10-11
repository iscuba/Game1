/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingblocks;

/**
 *
 * @author Isabella
 */
public class Block {

    public int x;
    public int y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // like equal? 
    public boolean isTaken(Block block) {
        return ((block.x == this.x) && (block.y == this.y));
    }

    public boolean isNotTop(Block block) {
        return !(this.x == block.x);
    }

    public boolean isTop(Block block) {
        return (this.x == block.x);
    }
    

}
