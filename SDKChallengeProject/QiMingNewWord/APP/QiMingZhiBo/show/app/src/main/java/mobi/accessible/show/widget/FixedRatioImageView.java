package mobi.accessible.show.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class FixedRatioImageView extends AppCompatImageView {

    private static final float ITEM_BG_RATIO = 141 / (float) 160;

    public FixedRatioImageView(Context context) {
        super(context);
    }

    public FixedRatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedRatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMeasuredWidth();
        int height = (int) (width * ITEM_BG_RATIO);
        setMeasuredDimension(widthMeasureSpec, height);
    }

}
