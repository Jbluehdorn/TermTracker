package com.jbluehdorn.termtracker.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.jbluehdorn.termtracker.util.Masker;

public class PhoneText extends AppCompatEditText {
    public PhoneText(Context context) {
        super(context);

        doAdditionalConstructorWork();
    }

    public PhoneText(Context context, AttributeSet attrs) {
        super(context, attrs);

        doAdditionalConstructorWork();
    }

    public PhoneText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        doAdditionalConstructorWork();
    }

    public void doAdditionalConstructorWork() {
        this.enableMask();
        this.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public void enableMask() {
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

                String numsOnly = editable.toString().replaceAll("[^\\d]", "");

                edited = true;

                editable.replace(0, editable.length(), Masker.maskPhone(numsOnly));
            }
        });
    }
}
