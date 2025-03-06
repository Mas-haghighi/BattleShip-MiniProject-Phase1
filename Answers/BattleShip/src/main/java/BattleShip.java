import java.util.Random;
import java.util.Scanner;

public class BattleShip {

    static final int GRID_SIZE = 10;
    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);


        placeShips(player1Grid);
        placeShips(player2Grid);


        boolean player1Turn = true;


        while (!isGameOver()) {
            if (player1Turn) {
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        System.out.println("Game Over!");
    }

    static void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '~';
            }
        }
    }

    static void placeShips(char[][] grid) {
        int[] shipSize = {2, 3, 4, 5};
        Random random = new Random();
        for (int size : shipSize) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                boolean horizontal = random.nextBoolean();
                if (canPlaceShip(grid, row, col, size, horizontal)) {
                    for (int i = 0; i < size; i++) {
                        if (horizontal) {
                            grid[row][col + i] = 'S';
                        } else {
                            grid[row + i][col] = 'S';
                        }

                    }
                    placed = true;
                }
            }
        }
    }

    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > GRID_SIZE)
                return false;
        } else {
            if (row + size > GRID_SIZE)
                return false;
        }
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                if (grid[row][col + i] != '~')
                    return false;
            } else if (grid[row + i][col] != '~')
                return false;
        }
        return true;

    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        System.out.println("Where do you wanna hit?(like A3)");
        String spot = BattleShip.scanner.nextLine().toUpperCase();
        while (!isValidInput(spot)) {
            System.out.println("You chose invalid spot darling!! Enter again(like A3)");
            spot = BattleShip.scanner.nextLine().toUpperCase();
        }
        int row = spot.charAt(0) - 'A';
        int col;
        col = Integer.parseInt(spot.substring(1)) -1 ;



        if (opponentGrid[row][col] == 'S') {
            System.out.println("Hell yeah!!You hit it");
            opponentGrid[row][col] = 'X';
            trackingGrid[row][col] = 'X';
        } else {
            System.out.println("Oh no! You missed it :( ");
            opponentGrid[row][col] = 'O';
            trackingGrid[row][col] = 'O';
        }

    }

    static boolean isGameOver() {
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);

    }

    static boolean allShipsSunk(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'S')
                    return false;
            }

        }
        return true;
    }

    static boolean isValidInput(String input) {
        if (input.length() < 2 || input.length() > 3)
            return false;
        if (input.charAt(0) < 'A' || input.charAt(0) > 'J')
            return false;
        String numberPart = input.substring(1);
            if (!numberPart.matches("\\d+"))
                return false;
        int col = Integer.parseInt(numberPart) - 1;
        if (col < 0 || col >= 10)
            return false;
        return true;
    }

    static void printGrid(char[][] grid) {
        System.out.print("  A B C D E F G H I J");
        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        }
    }

