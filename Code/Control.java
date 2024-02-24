import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class Control {

    public static ArrayList<Piece> chessPieces = new ArrayList<Piece>();
    public static Player activePlayer = Player.whitePlayer;
    private static Piece selectedPiece = null;
    public static int turn = 1;
    public static int round = 1;
    public static boolean rotated = false;

    // Create all pieces --- (Ju Wei)
    public static void createPieces() {
        // Create Black Point (Pawn)
        for(int i = 1; i <= 7; i++) {
            chessPieces.add(new Point(new Position(i, 2), Alliance.BLACK, -1));
        }
        // Create Black Plus (Rook)
        chessPieces.add(new Plus(new Position(1, 1), Alliance.BLACK, -1));
        chessPieces.add(new Plus(new Position(7, 1), Alliance.BLACK, -1));
        // Create Black HourGlass (Knight)
        chessPieces.add(new HourGlass(new Position(2, 1), Alliance.BLACK, -1));
        chessPieces.add(new HourGlass(new Position(6, 1), Alliance.BLACK, -1));
        // Create Black Time (Bishop)
        chessPieces.add(new Time(new Position(3, 1), Alliance.BLACK, -1));
        chessPieces.add(new Time(new Position(5, 1), Alliance.BLACK, -1));
        // Create Black Sun (King)
        chessPieces.add(new Sun(new Position(4, 1), Alliance.BLACK, -1));

        // Create White Point (Pawn)
        for(int i = 1; i <= 7; i++) {
            chessPieces.add(new Point(new Position(i, 5), Alliance.WHITE, -1));
        }
        // Create White Plus (Rook)
        chessPieces.add(new Plus(new Position(1, 6), Alliance.WHITE, -1));
        chessPieces.add(new Plus(new Position(7, 6), Alliance.WHITE, -1));
        // Create White HourGlass (Knight)
        chessPieces.add(new HourGlass(new Position(2, 6), Alliance.WHITE, -1));
        chessPieces.add(new HourGlass(new Position(6, 6), Alliance.WHITE, -1));
        // Create White Time (Bishop)
        chessPieces.add(new Time(new Position(3, 6), Alliance.WHITE, -1));
        chessPieces.add(new Time(new Position(5, 6), Alliance.WHITE, -1));
        // Create White Sun (King)
        chessPieces.add(new Sun(new Position(4, 6), Alliance.WHITE, -1));
    }

    // Actions when clicked box --- (Ju Wei)
    public static void clickBox(ActionEvent e) {
        JButton selectedBtn = (JButton) e.getSource();
        int selectedX = 0;
        int selectedY = 0;
        Piece pieceOnPosition = null;

        // Get selected box position
        for (int x = 0; x < View.chessBox.length; x++) {
            for (int y = 0; y < View.chessBox[x].length; y++) {
                if (View.chessBox[x][y] == selectedBtn) {
                    selectedX = x + 1;
                    selectedY = y + 1;
                }
            }
        }

        // Get piece on selected box
        for (Piece piece : chessPieces) {
            if (piece.getX() == selectedX && piece.getY() == selectedY) {
                pieceOnPosition = piece;
            }
        }

        if(selectedPiece == null) { // If no piece selected, select piece
            selectPiece(e, selectedX, selectedY);
        }
        else if(selectedPiece == pieceOnPosition) { // If selected same piece = unselect piece
            System.out.println("Unselected " + selectedPiece.getPieceName() + " (" + selectedPiece.getX() + ", " + selectedPiece.getY() + ")");
            View.repaintBox();
            selectedPiece = null;
        }
        else if(pieceOnPosition != null && selectedPiece.getPieceAlliance() == pieceOnPosition.getPieceAlliance()) { // If selected same alliance = select piece
            selectPiece(e, selectedX, selectedY);
        }
        else if(selectedPiece != null) { // If have selected piece, then move to selected box
            movePiece(e, selectedX, selectedY);
        }
    }

    // Select piece & calculate legal moves --- (Zheng Bin)
    public static void selectPiece(ActionEvent e, int x, int y) {
        for(Piece piece : chessPieces) {
            if(piece.getX() == x && piece.getY() == y) {
                if(piece.getPieceAlliance() == activePlayer.getPlayerAlliance()) {
                    piece.calculateMoves(piece);
                    selectedPiece = piece;

                    View.paintGreenBox(selectedPiece);
                    System.out.println("Selected " + piece.getPieceName() + " (" + piece.getX() + ", " + piece.getY() + ")");
                }
                else {
                    System.out.println("You cannot select this piece.");
                }
            }
        }
    }

    // Move piece --- (Ju Wei)
    public static void movePiece(ActionEvent e, int x, int y) {
        for(Position move : selectedPiece.availableMoves) {
            Piece pieceOnPosition = move.getPieceOnPosition();

            if(move.x == x && move.y == y) { // If new position is part of availableMoves
                if(pieceOnPosition != null) { // If moving position is occupied
                    if(selectedPiece.getPieceAlliance() != pieceOnPosition.getPieceAlliance()) { // If its opposite alliance, kill piece
                        System.out.println("Killed " + pieceOnPosition.getPieceName() + " (" + pieceOnPosition.getX() + ", " + pieceOnPosition.getY() + ")");
                        chessPieces.remove(pieceOnPosition); // Kill piece
                    }
                }
                selectedPiece.setPosition(x, y);
                System.out.println("Moved to (" + selectedPiece.getX() + ", " + selectedPiece.getY() + ")");

                updatePointDirection();
                updateActivePlayer(); // Active player = next player
                try {
                    View.updatePieces(); // Update piece position in board
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                View.repaintBox();
                checkForWinner(); 
            }
        }
        selectedPiece = null;
    }

    // Every round update status --- (Ye Xin)
    public static void updateActivePlayer() {
        if(activePlayer == Player.whitePlayer) { // If current player is white, next will be black
            activePlayer = Player.blackPlayer;
        }
        else { // else current player is black, next player iswhite
            activePlayer = Player.whitePlayer;
        }

        if(turn % 4 == 0) { // Even number = 2 players moved (1 round)
            round++;
            switchPiece();
        }
        rotateBoard();
        turn++;
    }

    // Rotate board --- (Ye Xin)
    public static void rotateBoard() {
        for(Piece piece : chessPieces) {
            int x = piece.getX();
            int y = piece.getY();
            piece.setPosition(8-x, 7-y);
        }

        // Update rotate boolean
        rotated = !rotated; // This will toggle the value of rotated
    }

    // Restart Game --- (Rui Ern)
    public static void restartGame() throws IOException {
        chessPieces.clear();
        createPieces();
        turn = 1;
        round = 1;
        rotated = false;
        activePlayer = Player.whitePlayer;
        View.updatePieces(); // Update the board
    }  
    
    // Switch Time and Plus --- (Zheng Bin)
    public static void switchPiece() {
        List<Piece> newPieces = new ArrayList<>();

        for (Piece piece : chessPieces) {
            if (piece instanceof Time) {
                int x = piece.getX();
                int y = piece.getY();
                Alliance alliance = piece.getPieceAlliance();
                newPieces.add(new Plus(new Position(x, y), alliance, -1));
            } else if (piece instanceof Plus) {
                int x = piece.getX();
                int y = piece.getY();
                Alliance alliance = piece.getPieceAlliance();
                newPieces.add(new Time(new Position(x, y), alliance, -1));
            } else {
                newPieces.add(piece);
            }
        }

        chessPieces.clear();
        chessPieces.addAll(newPieces);

    }

    // If Sun is killed --- (Rui Ern)
    public static void checkForWinner() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;
    
        // Check if White and Black Kings exist on the board
        for (Piece piece : chessPieces) {
            if (piece instanceof Sun) {
                if (piece.getPieceAlliance() == Alliance.WHITE) {
                    whiteKingExists = true;
                } else if (piece.getPieceAlliance() == Alliance.BLACK) {
                    blackKingExists = true;
                }
            }
        }
    
        // Display the winner
        if (!whiteKingExists) {
            View.showWinner("Black");
        } else if (!blackKingExists) {
            View.showWinner("White");
        }
    }

    // Point move backwards if reach top row --- (Rui Ern)
    public static void updatePointDirection() {
        for (Piece piece : chessPieces) {
            if (piece instanceof Point) {
                int y = piece.getY();
                if (y == 1) {
                    piece.direction = 1;
                }
            }
        }
    }
}

