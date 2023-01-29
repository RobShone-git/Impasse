
public class SU25132687 {
	
	public static int score(String cell[][], int m) { //created a method that calculates and returns the score
		int numOccupied = 0;
		for (int y = 0; y < m; y++) { // created a nested loop to through each element of the cell array
			for (int x = 0; x < m; x++) {
				if (cell[x][y] != "." && cell[x][y] != "*") { // tested to check if that element of the cell array is occupied
					numOccupied++; // incremented the number of occupied cells by 1
				}
			}
		}

		return Math.round((100 * numOccupied) / (float) (m * m)); // returned the score the player received
	}
	
	public static boolean won(String cell[][], int m) { //created a method that calculates the returns if the player has won
		int numOccupied = 0;
		boolean isWon = false;
		for (int y = 0; y < m; y++) { // created a nested loop to through each element of the cell array
			for (int x = 0; x < m; x++) {
				if (cell[x][y] != "." && cell[x][y] != "*") { // tested to check if that element of the cell array is occupied
					numOccupied++; // incremented the number of occupied cells by 1
				}
			}
		}
		
		if(numOccupied == (m*m)) { //tested to see if number of occupied cells equals the number of cells on the board
			isWon = true;
		}
		return isWon; //returned if the player won or not
	}

	public static int blockade(int row, int column, int k, int m, String cell[][]) { //created a method that calculates and returns if a blockade occurred

		int numDown = 0;
		int numUp = 0;
		for (int j = 1; j < (k); j++) { // looped the number of blockades
			if (row + j == m) { // tested to see if the loop should end if the current row reaches the number or rows and columns
				break;
			} else {
				if (cell[row + j][column] == cell[row][column]) { // tested to see if rows higher in cell array share the same value as the row the user inputed with the same column
					numDown++; // incremented the number of rows higher in cell array that share the same value as the row the user inputed with the same column to 0
				} else {
					break;
				}
			}
		}

		for (int h = 1; h < (k); h++) { // looped the number of blockades
			if (row - h == -1) { // tested to see if the loop should end if the current row reaches 0
				break;
			} else {
				if (cell[row - h][column] == cell[row][column]) { // tested to see if rows lower in cell array share the same value as the row the user inputed with the same column
					numUp++; // incremented the number of rows lower in cell array that share the same value as the row the user inputed with the same column to 0
				} else {
					break;
				}
			}
		}
		return numUp + numDown + 1; //returned if a blockade occurred or not
	}
	
	public static boolean deadEnd(int row, int column, String cell[][], String colour) { //created a method that calculates and returns if a deadend occurred
		String curPattern = "";
		for (int z = 0; z <= column - 1; z++) { //looped the amount of columns
			String cur = colour;
			if (cell[row][z] == colour) { // tested to see if cell is a certain color
				for (int w = z + 1; w <= column; w++) { // looped the amount of columns from certain point and store braces 
					cur += cell[row][w];
					if (cell[row][w] == colour) {
						curPattern += cur + ","; //Separated braces with a comma
					}
				}
			}
		}
		String brace[] = curPattern.split(","); // stored the each brace in an array
		boolean isPattern = false;
		for (int a = 0; a < brace.length; a++) { // created a nested loop the length of the array
			for (int b = 0; b < brace.length; b++) {
				if (a != b && brace[a].equals(brace[b])) { // if a brace repeated then set boolean to true
					isPattern = true;
					break;
				}
			}
			if (isPattern == true) { // breaks out of loop to avoid unnecessary computation 
				break;
			}
		}
		return isPattern; // returned if a deadend occurred or not
	}
	
	public static boolean split(int row, int column, String cell[][], int m) { //created a method that calculates and returns if a split occurred
		String split = "";
		Boolean isSplit = false;
		if (column == (m - 1)) {
			for (int g = 0; g < m; g++) { // looped through current full row and stored its values
				split += cell[row][g];
			}
			for (int f = 0; f < m; f++) { // created a nested loop to through each element of the cell array
				String curSplit = "";
				for (int l = 0; l < m; l++) {
					if (row != f) { //stored each new row
						curSplit += cell[f][l];
					} else {
						break;
					}
				}
				if (split.equals(curSplit)) { // if the new row is the same as the current row then set the boolean to true
					isSplit = true;
					break;
				}
			}	
		}
		
		return isSplit; // returned if a split occurred or not
	}
	
