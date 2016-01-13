
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

    InternalFrame internalFrame;

    Edit(JMenu mnEdit, InternalFrame internalFramein) {
        internalFrame = internalFramein;
        internalFrame.acCut = getActionByName(DefaultEditorKit.cutAction);
        internalFrame.acCopy = getActionByName(DefaultEditorKit.copyAction);
        internalFrame.acPaste = getActionByName(DefaultEditorKit.pasteAction);
        //取得JTextPane元件提供執行剪下、複製、貼上動作的Action物件

        internalFrame.acCut.putValue(Action.NAME, "剪下(T)"); //設定Action物件使用的名稱
        internalFrame.acCopy.putValue(Action.NAME, "複製(C)");
        internalFrame.acPaste.putValue(Action.NAME, "貼上(P)");

        internalFrame.acCut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        internalFrame.acCopy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        internalFrame.acPaste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        //設定Action物件使用的記憶鍵

        internalFrame.acCut.setEnabled(false); //設定Action物件無效
        internalFrame.acCopy.setEnabled(false);

        mnEdit.add(internalFrame.acCut); //將Action物件加入功能表做為選項
        mnEdit.add(internalFrame.acCopy);
        mnEdit.add(internalFrame.acPaste);
    }

    //運用Action物件的名稱, 取得文字編輯面版提供的Action物件
    private Action getActionByName(String name) {

        Action[] actionsArray = internalFrame.tifCurrent.getTextPane().getActions();
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
