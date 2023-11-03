import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.event.EventHandler;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * The Draughts class represents the main class of the Draughts game.
 * It contains the main method and initializes the game by creating a GameState object,
 * two HumanPlayer objects, and a GameDriver object.
 * The startPlayer field is set to 1.
 * 
 * @author jonahmitchell1
 */
public class Draughts extends Application implements EventHandler<ActionEvent>{
    GameState game;
    Player player1;
    Player player2;
    Button[][] grid = new Button[8][8];

    // Used for generating moves from user input.
    Position origin;
    Position destination;

    /**
     * The main method of the Draughts game.
     * It initializes the game by creating a GameState object,
     * two HumanPlayer objects, and a GameDriver object.
     * The startPlayer field is set to 1.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialise game
        game = new GameState("game");
        player1 = new HumanPlayer(-1, 0); // black
        player2 = new AlphaBetaPlayer(1, 1000, 4); // white
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        game.currentPlayer = player1;

        // Initialise JavaFX components
        int windowWidth = 600;
        int windowHeight = 600;
        primaryStage.setTitle("Draughts");

        GridPane layout = new GridPane();
        layout.gridLinesVisibleProperty().set(true);

        // add grid layout.
        for (int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints(windowHeight/8);
            ColumnConstraints col = new ColumnConstraints(windowWidth/8);
            layout.getRowConstraints().add(row);
            layout.getColumnConstraints().add(col);
        }
        
        String style;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch ((i % 2) + (j % 2)) {
                    case 0:
                        style = "-fx-background-color: #ffe5d8;"; //white
                        break;
                    case 1:
                        style = "-fx-background-color: #362419;"; //black
                        break;
                    case 2:
                        style = "-fx-background-color: #ffe5d8;"; //black
                        break;
                    default:
                        style = "-fx-background-color: #ffe5d8;"; //white
                        break;
                }
                Button btn = new Button();
                btn.setStyle(style);
                btn.setPrefHeight(windowHeight/8);
                btn.setPrefWidth(windowWidth/8);
                btn.setId(i +"," + j);

                // add button press handling
                btn.setOnAction(this);
                grid[i][j] = btn;

                layout.add(btn, j, i);
            }
        }
        updateBoard();
       
        Scene scene = new Scene(layout, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public void startRound() {
        Player player = game.currentPlayer;
        System.out.println("-------------");
        wait(game.currentPlayer.getTimeDelay());
        if (player instanceof HumanPlayer) {
            System.out.println("Human player's turn.");
            return;
        }
        else {
            System.out.println("AI player's turn.");
            makePlayerMove(new Move());
        }
    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public void handle(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String[] coords = btn.getId().split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        Move move = generateMove(x, y);
        if(move != null) {
            makePlayerMove(move);
        }
    }

    public void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j< 8; j++) {
                grid[i][j].setGraphic(null);
            }
        }

        HashSet<Piece> pieces = (HashSet<Piece>)game.getPieces();
        for (Piece piece : pieces) {
            int x = piece.getX();
            int y = piece.getY();
            Color pieceColor, textColor;
            String str = null;
            if (piece.isKing()) {
                str = "K";
            }
            if (piece.getColour() == 1) {
                pieceColor = Color.WHITE;
                textColor = Color.BLACK;
            }
            else {
                pieceColor = Color.BLACK;
                textColor = Color.WHITE;
            }

            StackPane pane = new StackPane();
            Text text = new Text(str);
            text.setFill(textColor);
                
            pane.getChildren().addAll(new Circle (15, 15, 30, pieceColor), text);

            grid[x][y].setGraphic(pane);
        }
    }

    private void makePlayerMove(Move hint) {
        //Add artificial "choice time" for AI player
        if (game.currentPlayer instanceof AlphaBetaPlayer) {
            System.out.println("AI player is thinking...");
        }

        Player player = game.currentPlayer;
        Move playerMove;
        HashSet<Move> validMoves = (HashSet<Move>)game.getValidMoves();
        playerMove = player.chooseMove(game, hint); //hint used for player selection of move
        if (!validMoves.contains(playerMove)) {
            System.out.println("Invalid move: " + playerMove);
            return;
        }

        game.move(playerMove);

        updateBoard();

        if (game.gameOver()) {
            System.out.println("Game over! " + game.currentPlayer.getOpponent().getColourAsString() + " wins!");
        }
        else {
            startRound();
        }
    }

    public Move generateMove(int x, int y) {
        if (origin == null) {
            origin = new Position(x, y);
            return null;
        }
        else {
            destination = new Position(x, y);
            Move proposedMove = new Move(origin, destination);
            origin = null;
            destination = null;
            return proposedMove;
        }
    }
}