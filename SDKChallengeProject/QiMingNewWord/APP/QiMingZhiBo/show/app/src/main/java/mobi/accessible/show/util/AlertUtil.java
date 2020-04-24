package mobi.accessible.show.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import mobi.accessible.show.ChatRoomApplication;
import mobi.accessible.show.R;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;

public class AlertUtil {

    public static void showToast(String format, Object... args) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(ChatRoomApplication.instance, String.format(format, args), Toast.LENGTH_SHORT).show());
    }

    public static void showPop(Context context, View view, int[] ids, OptionMenuView.OnOptionMenuClickListener clickListener, PopupWindow.OnDismissListener dismissListener) {
        PopupMenuView popupMenuView = new PopupMenuView(context);
        List<OptionMenu> menus = new ArrayList<>();
        for (int id : ids) {
            int strId = 0;
            switch (id) {
                case R.id.to_audience:
                    strId = R.string.to_audience;
                    break;
                case R.id.to_broadcast:
                    strId = R.string.to_broadcast;
                    break;
                case R.id.turn_off_mic:
                    strId = R.string.turn_off_mic;
                    break;
                case R.id.turn_on_mic:
                    strId = R.string.turn_on_mic;
                    break;
                case R.id.close_seat:
                    strId = R.string.close_seat;
                    break;
                case R.id.open_seat:
                    strId = R.string.open_seat;
                    break;
            }
            menus.add(new OptionMenu(id, strId));
        }
        popupMenuView.setMenuItems(menus);
        popupMenuView.setSites(PopupView.SITE_BOTTOM, PopupView.SITE_LEFT, PopupView.SITE_TOP, PopupView.SITE_RIGHT);
        popupMenuView.setOnMenuClickListener(clickListener);
        popupMenuView.setOnDismissListener(dismissListener);
        popupMenuView.show(view);
    }

    public static void showAlertDialog(Context context, String text, String action, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(text);
        builder.setPositiveButton(action, listener);
        builder.create().show();
    }

}