	public static String terminations(int row, int column, int k, int m, String cell[][], String colour) { //created a method that calculates and returns the correct termination messages 
		String arr[] = new String[3];
		int count = 0;
		String out = "";

		if (blockade(row, column, k, m, cell) >= k) { // tested to see a blockade occurred by calling its method
			arr[count] = "blockade";
			count++;		
		}

		
		if (deadEnd(row, column, cell, colour) == true) { // tested to see a deadend occurred by calling its method
			arr[count] = "dead end";
			count++;
		}
		
		if (split(row, column, cell, m) == true) { // tested to see a split occurred by calling its method
			arr[count] = "split";
			count++;
		}
		
		if(count == 3) { //depending on the amount of terminations the relevant message is stored 
		    out += "\nTermination: You have caused a blockade, a dead end, and a split!"; // displayed the reason for termination
		} else if (count == 2) {
			out += "\nTermination: You have caused a " + arr[0] + " and a "+ arr[1] + "!"; // displayed the reason for termination
		} else if(count == 1) {
			out += "\nTermination: You have caused a " + arr[0] + "!"; // displayed the reason for termination	
		}
		
		return out; // returned the relevant termination message
	}
	
	public static String[][] arrayCopy(String[][] arr, int m) { //created a method that duplicates an array into a new one
		String temp[][] = new String[m][m];
		for (int x = 0; x < m; x++) { // created a nested loop to go through each element and store into another array
			for (int y = 0; y < m; y++) {
				temp[x][y] = arr[x][y];
			}
		}
		
		return temp; // returned the duplicate of the inputed array as a new one
	}
	
	public static boolean impasse( String arr[][], int m, int n, String colour[], int k) { //created a method that calculates and returns if there are no more open cells that can be replaced with a color
		boolean isImpasse = false;
		int open = 0;
		int count = 0;
		for(int x = 0; x < m; x++) { // created a nested for loop to go through each cell in the array
			for(int y = 0; y < m; y++) {
				if(arr[x][y] == ".") { // tested to see if the cell was open
					open++;
					for(int z = 0; z < n; z++) { // looped through the colors array by the amount of colors user selected 
						String temp[][] = arrayCopy(arr, m); // created a copy array of the initial array parameter 
						temp[x][y] = colour[z]; // added color to that open cell in the copy array
						if(blockade(x, y, k, m, temp) >= k || deadEnd(x, y, temp, colour[z]) == true || split(x, y, temp, m) == true) { // tested game terminations after that cell was replaced with a new color
							count++;
						}
					}
				}
			}
		}
		if(open*n == count) { // tested to see if there are any moves available
			isImpasse = true;
		}
		
		return isImpasse; // returned if an early termination has occurred or not 
	}

