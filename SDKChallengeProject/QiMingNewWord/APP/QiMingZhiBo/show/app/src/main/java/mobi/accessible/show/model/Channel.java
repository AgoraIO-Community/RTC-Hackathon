package mobi.accessible.show.model;

import androidx.annotation.DrawableRes;

public class Channel {

    private int drawableRes;
    private int backgroundRes;
    private String name;

    public Channel(@DrawableRes int drawableRes, @DrawableRes int backgroundRes, String name) {
        this.drawableRes = drawableRes;
        this.backgroundRes = backgroundRes;
        this.name = name;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(@DrawableRes int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public int getBackgroundRes() {
        return backgroundRes;
    }

    public void setBackgroundRes(@DrawableRes int backgroundRes) {
        this.backgroundRes = backgroundRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
