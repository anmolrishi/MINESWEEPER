package technovision.minesweeper;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import static technovision.minesweeper.R.color.colorAccent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout mainLayout;
    LinearLayout[] rows;
    MineButton[][] buttons;
    boolean gameOver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (LinearLayout) findViewById(R.id.activity_main);

        SetUpBoard();
    }

    private void SetUpBoard() {
        buttons = new MineButton[8][8];

        rows = new LinearLayout[8];
        for (int i=0; i < 8 ; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);

            mainLayout.addView(rows[i]);
        }
        for(int i=0; i<8; i++){
            for(int j=0;j<8;j++){
                 buttons[i][j] = new MineButton(this);
                 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                params.setMargins(2,2,2,2);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);

                rows[i].addView(buttons[i][j]);
            }
        }

        addMines();
        addSurroundings();
   }

    private void addSurroundings() {
        int[] x = {-1,-1,-1,0,1,1,1,0};
        int[] y = {-1,0,1,1,1,0,-1,-1};
        for(int i=0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if(buttons[i][j].isMinePresent()){
                    for (int ind =0; ind<x.length;ind++){
                       if(setCoordinates(i+x[ind],j+y[ind])){
                           buttons[i+x[ind]][j+y[ind]].SurroundingMines++;
                       }

                    }
                }

            }
        }
    }

    private boolean setCoordinates(int a,int b) {
        if(a<0 || a>7){
            return false;
        }
        else if(b<0 || b>7){
            return false;
        }
        else{
            return true;
        }

    }


    private void addMines() {
        buttons[0][0].setMinePresent(true);
        buttons[1][1].setMinePresent(true);
        buttons[2][2].setMinePresent(true);
        buttons[3][3].setMinePresent(true);
        buttons[4][4].setMinePresent(true);
        buttons[5][5].setMinePresent(true);
        buttons[6][6].setMinePresent(true);
        buttons[7][7].setMinePresent(true);

    }



    @Override
    public void onClick(View v) {
        MineButton b = (MineButton)v;

        if(b.isMinePresent()){
            gameOver = true;
            endGame();
            Toast.makeText(this,"GAME OVER",Toast.LENGTH_SHORT).show();
        }
        else{
            gameOver=false;
            Search(b);
        }



    }

    private void Search(MineButton currentButton) {

        for(int i=0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if(buttons[i][j].equals(currentButton)){
                    action(i,j);
                    return;
                }

            }
        }
    }

    private void action(int i, int j) {

        int[] x = {-1,-1,-1,0,1,1,1,0};
        int[] y = {-1,0,1,1,1,0,-1,-1};

        if(!setCoordinates(i,j)){
            return;
        }
        else if(buttons[i][j].isMinePresent()){
            return;
        }
        else if(buttons[i][j].SurroundingMines!=0){
            buttons[i][j].setText(buttons[i][j].SurroundingMines + "");
            return;
        }

       // buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        buttons[i][j].setText(buttons[i][j].SurroundingMines + "");


        for (int ind =0; ind<x.length;ind++) {
            if(setCoordinates(i+x[ind],j+y[ind])){
                if(buttons[i+x[ind]][j+y[ind]].SurroundingMines==0){
                    action(i+x[ind],j+y[ind]);
                }
            }

        }
    }


    private void endGame() {
        for(int i=0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if(buttons[i][j].isMinePresent()){
                    buttons[i][j].setText("*");
                    buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
                }
                else{
                    buttons[i][j].setText(buttons[i][j].SurroundingMines + "");
                }

            }
        }
    }
}