	public static void main(String[] args) {

		int n; // declared a variable for the number of colors
		int k; // declared a variable for the number of blockades
		int m; // declared a variable for the number of rows and columns
		int move; // declared a variable for the players move
		int row; // declared a variable for the row the player selected
		int column; // declared a variable for the column the player selected
		int colourNum = 0; // initialized a variable for the color the players selects
		int numMoves = 0; // initialized the number of moves to 0
		String colour[] = { "G", "Y", "R", "B" }; // initialized a color array to convert players input into relevant colors on the board
		boolean gameIsRunning = true; // initialized the game is running variable to true
		double scale; // declared a variable for the scale of the GUI
		double height; // declared a variable for the height of the GUI
		
		if (args.length < 4) { // tested to see if there are the correct amount of arguments provided
			StdOut.println("Not enough arguments.");
			System.exit(1);
		} else if (args.length > 4) {
			StdOut.println("Too many arguments.");
			System.exit(1);
		}

		if (Integer.parseInt(args[0]) == 1 || Integer.parseInt(args[0]) == 2) { // tested to see if the first argument is supported and assigned the relevant value to game mode variable

			if (Integer.parseInt(args[1]) == 1) { // tested to see if the second argument is supported and assigned the relevant value to gui variable
				if (Integer.parseInt(args[2]) < 2 || Integer.parseInt(args[2]) > 4) { // tested to see if third argument is allowed and assigned the relevant value to number of colors variable
					StdOut.println("Third input reset to default.");
					n = 2;
				} else {
					n = Integer.parseInt(args[2]);
				}

				if (n == 2) { // tested to see the number of colors to assign the number of rows and column variable with the relevant number
					m = 8;
				} else if (n == 3) {
					m = 30;
				} else {
					m = 128;
				}
				
				if(n==2) { // tested the number of color variable to assign specific values to build the GUI
					scale = 1.0;
					height = 0.05;
				}else if(n == 3) {
					scale = 4.0;
					height = 0.07;
				} else {
					scale = 14.0;
					height = 0.09;
				}

				if (Integer.parseInt(args[3]) < 3 || Integer.parseInt(args[3]) > m) { // tested to see if fourth argument is allowed and assigned the relevant value to number of blockades variable
					StdOut.println("Fourth input reset to default.");
					k = 3;
				} else {
					k = Integer.parseInt(args[3]);
				}

				String cell[][] = new String[m][m]; // declared a 2d cell array with sizes of the number of rows and columns

				for (int x = 0; x < m; x++) { // created a nested loop to assign each element of the cell array with the relevant sign
					for (int y = 0; y < m; y++) {
						if (y == 0) {
							cell[x][y] = ".";
						} else {
							cell[x][y] = "*";
						}
					}
				}

				StdOut.println("The dimension of your board is: " + m + "x" + m); // displayed games board size
				StdOut.println("The length of a blockade is: " + k); // displayed length of blockades to lose the game
				
				double xNum = (scale) / (m + 1); // initialized variables to help with GUI 
				double yNum = (scale) / (m + 3);
				
				StdDraw.setScale(0.0, scale); //set the scale of the board
				StdDraw.clear(StdDraw.BLACK);
				StdDraw.setPenColor(StdDraw.WHITE);

				for (int y = 0; y < m; y++) { // created a nested loop to go through each element of the cell array and to create the board
					for (int x = 0; x < m; x++) {
						if (x == 0) {
							StdDraw.setPenColor(StdDraw.GRAY);
						}
						if (x == 0 && y == 0) {
							StdDraw.setPenColor(StdDraw.PINK);
							StdDraw.rectangle(xNum + (x * xNum), (scale) - yNum - (y * yNum), 0.05, 0.04);
							StdDraw.setPenColor(StdDraw.GRAY);

						}
						StdDraw.filledRectangle(xNum + (x * xNum), (scale) - yNum - (y * yNum), 0.05, 0.04);
						StdDraw.setPenColor(StdDraw.WHITE);
					}
				}

				int yCell = 0; //initialized variables to keep track of where the player moved
				int xCell = 0;
				
				StdOut.print("\n"); // displayed a new line

				for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it
					for (int y = 0; y < m; y++) {
						StdOut.print(cell[x][y]);

					}
					StdOut.print("\n");
				}

				while (gameIsRunning) { // created while loop to loop infinitely until game is finished

					if (StdDraw.isKeyPressed(83) && yCell < (m - 1)) { //tested to see if 's' key was pressed and made appropriate changes	
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						yCell++;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(87) && yCell > (0)) { //tested to see if 'w' key was pressed and made appropriate changes 	
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						yCell--;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(68) && xCell < (m - 1)) { //tested to see if 'd' key was pressed and made appropriate changes 	
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						xCell++;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(65) && xCell > 0) { //tested to see if 'a' key was pressed and made appropriate changes	
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						xCell--;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);	
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(81)) { //tested to see if 'q' key was pressed and made appropriate changes 
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: User terminated game!");
						StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
						StdDraw.pause(300);
						break;
					}

					if (StdDraw.isKeyPressed(88)) { //tested to see if 'x' key was pressed and made appropriate changes 
						if (cell[yCell][xCell] == "." || cell[yCell][xCell] == "*") { // tested to see how many cells from the column inputed are occupied
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Nothing to delete!");
							StdDraw.pause(300);
							continue;
						}

						for (int h = 0; h < (m - xCell); h++) { // looped the amount of times the column the user inputed is away from the column length variable
							if (h == 0) { // tested to see if the cells should be open or closed and set the cells in the array to the relevant signs
								cell[yCell][xCell] = ".";
							} else {
								cell[yCell][xCell + h] = "*";
							}
						}
						for (int h = 0; h < (m - xCell); h++) { // looped the amount of times the column the user inputed is away from the column length variable
							if (h == 0) { // tested to see if the cells should be open or closed and displayed the relevant cell color
								StdDraw.setPenColor(StdDraw.GRAY);
								StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
							} else {
								StdDraw.setPenColor(StdDraw.WHITE);
								StdDraw.filledRectangle(xNum + ((xCell + h) * xNum), scale - yNum - (yCell * yNum), 0.05,0.04);
							}
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}

						numMoves++; // incremented the number of moves by 1
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(48)) { //tested to see if '0' key was pressed and made appropriate changes 
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!");
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[0]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.GREEN);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04); 
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1
						
						if(terminations(yCell, xCell, k, m, cell, colour[0]) != "") { // tested to see if any of the terminations occurred 
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), terminations(yCell, xCell, k, m, cell, colour[0])); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
						
						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available							
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
						
