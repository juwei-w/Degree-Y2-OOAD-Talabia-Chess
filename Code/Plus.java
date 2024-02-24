public class Plus extends Piece { // --- (Zheng Bin)

    Plus(Position piecePosition, Alliance pieceAlliance, int direction) {
        super(piecePosition, pieceAlliance, direction);
    }

    @Override
    public String getPieceName() {
        return "Plus";
    }

    @Override
    public void calculateMoves(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        availableMoves.clear();

        // Check horizontally: left
        for (int newX = x - 1; newX >= 1; newX--) {
            Position newPosition = new Position(newX, y);
            if (!isWithinBoard(newPosition)) {
                break;
            }
            if (isEmptyBox(newPosition)) {
                availableMoves.add(newPosition);
            } else {
                if (isOppositeAlliance(piece, newPosition)) {
                    availableMoves.add(newPosition); // Capture enemy piece
                }
                break;
            }
        }

        // Check horizontally: right
        for (int newX = x + 1; newX <= 7; newX++) {
            Position newPosition = new Position(newX, y);
            if (!isWithinBoard(newPosition)) {
                break;
            }
            if (isEmptyBox(newPosition)) {
                availableMoves.add(newPosition);
            } else {
                if (isOppositeAlliance(piece, newPosition)) {
                    availableMoves.add(newPosition); // Capture enemy piece
                }
                break;
            }
        }

        // Check vertically: up
        for (int newY = y - 1; newY >= 1; newY--) {
            Position newPosition = new Position(x, newY);
            if (!isWithinBoard(newPosition)) {
                break;
            }
            if (isEmptyBox(newPosition)) {
                availableMoves.add(newPosition);
            } else {
                if (isOppositeAlliance(piece, newPosition)) {
                    availableMoves.add(newPosition); // Capture enemy piece
                }
                break;
            }
        }

        // Check vertically: down
        for (int newY = y + 1; newY <= 6; newY++) {
            Position newPosition = new Position(x, newY);
            if (!isWithinBoard(newPosition)) {
                break;
            }
            if (isEmptyBox(newPosition)) {
                availableMoves.add(newPosition);
            } else {
                if (isOppositeAlliance(piece, newPosition)) {
                    availableMoves.add(newPosition); // Capture enemy piece
                }
                break;
            }
        }
    }
}
