
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuhang
 */
public class InternalFrame {

    TextInternalFrame tifCurrent;
    WindowMenu wmWindow;
    MDIEditor MDIEditor;
    JCheckBoxMenuItem cbmiSize16, cbmiSize18, cbmiSize20;//控制字級大小的核取方塊選項
    JToggleButton tbnSize16, tbnSize18, tbnSize20;
    Action acCut, acCopy, acPaste; //執行編輯動作的Action物件
    
    //建立文字編輯內部框架
    InternalFrame(MDIEditor MDIEditorin, WindowMenu wmWindowin) {
        MDIEditor = MDIEditorin;
        wmWindow = wmWindowin;
    }

    public void createInternalFrame(String... strArgs) {

        //依照是否傳入參數決定呼叫的TextInternalFrame類別建構子
        if (strArgs.length == 0) {
            tifCurrent = new TextInternalFrame();
        } else {
            tifCurrent = new TextInternalFrame(strArgs[0], strArgs[1]);
        }

        tifCurrent.addCaretListener(cl);
        //註冊回應游標CaretEvent事件的監聽器

        tifCurrent.addInternalFrameListener(ifl);
        //註冊回應InternalFrameEvent事件的監聽器

        JCheckBoxMenuItem cbmiWindow = tifCurrent.getMenuItem();
        //取得代表完成建立之TextInternalFrame物件的核取方塊選項

        wmWindow.add(cbmiWindow, tifCurrent);
        //將核取方塊選項與對應的TextInternalFrame物件新增至視窗功能表

        MDIEditor.dpPane.add(tifCurrent);
        //將完成建立的TextInternalFrame物件加入虛擬桌面

        int FrameCount = MDIEditor.dpPane.getAllFrames().length;
        //取得虛擬桌面內TextInternalFrame物件的個數

        tifCurrent.setLocation(20 * (FrameCount - 1), 20 * (FrameCount - 1));
        //設定TextInternalFrame物件所顯示文字編輯視窗框架左上角在虛擬桌面的座標

        try {
            tifCurrent.setSelected(true);
            //設定選取完成建立的TextInternalFrame物件
        } catch (java.beans.PropertyVetoException pve) {
            System.out.println(pve.toString());
        }
    }

    //定義並宣告回應InternalFrame事件的監聽器
    InternalFrameAdapter ifl = new InternalFrameAdapter() {

        //當內部框架取得游標焦點觸發事件將由此方法回應
        @Override
        public void internalFrameActivated(InternalFrameEvent e) {

            tifCurrent = (TextInternalFrame) e.getInternalFrame();
            //取得觸發InternalFrame事件的TextInternalFrame物件

            tifCurrent.getMenuItem().setSelected(true);
            //設定視窗功能表內代表此TextInternalFrame物件的核取方塊選項為選取

            //取得TextInternalFrame物件顯示內容使用的字級大小
            switch (tifCurrent.geteditor()) {
                case 16:
                    cbmiSize16.setSelected(true); //設定對應的控制項為選取
                    tbnSize16.setSelected(true);
                    break;
                case 18:
                    cbmiSize18.setSelected(true);
                    tbnSize18.setSelected(true);
                    break;
                case 20:
                    cbmiSize20.setSelected(true);
                    tbnSize20.setSelected(true);
                    break;
            }

        }

        //當內部框架正在關閉時所觸發事件將由此方法回應
        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            wmWindow.remove(tifCurrent.getMenuItem());
            //移除視窗功能表內代表目前執行編輯之TextInternalFrame物件的選項
        }
    };

    //定義並宣告回應CaretEvent事件的監聽器
    CaretListener cl = new CaretListener() {

        //移動游標位置時, 將由此方法回應
        @Override
        public void caretUpdate(CaretEvent e) {

            if (e.getDot() != e.getMark()) {
                MDIEditor.lbStatus.setText("目前位置 : 第 " + e.getDot()
                        + " 個字元" + ", 選取範圍 : " + e.getDot() + "至" + e.getMark());
                //設定狀態列內的文字

                acCut.setEnabled(true);
                acCopy.setEnabled(true);
                //設定執行剪下與複製動字的Action元件為有效
            } else {
                MDIEditor.lbStatus.setText("目前位置 : 第 " + e.getDot() + " 個字元");
                //設定狀態列內的文字

                acCut.setEnabled(false);
                acCopy.setEnabled(false);
                //設定執行剪下與複製動字的Action元件為無效
            }
        }
    };
}
