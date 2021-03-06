package com.example.shume.game2048;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import java.util.Observable;

public class GestureDetectGridView2048 extends GridView {
    public static final int SWIPE_MIN_DISTANCE = 75;
    public static final int SWIPE_MAX_OFF_PATH = 100;
    public static final int SWIPE_THRESHOLD_VELOCITY = 75;
    private GestureDetector gDetector;
    private MovementController2048 mController;
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;
    private BoardManager2048 boardManager;

    public GestureDetectGridView2048(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView2048(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView2048(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView2048(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        mController = new MovementController2048();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView2048.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                //mController.processTapMovement(context, position, true);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {
            float dX = ev.getX() - mTouchX;
            float dY = ev.getY() - mTouchY;
            if ((Math.abs(dX) > SWIPE_MIN_DISTANCE) || (Math.abs(dY) > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                if (Math.abs(dX) >= Math.abs(dY)) {
                    if (dX > 0) {
                        mController.processSwipeMovement(getContext(), 3);
                        System.out.println("SWIPING - RIGHT");
                        System.out.println(boardManager.getBoard());
                    } else {
                        mController.processSwipeMovement(getContext(), 1);
                        System.out.println("SWIPING - LEFT");
                        System.out.println(boardManager.getBoard());
                    }
                }
                else if (Math.abs(dY) >= Math.abs(dX)){
                    if (dY <= 0) {
                        mController.processSwipeMovement(getContext(), 2);
                        System.out.println("SWIPING - UP");
                        System.out.println(boardManager.getBoard());
                    } else {
                        mController.processSwipeMovement(getContext(), 4);
                        System.out.println("SWIPING - DOWN");
                        System.out.println(boardManager.getBoard());
                    }
                }
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    public void setBoardManager(BoardManager2048 boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}

