package technovision.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by hp on 1/15/2017.
 */

public class MineButton extends Button {
    private boolean minePresent;
    public int SurroundingMines;

    public MineButton(Context context) {
        super(context);
    }

    public boolean isMinePresent() {
        return minePresent;
    }

    public void setMinePresent(boolean minePresent) {
        this.minePresent = minePresent;
    }

//    public int getSurroundingMines() {
//        return SurroundingMines;
//    }
//
//    public void setSurroundingMines(int surroundingMines) {
//        SurroundingMines = surroundingMines;
//    }
}
