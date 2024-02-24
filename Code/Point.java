public class Point extends Piece { // --- (Rui Ern)

    // put all coordinate moves as constants
    private final int[][] pointMoves = { { 0, 1 }, { 0, 2 } }; // Updated pointMoves for diagonal movement

    Point(Position piecePosition, Alliance pieceAlliance, int direction) {
        super(piecePosition, pieceAlliance, direction);
    }

    @Override
    public String getPieceName() {
        return "Point";
    }

    // Inside the Piece class
    public boolean isOppositeAlliance(Piece piece, Position newPosition) {
        Piece pieceOnPosition = newPosition.getPieceOnPosition();
        return pieceOnPosition != null && piece.getPieceAlliance() != pieceOnPosition.getPieceAlliance();
    }

    @Override
    public void calculateMoves(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();
            
        availableMoves.clear();
    
        // Iterate through possible moves for the Point piece
        for (int[] move : pointMoves) {
            int newX = x + move[0];
            int newY = y + (move[1] * direction); // Adjusted for direction

            Position newPosition = new Position(newX, newY);
    
            // Check if the new position is within the board
            if (isWithinBoard(newPosition)) {
                // If moving one step, check if the box is empty or has an opponent's piece
                if (move[1] == 1) {
                    if (isEmptyBox(newPosition) || (isOppositeAlliance(piece, newPosition) && newPosition.getPieceOnPosition() != null)) {
                        availableMoves.add(newPosition);
                    }
                } else if (move[1] == 2) {
                    // If moving two steps, check if the box in front is empty
                    Position inFrontPosition = new Position(x, y + (1 * direction));
                    if (isEmptyBox(inFrontPosition)) {
                        availableMoves.add(newPosition);
                    }
                }
            }
        }
    }
}