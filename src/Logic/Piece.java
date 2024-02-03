public abstract class Piece {
    protected boolean[][] shape;
    protected boolean horizontal; //is movable horizontally
    protected boolean vertical; //is movable vertically
    protected boolean rotatable; //is rotatable
    protected int anchorX, anchorY; //anchor point
    protected int x, y; //current position

    public boolean isValidMove(int x, int y){
        // check if the piece is movable horizontally

        // check if the piece is movable vertically

        // check if the piece is rotatable

        // check if the piece is within the arena

        // check if the piece is not overlapping with other pieces

        return true;
    }

    public void move(int x, int y){
        if (isValidMove(x, y)){
            this.x = x;
            this.y = y;
        }
    }

    public void rotate(){
        // todo: implement this
    }


    
}
