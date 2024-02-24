public class Position { // --- (Ju Wei)

    public int x;
    public int y;

    Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public Piece getPieceOnPosition() {
        for(Piece piece : Control.chessPieces) {
            if(piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }
}
