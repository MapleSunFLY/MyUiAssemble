package com.fly.viewlibrary.sectormenu;

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
 * 描述: 点击回调
 */
public interface ButtonEventListener {

    void onButtonClicked(int index);

    void onExpand();

    void onCollapse();
}