						if(impasse(cell, m, n, colour, k) == true) { // tested to see if there are any open cells that can be replaced with a color
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(0.5, yNum - (-1 * yNum), 0.5, 0.02);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: Impasse!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}	
	
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); //displayed that the move was valid
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(49)) { //tested to see if '1' key was pressed and made appropriate changes 
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!"); 
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[1]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.YELLOW);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1

						if(terminations(yCell, xCell, k, m, cell, colour[1]) != "") { // tested to see if any of the terminations occurred 
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), terminations(yCell, xCell, k, m, cell, colour[1])); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}				

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
						
						if(impasse(cell, m, n, colour, k) == true) { // tested to see if there are any open cells that can be replaced with a color
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(0.5, yNum - (-1 * yNum), 0.5, 0.02);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: Impasse!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}	

						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); // displayed that the move was valid
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(50) && n > 2) {
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!");
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[2]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05,
									0.04);
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1	

						if(terminations(yCell, xCell, k, m, cell, colour[2]) != "") { // tested to see if any of the terminations occurred 
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), terminations(yCell, xCell, k, m, cell, colour[2])); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
						
						if(impasse(cell, m, n, colour, k) == true) { // tested to see if there are any open cells that can be replaced with a color
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(0.5, yNum - (-1 * yNum), 0.5, 0.02);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: Impasse!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}	

						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), 0.5, 0.07);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); // displayed that the move was valid
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(51) && n > 3) {
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!");
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[3]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05,
									0.04);
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1
		
						if(terminations(yCell, xCell, k, m, cell, colour[3]) != "") { // tested to see if any of the terminations occurred 
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), terminations(yCell, xCell, k, m, cell, colour[3])); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
						
						if(impasse(cell, m, n, colour, k) == true) { // tested to see if there are any open cells that can be replaced with a color
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(0.5, yNum - (-1 * yNum), 0.5, 0.02);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: Impasse!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves); 
							break;
						}	
						
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); // displayed that the move was valid 
						StdDraw.pause(300);
					}

				}

				StdOut.print("\n"); // displayed a new line
				StdOut.println("Game ended!"); // displayed that the game ended			

			} else {
				if (Integer.parseInt(args[1]) != 0) {
					StdOut.println("Second input reset to default.");
				}
				if (Integer.parseInt(args[2]) < 2 || Integer.parseInt(args[2]) > 4) { // tested to see if third argument is allowed and assigned the relevant value to number of colors variable
					StdOut.println("Third input reset to default.");
					n = 2;
				} else {
					n = Integer.parseInt(args[2]);
				}

				if (n == 2) { // tested to see the number of colors to assign the number of rows and column variable with the relevant number
					m = 8;
				} else if (n == 3) {
					m = 30;
				} else {
					m = 128;
				}

				if (Integer.parseInt(args[3]) < 3 || Integer.parseInt(args[3]) > m) { // tested to see if fourth argument is allowed and assigned the relevant value to number of blockades variable
					StdOut.println("Fourth input reset to default.");
					k = 3;
				} else {
					k = Integer.parseInt(args[3]);
				}

				String cell[][] = new String[m][m]; // declared a 2d cell array with sizes of the number of rows and columns

				for (int x = 0; x < m; x++) { // created a nested loop to assign each element of the cell array with the relevant sign
					for (int y = 0; y < m; y++) {
						if (y == 0) {
							cell[x][y] = ".";
						} else {
							cell[x][y] = "*";
						}
					}
				}

				StdOut.println("The dimension of your board is: " + m + "x" + m); // displayed games board size
				StdOut.println("The length of a blockade is: " + k); // displayed length of blockades to lose the game

				StdOut.print("\n"); // displayed a new line

				for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array to display and create the board
					for (int y = 0; y < m; y++) {
						StdOut.print(cell[x][y]);

					}
					StdOut.print("\n");
				}

				while (gameIsRunning) { // created while loop to loop infinitely until game is finished

					StdOut.print("\nMove: ");
					move = StdIn.readInt(); // asked user to input the move they would like to make and assigned it to move variable

					if (move == 0) { // tested to see what move user inputed to proceed with relevant actions

						StdOut.print("Row Number: ");
						row = StdIn.readInt(); // asked user to input the row they would like to choose and assigned it to row variable
						StdOut.print("Column Number: ");
						column = StdIn.readInt(); // asked user to input the column they would like to choose and assigned it to column variable

						if (row < 0 || row > (m - 1)) { // tested to see if the row the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (column < 0 || column > (m - 1)) { // tested to see if the column the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (cell[row][column] == "." || cell[row][column] == "*") { // tested to see how many cells from the column inputed are occupied
							StdOut.print("Invalid move: Nothing to delete!");
							continue;
						}

						for (int h = 0; h < (m - column); h++) { // looped the amount of times the column the user inputed/ is away from the column length variable
							if (h == 0) { // tested to see if the cells should be open or closed and set the cells in the array to the relevant signs
								cell[row][column] = ".";
							} else {
								cell[row][column + h] = "*";
							}
						}

						StdOut.print("\n"); // displayed a new line

						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}

						numMoves++; // incremented the number of moves by 1

					} else if (move == 2) { // tested to see what move user inputed to proceed with relevant actions

						StdOut.println("Termination: User terminated game!"); // displayed the reason for termination
						StdOut.println("Score: " + score(cell, m) + "%"); // displayed the score the player received
						StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
						StdOut.println("Game ended!"); // displayed that the game ended
						System.exit(1); // stopped the program from running

					} else if (move == 1) { // tested to see what move user inputed to proceed with relevant actions

						StdOut.print("Row Number: ");
						row = StdIn.readInt(); // asked user to input the row they would like to choose and assigned it to row variable
						StdOut.print("Column Number: ");
						column = StdIn.readInt(); // asked user to input the column they would like to choose and assigned it to column variable
						StdOut.print("Color: ");
						colourNum = StdIn.readInt(); // asked user to input the color they would like to choose and assigned it to color variable

						if (row < 0 || row > (m - 1)) { // tested to see if the row the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (column < 0 || column > (m - 1)) { // tested to see if the column the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (colourNum > n - 1 || colourNum < 0) { // tested to see if the color the user inputed is within constrains
							StdOut.print("Invalid move: Unknown color!");
							continue;
						}

						if (cell[row][column] == "*" || cell[row][column] != ".") { // tested to see if the cell the user chose is available
							StdOut.print("Invalid move: Cell is not open!");
							continue;
						}

						cell[row][column] = colour[colourNum]; // assigned the cell of the array the user chose with the color sign chosen
						if (column + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[row][column + 1] = "."; // assigned the cell with the next column of the array to the open sign
						}

						StdOut.print("\n"); // displayed a new line

						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}

						numMoves++; // incremented the number of moves by 1
						
						if(terminations(row, column, k, m, cell, colour[colourNum]) != "") {
							StdOut.println(terminations(row, column, k, m, cell, colour[colourNum])); // displayed the reason for termination
							StdOut.println("Score: " + score(cell, m) + "%"); // displayed the score the player received
							StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
							break;
						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							StdOut.println("\nTermination: You have won!"); // displayed the reason for termination
							StdOut.println("Score: 100%"); // displayed the score the player received
							StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
							break;
						}
						
						if(impasse(cell, m, n, colour, k) == true) {
							StdOut.println("\nTermination: Impasse!"); // displayed the reason for termination
							StdOut.println("Score: " + score(cell, m) + "%"); // displayed the score the player received
							StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
							break;
						}					

					} else {

						StdOut.print("Invalid move: Unknown move!"); // displayed this message after the move the player made was not one of the choices

					}
				}
				StdOut.println("Game ended!"); // displayed that the game ended
			}

		} else {

			if (Integer.parseInt(args[0]) != 0) {
				StdOut.println("First input reset to default.");
			}

			if (Integer.parseInt(args[1]) == 1) { // tested to see if the second argument is supported and directs control flow
				if (Integer.parseInt(args[2]) < 2 || Integer.parseInt(args[2]) > 4) { // tested to see if third argument is allowed and assigned the relevant value to number of colors variable
					StdOut.println("Third input reset to default.");
					n = 2;
				} else {
					n = Integer.parseInt(args[2]);
				}

				if (n == 2) { // tested to see the number of colors to assign the number of rows and column variable with the relevant number
					m = 8;
				} else if (n == 3) {
					m = 30;
				} else {
					m = 128;
				}
				
				if(n==2) { // tested the number of color variable to assign specific values to build the GUI
					scale = 1.0;
					height = 0.05;
				}else if(n == 3) {
					scale = 4.0;
					height = 0.07;
				} else {
					scale = 14.0;
					height = 0.09;
				}

				if (Integer.parseInt(args[3]) < 3 || Integer.parseInt(args[3]) > m) { // tested to see if fourth argument is allowed and assigned the relevant value to number of blockades variable
					StdOut.println("Fourth input reset to default.");
					k = 3;
				} else {
					k = Integer.parseInt(args[3]);
				}

				String cell[][] = new String[m][m]; // declared a 2d cell array with sizes of the number of rows and columns

				for (int x = 0; x < m; x++) { // created a nested loop to assign each element of the cell array with the relevant sign
					for (int y = 0; y < m; y++) {
						if (y == 0) {
							cell[x][y] = ".";
						} else {
							cell[x][y] = "*";
						}
					}
				}

				StdOut.println("The dimension of your board is: " + m + "x" + m); // displayed games board size
				StdOut.println("The length of a blockade is: " + k); // displayed length of blockades to lose the game
				
				double xNum = (scale) / (m + 1); // initialized variables to help with GUI 
				double yNum = (scale) / (m + 3);
				
				StdDraw.setScale(0.0, scale); //set the scale of the board
				StdDraw.clear(StdDraw.BLACK);
				StdDraw.setPenColor(StdDraw.WHITE);

				for (int y = 0; y < m; y++) { // created a nested loop to go through each element of the cell array and to create the board
					for (int x = 0; x < m; x++) {
						if (x == 0) {
							StdDraw.setPenColor(StdDraw.GRAY);
						}
						if (x == 0 && y == 0) {
							StdDraw.setPenColor(StdDraw.PINK);
							StdDraw.rectangle(xNum + (x * xNum), (scale) - yNum - (y * yNum), 0.05, 0.04);
							StdDraw.setPenColor(StdDraw.GRAY);
						}
						StdDraw.filledRectangle(xNum + (x * xNum), (scale) - yNum - (y * yNum), 0.05, 0.04);
						StdDraw.setPenColor(StdDraw.WHITE);
					}
				}

				int yCell = 0; //initialized variables to keep track of where the player moved
				int xCell = 0;
				
				StdOut.print("\n"); // displayed a new line

				for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array to display and create the board
					for (int y = 0; y < m; y++) {
						StdOut.print(cell[x][y]);

					}
					StdOut.print("\n");
				}

				while (gameIsRunning) { // created while loop to loop infinitely until game is finished

					if (StdDraw.isKeyPressed(83) && yCell < (m - 1)) { //tested to see if 's' key was pressed and made appropriate changes 	
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						yCell++;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(87) && yCell > (0)) { //tested to see if 'w' key was pressed and made appropriate changes 
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						yCell--;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(68) && xCell < (m - 1)) { //tested to see if 'd' key was pressed and made appropriate changes 
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						xCell++;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(65) && xCell > 0) { //tested to see if 'a' key was pressed and made appropriate changes 
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						xCell--;
						StdDraw.setPenColor(StdDraw.PINK);
						StdDraw.rectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(81)) { //tested to see if 'q' key was pressed and made appropriate changes 
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: User terminated game!");
						StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
						StdDraw.pause(300);
						break;
					}

					if (StdDraw.isKeyPressed(88)) { //tested to see if 'x' key was pressed and made appropriate changes 
						if (cell[yCell][xCell] == "." || cell[yCell][xCell] == "*") { // tested to see how many cells from the column inputed are occupied
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Nothing to delete!");
							StdDraw.pause(300);
							continue;
						}

						for (int h = 0; h < (m - xCell); h++) { // looped the amount of times the column the user inputed is away from the column length variable
							if (h == 0) { // tested to see if the cells should be open or closed and set the cells in the array to the relevant signs
								cell[yCell][xCell] = ".";
							} else {
								cell[yCell][xCell + h] = "*";
							}
						}
						for (int h = 0; h < (m - xCell); h++) { // looped the amount of times the column the user inputed is away from the column length variable
							if (h == 0) { // tested to see if the cells should be open or closed and displayed the relevant cell color
								StdDraw.setPenColor(StdDraw.GRAY);
								StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
							} else {
								StdDraw.setPenColor(StdDraw.WHITE);
								StdDraw.filledRectangle(xNum + ((xCell + h) * xNum), scale - yNum - (yCell * yNum), 0.05,0.04);
							}
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}

						numMoves++; // incremented the number of moves by 1
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(48)) { //tested to see if '0' key was pressed and made appropriate changes 
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!");
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[0]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.GREEN);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04); 
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1
						
						if (blockade(yCell, xCell, k, m, cell) >= k) { // tested to see if the number of rows in row with the same color equals the number of blockades
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have caused a blockade!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;

						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
						
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale*0.5, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); //displayed that the move was valid
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(49)) { //tested to see if '1' key was pressed and made appropriate changes 
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale*0.5, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!"); 
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[1]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.YELLOW);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1						

						if (blockade(yCell, xCell, k, m, cell) >= k) { // tested to see if the number of rows in row with the same color equals the number of blockades
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have caused a blockade!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;

						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}
				
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); // displayed that the move was valid
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(50) && n > 2) {
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!");
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[2]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05,
									0.04);
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1
						
						if (blockade(yCell, xCell, k, m, cell) >= k) { // tested to see if the number of rows in row with the same color equals the number of blockades
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have caused a blockade!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}

						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), 0.5, 0.07);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); // displayed that the move was valid
						StdDraw.pause(300);
					}

					if (StdDraw.isKeyPressed(51) && n > 3) {
						if (cell[yCell][xCell] != ".") { // tested to see if the cell the user chose is available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Invalid move: Cell is not open!");
							StdDraw.pause(300);
							continue;
						}
						cell[yCell][xCell] = colour[3]; // assigned the cell of the array the user chose with the color sign chosen
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.filledRectangle(xNum + (xCell * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						if (xCell + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[yCell][xCell + 1] = "."; // assigned the cell with the next column of the array to the open sign
							StdDraw.setPenColor(StdDraw.GRAY);
							StdDraw.filledRectangle(xNum + ((xCell + 1) * xNum), scale - yNum - (yCell * yNum), 0.05, 0.04);
						}
						
						StdOut.print("\n"); // displayed a new line
						
						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}
						
						numMoves++; // incremented the number of moves by 1

						if (blockade(yCell, xCell, k, m, cell) >= k) { // tested to see if the number of rows in row with the same color equals the number of blockades
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have caused a blockade!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;

						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Termination: You have won!"); // displayed termination message and score
							StdDraw.text(scale*0.5, yNum - (0 * yNum), "Score: " + score(cell, m) + "%, Moves: " + numMoves);
							break;
						}

						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledRectangle(scale*0.5, yNum - (-1 * yNum), scale, height);
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(scale*0.5, yNum - (-1 * yNum), "Valid"); // displayed that the move was valid 
						StdDraw.pause(300);
					}

				}

				StdOut.print("\n"); // displayed a new line
				StdOut.println("Game ended!"); // displayed that the game ended

			} else {
				if (Integer.parseInt(args[1]) != 0) {
					StdOut.println("Second input reset to default.");
				}
				if (Integer.parseInt(args[2]) < 2 || Integer.parseInt(args[2]) > 4) { // tested to see if third argument is allowed and assigned the relevant value to number of colors variable
					StdOut.println("Third input reset to default.");
					n = 2;
				} else {
					n = Integer.parseInt(args[2]);
				}

				if (n == 2) { // tested to see the number of colors to assign the number of rows and column variable with the relevant number
					m = 8;
				} else if (n == 3) {
					m = 30;
				} else {
					m = 128;
				}

				if (Integer.parseInt(args[3]) < 3 || Integer.parseInt(args[3]) > m) { // tested to see if fourth argument is allowed and assigned the relevant value to number of blockades variable
					StdOut.println("Fourth input reset to default.");
					k = 3;
				} else {
					k = Integer.parseInt(args[3]);
				}

				String cell[][] = new String[m][m]; // declared a 2d cell array with sizes of the number of rows and columns

				for (int x = 0; x < m; x++) { // created a nested loop to assign each element of the cell array with the relevant sign
					for (int y = 0; y < m; y++) {
						if (y == 0) {
							cell[x][y] = ".";
						} else {
							cell[x][y] = "*";
						}
					}
				}

				StdOut.println("The dimension of your board is: " + m + "x" + m); // displayed games board size
				StdOut.println("The length of a blockade is: " + k); // displayed length of blockades to lose the game

				StdOut.print("\n"); // displayed a new line

				for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
					for (int y = 0; y < m; y++) {
						StdOut.print(cell[x][y]);
					}
					StdOut.print("\n");
				}

				while (gameIsRunning) { // created while loop to loop infinitely until game is finished

					StdOut.print("\nMove: ");
					move = StdIn.readInt(); // asked user to input the move they would like to make and assigned it to move variable

					if (move == 0) { // tested to see what move user inputed to proceed with relevant actions
						
						StdOut.print("Row Number: ");
						row = StdIn.readInt(); // asked user to input the row they would like to choose and assigned it to row variable
						StdOut.print("Column Number: ");
						column = StdIn.readInt(); // asked user to input the column they would like to choose and assigned it to column variable

						if (row < 0 || row > (m - 1)) { // tested to see if the row the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (column < 0 || column > (m - 1)) { // tested to see if the column the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (cell[row][column] == "." || cell[row][column] == "*") { // tested to see how many cells from the column inputed are occupied
							StdOut.print("Invalid move: Nothing to delete!");
							continue;
						}

						for (int h = 0; h < (m - column); h++) { // looped the amount of times the column the user inputed is away from the column length variable
							if (h == 0) { // tested to see if the cells should be open or closed and set the cells in the array to the relevant signs
								cell[row][column] = ".";
							} else {
								cell[row][column + h] = "*";
							}
						}

						StdOut.print("\n"); // displayed a new line

						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}

						numMoves++; // incremented the number of moves by 1

					} else if (move == 2) { // tested to see what move user inputed to proceed with relevant actions

						StdOut.println("Termination: User terminated game!"); // displayed the reason for termination
						StdOut.println("Score: " + score(cell, m) + "%"); // displayed the score the player received
						StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
						StdOut.println("Game ended!"); // displayed that the game ended
						System.exit(1); // stopped the program from running

					} else if (move == 1) { // tested to see what move user inputed to proceed with relevant actions

						StdOut.print("Row Number: ");
						row = StdIn.readInt(); // asked user to input the row they would like to choose and assigned it to row variable
						StdOut.print("Column Number: ");
						column = StdIn.readInt(); // asked user to input the column they would like to choose and assigned it to column variable
						StdOut.print("Color: ");
						colourNum = StdIn.readInt(); // asked user to input the color they would like to choose and assigned it to color variable

						if (row < 0 || row > (m - 1)) { // tested to see if the row the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (column < 0 || column > (m - 1)) { // tested to see if the column the user inputed is within constrains
							StdOut.print("Invalid move: Outside of board!");
							continue;
						}

						if (colourNum > n - 1 || colourNum < 0) { // tested to see if the color the user inputed is within constrains
							StdOut.print("Invalid move: Unknown color!");
							continue;
						}

						if (cell[row][column] == "*" || cell[row][column] != ".") { // tested to see if the cell the user chose is available
							StdOut.print("Invalid move: Cell is not open!");
							continue;
						}

						cell[row][column] = colour[colourNum]; // assigned the cell of the array the user chose with the color sign chosen
						if (column + 1 != m) { // tested to see if there was another column after current column user inputed
							cell[row][column + 1] = "."; // assigned the cell with the next column of the array to the open sign
						}

						StdOut.print("\n"); // displayed a new line

						for (int x = 0; x < m; x++) { // created a nested loop to go through each element of the cell array and to display it to create the board
							for (int y = 0; y < m; y++) {
								StdOut.print(cell[x][y]);
							}
							StdOut.print("\n");
						}

						numMoves++; // incremented the number of moves by 1

						if (blockade(row, column, k, m, cell) >= k) { // tested to see if the number of rows in row with the same color equals the number of blockades
							StdOut.println("\nTermination: Blockade!"); // displayed the reason for termination
							StdOut.println("Score: " + score(cell, m) + "%"); // displayed the score the player received
							StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
							break;
						}

						if (won(cell, m) == true) { // tested to see if the number of occupied cells is equal to all the all the cells available
							StdOut.println("\nTermination: You have won!"); // displayed the reason for termination
							StdOut.println("Score: 100%"); // displayed the score the player received
							StdOut.println("Moves: " + numMoves); // displayed the number of moves the player made
							break;
						}

					} else {

						StdOut.print("Invalid move: Unknown move!"); // displayed this message after the move the player made was not one of the choices
					}
				}

				StdOut.println("Game ended!"); // displayed that the game ended
			}
		}
	}

}
