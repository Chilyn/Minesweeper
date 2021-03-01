package com.example.minesweeper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int MINE_NUMBER = 9;
    private static final int NO_MINE_AROUND_NUMBER = 0;
    private static final int GAME_STATE_IDLE = 0;
    private static final int GAME_STATE_PLAYING = 1;
    private static final int GAME_STATE_WIN = 2;
    private static final int GAME_STATE_LOSE = 3;
    private Context mContext;
    private int mSpanCount;
    private int[][] mArrayData;
    private List<GridDataBean> mListData = new ArrayList<>();
    private int mGameState = GAME_STATE_IDLE;
    private int[] mGridIcons = {R.drawable.zero, R.drawable.one, R.drawable.two,
            R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight,
            R.drawable.mine};

    public GridAdapter(Context context, int spanCount) {
        mContext = context;
        mSpanCount = spanCount;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_grid, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        GridDataBean dataBean = mListData.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.itemView.setTag(position);
        if (mGameState == GAME_STATE_WIN ||
                mGameState == GAME_STATE_LOSE ||
                dataBean.isShow()) {
//            holder.mTvNumber.setText(String.valueOf(dataBean.getNumber()));
            if (mGameState == GAME_STATE_LOSE && dataBean.isBlood()) {
                holder.mIvGrid.setImageResource(R.drawable.blood);
            } else {
                holder.mIvGrid.setImageResource(mGridIcons[dataBean.getNumber()]);
            }
        } else {
//            holder.mTvNumber.setText("");
            holder.mIvGrid.setImageResource(R.drawable.blank);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setData(int[][] data) {
        if (data != null) {
            mArrayData = data;
            mListData.clear();
            for (int i = 0; i < data.length; i++) {
                Log.e("aaaaaaaa", Arrays.toString(data[i]));
                for (int j = 0; j < data[i].length; j++) {
                    GridDataBean dataBean = new GridDataBean(data[i][j], false);
                    mListData.add(dataBean);
                }
            }

            mGameState = GAME_STATE_PLAYING;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.root:
                onItemClick((Integer) v.getTag());
                break;
        }
    }

    private void onItemClick(int position) {
        if (mGameState == GAME_STATE_LOSE || mGameState == GAME_STATE_WIN) {
            return;
        }

        GridDataBean dataBean = mListData.get(position);
        if (dataBean.getNumber() == MINE_NUMBER) {
            mGameState = GAME_STATE_LOSE;
            dataBean.setIsBlood(true);
            notifyDataSetChanged();
            if (mCallBack != null) {
                mCallBack.gameOver();
            }
            return;
        }

        dataBean.setIsShow(true);
        notifyItemChanged(position);
        if (dataBean.getNumber() == NO_MINE_AROUND_NUMBER) {
            showAroundGrid(position / mSpanCount, position % mSpanCount);
        }

        if (isWin()) {
            Toast.makeText(mContext, mContext.getString(R.string.you_win), Toast.LENGTH_LONG).show();
            mGameState = GAME_STATE_WIN;
            notifyDataSetChanged();
        }
    }

    /**
     * 找出数字为0格子周围8个格子，如果不是9就翻开，如果是0，继续翻开周围8个格子
     * @param row
     * @param column
     */
    private void showAroundGrid(int row, int column) {
        /*
           [
            [0, 2, 9, 9],
            [1, 3, 9, 9],
            [9, 5, 5, 4],
            [9, 9, 9, 9],
           ]
        */
        int[][] aroundGridRowColumns = {
                {row - 1, column - 1}, {row - 1, column}, {row - 1, column + 1},
                {row, column - 1}, {row, column + 1},
                {row + 1, column - 1}, {row + 1, column}, {row + 1, column + 1},
        };

        for (int i = 0; i < aroundGridRowColumns.length; i++) {
            if (setAroundGridShowHide(aroundGridRowColumns[i][0], aroundGridRowColumns[i][1])) {
                showAroundGrid(aroundGridRowColumns[i][0], aroundGridRowColumns[i][1]);
            }
        }
    }

    /**
     *
     * @param row
     * @param column
     * @return true 数字为0, false 数字不为0
     */
    private boolean setAroundGridShowHide(int row, int column) {
        if (row < 0 || row >= mArrayData.length || column < 0 || column >= mArrayData.length) {
            return false;
        }

        boolean isShownBefore = false;
        int number = mArrayData[row][column];
        if (number != 9) {
            int position = row * mSpanCount + column;
            GridDataBean dataBean = mListData.get(position);
            if (dataBean.isShow()) {
                isShownBefore = true;
            } else {
                dataBean.setIsShow(true);
                notifyItemChanged(position);
            }
        }

        return number == 0 && !isShownBefore;//数字为0，并且之前没显示出来才需要显示，不然showAroundGrid会死循环
    }

    private boolean isWin() {
        GridDataBean dataBean;
        for (int i = 0; i < mListData.size(); i++) {
            dataBean = mListData.get(i);
            if (dataBean.getNumber() != MINE_NUMBER && !dataBean.isShow()) {
                return false;
            }
        }

        return true;
    }

    private void plus1(int[][] array, int x, int y) {
        // 1. array[x][y] 不能是 9
        // x 和 y 符合条件, 比如不能越界
        int n = array.length;
        if (x >= 0 && x < n && y >= 0 && y < n) {
            if (array[x][y] != 9) {
                array[x][y] += 1;
            }
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvNumber;
        private ImageView mIvGrid;

        ViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            mTvNumber = itemView.findViewById(R.id.tv_number);
            mIvGrid = itemView.findViewById(R.id.iv_grid);
            itemView.setOnClickListener(listener);
        }
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void gameOver();
    }
}
