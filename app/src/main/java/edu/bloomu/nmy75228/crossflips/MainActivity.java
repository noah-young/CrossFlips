package edu.bloomu.nmy75228.crossflips;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Debug;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Frontend for CrossFlips game. This part creates a CrossFlips object and generates a
 * grid layout that is used as the game board. Most of the frontend code for the game is handled
 * in the onClick method within each disc, while the updating of the discs is handled in the
 * updateBoard method and the initialization of a new board is handled in the initializeBoard
 * method.
 *
 * @author Noah Young
 * @version 25 Feb 2020
 */
public class MainActivity extends AppCompatActivity {

    private int ROWS;
    private int COLS;
    private int moveCount;

    private ImageView[][] imageViews;// = new ImageView[ROWS][COLS];

    private Animation animation;

    private CrossFlips crossFlips;// = new CrossFlips(ROWS, COLS, 1);

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = crossFlips.getHint();

                if(!crossFlips.allYellow()) {
                    int x = Integer.parseInt(String.valueOf(s.charAt(1)));
                    int y = Integer.parseInt(String.valueOf(s.charAt(4)));
                    imageViews[x][y].startAnimation(animation);
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.disc_shake);
        gridLayout = findViewById(R.id.grid_layout);

        initializeBoard(6, 6, 3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /**
         * This if-else statement checks to see which difficulty has been selected and
         * then initializes a new board based on that difficulty
         */
        if (id == R.id.easy) {
            initializeBoard(4, 4, 3);
        } else if (id == R.id.Medium) {
            initializeBoard(5, 5, 5);
        } else if (id == R.id.Hard) {
            initializeBoard(6, 6, 7);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Used to initialize the game board. Takes in three parameters to determine the
     * size of the board and the number of random moves.
     *
     * @param x the number of rows of the board
     * @param y the number of columns of the board
     * @param m the number of starting moves to initialize the board with
     */
    public void initializeBoard(int x, int y, int m) {
        gridLayout.removeAllViews(); // Reset gridLayout
        ROWS = x;
        COLS = y;
        moveCount = 0; // Reset move counter
        gridLayout.setRowCount(ROWS);
        gridLayout.setColumnCount(COLS);
        String imageName;
        crossFlips = new CrossFlips(ROWS, COLS, m);
        imageViews = new ImageView[ROWS][COLS];
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                ImageView imageView = new ImageView(getApplicationContext());
                if(crossFlips.isYellow(i, j))
                    imageName = "yellow";
                else
                    imageName = "red";
                int imageId = getResources().getIdentifier(imageName, "drawable",
                        getPackageName());
                imageView.setImageResource(imageId);

                //set size of images
                imageView.setAdjustViewBounds(true);
                imageView.setMaxWidth(100);
                imageView.setMaxHeight(100);

                //padding/make it look nice
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setMargins(8, 8, 8, 8);
                imageView.setLayoutParams(params);

                //event handlers
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < ROWS; i++) {
                            for(int j = 0; j < COLS; j++) {
                                if(imageViews[i][j] == v && !crossFlips.allYellow() && crossFlips.validMove(i, j)) {
                                    crossFlips.move(i, j);
                                    moveCount++;
                                    updateBoard();
                                }
                            }
                        }
                    }
                });

                gridLayout.addView(imageView);
                imageViews[i][j] = imageView;
            }
        }
    }

    /**
     * Event handler for updating the color of each disc on the board.
     * Also responsible for checking to see if the game has been won.
     */
    public void updateBoard() {
        String imageName;
        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (crossFlips.isYellow(i, j))
                    imageName = "yellow";
                else
                    imageName = "red";
                int imageId = getResources().getIdentifier(imageName, "drawable",
                        getPackageName());
                imageViews[i][j].setImageResource(imageId);
            }
        }
        if(crossFlips.allYellow()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "YOU WON! Total moves: " + moveCount +
                            "\nTo play again, select a difficulty from the menu in the top right hand corner.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 200);
            toast.show();
        }
    }

    /**
     * Event handler for hint animation
     */
    public void shakeDisc(View view) {
        view.startAnimation(animation);
    }
}
