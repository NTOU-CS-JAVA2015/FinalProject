
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.text.DefaultEditorKit;

public class Edit {

    InternalEditor internalEditor;

    Edit(JMenu mnEdit, InternalEditor internalFramein) {
        internalEditor = internalFramein;
        internalEditor.acCut = getActionByName(DefaultEditorKit.cutAction);
        internalEditor.acCopy = getActionByName(DefaultEditorKit.copyAction);
        internalEditor.acPaste = getActionByName(DefaultEditorKit.pasteAction);
        //取得JTextPane元件提供執行剪下、複製、貼上動作的Action物件

        internalEditor.acCut.putValue(Action.NAME, "剪下(T)"); //設定Action物件使用的名稱
        internalEditor.acCopy.putValue(Action.NAME, "複製(C)");
        internalEditor.acPaste.putValue(Action.NAME, "貼上(P)");

        internalEditor.acCut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        internalEditor.acCopy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        internalEditor.acPaste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        //設定Action物件使用的記憶鍵

        internalEditor.acCut.setEnabled(false); //設定Action物件無效
        internalEditor.acCopy.setEnabled(false);

        mnEdit.add(internalEditor.acCut); //將Action物件加入功能表做為選項
        mnEdit.add(internalEditor.acCopy);
        mnEdit.add(internalEditor.acPaste);
    }

    //運用Action物件的名稱, 取得文字編輯面版提供的Action物件
    private Action getActionByName(String name) {

        Action[] actionsArray = internalEditor.tifCurrent.getTextPane().getActions();
        //取得文字編輯面版提供的Action物件

        for (Action elm : actionsArray) {
            //運用比對名稱的方式, 取得Action物件的
            if (elm.getValue(Action.NAME).equals(name)) {
                return elm;
            }
        }
        return null;
    }
}
