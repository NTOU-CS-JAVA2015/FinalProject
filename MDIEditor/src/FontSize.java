
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JToggleButton;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuhang
 */
public class FontSize {

    MDIEditor MDIEditor;

    FontSize(JMenu mneditor, MDIEditor MDIEditorin) {

        MDIEditor = MDIEditorin;
        java.net.URL imgSize16URL = MDIEditor.class.getResource("/icon/size16.png");
        java.net.URL imgSize18URL = MDIEditor.class.getResource("/icon/size18.png");
        java.net.URL imgSize20URL = MDIEditor.class.getResource("/icon/size20.png");
        FontSize.EditorAction fsaSize16 = new FontSize.EditorAction("16(S)", new ImageIcon(imgSize16URL), "設定字體大小為16", KeyEvent.VK_S);
        FontSize.EditorAction fsaSize18 = new FontSize.EditorAction("18(M)", new ImageIcon(imgSize18URL), "設定字體大小為18", KeyEvent.VK_M);
        FontSize.EditorAction fsaSize20 = new FontSize.EditorAction("20(L)", new ImageIcon(imgSize20URL), "設定字體大小為20", KeyEvent.VK_L);
        //宣告執行字級大小設定動作的Action物件

        MDIEditorin.cbmiSize16 = new JCheckBoxMenuItem(fsaSize16);
        MDIEditorin.cbmiSize18 = new JCheckBoxMenuItem(fsaSize18);
        MDIEditorin.cbmiSize20 = new JCheckBoxMenuItem(fsaSize20);
        //以執行字級大小設定之Action物件建立核取方塊選項

        MDIEditorin.cbmiSize16.setIcon(null); //設定核取方塊選項不使用圖示
        MDIEditorin.cbmiSize18.setIcon(null);
        MDIEditorin.cbmiSize20.setIcon(null);

        MDIEditorin.cbmiSize16.setState(true); //設定選取代表16字級的核取方塊選項

        mneditor.add(MDIEditorin.cbmiSize16); //將核取方塊選項加入功能表
        mneditor.add(MDIEditorin.cbmiSize18);
        mneditor.add(MDIEditorin.cbmiSize20);

        ButtonGroup bgSize = new ButtonGroup(); //宣告按鈕群組
        bgSize.add(MDIEditorin.cbmiSize16); //將核取方塊選項加入按鈕群組
        bgSize.add(MDIEditorin.cbmiSize18);
        bgSize.add(MDIEditorin.cbmiSize20);

        MDIEditorin.tbnSize16 = new JToggleButton(fsaSize16);
        MDIEditorin.tbnSize18 = new JToggleButton(fsaSize18);
        MDIEditorin.tbnSize20 = new JToggleButton(fsaSize20);
        //以執行字級大小設定的Action物件, 宣告工具列的JToggleButton按鈕

        MDIEditorin.tbnSize16.setActionCommand("16(S)");
        MDIEditorin.tbnSize18.setActionCommand("18(M)");
        MDIEditorin.tbnSize20.setActionCommand("20(L)");
        //因為按鈕不顯示字串,故必須設定動作命令字串, 以便於回應事件時判別

        MDIEditorin.tbnSize16.setText(null); //設定JToggleButton按鈕不顯示字串
        MDIEditorin.tbnSize18.setText(null);
        MDIEditorin.tbnSize20.setText(null);

        MDIEditorin.tbnSize16.setSelected(true);//設定選取代表16字級的JToggleButton按鈕

        ButtonGroup bgToolBar = new ButtonGroup(); //宣告按鈕群組
        bgToolBar.add(MDIEditorin.tbnSize16); //將JToggleButton按鈕加入按鈕群組
        bgToolBar.add(MDIEditorin.tbnSize18);
        bgToolBar.add(MDIEditorin.tbnSize20);
    }

    //定義執行文字字級設定的Action物件
    class EditorAction extends AbstractAction {

        public EditorAction(String text, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(text, icon); //呼叫基礎類別建構子
            putValue(SHORT_DESCRIPTION, desc); //設定提示字串
            putValue(MNEMONIC_KEY, mnemonic); //設定記憶鍵
        }

        @Override
        public void actionPerformed(ActionEvent e) { //回應事件的執行動作
            //依照動作命令字串判別欲執行的動作
            switch (e.getActionCommand()) {
                case "20(L)":
                    MDIEditor.internalFrame.tifCurrent.seteditor(20);
                    //設定文字編輯面版使用20級字
                    MDIEditor.cbmiSize20.setSelected(true); //設定對應的控制項為選取
                    MDIEditor.tbnSize20.setSelected(true);
                    break;
                case "18(M)":
                    MDIEditor.internalFrame.tifCurrent.seteditor(18);
                    MDIEditor.cbmiSize18.setSelected(true);
                    MDIEditor.tbnSize18.setSelected(true);
                    break;
                default:
                    //預設16級字
                    MDIEditor.internalFrame.tifCurrent.seteditor(16);
                    MDIEditor.cbmiSize16.setSelected(true);
                    MDIEditor.tbnSize16.setSelected(true);
                    break;
            }
        }
    }
}