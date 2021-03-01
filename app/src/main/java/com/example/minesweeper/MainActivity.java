package com.example.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnStartGame;
    private RecyclerView mRv;
    private GridAdapter mAdapter;
    private int mSpanCount = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        setListener();
    }

    private void initViews() {
        mBtnStartGame = findViewById(R.id.btn_start_game);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(this, mSpanCount));
    }

    private void initData() {
        mAdapter = new GridAdapter(this, mSpanCount);
        mRv.setAdapter(mAdapter);
    }

    private void setListener() {
        mBtnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        mAdapter.setCallBack(new GridAdapter.CallBack() {
            @Override
            public void gameOver() {
                showGameOver();
            }
        });
    }

    private void startGame() {
        int[][] data = DataUtil.markedSquare(DataUtil.randomSquare09(mSpanCount));
        mAdapter.setData(data);
    }

    private void showGameOver() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.game_over))
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startGame();
                    }
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
