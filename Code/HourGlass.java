public class HourGlass extends Piece { // --- (Zheng Bin)

    private final int[][] hourGlassMoves = {{1, 2}, {2, 1},       // Top right
                                            {2, -1}, {1, -2},     // Bottom right
                                            {-2, 1}, {-1, 2},     // Top left
                                            {-1, -2}, {-2, -1}};  // Bottom left

    public HourGlass(Position piecePosition, Alliance pieceAlliance, int direction) {
        super(piecePosition, pieceAlliance, direction);
    }

    @Override
    public String getPieceName() {
        return "HourGlass";
    }
    
    @Override
    public void calculateMoves(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        availableMoves.clear();

        for(int[] Move : hourGlassMoves) {
            int newX = x + Move[0];
            int newY = y + Move[1];
            Position newPosition = new Position(newX, newY);

            if (isWithinBoard(newPosition) && isEmptyBox(newPosition) || 
                isWithinBoard(newPosition) && isOppositeAlliance(piece, newPosition)) {
                availableMoves.add(newPosition);
            }
        }
    }
}
