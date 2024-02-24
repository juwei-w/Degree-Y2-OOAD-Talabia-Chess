public class Sun extends Piece { // --- (Ye Xin)

    private final int[][] sunMoves = {{1, 1}, {1, 0}, {1, -1},
                                      {0, 1}, {0, -1},
                                      {-1, 1}, {-1, 0}, {-1, -1}
    };

    Sun(Position piecePosition, Alliance pieceAlliance, int direction) {
        super(piecePosition, pieceAlliance, direction);
    }

    @Override
    public String getPieceName() {
        return "Sun";
    }

    @Override
    public void calculateMoves(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        availableMoves.clear();

        for (int[] move : sunMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            Position newPosition = new Position(newX, newY);

            if (isWithinBoard(newPosition) && (isEmptyBox(newPosition) || isOppositeAlliance(piece, newPosition))) {
                availableMoves.add(newPosition);
            }
        }
    }
}

