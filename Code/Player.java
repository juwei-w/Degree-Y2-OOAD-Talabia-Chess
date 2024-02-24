public class Player { // --- (Ye Xin)
    public static Player whitePlayer, blackPlayer;
    private Alliance playerAlliance;
    private boolean playerTurn;

    Player(Alliance alliance, boolean turn) {
        this.playerAlliance = alliance;
        this.playerTurn = turn;
    }

    public static void createPlayer() {
        whitePlayer = new Player(Alliance.WHITE, true);
        blackPlayer = new Player(Alliance.BLACK, false);
    }

    // load game
    public static Player getPlayerByAlliance(Alliance alliance) {
        return alliance == Alliance.WHITE ? whitePlayer : blackPlayer;
    }

    public Alliance getPlayerAlliance() {
        return playerAlliance;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

}
