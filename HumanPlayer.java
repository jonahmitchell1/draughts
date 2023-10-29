import java.io.Console;

public class HumanPlayer extends Player {
    public HumanPlayer(int colour, GameState game) {
        super(colour, game);
    }

    public Move getMove() throws InvalidMoveException {
        Console cons = System.console();
        Position origin, destination;
        String input;
        Move move;
        while (true){
            System.out.println("> Enter origin:");
            input = cons.readLine();  // Read user input
            try {
                origin = stringToPosition(input);  // Read user input
            }
            catch (InvalidMoveException e) {
                throw e;
            }

            System.out.println("> Enter destination:");
            input = cons.readLine();  // Read user input
            try {
                destination = stringToPosition(input);  // Read user input
            }
            catch (InvalidMoveException e) {
                throw e;
            }

            Piece originPiece = game.getPiece(origin);
            if (originPiece == null) {
                throw new InvalidMoveException("No piece at origin: " + origin);
            }

            if (originPiece.getColour() != this.getColour()) {
                throw new InvalidMoveException("Not your piece at origin: " + origin);
            }

            move = new Move(originPiece, destination);
            break;
        }

        return move;
    }

    public Position stringToPosition(String string) throws InvalidMoveException {
        String stringArray[] = string.split(",");

        if (stringArray.length != 2) {
            throw new InvalidMoveException("Invalid position: " + string);
        }

        int[] coords = new int[4];
        coords[0] = (int) stringArray[0].charAt(0) - 97; // numbers convert nicely
        coords[1] = Integer.parseInt(stringArray[1]); // ascii conversion alphabet to index

        return new Position(coords[0], coords[1]);
    }
}