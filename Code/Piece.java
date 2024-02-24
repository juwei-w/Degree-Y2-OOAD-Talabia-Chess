import java.util.*;

public abstract class Piece { // --- (Ju Wei)

    private Alliance pieceAlliance;
    private Position piecePosition;
    public ArrayList<Position> availableMoves = new ArrayList<>();
    public int direction;

    Piece(Position piecePosition, Alliance pieceAlliance, int direction) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.direction = direction;
    }

    // Return piece name
    public abstract String getPieceName();
    
    // Add legal moves into "availableMoves"
    public abstract void calculateMoves(Piece piece);

    // Get alliance
    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }

    // Get X
    public int getX() {
        return piecePosition.x;
    }

    // Get Y
    public int getY() {
        return piecePosition.y;
    }

    // Set new position after move
    public void setPosition(int x, int y) {
        this.piecePosition.setX(x);
        this.piecePosition.setY(y);
    }

    // Is move within board
    public boolean isWithinBoard(Position position) {
        return position.x >= 1 && position.y >= 1 && position.x <= 7 && position.y <=6;
    }

    // Is move occupied
    public boolean isEmptyBox(Position position) {
        boolean emptyFlag = true;
        for(Piece piece : Control.chessPieces) {
            if(piece.getX() == position.x && piece.getY() == position.y) {
                emptyFlag = false;
            }
        }
        return emptyFlag;
    }

    // Is move killable
    public boolean isOppositeAlliance(Piece piece, Position position) {
        boolean killableFlag = false;
        Piece pieceOnPosition = position.getPieceOnPosition();

        if(piece.getPieceAlliance() != pieceOnPosition.getPieceAlliance()) {
            killableFlag = true;
        }
        return killableFlag;
    }

}
