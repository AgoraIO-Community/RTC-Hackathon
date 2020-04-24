package mobi.accessible.show.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;


/**
 * @aruth wlk
 * Created by 98283 on 2019/6/14.
 */
public class ExitDialogUtils {

    private static AlertDialog dialog = null;
     public interface OnPositivieClickListener{
         /**
          * @param name
          */
        void onCLick(String name);
     }

    /**
     * 带编辑框的弹框
     * @param context
     * @param listener
     */
    public static void showEditViewDialog(Context context, final OnPositivieClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText editText = new EditText(context);
        editText.setMinimumHeight(45);
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = editText.getText().toString();
                if(!TextUtils.isEmpty(name)){
                    if(listener != null){
                        listener.onCLick(name);
                    }
                }
                hideInput(context,editText);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setTitle("请输入昵称");
        dialog = builder.show();
    }
    /**
     * 强制隐藏输入法键盘
     *
     * @param context Context
     * @param view    EditText
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
