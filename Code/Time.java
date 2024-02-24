public class Time extends Piece { // --- (Zheng Bin)

    Time(Position piecePosition, Alliance pieceAlliance, int direction) {
        super(piecePosition, pieceAlliance, direction);
    }

    @Override
    public String getPieceName() {
        return "Time";
    }

    @Override
    public void calculateMoves(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        availableMoves.clear();

        // Diagonal movements: top-right, top-left, bottom-right, bottom-left
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                for (int k = 1; k <= 6; k++) {
                    int newX = x + k * i;
                    int newY = y + k * j;
                    Position newPosition = new Position(newX, newY);

                    if (!isWithinBoard(newPosition)) {
                        break;
                    }

                    if (isEmptyBox(newPosition)) {
                        availableMoves.add(newPosition);
                    } else if (isOppositeAlliance(piece, newPosition)) {
                        availableMoves.add(newPosition);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
