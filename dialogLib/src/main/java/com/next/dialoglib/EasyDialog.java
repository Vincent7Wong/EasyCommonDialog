package com.next.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class EasyDialog extends Dialog {

    private static WeakReference<EasyDialog> commonDialogWeakReference;

    public EasyDialog(@NonNull Context context) {
        super(context);
    }


    private static EasyDialog getDialog() {
        return commonDialogWeakReference == null ? null : commonDialogWeakReference.get();
    }

    public static void dismissEasyCommonDialog() {
        EasyDialog dialog = getDialog();
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

        //标题文字
        private String mTitle;
        //内容文字
        private String mContent;
        //右边按钮文字
        private String rightTextStr = "确认";
        //左边按钮文字
        private String leftTextStr = "取消";

        //弹窗背景颜色
        private int mDialogBgColor = Color.parseColor("#ffffff");
        //弹窗圆角
        private float mDialogRadius = 8f;

        //点击外部是否消失
        private boolean mOutsideCancelable = false;
        //分割线的宽度
        private float mLineHeight = 0.5f;
        //按钮高度
        private float mBtnHeight = 0;
        //弹窗宽度
        private float mDialogWidth = 0;
        //弹窗高度
        private float mDialogHeight = 0;
        //弹窗最小高度
        private float mDialogMinHeight = 0;

        //分割线颜色
        private int mLineColor = Color.parseColor("#E6E6E6");
        //标题文字颜色
        private int mTitleTextColor = Color.parseColor("#222222");
        //标题内容颜色
        private int mContentTextColor = Color.parseColor("#333333");
        //右边的文字颜色
        private int mRightTextColor = Color.parseColor("#333333");
        //左边的文字颜色
        private int mLeftTextColor = Color.parseColor("#A8A7AE");

        //标题文字大小
        private float mTitleTextSize = 17;
        //内容文字大小
        private float mContentTextSize = 16;
        //左边文字大小
        private float mLeftTextSize = 16;
        //右边文字大小
        private float mRightTextSize = 16;

        //标题字体是否粗体
        private boolean isTitleBold = true;
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
        //系统返回是否消失
        private boolean mBackDismiss = true;

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

        @NonNull
        public EasyDialog create() {
            EasyDialog dialog = getDialog();

            if (dialog != null && dialog.getContext() != mContext) {
                dismissEasyCommonDialog();
                dialog = null;
            }

            if (dialog == null) {
                dialog = new EasyDialog(mContext);
                commonDialogWeakReference = new WeakReference<>(dialog);
            }

            final EasyDialog finalDialog = dialog;


            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout_easy_common, null);

            ll_dialog = view.findViewById(R.id.ll_dialog);
            DialogUtil.setRoundRectBg(mContext, ll_dialog, mDialogRadius, mDialogBgColor);
            if (mDialogWidth > 0) {
                LinearLayout.LayoutParams dialogParams = (LinearLayout.LayoutParams) ll_dialog.getLayoutParams();
                dialogParams.width = DialogUtil.dip2px(mContext, mDialogWidth);
                ll_dialog.setLayoutParams(dialogParams);
            }
            if (mDialogMinHeight > 0) {
                ll_dialog.setMinimumHeight(DialogUtil.dip2px(mContext, mDialogMinHeight));
            }

            if (mDialogHeight > 0) {
                LinearLayout.LayoutParams dialogParams = (LinearLayout.LayoutParams) ll_dialog.getLayoutParams();
                dialogParams.height = DialogUtil.dip2px(mContext, mDialogHeight);
                ll_dialog.setLayoutParams(dialogParams);
            }

            if (mBtnHeight > 0) {
                LinearLayout ll_btn = view.findViewById(R.id.ll_btn);
                LinearLayout.LayoutParams btnLLParams = (LinearLayout.LayoutParams) ll_btn.getLayoutParams();
                btnLLParams.height = DialogUtil.dip2px(mContext, mBtnHeight);
                ll_btn.setLayoutParams(btnLLParams);
            }

            btn_dialog_right = view.findViewById(R.id.btn_dialog_right);
            btn_dialog_right.setText(rightTextStr);
            btn_dialog_right.setTextColor(mRightTextColor);
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
            btn_dialog_left.setText(leftTextStr);
            btn_dialog_left.setTextColor(mLeftTextColor);
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
                if (!TextUtils.isEmpty(mTitle)) {
                    tv_title.setVisibility(View.VISIBLE);
                    tv_title.setTextSize(mTextSizeType, mTitleTextSize);
                    TextPaint titlePaint = tv_title.getPaint();
                    titlePaint.setFakeBoldText(isTitleBold);
                    tv_title.setTextColor(mTitleTextColor);
                    tv_title.setText(mTitle);
                } else {
                    tv_title.setVisibility(View.GONE);
                }
            }

            tv_content = view.findViewById(R.id.tv_content);
            if (tv_content != null) {
                if (!TextUtils.isEmpty(mContent)) {
                    tv_content.setText(mContent);
                    tv_content.setVisibility(View.VISIBLE);
                    tv_content.setTextSize(mTextSizeType, mContentTextSize);
                    TextPaint contentPaint = tv_content.getPaint();
                    contentPaint.setFakeBoldText(isContentBold);
                    tv_content.setTextColor(mContentTextColor);
                } else {
                    tv_content.setVisibility(View.INVISIBLE);
                }
            }


            view_line01 = view.findViewById(R.id.view_line01);
            view_line02 = view.findViewById(R.id.view_line02);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view_line01.getLayoutParams();
            params.height = DialogUtil.dip2px(mContext, mLineHeight);
            view_line01.setLayoutParams(params);
            view_line01.setBackgroundColor(mLineColor);

            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) view_line02.getLayoutParams();
            params2.width = DialogUtil.dip2px(mContext, mLineHeight);
            view_line02.setLayoutParams(params2);
            view_line02.setBackgroundColor(mLineColor);


            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog.setCanceledOnTouchOutside(mOutsideCancelable);
            dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(android.content.DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (!mBackDismiss) {
                           return true;
                        }
                    }
                    return false;
                }
            });

            return dialog;
        }


        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder btnHeight(float mBtnHeight) {
            this.mBtnHeight = mBtnHeight;
            return this;
        }

        public Builder lineColor(int mLineColor) {
            this.mLineColor = mLineColor;
            return this;
        }

        public Builder dialogMinHeight(float mDialogMinHeight) {
            this.mDialogMinHeight = mDialogMinHeight;
            return this;
        }

        public Builder dialogHeight(float mDialogHeight) {
            this.mDialogHeight = mDialogHeight;
            return this;
        }

        public Builder dialogWidth(float mDialogWidth) {
            this.mDialogWidth = mDialogWidth;
            return this;
        }

        public Builder rightTextStr(String rightTextStr) {
            this.rightTextStr = rightTextStr;
            return this;
        }

        public Builder leftTextStr(String leftTextStr) {
            this.leftTextStr = leftTextStr;
            return this;
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

        public Builder dialogRadius(float radius) {
            mDialogRadius = radius;
            return this;
        }

        public Builder dialogBgColor(int mBgColor) {
            this.mDialogBgColor = mBgColor;
            return this;
        }

        public Builder touchOutsideCancelable(boolean cancelable) {
            mOutsideCancelable = cancelable;
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

        public Builder backDismiss(boolean mBackDismiss) {
            this.mBackDismiss = mBackDismiss;
            return this;
        }
    }


}
