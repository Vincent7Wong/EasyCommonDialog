package com.next.dialoglib;

interface DialogInterface {

    interface OnRightListener {
        boolean onRightEvent(EasyCommonDialog dialog);
    }

    interface OnLeftListener{
        boolean onLeftEvent(EasyCommonDialog dialog);
    }
}
