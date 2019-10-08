package com.fly.viewlibrary.SectorMenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.shangyi.android.core.view.SectorMenu
 * 作    者 : FLY
 * 创建时间 : 2018/11/15
 * 描述: 按键数据
 */

public class ButtonData implements Cloneable {
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private boolean isMainButton = false;//main button is the button you see when buttons are all collapsed

    private String[] texts;//String array that you want to show at button center,texts[i] will be shown at the ith row
    private Drawable icon;//icon drawable that will be shown at button center
    private float iconPaddingDp;//the padding of the icon drawable in button
    private int backgroundColor = DEFAULT_BACKGROUND_COLOR;//the background color of the button

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ButtonData buttonData = (ButtonData)super.clone();
        buttonData.setBackgroundColor(this.backgroundColor);
        buttonData.setIsMainButton(this.isMainButton);
        buttonData.setIcon(this.icon);
        buttonData.setIconPaddingDp(this.iconPaddingDp);
        buttonData.setTexts(this.texts);
        return buttonData;
    }

    public static ButtonData buildTextButton(String... text) {
        ButtonData buttonData = new ButtonData();
        buttonData.setText(text);
        return buttonData;
    }

    public static ButtonData buildIconButton(Context context, int iconResId, float iconPaddingDp) {
        ButtonData buttonData = new ButtonData();
        buttonData.iconPaddingDp = iconPaddingDp;
        buttonData.setIconResId(context, iconResId);
        return buttonData;
    }

    private ButtonData() {}

    public void setIsMainButton(boolean isMainButton) {
        this.isMainButton = isMainButton;
    }

    public boolean isMainButton() {
        return isMainButton;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    public void setText(String... text) {
        this.texts = new String[text.length];
        for (int i = 0, length = text.length; i < length; i++) {
            this.texts[i] = text[i];
        }
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIconResId(Context context, int iconResId) {
        this.icon = context.getResources().getDrawable(iconResId);
    }

    public float getIconPaddingDp() {
        return iconPaddingDp;
    }

    public void setIconPaddingDp(float padding) {
        this.iconPaddingDp = padding;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundColorId(Context context, int backgroundColorId) {
        this.backgroundColor = context.getResources().getColor(backgroundColorId);
    }
}
