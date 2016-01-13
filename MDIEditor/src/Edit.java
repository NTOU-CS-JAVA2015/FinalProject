
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.text.DefaultEditorKit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuhang
 */
public class Edit {

    MDIEditor MDIEditor;

    Edit(JMenu mnEdit, MDIEditor MDIEditorin) {
        MDIEditor = MDIEditorin;
        MDIEditorin.acCut = getActionByName(DefaultEditorKit.cutAction);
        MDIEditorin.acCopy = getActionByName(DefaultEditorKit.copyAction);
        MDIEditorin.acPaste = getActionByName(DefaultEditorKit.pasteAction);
        //取得JTextPane元件提供執行剪下、複製、貼上動作的Action物件

        MDIEditorin.acCut.putValue(Action.NAME, "剪下(T)"); //設定Action物件使用的名稱
        MDIEditorin.acCopy.putValue(Action.NAME, "複製(C)");
        MDIEditorin.acPaste.putValue(Action.NAME, "貼上(P)");

        MDIEditorin.acCut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        MDIEditorin.acCopy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        MDIEditorin.acPaste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        //設定Action物件使用的記憶鍵

        MDIEditorin.acCut.setEnabled(false); //設定Action物件無效
        MDIEditorin.acCopy.setEnabled(false);

        mnEdit.add(MDIEditorin.acCut); //將Action物件加入功能表做為選項
        mnEdit.add(MDIEditorin.acCopy);
        mnEdit.add(MDIEditorin.acPaste);
    }

    //運用Action物件的名稱, 取得文字編輯面版提供的Action物件
    private Action getActionByName(String name) {

        Action[] actionsArray = MDIEditor.internalFrame.tifCurrent.getTextPane().getActions();
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
