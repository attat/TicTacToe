package com.tat.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GameBoard board = null;
    private int moveCount = 0, xloc = 0, yloc = 0;
    private String mark = "X", opponent = "O";
    private boolean isOver = false;


    //makes board
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //uses activity_main
        setContentView(R.layout.activity_main);

        //sets up board with ai
        board = new GameBoard();
    }

    //resets board
    public void resetClick(View v) {
        clear();
    }

    public void cellClick(View v) {
        TextView cell = (TextView) findViewById(v.getId());
        //Check and make sure the cell is empty and that the game isn't over
        String content = (String) cell.getText();
        if (content == "" && !isOver) {

            //Find the X Y location values of cell that was clicked
            switch (cell.getId())
            {
                case R.id.cell11:
                    xloc = 0;
                    yloc = 0;
                    break;
                case R.id.cell12:
                    xloc = 0;
                    yloc = 1;
                    break;
                case R.id.cell13:
                    xloc = 0;
                    yloc = 2;
                    break;
                case R.id.cell21:
                    xloc = 1;
                    yloc = 0;
                    break;
                case R.id.cell22:
                    xloc = 1;
                    yloc = 1;
                    break;
                case R.id.cell23:
                    xloc = 1;
                    yloc = 2;
                    break;
                case R.id.cell31:
                    xloc = 2;
                    yloc = 0;
                    break;
                case R.id.cell32:
                    xloc = 2;
                    yloc = 1;
                    break;
                case R.id.cell33:
                    xloc = 2;
                    yloc = 2;
                    break;
            }
            //switches players
            if(moveCount%2 == 0) {
                //Place the player's mark on the specific X Y location on both the virtual and displayed board
                board.placeMark(xloc, yloc, mark);
                cell.setText(mark);

                //Increment move Count because a move was just made
                moveCount++;

                //Check to see if the game is over
                isOver = checkEnd(mark);
            }
            else {
                //Place the player's mark on the specific X Y location on both the virtual and displayed board
                board.placeMark(xloc, yloc, opponent);
                cell.setText(opponent);

                //Increment move Count because a move was just made
                moveCount++;

                //Check to see if the game is over
                isOver = checkEnd(opponent);
            }

        }
    }
    //Checks to see if the game has ended provided with the last player to make a move
    private boolean checkEnd(String player) {
        //Checks the virtual board for a winner if there's a winner announce it with the provided player
        if (board.isWinner())
        {

            announce(true, player);
            return true;
        }

        //Check to see if we've reached our move total meaning it's a draw
        else if (moveCount >= 9)
        {

            announce(false, player);
            return true;

        }
        //If neither win or draw then the game is still on
        return false;

    }

    //Announce the winner, given a boolean for whether it was a win or a draw
    // and given the last player to make a mark
    private void announce(boolean endState, String player) {
        //shows winner or draws
        if (endState == true)
            player = player + " wins!";
        else
            player = "It's a draw!";

        //Get the application Context and setup the Toast notification with the end state info
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
        toast.show();

    }

    //Clears the game Board
    private void clear() {
        //Get the id list of all the Textview cells
        int[] idList = { R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33 };
        TextView cell;
        //reset all cells to empty string
        for (int item : idList) {

            cell = (TextView) findViewById(item);
            cell.setText("");

        }

        //Reset all variables
        isOver = false;
        moveCount = 0;
        board.clear();
    }

}