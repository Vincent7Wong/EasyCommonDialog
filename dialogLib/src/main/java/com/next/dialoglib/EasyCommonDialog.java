package com.next.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class EasyCommonDialog extends Dialog {

    private static WeakReference<EasyCommonDialog> commonDialogWeakReference;

    public EasyCommonDialog(@NonNull Context context) {
        super(context);
    }


    private static EasyCommonDialog getDialog() {
        return commonDialogWeakReference == null ? null : commonDialogWeakReference.get();
    }

    public static void dismissEasyCommonDialog() {
        EasyCommonDialog dialog = getDialog();
        if (null == dialog) {
            return;
        }
        commonDialogWeakReference.clear();
        if (dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mContent;
        private int mBgColor = Color.parseColor("#ffffff");

        private float mRadius = 10f;
        private boolean mCancelable = false;
        //分割线的宽度
        private float mLineHeight = 0.5f;
        //分割线颜色
        private int mLineColor = Color.parseColor("#E6E6E6");
        //标题文字颜色
        private int mTitleTextColor = Color.parseColor("#333333");
        //标题内容颜色
        private int mContentTextColor = Color.parseColor("#333333");
        //右边的文字颜色
        private int mRightTextColor = Color.parseColor("#333333");
        //左边的文字颜色
        private int mLeftTextColor = Color.parseColor("#A8A7AE");

        //标题文字大小
        private float mTitleTextSize = 17;
        //内容文字大小
        private float mContentTextSize = 17;
        //左边文字大小
        private float mLeftTextSize = 14;
        //右边文字大小
        private float mRightTextSize = 14;

        //标题字体是否粗体
        private boolean isTitleBold = false;
        //内容字体是否粗体
        private boolean isContentBold = false;
        //左边字体是否粗体
        private boolean isLeftBold = true;
        //右边字体是否粗体
        private boolean isRightBold = true;

        private DialogInterface.OnRightListener mOnRightListener;
        private DialogInterface.OnLeftListener mOnLeftListener;
        private LinearLayout ll_dialog;
        //标题
        private TextView tv_title;
        //内容
        private TextView tv_content;
        private Button btn_dialog_right;
        private Button btn_dialog_left;
        private View view_line01;
        private View view_line02;
        //字体单位DP、SP
        private int mTextSizeType = 1;

        @IntDef({
                TextSizeType.TYPE_DP,
                TextSizeType.TYPE_SP,
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface TextSizeType {
            //文字单位：1、DP   2、SP
            int TYPE_DP = 1;
            int TYPE_SP = 2;
        }

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder titleBold(boolean titleBold) {
            isTitleBold = titleBold;
            return this;
        }

        public Builder contentBold(boolean contentBold) {
            isContentBold = contentBold;
            return this;
        }

        public Builder leftBold(boolean leftBold) {
            isLeftBold = leftBold;
            return this;
        }

        public Builder rightBold(boolean rightBold) {
            isRightBold = rightBold;
            return this;
        }

        public float getmTitleTextSize() {
            return mTitleTextSize;
        }

        public Builder titleTextSize(float mTitleTextSize) {
            this.mTitleTextSize = mTitleTextSize;
            return this;
        }

        public float getmContentTextSize() {
            return mContentTextSize;
        }

        public Builder contentTextSize(float mContentTextSize) {
            this.mContentTextSize = mContentTextSize;
            return this;
        }

        public float getmLeftTextSize() {
            return mLeftTextSize;
        }

        public Builder leftTextSize(float mLeftTextSize) {
            this.mLeftTextSize = mLeftTextSize;
            return this;
        }

        public float getmRightTextSize() {
            return mRightTextSize;
        }

        public Builder rightTextSize(float mRightTextSize) {
            this.mRightTextSize = mRightTextSize;
            return this;
        }

        public Builder contentTextColor(int mContentTextColor) {
            this.mContentTextColor = mContentTextColor;
            return this;
        }

        public Builder textSizeType(int mTextSizeType) {
            this.mTextSizeType = mTextSizeType;
            return this;
        }


        public Builder rightTextColor(int mRightTextColor) {
            this.mRightTextColor = mRightTextColor;
            return this;
        }

        public Builder leftTextColor(int mLeftTextColor) {
            this.mLeftTextColor = mLeftTextColor;
            return this;
        }

        public Builder lineHeight(float lineHeight) {
            this.mLineHeight = lineHeight;
            return this;
        }

        public Builder title(String mTitle) {
            this.mTitle = mTitle;
            return this;
        }

        public Builder titleTextColor(int titleTextColor) {
            this.mTitleTextColor = titleTextColor;
            return this;
        }

        public Builder content(String mContent) {
            this.mContent = mContent;
            return this;
        }

        public Builder radius(float radius) {
            mRadius = radius;
            return this;
        }

        public Builder bgColor(int mBgColor) {
            this.mBgColor = mBgColor;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setOnRightListener(DialogInterface.OnRightListener mOnRightListener) {
            this.mOnRightListener = mOnRightListener;
            return this;
        }

        public Builder setOnLeftListener(DialogInterface.OnLeftListener mOnLeftListener) {
            this.mOnLeftListener = mOnLeftListener;
            return this;
        }

        @NonNull
        public EasyCommonDialog create() {
            EasyCommonDialog dialog = getDialog();

            if (dialog != null && dialog.getContext() != mContext) {
                dismissEasyCommonDialog();
                dialog = null;
            }

            if (dialog == null) {
                dialog = new EasyCommonDialog(mContext);
                commonDialogWeakReference = new WeakReference<>(dialog);
            }

            final EasyCommonDialog finalDialog = dialog;


            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout_easy_common, null);

            ll_dialog = view.findViewById(R.id.ll_dialog);
            DialogUtil.setRoundRectBg(mContext, ll_dialog, mRadius, mBgColor);

            btn_dialog_right = view.findViewById(R.id.btn_dialog_right);
            btn_dialog_right.setTextColor(mLeftTextColor);
            btn_dialog_right.setTextSize(mTextSizeType, mRightTextSize);
            TextPaint paint = btn_dialog_right.getPaint();
            paint.setFakeBoldText(isRightBold);
            btn_dialog_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRightListener != null) {
                        if (!mOnRightListener.onRightEvent(finalDialog)) {
                            dismissEasyCommonDialog();
                        }
                    } else {
                        dismissEasyCommonDialog();
                    }
                }
            });

            btn_dialog_left = view.findViewById(R.id.btn_dialog_left);
            btn_dialog_left.setTextColor(mRightTextColor);
            btn_dialog_left.setTextSize(mTextSizeType, mLeftTextSize);
            TextPaint paint2 = btn_dialog_left.getPaint();
            paint2.setFakeBoldText(isLeftBold);
            btn_dialog_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnLeftListener != null) {
                        if (!mOnLeftListener.onLeftEvent(finalDialog)) {
                            dismissEasyCommonDialog();
                        }
                    } else {
                        dismissEasyCommonDialog();
                    }

                }
            });

            tv_title = view.findViewById(R.id.tv_title);
            if (tv_title != null) {
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setTextSize(mTextSizeType, mTitleTextSize);
                TextPaint titlePaint = tv_title.getPaint();
                titlePaint.setFakeBoldText(isTitleBold);
                tv_title.setTextColor(mTitleTextColor);
                if (!TextUtils.isEmpty(mTitle))
                    tv_title.setText(mTitle);
            }

            tv_content = view.findViewById(R.id.tv_content);
            if (tv_content != null) {
                tv_content.setVisibility(View.VISIBLE);
                tv_content.setTextSize(mTextSizeType, mContentTextSize);
                TextPaint contentPaint = tv_content.getPaint();
                contentPaint.setFakeBoldText(isContentBold);
                tv_content.setTextColor(mContentTextColor);
                if (!TextUtils.isEmpty(mContent))
                    tv_content.setText(mContent);
            }


            view_line01 = view.findViewById(R.id.view_line01);
            view_line02 = view.findViewById(R.id.view_line02);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view_line01.getLayoutParams();
            params.height = DialogUtil.dip2px(mContext, mLineHeight);
            view_line01.setLayoutParams(params);
            view_line01.setBackgroundColor(mLineColor);

            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) view_line02.getLayoutParams();
            params.width = DialogUtil.dip2px(mContext, mLineHeight);
            view_line02.setLayoutParams(params2);
            view_line02.setBackgroundColor(mLineColor);


            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog.setCanceledOnTouchOutside(mCancelable);
            return dialog;
        }
    }


}
