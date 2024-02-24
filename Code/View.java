import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class View {

    static int BOARD_X_AXIS = 7;
    static int BOARD_Y_AXIS = 6;

    static JFrame frame; // Outer frame
    static JPanel chessPanel; // Panel to hold and position chessBoard
    static JPanel chessBoard; // Board to hold chessBox
    static JButton[][] chessBox; // Box to hold chessPiece (coordinate - 1)

    // Main frame & functions --- (Ju Wei)
    public static void createGame() throws IOException {
        // Create a frame
        frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 500);

        chessPanel = new JPanel(new BorderLayout(3, 3));
        chessBoard = new JPanel(new GridLayout(0, 7));
        chessBox = new JButton[7][6];

        createBoard();
        Player.createPlayer();
        Control.createPieces();

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create the file menu
        JMenu fileMenu = new JMenu("Main Menu");
        menuBar.add(fileMenu);

        // Create menu items
        JMenuItem restartMenuItem = new JMenuItem("Restart Game");
        JMenuItem saveMenuItem = new JMenuItem("Save Game");
        JMenuItem loadMenuItem = new JMenuItem("Load Game");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add action listeners to menu items
        restartMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Control.restartGame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog to get the desired filename
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
                if (fileName != null) {
                    // Check if the user entered a filename
                    saveGame(fileName + ".txt");
                }
            }
        });

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog to get the desired filename
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
                if (fileName != null) {
                    fileName += ".txt";
                    File file = new File(fileName);

                    if (file.exists()) {
                        loadGame(fileName);
                    }
                    else {
                        System.out.println("Error: File not found. Please check the file name and try again.");
                        JOptionPane.showMessageDialog(frame, "Error: File not found. Please check the file name and try again.");
                    }
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add menu items to the file menu
        fileMenu.add(restartMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.add(exitMenuItem);

        frame.add(chessPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Board layout --- (Ju Wei)
    public static void createBoard() {

        for (int y = 0; y < BOARD_Y_AXIS; y++) {
            for (int x = 0; x < BOARD_X_AXIS; x++) {
                chessBox[x][y] = new JButton();
                chessBox[x][y].setBackground(Color.white);
                chessBox[x][y].setBorder(new LineBorder(new Color(56, 93, 138, 255), 3));
                chessBox[x][y].setLayout(new BorderLayout());
                chessBox[x][y].addActionListener((ActionEvent e) -> Control.clickBox(e));
                chessBoard.add(chessBox[x][y]);
            }
        }
        chessPanel.add(chessBoard);
    }

    // Update pieces --- (Ju Wei + Rui Ern)
    public static void updatePieces() throws IOException {

        int pieceNumber = 0; 

        // Clear the chessBox
        for (int i = 0; i < chessBox.length; i++) {
            for (int j = 0; j < chessBox[i].length; j++) {
                chessBox[i][j].removeAll();
                chessBox[i][j].revalidate();
                chessBox[i][j].repaint();
            }
        }

        // Get piece image
        for (Piece piece : Control.chessPieces) {
            int x = piece.getX();
            int y = piece.getY();

            if (piece instanceof Sun) {
                pieceNumber = 1;
            } 
            else if (piece instanceof Time) {
                pieceNumber = 2;
            } 
            else if (piece instanceof HourGlass) {
                pieceNumber = 3;
            } 
            else if (piece instanceof Plus) {
                pieceNumber = 4;
            } 
            else if (piece instanceof Point) {
                pieceNumber = 5;

                if (!Control.rotated && piece.direction == 1) { // If piece move backwards
                    pieceNumber = 6; // DOWN
                }
                else if (Control.rotated && piece.direction == 1) { // If board rotate and piece move backwards
                    pieceNumber = 5; // UP
                }
                else if (Control.rotated) { // If board rotate
                    pieceNumber = 6; // DOWN
                }
                else if (!Control.rotated) { // If normal board
                    pieceNumber = 5; // UP
                }
            }

            if (piece.getPieceAlliance() == Alliance.BLACK) {
                pieceNumber += 6;
            }

            String filePath = "Image/" + pieceNumber + ".png";
            BufferedImage pieceImage = ImageIO.read(new File(filePath));
            chessBox[x - 1][y - 1].add(new JLabel(new ImageIcon(pieceImage)));
        }

        System.out.println("\nRound: " + Control.round);
        System.out.println("Player turn: " + Control.activePlayer.getPlayerAlliance());
    }

    // Show available moves --- (Ye Xin)
    public static void paintGreenBox(Piece selectedPiece) {
        repaintBox();

        for (Position position : selectedPiece.availableMoves) {
            chessBox[position.x - 1][position.y - 1].setBackground(Color.green);
        }
    }

    // Repaint board --- (Ye Xin)
    public static void repaintBox() {
        for (int x = 0; x < BOARD_X_AXIS; x++) {
            for (int y = 0; y < BOARD_Y_AXIS; y++) {
                chessBox[x][y].setBackground(Color.white);
            }
        }
    }

    // Start Frame --- (Rui Ern)
    public static void startGame() {
        JFrame startGameFrame = new JFrame("Talabia Chess");
        startGameFrame.setSize(550, 500);

        JLabel startGameLabel = new JLabel("Welcome to Talabia Chess!");
        startGameLabel.setBounds(190, 150, 200, 50);
        startGameFrame.add(startGameLabel);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(195, 200, 150, 40);

        JButton loadButton = new JButton("Load Game");
        loadButton.setBounds(195, 250, 150, 40);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the method to start the game
                try {
                    createGame();
                    Control.restartGame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // Close the startGameFrame
                startGameFrame.dispose();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog to get the desired filename
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
                if (fileName != null) {
                    fileName += ".txt";
                    File file = new File(fileName);

                    if (file.exists()) {
                        startGameFrame.dispose();
                        try {
                            createGame();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        };
                        loadGame(fileName);
                    }
                    else {
                        System.out.println("Error: File not found. Please check the file name and try again.");
                        JOptionPane.showMessageDialog(frame, "Error: File not found. Please check the file name and try again.");
                    }
                }
            }
        });

        startGameFrame.setLayout(null);
        startGameFrame.add(startButton);
        startGameFrame.add(loadButton);
        startGameFrame.setVisible(true);
    }

    // Save Game --- (Rui Ern)
    public static void saveGame(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            // Save the current round
            writer.println("Round: " + Control.round);

            // Save whose turn it is
            writer.println("Turn: " + Control.turn);

            // Save active player's information
            writer.println("ActivePlayer: " + Control.activePlayer.getPlayerAlliance());

            // Save is board rotated (for image rotation)
            writer.println("Board Rotated: " + Control.rotated);

            // Save chess pieces
            for (Piece piece : Control.chessPieces) {
                // Save piece information as: PieceType, x, y, PieceAlliance
                writer.println(piece.getClass().getSimpleName() + "," + piece.getX() + "," +
                    piece.getY() + "," + piece.getPieceAlliance() + "," + piece.direction);
            }

            JOptionPane.showMessageDialog(frame, "Game Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load Game --- (Ye Xin)
    public static void loadGame(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read and set the round
            line = reader.readLine();
            if (line != null && line.startsWith("Round: ")) {
                Control.round = Integer.parseInt(line.substring(7).trim());
            }

            // Read and set the turn
            line = reader.readLine();
            if (line != null && line.startsWith("Turn: ")) {
                Control.turn = Integer.parseInt(line.substring(6).trim());
            }

            // Read and set the active player
            line = reader.readLine();
            if (line != null && line.startsWith("ActivePlayer: ")) {
                String alliance = line.substring(14).trim();
                Control.activePlayer = Player.getPlayerByAlliance(Alliance.valueOf(alliance));
            }

            // Read and set the board rotation
            line = reader.readLine();
            if (line != null && line.startsWith("Board Rotated: ")) {
                String rotated = line.substring(15).trim();
                Control.rotated = Boolean.parseBoolean(rotated);
            }

            // Clear existing chess pieces
            Control.chessPieces.clear();

            // Read and add chess pieces
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String pieceType = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    Alliance alliance = Alliance.valueOf(parts[3]);
                    int direction = Integer.parseInt(parts[4]);

                    Piece piece = null;
                    switch (pieceType) {
                        case "Sun":
                            piece = new Sun(new Position(x, y), alliance, direction);
                            break;
                        case "Time":
                            piece = new Time(new Position(x, y), alliance, direction);
                            break;
                        case "HourGlass":
                            piece = new HourGlass(new Position(x, y), alliance, direction);
                            break;
                        case "Plus":
                            piece = new Plus(new Position(x, y), alliance, direction);
                            break;
                        case "Point":
                            piece = new Point(new Position(x, y), alliance, direction);
                            break;
                        // Add cases for other piece types if needed
                    }

                    if (piece != null) {
                        Control.chessPieces.add(piece);
                    }
                }
            }

            // Update the GUI with loaded pieces
            updatePieces();
    
            JOptionPane.showMessageDialog(frame, "Game Loaded");
        } catch (IOException e) {
            System.out.println("Error loading the game.");
            JOptionPane.showMessageDialog(frame, "Error loading the game.");
        }
    }

    // Show winner and replay screen --- (Zheng Bin)
    public static void showWinner(String winner) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JDialog dialog = new JDialog(frame, "Game Over", true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Game Over! " + winner + " wins!\n");
        JLabel label2 = new JLabel("Congratulations!!!");
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        label2.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JButton playAgainButton = new JButton("Play Again");
        JButton exitButton = new JButton("Exit Game");

        playAgainButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Control.restartGame();
                } catch (IOException ex) {
                    ex.printStackTrace(); // Handle or log the exception appropriately
                }
                dialog.dispose();
            }
        });
        

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        dialog.add(label);
        dialog.add(label2);
        dialog.add(playAgainButton);
        dialog.add(exitButton);

        dialog.setSize(300, 160);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        startGame();
    }
}