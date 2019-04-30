package com.jbluehdorn.termtracker.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.jbluehdorn.termtracker.util.Masker;

public class DateText extends AppCompatEditText {
    public DateText(Context context) {
        super(context);

        doAdditionalConstructorWork();
    }

    public DateText(Context context, AttributeSet attrs) {
        super(context, attrs);

        doAdditionalConstructorWork();
    }

    public DateText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        doAdditionalConstructorWork();
    }

    private void doAdditionalConstructorWork() {
        this.enableMask();
        this.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    private void enableMask() {
        this.addTextChangedListener(new TextWatcher() {
            private boolean edited = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edited) {
                    edited = false;
                    return;
                }

                String numsOnly = editable.toString().replaceAll("/", "");

                edited = true;

                editable.replace(0, editable.length(), Masker.maskDate(numsOnly));
            }
        });
    }
}
