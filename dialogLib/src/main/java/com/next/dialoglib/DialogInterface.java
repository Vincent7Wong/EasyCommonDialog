package com.next.dialoglib;

interface DialogInterface {

    interface OnRightListener {
        boolean onRightEvent(EasyDialog dialog);
    }

    interface OnLeftListener{
        boolean onLeftEvent(EasyDialog dialog);
    }
}
